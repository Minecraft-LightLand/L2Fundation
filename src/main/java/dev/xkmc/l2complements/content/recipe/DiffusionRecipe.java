package dev.xkmc.l2complements.content.recipe;

import dev.xkmc.l2complements.init.registrate.LCRecipes;
import dev.xkmc.l2library.serial.recipe.BaseRecipe;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

@SerialClass
public class DiffusionRecipe extends BaseRecipe<DiffusionRecipe, DiffusionRecipe, DiffusionRecipe.Inv> {

	@SerialClass.SerialField
	public Block ingredient, base, result;

	public DiffusionRecipe(ResourceLocation id) {
		super(id, LCRecipes.RS_DIFFUSION.get());
	}

	@Override
	public boolean matches(Inv inv, Level level) {
		return (inv.getItem(0).getItem() instanceof BlockItem b0 && b0.getBlock() == ingredient) &&
				(inv.getItem(1).getItem() instanceof BlockItem b1 && b1.getBlock() == base);
	}

	@Override
	public ItemStack assemble(Inv inv, RegistryAccess access) {
		return result.asItem().getDefaultInstance();
	}

	@Override
	public boolean canCraftInDimensions(int i, int i1) {
		return false;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess access) {
		return result.asItem().getDefaultInstance();
	}

	public static class Inv extends SimpleContainer implements RecInv<DiffusionRecipe> {

		public Inv() {
			super(2);
		}

	}

}
