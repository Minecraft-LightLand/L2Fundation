package dev.xkmc.l2complements.content.enchantment.core;

import net.minecraft.core.Holder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public class EnchantmentRecipeBuilder extends ShapedRecipeBuilder {

	private final ResourceKey<Enchantment> key;

	public EnchantmentRecipeBuilder(Holder<Enchantment> ench, int lv) {
		super(RecipeCategory.MISC, EnchantedBookItem.createForEnchantment(new EnchantmentInstance(ench, lv)));
		this.key = ench.getKey();
	}

	public void save(RecipeOutput pvd) {
		// TODO ars compat
		this.save(pvd, key.location());
	}

}
