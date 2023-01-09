package dev.xkmc.l2complements.init.registrate;

import dev.xkmc.l2complements.content.recipe.BurntRecipe;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.base.recipe.BaseRecipe;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static dev.xkmc.l2complements.init.L2Complements.REGISTRATE;

public class LCRecipes {

	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, L2Complements.MODID);
	public static RegistryObject<RecipeType<BurntRecipe>> RT_BURNT = REGISTRATE.recipe(RECIPE_TYPES, "burnt");

	public static final RegistryEntry<BaseRecipe.RecType<BurntRecipe, BurntRecipe, BurntRecipe.Inv>> RS_BURNT =
			reg("burnt", () -> new BaseRecipe.RecType<>(BurntRecipe.class, RT_BURNT));

	public static void register(IEventBus bus) {
		RECIPE_TYPES.register(bus);
	}

	private static <A extends RecipeSerializer<?>> RegistryEntry<A> reg(String id, NonNullSupplier<A> sup) {
		return REGISTRATE.simple(id, ForgeRegistries.Keys.RECIPE_SERIALIZERS, sup);
	}

}
