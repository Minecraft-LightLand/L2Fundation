package dev.xkmc.l2complements.content.enchantment.weapon;

import dev.xkmc.l2complements.content.effect.skill.StackingEffect;
import dev.xkmc.l2complements.content.enchantment.core.BattleEnchantment;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SharpBladeEnchantment extends BattleEnchantment {

	@Override
	public void doPostAttack(LivingEntity attacker, Entity target, int pLevel) {
		var le = getTarget(target);
		if (le != null && !attacker.level().isClientSide())
			StackingEffect.addTo(LCEffects.BLEED.holder(), le, LCConfig.COMMON.bleedEnchantDuration.get(), pLevel * 3 - 1, attacker);
	}

}
