package dev.xkmc.l2complements.content.enchantment.weapon;

import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Random;

public class VoidTouchEnchantment extends UnobtainableEnchantment {

	public VoidTouchEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
		super(rarity, category, slots);
	}

	private double getChance(AttackCache cache, ItemStack weapon, int level) {
		if (cache.getStrength() < 0.95f) return 0;
		double chance = LCConfig.COMMON.voidTouchChance.get() * level;
		DamageSource source = null;
		if (cache.getLivingHurtEvent() != null)
			source = cache.getLivingHurtEvent().getSource();
		else if (cache.getLivingAttackEvent() != null) {
			source = cache.getLivingAttackEvent().getSource();
		}
		if (source != null) {
			if (source.is(DamageTypeTags.BYPASSES_ARMOR))
				chance += LCConfig.COMMON.voidTouchChanceBonus.get();
			if (source.is(DamageTypeTags.BYPASSES_EFFECTS) && source.is(DamageTypeTags.BYPASSES_ENCHANTMENTS))
				chance += LCConfig.COMMON.voidTouchChanceBonus.get();
		}
		return chance;
	}

	private boolean allow(AttackCache cache, ItemStack weapon) {
		int level = weapon.getEnchantmentLevel(LCEnchantments.VOID_TOUCH.get());
		if (level <= 0) return false;
		double chance = getChance(cache, weapon, level);
		double rr = new Random(new Random(cache.getAttackTarget().tickCount).nextLong()).nextDouble();
		if (rr > chance) return false;
		return true;
	}

	public void postAttack(AttackCache cache, LivingAttackEvent event, ItemStack weapon) {
		if (!allow(cache, weapon)) return;
		if (event.isCanceled()) {
			event.setCanceled(false);
		}
	}

	public void initAttack(AttackCache cache, ItemStack weapon) {
		if (!allow(cache, weapon)) return;
		if (cache.getAttacker() == null) return;
		if (cache.getAttacker().getAttribute(Attributes.ATTACK_DAMAGE) == null) return;
		double damage = cache.getAttacker().getAttributeValue(Attributes.ATTACK_DAMAGE);
		if (cache.getCriticalHitEvent() != null) {
			damage *= cache.getCriticalHitEvent().getDamageModifier();
		}
		float finalDamage = (float) damage;
		cache.addHurtModifier(DamageModifier.nonlinearPre(0, e -> Math.max(e, finalDamage)));
	}

	public void postHurt(AttackCache cache, LivingHurtEvent event, ItemStack weapon) {
		if (!allow(cache, weapon)) return;
		if (event.isCanceled()) {
			event.setCanceled(false);
		}
		event.setAmount(Math.max(event.getAmount(), cache.getPreDamage()));
	}

	public void initDamage(AttackCache cache, ItemStack weapon) {
		if (!allow(cache, weapon)) return;
		float finalDamage = cache.getPreDamage();
		cache.addDealtModifier(DamageModifier.nonlinearPre(5000, e -> Math.max(e, finalDamage)));
	}

	public ChatFormatting getColor() {
		return ChatFormatting.GOLD;
	}

	@Override
	public int getMinLevel() {
		return 1;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}
}
