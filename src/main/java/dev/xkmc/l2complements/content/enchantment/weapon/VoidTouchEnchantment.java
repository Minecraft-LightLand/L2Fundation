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

import java.util.Random;

public class VoidTouchEnchantment extends UnobtainableEnchantment {

	public VoidTouchEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
		super(rarity, category, slots);
	}

	private double getChance(AttackCache cache, ItemStack weapon, int level) {
		double chance = LCConfig.COMMON.voidTouchChance.get() * level;
		assert cache.getLivingHurtEvent() != null;
		DamageSource source = cache.getLivingHurtEvent().getSource();
		if (source.is(DamageTypeTags.BYPASSES_ARMOR))
			chance += LCConfig.COMMON.voidTouchChanceBonus.get();
		if (source.is(DamageTypeTags.BYPASSES_EFFECTS) && source.is(DamageTypeTags.BYPASSES_ENCHANTMENTS))
			chance += LCConfig.COMMON.voidTouchChanceBonus.get();
		return chance;
	}

	public void initAttack(AttackCache cache, ItemStack weapon) {
		int level = weapon.getEnchantmentLevel(LCEnchantments.VOID_TOUCH.get());
		if (level <= 0) return;
		double chance = getChance(cache, weapon, level);

		double rr = new Random(new Random(cache.getAttackTarget().tickCount).nextLong()).nextDouble();
		if (rr > chance) return;
		double damage = cache.getAttacker().getAttributeValue(Attributes.ATTACK_DAMAGE);
		if (cache.getCriticalHitEvent() != null) {
			damage *= cache.getCriticalHitEvent().getDamageModifier();
		}
		float finalDamage = (float) damage;
		cache.addHurtModifier(DamageModifier.nonlinearPre(0, e -> Math.max(e, finalDamage)));
	}

	public void initDamage(AttackCache cache, ItemStack weapon) {
		int level = weapon.getEnchantmentLevel(LCEnchantments.VOID_TOUCH.get());
		if (level <= 0) return;
		double chance = getChance(cache, weapon, level);
		double rr = new Random(new Random(cache.getAttackTarget().tickCount).nextLong()).nextDouble();
		if (rr > chance) return;
		float finalDamage = cache.getPreDamage();
		cache.addDealtModifier(DamageModifier.nonlinearPre(0, e -> Math.max(e, finalDamage)));
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
