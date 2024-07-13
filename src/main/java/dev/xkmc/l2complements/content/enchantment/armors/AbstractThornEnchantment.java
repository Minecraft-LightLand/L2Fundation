package dev.xkmc.l2complements.content.enchantment.armors;

import dev.xkmc.l2complements.content.enchantment.core.BattleEnchantment;
import dev.xkmc.l2core.base.effects.EffectUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public abstract class AbstractThornEnchantment extends BattleEnchantment {

	@Override
	public void doPostHurt(LivingEntity self, Entity attacker, int pLevel) {
		var le = getTarget(attacker);
		if (le != null && le != self && !self.level().isClientSide())
			EffectUtil.addEffect(le, getEffect(pLevel), self);
	}

	protected abstract MobEffectInstance getEffect(int pLevel);

}
