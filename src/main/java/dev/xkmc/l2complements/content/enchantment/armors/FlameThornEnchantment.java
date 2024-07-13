package dev.xkmc.l2complements.content.enchantment.armors;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FlameThornEnchantment extends AbstractThornEnchantment {

	@Override
	protected MobEffectInstance getEffect(int pLevel) {
		return new MobEffectInstance(LCEffects.FLAME.holder(), LCConfig.COMMON.iceEnchantDuration.get(), pLevel - 1);
	}

}
