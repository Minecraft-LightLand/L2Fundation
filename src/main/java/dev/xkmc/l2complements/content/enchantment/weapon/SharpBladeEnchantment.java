package dev.xkmc.l2complements.content.enchantment.weapon;

import dev.xkmc.l2complements.content.effect.skill.StackingEffect;
import dev.xkmc.l2complements.content.enchantment.core.AbstractBladeEnchantment;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import net.minecraft.world.entity.LivingEntity;

public class SharpBladeEnchantment extends AbstractBladeEnchantment {

	@Override
	public void onAttack(LivingEntity attacker, LivingEntity target, int level) {
		if (!attacker.level().isClientSide())
			StackingEffect.addTo(LCEffects.BLEED.holder(), target, LCConfig.SERVER.bleedEnchantDuration.get(), level * 3 - 1, attacker);
	}

}
