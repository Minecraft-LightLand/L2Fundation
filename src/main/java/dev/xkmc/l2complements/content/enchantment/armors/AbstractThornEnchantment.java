package dev.xkmc.l2complements.content.enchantment.armors;

import dev.xkmc.l2complements.content.enchantment.core.BattleEnchantment;
import dev.xkmc.l2library.base.effects.EffectUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class AbstractThornEnchantment extends BattleEnchantment {

	protected AbstractThornEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}

	@Override
	public void doPostHurt(LivingEntity self, Entity attacker, int pLevel) {
		var le = getTarget(attacker);
		if (le != null && !self.level().isClientSide())
			EffectUtil.addEffect(le, getEffect(pLevel), EffectUtil.AddReason.FORCE, self);
	}

	protected abstract MobEffectInstance getEffect(int pLevel);

}
