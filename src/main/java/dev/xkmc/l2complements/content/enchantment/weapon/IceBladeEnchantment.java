package dev.xkmc.l2complements.content.enchantment.weapon;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class IceBladeEnchantment extends AbstractBladeEnchantment {

	@Override
	protected MobEffectInstance getEffect(int pLevel) {
		return new MobEffectInstance(LCEffects.ICE.holder(), LCConfig.COMMON.iceEnchantDuration.get() << (pLevel - 1));
	}


}
