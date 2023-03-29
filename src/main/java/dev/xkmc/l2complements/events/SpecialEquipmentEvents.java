package dev.xkmc.l2complements.events;

import dev.xkmc.l2complements.content.item.generic.GenericArmorItem;
import dev.xkmc.l2complements.content.item.generic.GenericTieredItem;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;

public class SpecialEquipmentEvents {

	public static ThreadLocal<ServerPlayer> PLAYER = new ThreadLocal<>();

	public static boolean isVisible(LivingEntity entity, ItemStack stack) {
		if (entity.hasEffect(MobEffects.INVISIBILITY)) {
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

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
		ServerPlayer player = PLAYER.get();
		if (player == null) return;
		if (!(event.getEntity() instanceof ItemEntity e)) return;
		if (player.getMainHandItem().getEnchantmentLevel(LCEnchantments.SMELT.get()) > 0) {
			ItemStack input = e.getItem().copy();
			SimpleContainer cont = new SimpleContainer(input);
			var opt = event.getLevel().getRecipeManager()
					.getRecipeFor(RecipeType.SMELTING, cont, event.getLevel());
			if (opt.isPresent()) {
				ItemStack ans = opt.get().assemble(cont);
				int count = ans.getCount() * input.getCount();
				ans.setCount(count);
				e.setItem(ans);
			}
			opt = Optional.empty();
			opt.ifPresent(r -> ((ItemEntity) event.getEntity()).setItem(r.assemble(cont)));
		}
		if (player.getMainHandItem().getEnchantmentLevel(LCEnchantments.ENDER.get()) > 0) {
			ItemStack stack = e.getItem().copy();
			if (!player.getInventory().add(stack)) {
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

}
