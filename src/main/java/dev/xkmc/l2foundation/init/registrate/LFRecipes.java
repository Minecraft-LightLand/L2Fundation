package dev.xkmc.l2foundation.init.registrate;

import dev.xkmc.l2library.base.recipe.AbstractShapedRecipe;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2foundation.content.recipe.MedArmorRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import static dev.xkmc.l2foundation.init.L2Foundation.REGISTRATE;

public class LFRecipes {

	public static final RegistryEntry<AbstractShapedRecipe.Serializer<MedArmorRecipe>> RSC_MED_ARMOR =
			reg("medicine_armor", () -> new AbstractShapedRecipe.Serializer<>(MedArmorRecipe::new));

	public static void register() {
	}

	private static <A extends RecipeSerializer<?>> RegistryEntry<A> reg(String id, NonNullSupplier<A> sup) {
		return REGISTRATE.simple(id, ForgeRegistries.Keys.RECIPE_SERIALIZERS, sup);
	}

}
