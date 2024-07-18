package dev.xkmc.l2complements.content.enchantment.weapon;

import dev.xkmc.l2complements.content.enchantment.core.AbstractBladeEnchantment;
import dev.xkmc.l2core.base.effects.EffectUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public abstract class EffectBladeEnchantment extends AbstractBladeEnchantment {

	public void onAttack(LivingEntity attacker, LivingEntity target, int pLevel) {
		if (target != attacker && !attacker.level().isClientSide())
			EffectUtil.addEffect(target, getEffect(pLevel), attacker);
	}

	protected abstract MobEffectInstance getEffect(int pLevel);

}
