package dev.xkmc.l2complements.content.enchantment.weapon;

import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2library.base.effects.EffectUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SoulFlameBladeEnchantment extends UnobtainableEnchantment {

	public SoulFlameBladeEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}

	@Override
	public void doPostAttack(LivingEntity attacker, Entity target, int pLevel) {
		if (target instanceof LivingEntity le && !attacker.level().isClientSide())
			EffectUtil.addEffect(le, new MobEffectInstance(LCEffects.FLAME.get(), LCConfig.COMMON.flameEnchantDuration.get(), pLevel - 1), EffectUtil.AddReason.NONE, attacker);
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
