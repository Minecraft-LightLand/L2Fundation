package dev.xkmc.l2complements.events;

import dev.xkmc.l2complements.compat.CurioCompat;
import dev.xkmc.l2complements.content.effect.skill.CleanseEffect;
import dev.xkmc.l2complements.content.effect.skill.SkillEffect;
import dev.xkmc.l2complements.content.enchantment.digging.DiggerHelper;
import dev.xkmc.l2complements.content.enchantment.special.SoulBoundPlayerData;
import dev.xkmc.l2complements.content.feature.EntityFeature;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LCTagGen;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2core.base.effects.ForceAddEffectEvent;
import dev.xkmc.l2core.events.SchedulerHandler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = L2Complements.MODID, bus = EventBusSubscriber.Bus.GAME)
public class MagicEventHandler {

	@SubscribeEvent
	public static void onLivingAttack(LivingIncomingDamageEvent event) {
		if (EntityFeature.OWNER_PROTECTION.test(event.getEntity())) {
			if (event.getSource().getEntity() instanceof OwnableEntity ownable && ownable.getOwner() == event.getEntity()) {
				event.setCanceled(true);
			}
		}
		if (!LCConfig.COMMON.enableImmunityEnchantments.get()) {
			return;
		}
		if (EntityFeature.INVINCIBLE.test(event.getEntity())) {
			event.setCanceled(true);
		}
		if (event.getSource().is(DamageTypeTags.BYPASSES_EFFECTS) || event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY))
			return;
		if (EntityFeature.ENVIRONMENTAL_REJECT.test(event.getEntity())) {
			if (event.getSource().getEntity() == null) event.setCanceled(true);
		}
		if (EntityFeature.MAGIC_REJECT.test(event.getEntity())) {
			if (event.getSource().is(Tags.DamageTypes.IS_MAGIC)) event.setCanceled(true);
		}
		if (event.getSource().is(DamageTypeTags.BYPASSES_ENCHANTMENTS))
			return;
		if (EntityFeature.PROJECTILE_REJECT.test(event.getEntity())) {
			if (event.getSource().is(DamageTypeTags.IS_PROJECTILE)) event.setCanceled(true);
		}
		if (EntityFeature.FIRE_REJECT.test(event.getEntity())) {
			if (event.getSource().is(DamageTypeTags.IS_FIRE)) event.setCanceled(true);
		}
		if (EntityFeature.EXPLOSION_REJECT.test(event.getEntity())) {
			if (event.getSource().is(DamageTypeTags.IS_EXPLOSION)) event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onLivingTick(EntityTickEvent event) {
		var e = event.getEntity();
		if (e instanceof LivingEntity le && le.isOnFire()) {
			if (EntityFeature.FIRE_REJECT.test(le)) {
				event.getEntity().clearFire();
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onInventoryDrop(LivingDropsEvent event) {
		if (!(event.getEntity() instanceof ServerPlayer player))
			return;
		event.getDrops().removeIf(e -> e.getItem().getEnchantmentLevel(LCEnchantments.SOUL_BOUND.holder()) > 0 &&
				SoulBoundPlayerData.addToPlayer(player, e.getItem()));
	}

	@SubscribeEvent
	public static void onHeal(LivingHealEvent event) {
		if (event.getEntity().hasEffect(LCEffects.CURSE.holder())) {
			event.setCanceled(true);
			return;
		}
		float amount = event.getAmount();
		for (ItemStack stack : CurioCompat.getAllSlots(event.getEntity())) {
			if (!stack.isEnchanted() || !stack.isDamaged()) continue;
			int lv = stack.getEnchantmentLevel(LCEnchantments.LIFE_MENDING.holder());
			if (lv > 0) {
				int damage = stack.getDamageValue();
				int repair = 1 << (lv - 1);
				int armor = stack.getEnchantmentLevel(LCEnchantments.DURABLE_ARMOR.holder());
				if (armor > 0) {
					repair *= 1 + armor;
				}
				int recover = Math.min(damage, (int) Math.floor(amount * repair));
				stack.setDamageValue(damage - recover);
				amount -= 1f * recover / repair;
				if (amount < 1e-3) break;
			}
		}
		event.setAmount(amount);
	}

	@SubscribeEvent
	public static void onTeleport(EntityTeleportEvent event) {
		if (event.getEntity() instanceof LivingEntity le) {
			if (le.hasEffect(LCEffects.INCARCERATE.holder())) {
				event.setCanceled(true);
			}
		}
	}

	public static boolean isSkill(MobEffectInstance ins, LivingEntity entity) {
		if (ins.getEffect() instanceof SkillEffect)
			return true;
		int pred = LCConfig.COMMON.cleansePredicate.get();
		if (ins.getEffect().value().isBeneficial() && pred > 0) return true;
		if (ins.getEffect().value().getCategory() == MobEffectCategory.NEUTRAL && pred > 1) return true;
		var tag = BuiltInRegistries.MOB_EFFECT.getTag(LCTagGen.SKILL_EFFECT);
		if (tag.isPresent() && tag.get().contains(ins.getEffect())) {
			return true;
		}
		return CurioCompat.testEffect(ins, entity);
	}

	@SubscribeEvent
	public static void onPotionTest(MobEffectEvent.Applicable event) {
		if (event.getEntity().hasEffect(LCEffects.CLEANSE.holder())) {
			if (isSkill(event.getEffectInstance(), event.getEntity())) return;
			event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
		}
	}

	@SubscribeEvent
	public static void onForceAdd(ForceAddEffectEvent event) {
		if (event.getEntity().hasEffect(LCEffects.CLEANSE.holder())) {
			if (isSkill(event.getEffectInstance(), event.getEntity())) return;
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onPotionAdded(MobEffectEvent.Added event) {
		if (event.getEntity().hasEffect(LCEffects.CLEANSE.holder())) {
			var ins = event.getEffectInstance();
			if (ins == null || isSkill(ins, event.getEntity())) return;
			SchedulerHandler.schedule(() -> CleanseEffect.clearOnEntity(event.getEntity()));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		if (!(event.getPlayer() instanceof ServerPlayer player)) return;
		ItemStack stack = player.getMainHandItem();
		var ent = DiggerHelper.getDigger(stack);
		if (ent == null) return;
		ent.digger().onBlockBreak(player, event.getPos(), stack, Math.min(ent.ench().value().getMaxLevel(), ent.level()));
	}

}
