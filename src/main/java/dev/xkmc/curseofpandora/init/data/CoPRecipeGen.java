package dev.xkmc.curseofpandora.init.data;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.RecipeGen;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2hostility.init.L2Hostility;
import dev.xkmc.l2hostility.init.registrate.LHItems;
import dev.xkmc.l2hostility.init.registrate.LHTraits;
import dev.xkmc.l2library.serial.ingredients.PotionIngredient;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiFunction;

public class CoPRecipeGen {

	public static void recipeGen(RegistrateRecipeProvider pvd) {
		RecipeGen.currentFolder = "pandora/";
		// pandora
		{
			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.STABLE_BODY.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern(" C ").pattern("ABA").pattern(" A ")
					.define('A', Items.OBSIDIAN)
					.define('B', CoPItems.CHARM.get())
					.define('C', Items.CRYING_OBSIDIAN)
					.save(pvd, getID(CoPItems.STABLE_BODY.get()));


			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.ENDER_CHARM.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern(" C ").pattern("ABA").pattern(" A ")
					.define('A', Items.ENDER_EYE)
					.define('B', CoPItems.CHARM.get())
					.define('C', Items.CARVED_PUMPKIN)
					.save(pvd, getID(CoPItems.ENDER_CHARM.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.GOLDEN_HEART.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern(" A ").pattern("ABA").pattern(" A ")
					.define('A', Items.GOLD_INGOT)
					.define('B', CoPItems.CHARM.get())
					.save(pvd, getID(CoPItems.GOLDEN_HEART.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.BLESS_SNOW_WALKER.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern(" A ").pattern("ABA").pattern(" A ")
					.define('A', Items.LEATHER)
					.define('B', CoPItems.CHARM.get())
					.save(pvd, getID(CoPItems.BLESS_SNOW_WALKER.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.BLESS_LAVA_WALKER.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern(" C ").pattern("ABA").pattern(" A ")
					.define('A', Items.MAGMA_CREAM)
					.define('C', LCItems.HARD_ICE)
					.define('B', CoPItems.CHARM.get())
					.save(pvd, getID(CoPItems.BLESS_LAVA_WALKER.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.NIGHT_VISION_CHARM.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern(" C ").pattern("ABA").pattern(" A ")
					.define('A', new PotionIngredient(Potions.NIGHT_VISION))
					.define('C', LCItems.SOUL_FLAME)
					.define('B', CoPItems.CHARM.get())
					.save(pvd, getID(CoPItems.NIGHT_VISION_CHARM.get()));

		}

		if (ModList.get().isLoaded(L2Hostility.MODID)) {
			{
				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CURSE_OF_INERTIA.get())::unlockedBy, LHItems.CHAOS_INGOT.get())
						.pattern(" C ").pattern("ABA").pattern(" C ")
						.define('A', LHTraits.WEAKNESS.get().asItem())
						.define('B', LHItems.CHAOS_INGOT.get())
						.define('C', LHTraits.SLOWNESS.get().asItem())
						.save(pvd, getID(CoPItems.CURSE_OF_INERTIA.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CURSE_OF_PROXIMITY.get())::unlockedBy, LHItems.CHAOS_INGOT.get())
						.pattern(" C ").pattern("ABA").pattern(" C ")
						.define('A', LHTraits.BLIND.get().asItem())
						.define('B', LHItems.CHAOS_INGOT.get())
						.define('C', LHTraits.CONFUSION.get().asItem())
						.save(pvd, getID(CoPItems.CURSE_OF_PROXIMITY.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CURSE_OF_FLESH.get())::unlockedBy, LHItems.CHAOS_INGOT.get())
						.pattern(" C ").pattern("ABA").pattern(" C ")
						.define('A', LHTraits.WITHER.get().asItem())
						.define('B', LHItems.CHAOS_INGOT.get())
						.define('C', LHTraits.POISON.get().asItem())
						.save(pvd, getID(CoPItems.CURSE_OF_FLESH.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CURSE_OF_METABOLISM.get())::unlockedBy, LHItems.CHAOS_INGOT.get())
						.pattern(" C ").pattern("ABA").pattern(" C ")
						.define('A', LHTraits.BLIND.get().asItem())
						.define('B', LHItems.CHAOS_INGOT.get())
						.define('C', LHTraits.SLOWNESS.get().asItem())
						.save(pvd, getID(CoPItems.CURSE_OF_METABOLISM.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CURSE_OF_TENSION.get())::unlockedBy, LHItems.CHAOS_INGOT.get())
						.pattern(" C ").pattern("ABA").pattern(" C ")
						.define('A', LHTraits.CONFUSION.get().asItem())
						.define('B', LHItems.CHAOS_INGOT.get())
						.define('C', LHTraits.WEAKNESS.get().asItem())
						.save(pvd, getID(CoPItems.CURSE_OF_TENSION.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CURSE_OF_PRUDENCE.get())::unlockedBy, LHItems.CHAOS_INGOT.get())
						.pattern(" C ").pattern("ABA").pattern(" C ")
						.define('A', LHTraits.POISON.get().asItem())
						.define('B', LHItems.CHAOS_INGOT.get())
						.define('C', LHTraits.LEVITATION.get().asItem())
						.save(pvd, getID(CoPItems.CURSE_OF_PRUDENCE.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CURSE_OF_SPELL.get())::unlockedBy, LHItems.CHAOS_INGOT.get())
						.pattern(" C ").pattern("ABA").pattern(" C ")
						.define('A', LHTraits.POISON.get().asItem())
						.define('B', LHItems.CHAOS_INGOT.get())
						.define('C', LHTraits.SLOWNESS.get().asItem())
						.save(pvd, getID(CoPItems.CURSE_OF_SPELL.get()));
			}
			{
				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM.get(), 2)::unlockedBy, CoPItems.CHARM.get())
						.pattern("AAA").pattern("ABA").pattern("AAA")
						.define('A', LHItems.MIRACLE_POWDER.get())
						.define('B', CoPItems.CHARM.get())
						.save(pvd, getID(CoPItems.CHARM.get(), "_renew"));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM.get(), 2)::unlockedBy, CoPItems.CHARM.get())
						.pattern("AAA").pattern("ABA").pattern("AAA")
						.define('A', LHItems.MIRACLE_POWDER.get())
						.define('B', LHItems.MIRACLE_INGOT.get())
						.save(pvd, getID(CoPItems.CHARM.get(), "_craft"));

				unlock(pvd, SimpleCookingRecipeBuilder.blasting(Ingredient.of(CoPTagGen.PANDORA_BASE),
						RecipeCategory.MISC, CoPItems.CHARM.get(), 1, 200)::unlockedBy, CoPItems.CHARM.get())
						.save(pvd, getID(CoPItems.CHARM.get(), "_smelt"));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_HEALTH.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("AAA").pattern("ABA").pattern("AAA")
						.define('A', LHTraits.TANK.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.save(pvd, getID(CoPItems.CHARM_HEALTH.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_ARMOR.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.TANK.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.PROTECTION.get().asItem())
						.save(pvd, getID(CoPItems.CHARM_ARMOR.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_SPEED.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("AAA").pattern("ABA").pattern("AAA")
						.define('A', LHTraits.SPEEDY.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.save(pvd, getID(CoPItems.CHARM_SPEED.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_DAMAGE.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.FIERY.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.STRIKE.get().asItem())
						.save(pvd, getID(CoPItems.CHARM_DAMAGE.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_HEAVY.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.GRAVITY.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.SLOWNESS.get().asItem())
						.save(pvd, getID(CoPItems.CHARM_HEAVY.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_BOW.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.WEAKNESS.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.SHULKER.get().asItem())
						.save(pvd, getID(CoPItems.CHARM_BOW.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_CRIT.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.MOONWALK.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.LEVITATION.get().asItem())
						.save(pvd, getID(CoPItems.CHARM_CRIT.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_ACCURACY.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.FREEZING.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.REFLECT.get().asItem())
						.save(pvd, getID(CoPItems.CHARM_ACCURACY.get()));
			}
		}


	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

	private static ResourceLocation getID(Item item) {
		return new ResourceLocation(L2Complements.MODID, RecipeGen.currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath());
	}

	@SuppressWarnings("ConstantConditions")
	private static ResourceLocation getID(Item item, String suffix) {
		return new ResourceLocation(L2Complements.MODID, RecipeGen.currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath() + suffix);
	}


}
