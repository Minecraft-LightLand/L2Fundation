package dev.xkmc.l2complements.content.enchantment.legacy;

import dev.xkmc.l2core.base.effects.EffectUtil;
import dev.xkmc.l2core.init.reg.ench.CustomDescEnchantment;
import dev.xkmc.l2core.init.reg.ench.EnchColor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public abstract class EffectBladeEnchantment extends AbstractBladeEnchantment {

	public void onAttack(LivingEntity attacker, LivingEntity target, int pLevel) {
		if (target != attacker && !attacker.level().isClientSide())
			EffectUtil.addEffect(target, getEffect(pLevel), attacker);
	}

	protected abstract MobEffectInstance getEffect(int pLevel);

	@Override
	public List<Component> descFull(int lv, String key, boolean alt, boolean book, EnchColor color) {
		return List.of(Component.translatable(key,
				CustomDescEnchantment.eff(getEffect(lv))
		).withStyle(color.desc()));
	}

}
