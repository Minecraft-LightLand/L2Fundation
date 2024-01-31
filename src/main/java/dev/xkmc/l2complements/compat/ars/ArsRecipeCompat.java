package dev.xkmc.l2complements.compat.ars;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2complements.content.enchantment.core.BannableEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.EnchantmentRecipeBuilder;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2library.serial.conditions.BooleanValueCondition;
import dev.xkmc.l2library.serial.recipe.ConditionalRecipeWrapper;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.AndCondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class ArsRecipeCompat {
	public static Consumer<FinishedRecipe> saveCompat(
			EnchantmentRecipeBuilder builder,
			Consumer<FinishedRecipe> pvd,
			ResourceLocation id) {
		List<Ingredient> list = new ArrayList<>();
		for (var ss : builder.rows) {
			for (int i = 0; i < ss.length(); i++) {
				char ch = ss.charAt(i);
				var ing = builder.key.get(ch);
				if (ing != null) {
					if (ing.test(new ItemStack(Items.BOOK))) {
						continue;
					}
					list.add(ing);
				}
			}
		}
		list.sort(Comparator.comparing(e -> e.toJson().toString()));
		ArsRecipeBuilder ars = ArsRecipeBuilder.of(builder.enchantment, builder.level, 2000, list);
		var cond = new AndCondition(new ModLoadedCondition(ArsNouveau.MODID),
				BooleanValueCondition.of(LCConfig.COMMON_PATH,
						LCConfig.COMMON.useArsNouveauForEnchantmentRecipe, true));
		if (builder.enchantment instanceof BannableEnchantment) {
			var additional = BooleanValueCondition.of(LCConfig.COMMON_PATH,
					LCConfig.COMMON.enableImmunityEnchantments, true);
			ConditionalRecipeWrapper.of((RegistrateRecipeProvider) pvd, cond, additional).accept(new ArsFinished(ars, id.withSuffix("_ars")));
			return ConditionalRecipeWrapper.of((RegistrateRecipeProvider) pvd, new NotCondition(cond), additional);
		}
		ConditionalRecipeWrapper.of((RegistrateRecipeProvider) pvd, cond).accept(new ArsFinished(ars, id.withSuffix("_ars")));
		return ConditionalRecipeWrapper.of((RegistrateRecipeProvider) pvd, new NotCondition(cond));
	}

}
