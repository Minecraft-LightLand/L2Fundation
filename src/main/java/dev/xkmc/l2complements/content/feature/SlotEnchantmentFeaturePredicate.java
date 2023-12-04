package dev.xkmc.l2complements.content.feature;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.function.Supplier;

public record SlotEnchantmentFeaturePredicate(EquipmentSlot slot, Supplier<Enchantment> e) implements FeaturePredicate {

	@Override
	public boolean test(LivingEntity entity) {
		ItemStack stack = entity.getItemBySlot(slot);
		return stack.getEnchantmentLevel(e.get()) > 0;
	}

}
