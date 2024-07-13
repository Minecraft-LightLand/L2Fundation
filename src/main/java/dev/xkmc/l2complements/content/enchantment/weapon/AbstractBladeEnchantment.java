package dev.xkmc.l2complements.content.enchantment.weapon;

import dev.xkmc.l2complements.content.enchantment.core.BattleEnchantment;
import dev.xkmc.l2core.base.effects.EffectUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public abstract class AbstractBladeEnchantment extends BattleEnchantment {

	public void doPostAttack(LivingEntity attacker, Entity target, int pLevel) {
		var le = getTarget(target);
		if (le != null && le != attacker && !attacker.level().isClientSide())
			EffectUtil.addEffect(le, getEffect(pLevel), attacker);
	}

	protected abstract MobEffectInstance getEffect(int pLevel);

}
