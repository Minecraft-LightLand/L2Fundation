package dev.xkmc.l2complements.events;

import dev.xkmc.l2complements.content.item.misc.WarpStone;
import dev.xkmc.l2complements.content.recipe.BurntRecipe;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2complements.init.registrate.LCRecipes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.GrindstoneEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = L2Complements.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MaterialEventHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof EnderMan ender) {
			if (!ender.level().isClientSide() && event.getSource().getEntity() instanceof Player player) {
				if (ender.isCreepy() && ender.getOnPos().getY() <= ender.level().getMinBuildHeight() - LCConfig.COMMON.belowVoid.get()) {
					ender.spawnAtLocation(LCItems.VOID_EYE.asStack());
				}
			}
		}
		if (event.getEntity() instanceof Phantom phantom) {
			Level level = phantom.level();
			if (!level.isClientSide()) {
				if (phantom.getOnPos().getY() >= level.getMaxBuildHeight() + LCConfig.COMMON.phantomHeight.get()) {
					if (level.isDay() && level.canSeeSky(phantom.getOnPos()) && phantom.isOnFire()) {
						phantom.spawnAtLocation(LCItems.SUN_MEMBRANE.asStack());
					}
				}
				if (event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
					phantom.spawnAtLocation(LCItems.STORM_CORE.asStack());
				}
			}
		}
		if (event.getEntity() instanceof Drowned drowned) {
			Level level = drowned.level();
			if (!level.isClientSide() && event.getSource().is(DamageTypeTags.IS_FREEZING)) {
				drowned.spawnAtLocation(LCItems.HARD_ICE.asStack());
			}
		}
		if (event.getEntity() instanceof PiglinBrute brute) {
			if (!brute.level().isClientSide() && brute.hasEffect(LCEffects.STONE_CAGE.get())) {
				brute.spawnAtLocation(LCItems.BLACKSTONE_CORE.asStack());
			}
		}
		if (event.getEntity() instanceof ElderGuardian guardian) {
			if (!guardian.level().isClientSide() && event.getSource().is(DamageTypeTags.IS_LIGHTNING)) {
				guardian.spawnAtLocation(LCItems.GUARDIAN_EYE.asStack());
			}
		}
		if (event.getEntity() instanceof WitherBoss wither) {
			if (!wither.level().isClientSide() && event.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
				wither.spawnAtLocation(LCItems.FORCE_FIELD.asStack());
			}
		}
		if (event.getEntity() instanceof Warden warden) {
			if (!warden.level().isClientSide() && event.getSource().getEntity() instanceof Player) {
				warden.spawnAtLocation(LCItems.WARDEN_BONE_SHARD.asStack());
			}
		}
		if (event.getEntity() instanceof Ghast ghast) {
			Level level = ghast.level();
			if (!level.isClientSide() && ghast.hasEffect(LCEffects.FLAME.get())) {
				ghast.spawnAtLocation(LCItems.SOUL_FLAME.asStack());
			}
		}
	}

	@SubscribeEvent
	public static void onInteract(PlayerInteractEvent.EntityInteract event) {
		if (event.getLevel().isClientSide()) return;
		if (event.getItemStack().is(LCItems.WIND_BOTTLE.get()) && event.getTarget() instanceof ShulkerBullet bullet) {
			bullet.hurt(event.getLevel().damageSources().playerAttack(event.getEntity()), 1);
			event.getItemStack().shrink(1);
			event.getEntity().getInventory().placeItemBackInInventory(LCItems.CAPTURED_BULLET.asStack());
		}
	}

	@SubscribeEvent
	public static void onGrind(GrindstoneEvent.OnPlaceItem event) {
		if (event.getTopItem().getItem() instanceof WarpStone) {
			ItemStack copy = event.getTopItem().copy();
			if (WarpStone.getPos(copy).isPresent()) {
				copy.getOrCreateTag().remove("pos");
				event.setOutput(copy);
				event.setXp(0);
			}
		}
	}

	public static void onItemKill(Level level, Entity entity, ItemStack stack) {
		BurntRecipe.Inv inv = new BurntRecipe.Inv();
		inv.setItem(0, stack);
		var opt = level.getRecipeManager().getRecipeFor(LCRecipes.RT_BURNT.get(), inv, level);
		if (opt.isPresent()) {
			BurntRecipe r = opt.get();
			ItemStack result = r.assemble(inv, level.registryAccess());
			int chance = r.chance;
			int trial = stack.getCount();
			int det = trial / chance;
			trial = trial % chance;
			if (level.random.nextInt(chance) < trial) det++;
			det *= result.getCount();
			while (det > 0) {
				int sup = Math.min(det, result.getMaxStackSize());
				det -= sup;
				ItemStack copy = result.copy();
				copy.setCount(sup);
				level.addFreshEntity(new ItemEntity(level,
						entity.getX(), entity.getY(), entity.getZ(),
						copy, 0, 0.5, 0));
			}
		}


	}

}
