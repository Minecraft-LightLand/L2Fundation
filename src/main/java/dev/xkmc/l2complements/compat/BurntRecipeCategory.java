package dev.xkmc.l2complements.compat;

import dev.xkmc.l2complements.content.recipe.BurntRecipe;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCLang;
import dev.xkmc.l2core.compat.jei.BaseRecipeCategory;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class BurntRecipeCategory extends BaseRecipeCategory<BurntRecipe, BurntRecipeCategory> {

	protected static final ResourceLocation BG = L2Complements.loc("textures/jei/background.png");

	public BurntRecipeCategory() {
		super(L2Complements.loc("burning"), BurntRecipe.class);
	}

	public BurntRecipeCategory init(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(BG, 0, 0, 72, 18);
		icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, Items.LAVA_BUCKET.getDefaultInstance());
		return this;
	}

	@Override
	public Component getTitle() {
		return LCLang.IDS.BURNT_TITLE.get();
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, BurntRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(recipe.ingredient);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55, 1).addItemStack(recipe.result)
				.addTooltipCallback((e, list) -> list.add(LCLang.IDS.BURNT_COUNT.get(recipe.chance)));
	}

}
