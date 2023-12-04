package dev.xkmc.l2complements.content.feature;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.function.Supplier;

public record EnchantmentFeaturePredicate(Supplier<Enchantment> e) implements FeaturePredicate {

	@Override
	public boolean test(LivingEntity entity) {
		return EnchantmentHelper.getEnchantmentLevel(e.get(), entity) > 0;
	}

}
