package dev.xkmc.l2complements.content.enchantment.legacy;

import dev.xkmc.l2complements.content.effect.StackingEffect;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2core.init.reg.ench.CustomDescEnchantment;
import dev.xkmc.l2core.init.reg.ench.EnchColor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class SharpBladeEnchantment extends AbstractBladeEnchantment {

	@Override
	public void onAttack(LivingEntity attacker, LivingEntity target, int level) {
		if (!attacker.level().isClientSide())
			StackingEffect.addTo(LCEffects.BLEED.holder(), target, LCConfig.SERVER.bleedEnchantDuration.get(), level * 3 - 1, attacker);
	}

	@Override
	public List<Component> descFull(int lv, String key, boolean alt, boolean book, EnchColor color) {
		return List.of(Component.translatable(key,
				CustomDescEnchantment.eff(
						new MobEffectInstance(LCEffects.BLEED.holder(),
								LCConfig.SERVER.bleedEnchantDuration.get()),
						false, true),
				CustomDescEnchantment.num(lv * 3)
		).withStyle(color.desc()));
	}

}
