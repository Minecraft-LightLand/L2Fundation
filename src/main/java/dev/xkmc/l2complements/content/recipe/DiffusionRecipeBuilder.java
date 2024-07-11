package dev.xkmc.l2complements.content.recipe;

import dev.xkmc.l2complements.init.registrate.LCRecipes;
import dev.xkmc.l2core.serial.recipe.BaseRecipeBuilder;
import net.minecraft.world.level.block.Block;

public class DiffusionRecipeBuilder extends BaseRecipeBuilder<DiffusionRecipeBuilder, DiffusionRecipe, DiffusionRecipe, DiffusionRecipe.Inv> {

	public DiffusionRecipeBuilder(Block in, Block base, Block out) {
		super(LCRecipes.RS_DIFFUSION.get(), out.asItem());
		this.recipe.ingredient = in;
		this.recipe.base = base;
		this.recipe.result = out;
	}

}
