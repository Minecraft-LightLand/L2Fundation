package dev.xkmc.l2complements.compat;

import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class LCEmiPlugin {

	public static String partSubType(ItemStack stack, UidContext context) {
		var map = EnchantmentHelper.getEnchantments(stack);
		if (map.size() != 1) return "";
		var e = map.entrySet().stream().findFirst().get();
		return ForgeRegistries.ENCHANTMENTS.getKey(e.getKey()).toString();
	}

}
