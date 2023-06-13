package dev.xkmc.l2complements.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2complements.content.recipe.BurntRecipe;
import dev.xkmc.l2library.serial.recipe.BaseRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static dev.xkmc.l2complements.init.L2Complements.REGISTRATE;

public class LCRecipes {

	public static RegistryEntry<RecipeType<BurntRecipe>> RT_BURNT = REGISTRATE.recipe("burnt");

	public static final RegistryEntry<BaseRecipe.RecType<BurntRecipe, BurntRecipe, BurntRecipe.Inv>> RS_BURNT =
			reg("burnt", () -> new BaseRecipe.RecType<>(BurntRecipe.class, RT_BURNT));

	public static void register(IEventBus bus) {
	}

	private static <A extends RecipeSerializer<?>> RegistryEntry<A> reg(String id, NonNullSupplier<A> sup) {
		return REGISTRATE.simple(id, ForgeRegistries.Keys.RECIPE_SERIALIZERS, sup);
	}

}
