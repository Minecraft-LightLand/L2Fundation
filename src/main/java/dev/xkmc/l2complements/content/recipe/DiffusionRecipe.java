package dev.xkmc.l2complements.content.recipe;

import dev.xkmc.l2complements.init.registrate.LCRecipes;
import dev.xkmc.l2core.serial.recipe.BaseRecipe;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

@SerialClass
public class DiffusionRecipe extends BaseRecipe<DiffusionRecipe, DiffusionRecipe, DiffusionRecipe.Inv> {

	@SerialField
	public Block ingredient, base, result;

	public DiffusionRecipe() {
		super(LCRecipes.RS_DIFFUSION.get());
	}

	@Override
	public boolean matches(Inv inv, Level level) {
		return (inv.getItem(0).getItem() instanceof BlockItem b0 && b0.getBlock() == ingredient) &&
				(inv.getItem(1).getItem() instanceof BlockItem b1 && b1.getBlock() == base);
	}

	@Override
	public ItemStack assemble(Inv inv, HolderLookup.Provider access) {
		return result.asItem().getDefaultInstance();
	}

	@Override
	public boolean canCraftInDimensions(int i, int i1) {
		return false;
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider access) {
		return result.asItem().getDefaultInstance();
	}

	public static class Inv extends SimpleContainer implements RecipeInput {

		public Inv() {
			super(2);
		}

		@Override
		public int size() {
			return 2;
		}

	}

}
