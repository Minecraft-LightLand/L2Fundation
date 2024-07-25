package dev.xkmc.l2complements.content.enchantment.legacy;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class FlameThornEnchantment extends AbstractThornEnchantment {

	@Override
	protected MobEffectInstance getEffect(int pLevel) {
		return new MobEffectInstance(LCEffects.FLAME.holder(), LCConfig.SERVER.iceEnchantDuration.get(), pLevel - 1);
	}

}
