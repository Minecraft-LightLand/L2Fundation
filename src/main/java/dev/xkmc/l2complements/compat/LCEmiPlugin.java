package dev.xkmc.l2complements.compat;

import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.CommonHooks;

public class LCEmiPlugin {

	public static String partSubType(ItemStack stack, UidContext context) {
		var reg = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
		if (reg == null) return "";
		var map = stack.getAllEnchantments(reg);
		if (map.size() != 1) return "";
		var e = map.entrySet().stream().findFirst().orElseThrow();
		return e.getKey().unwrapKey().orElseThrow().location().toString();
	}

}
