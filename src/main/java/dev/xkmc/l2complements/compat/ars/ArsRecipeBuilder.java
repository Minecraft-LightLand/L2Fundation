package dev.xkmc.l2complements.compat.ars;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;

public record ArsRecipeBuilder(ResourceLocation type, Enchantment enchantment, int level, int sourceCost,
							   ArrayList<WrappedIngredient> pedestalItems) {

	public static ArsRecipeBuilder of(Enchantment ench, int lv, int cost, List<Ingredient> ings) {
		ArrayList<WrappedIngredient> list = new ArrayList<>();
		for (var e : ings) {
			list.add(new WrappedIngredient(e));
		}
		return new ArsRecipeBuilder(new ResourceLocation("ars_nouveau:enchantment"),
				ench, lv, cost, list);
	}

	public record WrappedIngredient(Ingredient item) {

	}

}
