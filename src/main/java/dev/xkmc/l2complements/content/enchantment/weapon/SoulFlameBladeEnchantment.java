package dev.xkmc.l2complements.content.enchantment.weapon;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class SoulFlameBladeEnchantment extends EffectBladeEnchantment {

	@Override
	protected MobEffectInstance getEffect(int pLevel) {
		return new MobEffectInstance(LCEffects.FLAME.holder(), LCConfig.COMMON.flameEnchantDuration.get(), pLevel - 1);
	}

}
