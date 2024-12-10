package dev.xkmc.l2complements.events;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.datafixers.util.Pair;
import dev.xkmc.l2complements.content.feature.EntityFeature;
import dev.xkmc.l2complements.events.event.EnderPickupEvent;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2damagetracker.contents.materials.generic.GenericArmorItem;
import dev.xkmc.l2damagetracker.contents.materials.generic.GenericTieredItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.Stack;

@Mod.EventBusSubscriber(modid = L2Complements.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpecialEquipmentEvents {

	public static ThreadLocal<Stack<Pair<ServerPlayer, BlockState>>> PLAYER = ThreadLocal.withInitial(Stack::new);

	public static boolean isVisible(LivingEntity entity, ItemStack stack) {
		if (entity.isInvisible()) {
			if (stack.getItem() instanceof GenericTieredItem item) {
				if (item.getExtraConfig().hideWithEffect())
					return false;
			}
			if (stack.getItem() instanceof GenericArmorItem item) {
				if (item.getConfig().hideWithEffect())
					return false;
			}
			return stack.getEnchantmentLevel(LCEnchantments.SHULKER_ARMOR.get()) == 0;
		}
		return true;
	}

	public static int blockSound(ItemStack stack) {
		if (stack.getItem() instanceof GenericArmorItem item) {
			if (item.getConfig().dampenVibration())
				return 1;
		}
		return stack.getEnchantmentLevel(LCEnchantments.DAMPENED.get());
	}

	private static ItemStack process(Level level, ItemStack stack) {
		ItemStack input = stack.copy();
		SimpleContainer cont = new SimpleContainer(input);
		var opt = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, cont, level);
		if (opt.isPresent()) {
			ItemStack ans = opt.get().assemble(cont, level.registryAccess());
			int count = ans.getCount() * input.getCount();
			ans.setCount(count);
			return ans;
		}
		return stack;
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onEntityDrop(LivingDropsEvent event) {
		if (event.getSource().getEntity() instanceof LivingEntity player) {
			if (player.getMainHandItem().getEnchantmentLevel(LCEnchantments.SMELT.get()) > 0) {
				for (var e : event.getDrops()) {
					ItemStack result = process(player.level(), e.getItem());
					e.setItem(result);
				}
			}
		}
		if (event.getSource().getEntity() instanceof ServerPlayer player) {
			if (player.getMainHandItem().getEnchantmentLevel(LCEnchantments.ENDER.get()) > 0) {
				for (var e : event.getDrops()) {
					EnderPickupEvent ender = new EnderPickupEvent(player, e.getItem().copy());
					MinecraftForge.EVENT_BUS.post(ender);
					ItemStack stack = ender.getStack();
					if (!stack.isEmpty() && !player.getInventory().add(stack)) {
						e.setItem(stack);
						e.teleportTo(player.getX(), player.getY(), player.getZ());
					} else {
						e.setItem(ItemStack.EMPTY);
					}
				}
				event.getDrops().removeIf(e -> e.getItem().isEmpty());
			}
		}
	}

	public static void dropExp(ServerLevel level, Vec3 pos, int exp, Operation<Void> original, @Nullable Player player) {
		if (player == null) return;
		if (player.getMainHandItem().getEnchantmentLevel(LCEnchantments.ENDER.get()) > 0) {
			pos = player.position();
		}
		original.call(level, pos, exp);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
		var players = PLAYER.get();
		if (players.isEmpty()) return;
		ServerPlayer player = players.peek().getFirst();
		if (event.getEntity() instanceof ItemEntity e) {
			if (player.getMainHandItem().getEnchantmentLevel(LCEnchantments.SMELT.get()) > 0) {
				ItemStack result = process(event.getLevel(), e.getItem());
				e.setItem(result);
			}
			if (player.getMainHandItem().getEnchantmentLevel(LCEnchantments.ENDER.get()) > 0) {
				EnderPickupEvent ender = new EnderPickupEvent(player, e.getItem().copy());
				MinecraftForge.EVENT_BUS.post(ender);
				ItemStack stack = ender.getStack();
				if (!stack.isEmpty() && !player.getInventory().add(stack)) {
					e.setItem(stack);
					e.teleportTo(player.getX(), player.getY(), player.getZ());
				} else {
					event.setCanceled(true);
					return;
				}
			}
			if (player.dampensVibrations()) {
				e.getPersistentData().putBoolean("dampensVibrations", true);
			}
		}
		if (event.getEntity() instanceof ExperienceOrb e) {
			if (player.getMainHandItem().getEnchantmentLevel(LCEnchantments.ENDER.get()) > 0) {
				player.takeXpDelay = 0;
				e.playerTouch(player);
				if (e.isRemoved()) {
					event.setCanceled(true);
					return;
				}
				e.teleportTo(player.getX(), player.getY(), player.getZ());
			}
			if (player.dampensVibrations()) {
				e.getPersistentData().putBoolean("dampensVibrations", true);
			}
		}
	}

	public static void pushPlayer(ServerPlayer player, BlockPos pos) {
		PLAYER.get().push(Pair.of(player, player.level().getBlockState(pos)));
	}


	public static void popPlayer(ServerPlayer player) {
		if (PLAYER.get().peek().getFirst() == player)
			PLAYER.get().pop();
	}

	public static boolean canWalkOn(FluidState state, LivingEntity self) {
		if (state.getType() == Fluids.LAVA) {
			double dy = self.getY();
			double vy = self.getDeltaMovement().y;
			if (vy > 0 && dy - Math.floor(dy) < 0.5) return false;
			return EntityFeature.LAVA_WALKER.test(self);
		}
		return false;
	}

	public static boolean canSee(Entity instance, Operation<Boolean> original) {
		boolean ans = original.call(instance);
		if (ans) return true;
		if (instance instanceof LivingEntity le) {
			if (le.isInLava()) {
				if (EntityFeature.FIRE_REJECT.test(le) || EntityFeature.ENVIRONMENTAL_REJECT.test(le) || EntityFeature.LAVA_WALKER.test(le)) {
					return true;
				}
			}
			if (le.isInPowderSnow) {
				if (PowderSnowBlock.canEntityWalkOnPowderSnow(instance) || EntityFeature.ENVIRONMENTAL_REJECT.test(le)) {
					return true;
				}
			}
		}
		return false;
	}

}
