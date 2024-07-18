package dev.xkmc.l2complements.content.enchantment.armors;

import dev.xkmc.l2core.base.effects.EffectUtil;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public abstract class AbstractThornEnchantment extends LegacyEnchantment {

	public void onDamage(LivingEntity self, LivingEntity le, int level) {
		if (le != self && !self.level().isClientSide())
			EffectUtil.addEffect(le, getEffect(level), self);
	}

	protected abstract MobEffectInstance getEffect(int pLevel);

}
