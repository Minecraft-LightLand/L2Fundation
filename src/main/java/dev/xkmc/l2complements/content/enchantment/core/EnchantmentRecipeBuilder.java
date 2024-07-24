package dev.xkmc.l2complements.content.enchantment.core;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2core.init.reg.ench.EnchVal;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public class EnchantmentRecipeBuilder extends ShapedRecipeBuilder {

	private final ResourceKey<Enchantment> key;

	public EnchantmentRecipeBuilder(EnchVal ench, RegistrateRecipeProvider pvd, int lv) {
		super(RecipeCategory.MISC, EnchantedBookItem.createForEnchantment(
				new EnchantmentInstance(ench.datagenDirect(pvd), lv)));
		this.key = ench.id();
	}

	public void save(RecipeOutput pvd) {
		// TODO ars compat
		this.save(pvd, key.location());
	}

}
