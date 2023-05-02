package dev.xkmc.l2complements.events;

import dev.xkmc.l2complements.content.effect.skill.CleanseEffect;
import dev.xkmc.l2complements.content.effect.skill.SkillEffect;
import dev.xkmc.l2complements.content.enchantment.core.AttributeEnchantment;
import dev.xkmc.l2complements.content.item.generic.GenericArmorItem;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2library.base.effects.EffectUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

public class MagicEventHandler {

	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event) {
		if (EnchantmentHelper.getEnchantmentLevel(LCEnchantments.ENCH_INVINCIBLE.get(), event.getEntity()) > 0) {
			event.setCanceled(true);
		}
		if (event.getSource().is(DamageTypeTags.BYPASSES_EFFECTS) || event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY))
			return;
		if (EnchantmentHelper.getEnchantmentLevel(LCEnchantments.ENCH_ENVIRONMENT.get(), event.getEntity()) > 0) {
			if (event.getSource().getEntity() == null) event.setCanceled(true);
		}
		if (EnchantmentHelper.getEnchantmentLevel(LCEnchantments.ENCH_MAGIC.get(), event.getEntity()) > 0) {
			if (event.getSource().is(DamageTypeTags.IS_MAGIC)) event.setCanceled(true);
		}
		if (event.getSource().is(DamageTypeTags.BYPASSES_ENCHANTMENTS))
			return;
		if (EnchantmentHelper.getEnchantmentLevel(LCEnchantments.ENCH_PROJECTILE.get(), event.getEntity()) > 0) {
			if (event.getSource().is(DamageTypeTags.IS_PROJECTILE)) event.setCanceled(true);
		}
		if (EnchantmentHelper.getEnchantmentLevel(LCEnchantments.ENCH_FIRE.get(), event.getEntity()) > 0) {
			if (event.getSource().is(DamageTypeTags.IS_FIRE)) event.setCanceled(true);
		}
		if (EnchantmentHelper.getEnchantmentLevel(LCEnchantments.ENCH_EXPLOSION.get(), event.getEntity()) > 0) {
			if (event.getSource().is(DamageTypeTags.IS_EXPLOSION)) event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onItemAttributes(ItemAttributeModifierEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack.isEnchanted()) {
			for (Map.Entry<Enchantment, Integer> ent : EnchantmentHelper.getEnchantments(stack).entrySet()) {
				if (ent.getKey() instanceof AttributeEnchantment attr) {
					attr.addAttributes(ent.getValue(), event);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onInventoryDrop(LivingDropsEvent event) {
		if (!(event.getEntity() instanceof ServerPlayer player))
			return;
		event.getDrops().removeIf(e -> e.getItem().getEnchantmentLevel(LCEnchantments.SOUL_BOUND.get()) > 0 && player.getInventory().add(e.getItem()));
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event) {
		event.getEntity().getInventory().replaceWith(event.getOriginal().getInventory());
	}

	@SubscribeEvent
	public static void onHeal(LivingHealEvent event) {
		if (event.getEntity().hasEffect(LCEffects.CURSE.get())) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onPotionTest(MobEffectEvent.Applicable event) {
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
			ItemStack stack = event.getEntity().getItemBySlot(slot);
			if (!stack.isEmpty()) {
				if (stack.getItem() instanceof GenericArmorItem armor) {
					if (armor.getConfig().immuneToEffect(stack, armor, event.getEffectInstance())) {
						event.setResult(Event.Result.DENY);
						return;
					}
				}
			}
		}
		if (event.getEntity().hasEffect(LCEffects.CLEANSE.get())) {
			if (event.getEffectInstance().getEffect() instanceof SkillEffect)
				return;
			if (EffectUtil.getReason() == EffectUtil.AddReason.SKILL)
				return;
			event.setResult(Event.Result.DENY);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onPotionAdded(MobEffectEvent.Added event) {
		if (event.getEntity().hasEffect(LCEffects.CLEANSE.get())) {
			CleanseEffect.clearOnEntity(event.getEntity());
		}
	}


}
