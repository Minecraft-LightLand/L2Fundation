package dev.xkmc.l2complements.content.recipe;

import dev.xkmc.l2complements.init.registrate.LCRecipes;
import dev.xkmc.l2core.serial.recipe.BaseRecipe;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;

@SerialClass
public class BurntRecipe extends BaseRecipe<BurntRecipe, BurntRecipe, BurntRecipe.Inv> {

	@SerialField
	public Ingredient ingredient;

	@SerialField
	public ItemStack result;

	@SerialField
	public int chance;

	public BurntRecipe() {
		super(LCRecipes.RS_BURNT.get());
	}

	@Override
	public boolean matches(Inv inv, Level level) {
		return ingredient.test(inv.getItem(0));
	}

	@Override
	public ItemStack assemble(Inv inv, HolderLookup.Provider access) {
		return result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int i, int i1) {
		return false;
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider access) {
		return result;
	}

	public static class Inv extends SimpleContainer implements RecipeInput {

		public Inv() {
			super(1);
		}

		@Override
		public int size() {
			return 1;
		}

	}

}
