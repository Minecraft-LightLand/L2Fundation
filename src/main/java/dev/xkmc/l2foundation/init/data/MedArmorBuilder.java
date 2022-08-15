package dev.xkmc.l2foundation.init.data;

import dev.xkmc.l2library.base.recipe.CustomShapedBuilder;
import dev.xkmc.l2foundation.content.recipe.MedArmorRecipe;
import dev.xkmc.l2foundation.init.registrate.LFRecipes;
import net.minecraft.world.level.ItemLike;

public class MedArmorBuilder extends CustomShapedBuilder<MedArmorRecipe> {

	public MedArmorBuilder(ItemLike result, int count) {
		super(LFRecipes.RSC_MED_ARMOR, result, count);
	}

}
