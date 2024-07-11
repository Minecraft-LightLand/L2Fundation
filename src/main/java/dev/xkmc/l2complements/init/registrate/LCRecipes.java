package dev.xkmc.l2complements.init.registrate;

import dev.xkmc.l2complements.content.recipe.BurntRecipe;
import dev.xkmc.l2complements.content.recipe.DiffusionRecipe;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.init.reg.simple.SR;
import dev.xkmc.l2core.init.reg.simple.Val;
import dev.xkmc.l2core.serial.recipe.BaseRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class LCRecipes {


	private static final SR<RecipeType<?>> RT = SR.of(L2Complements.REG, BuiltInRegistries.RECIPE_TYPE);
	private static final SR<RecipeSerializer<?>> RS = SR.of(L2Complements.REG, BuiltInRegistries.RECIPE_SERIALIZER);

	public static Val<RecipeType<BurntRecipe>> RT_BURNT = RT.reg("burnt", RecipeType::simple);
	public static Val<RecipeType<DiffusionRecipe>> RT_DIFFUSION = RT.reg("diffusion", RecipeType::simple);

	public static final Val<BaseRecipe.RecType<BurntRecipe, BurntRecipe, BurntRecipe.Inv>> RS_BURNT =
			RS.reg("burnt", () -> new BaseRecipe.RecType<>(BurntRecipe.class, RT_BURNT));

	public static final Val<BaseRecipe.RecType<DiffusionRecipe, DiffusionRecipe, DiffusionRecipe.Inv>> RS_DIFFUSION =
			RS.reg("diffusion", () -> new BaseRecipe.RecType<>(DiffusionRecipe.class, RT_DIFFUSION));

	public static void register() {
	}

}
