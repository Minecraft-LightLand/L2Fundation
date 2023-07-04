package dev.xkmc.l2complements.content.enchantment.weapon;

import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import net.minecraft.ChatFormatting;
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
		if (source.isBypassArmor()) chance += 0.5;
		if (source.isBypassMagic()) chance += 0.5;
		return chance;
	}

	public void initAttack(AttackCache cache, ItemStack weapon) {
		int level = weapon.getEnchantmentLevel(LCEnchantments.VOID_TOUCH.get());
		if (level <= 0) return;
		if (cache.getAttackTarget() == null) return;
		double chance = getChance(cache, weapon, level);
		double rr = new Random(new Random(cache.getAttackTarget().tickCount).nextLong()).nextDouble();
		if (rr > chance) return;
		double damage = cache.getAttacker().getAttributeValue(Attributes.ATTACK_DAMAGE);
		if (cache.getCriticalHitEvent() != null) {
			damage *= cache.getCriticalHitEvent().getDamageModifier();
		}
		if (cache.getDamageModified() < damage) {
			cache.setDamageModified((float) damage);
		}
	}

	public void initDamage(AttackCache cache, ItemStack weapon) {
		int level = weapon.getEnchantmentLevel(LCEnchantments.VOID_TOUCH.get());
		if (level <= 0) return;
		if (cache.getAttackTarget() == null) return;
		double chance = getChance(cache, weapon, level);
		double rr = new Random(new Random(cache.getAttackTarget().tickCount).nextLong()).nextDouble();
		if (rr > chance) return;
		if (cache.getDamageModified() > cache.getDamageDealt()) {
			cache.setDamageDealt(cache.getDamageModified());
		}
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
