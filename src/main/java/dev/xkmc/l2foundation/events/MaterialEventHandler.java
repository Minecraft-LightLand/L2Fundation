package dev.xkmc.l2foundation.events;

import dev.xkmc.l2foundation.init.data.LFConfig;
import dev.xkmc.l2foundation.init.registrate.LFEffects;
import dev.xkmc.l2foundation.init.registrate.LFItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MaterialEventHandler {

	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof EnderMan ender) {
			if (!ender.getLevel().isClientSide() && event.getSource().getEntity() instanceof Player player) {
				if (ender.isCreepy() && ender.getOnPos().getY() <= ender.getLevel().getMinBuildHeight() - LFConfig.COMMON.belowVoid.get()) {
					ender.spawnAtLocation(LFItems.VOID_EYE.asStack());
				}
			}
		}
		if (event.getEntity() instanceof Phantom phantom) {
			Level level = phantom.getLevel();
			if (!level.isClientSide()) {
				if (event.getSource().isProjectile()) {
					if (phantom.getOnPos().getY() >= level.getMaxBuildHeight() + LFConfig.COMMON.phantomHeight.get()) {
						if (level.isDay() && level.canSeeSky(phantom.getOnPos()) && phantom.isOnFire()) {
							phantom.spawnAtLocation(LFItems.SUN_MEMBRANE.asStack());
						}
					}
				}
				if (event.getSource().isExplosion()) {
					phantom.spawnAtLocation(LFItems.STORM_CORE.asStack());
				}
			}
		}
		if (event.getEntity() instanceof Drowned drowned) {
			Level level = drowned.getLevel();
			if (!level.isClientSide() && event.getSource() == DamageSource.FREEZE) {
				drowned.spawnAtLocation(LFItems.HARD_ICE.asStack());
			}
		}
		if (event.getEntity() instanceof PiglinBrute brute) {
			if (!brute.getLevel().isClientSide() && event.getSource().isProjectile() && brute.hasEffect(LFEffects.STONE_CAGE.get())) {
				brute.spawnAtLocation(LFItems.BLACKSTONE_CORE.asStack());
			}
		}
		if (event.getEntity() instanceof WitherBoss wither) {
			if (!wither.getLevel().isClientSide() && event.getSource().isProjectile()) {
				wither.spawnAtLocation(LFItems.FORCE_FIELD.asStack());
			}
		}
		if (event.getEntity() instanceof Warden wither) {
			if (!wither.getLevel().isClientSide() && event.getSource().getEntity() instanceof Player) {
				wither.spawnAtLocation(LFItems.WARDEN_BONE_SHARD.asStack());
			}
		}
		if (event.getEntity() instanceof Ghast ghast) {
			Level level = ghast.getLevel();
			DamageSource source = event.getSource();
			if (!level.isClientSide() && source.getMsgId().equals("soul_flame")) {
				ghast.spawnAtLocation(LFItems.SOUL_FLAME.asStack());
			}
		}
	}

	@SubscribeEvent
	public static void onInteract(PlayerInteractEvent.EntityInteract event) {
		if (event.getLevel().isClientSide()) return;
		if (event.getItemStack().is(LFItems.WIND_BOTTLE.get()) && event.getTarget() instanceof ShulkerBullet bullet) {
			bullet.hurt(DamageSource.playerAttack(event.getEntity()), 1);
			event.getItemStack().shrink(1);
			event.getEntity().getInventory().placeItemBackInInventory(LFItems.CAPTURED_BULLET.asStack());
		}
	}

}
