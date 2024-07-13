package dev.xkmc.l2complements.content.enchantment.armors;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class IceThornEnchantment extends AbstractThornEnchantment {

	@Override
	protected MobEffectInstance getEffect(int pLevel) {
		return new MobEffectInstance(LCEffects.ICE.holder(), LCConfig.COMMON.iceEnchantDuration.get() << (pLevel - 1));
	}

}
