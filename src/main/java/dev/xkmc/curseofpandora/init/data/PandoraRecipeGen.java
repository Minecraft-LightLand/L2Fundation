package dev.xkmc.curseofpandora.init.data;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import dev.xkmc.curseofpandora.init.registrate.LCPandora;
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

public class PandoraRecipeGen {

	public static void recipeGen(RegistrateRecipeProvider pvd) {
		RecipeGen.currentFolder = "pandora/";
		// pandora
		{
			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.STABLE_BODY.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern(" C ").pattern("ABA").pattern(" A ")
					.define('A', Items.OBSIDIAN)
					.define('B', LCPandora.CHARM.get())
					.define('C', Items.CRYING_OBSIDIAN)
					.save(pvd, getID(LCPandora.STABLE_BODY.get()));


			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.ENDER_CHARM.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern(" C ").pattern("ABA").pattern(" A ")
					.define('A', Items.ENDER_EYE)
					.define('B', LCPandora.CHARM.get())
					.define('C', Items.CARVED_PUMPKIN)
					.save(pvd, getID(LCPandora.ENDER_CHARM.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.GOLDEN_HEART.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern(" A ").pattern("ABA").pattern(" A ")
					.define('A', Items.GOLD_INGOT)
					.define('B', LCPandora.CHARM.get())
					.save(pvd, getID(LCPandora.GOLDEN_HEART.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.BLESS_SNOW_WALKER.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern(" A ").pattern("ABA").pattern(" A ")
					.define('A', Items.LEATHER)
					.define('B', LCPandora.CHARM.get())
					.save(pvd, getID(LCPandora.BLESS_SNOW_WALKER.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.BLESS_LAVA_WALKER.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern(" C ").pattern("ABA").pattern(" A ")
					.define('A', Items.MAGMA_CREAM)
					.define('C', LCItems.HARD_ICE)
					.define('B', LCPandora.CHARM.get())
					.save(pvd, getID(LCPandora.BLESS_LAVA_WALKER.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.NIGHT_VISION_CHARM.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern(" C ").pattern("ABA").pattern(" A ")
					.define('A', new PotionIngredient(Potions.NIGHT_VISION))
					.define('C', LCItems.SOUL_FLAME)
					.define('B', LCPandora.CHARM.get())
					.save(pvd, getID(LCPandora.NIGHT_VISION_CHARM.get()));

		}

		if (ModList.get().isLoaded(L2Hostility.MODID)) {

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.CURSE_OF_INERTIA.get())::unlockedBy, LHItems.CHAOS_INGOT.get())
					.pattern(" C ").pattern("ABA").pattern(" C ")
					.define('A', LHTraits.WEAKNESS.get().asItem())
					.define('B', LHItems.CHAOS_INGOT.get())
					.define('C', LHTraits.SLOWNESS.get().asItem())
					.save(pvd, getID(LCPandora.CURSE_OF_INERTIA.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.CURSE_OF_PROXIMITY.get())::unlockedBy, LHItems.CHAOS_INGOT.get())
					.pattern(" C ").pattern("ABA").pattern(" C ")
					.define('A', LHTraits.BLIND.get().asItem())
					.define('B', LHItems.CHAOS_INGOT.get())
					.define('C', LHTraits.CONFUSION.get().asItem())
					.save(pvd, getID(LCPandora.CURSE_OF_PROXIMITY.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.CHARM.get(), 2)::unlockedBy, LCPandora.CHARM.get())
					.pattern("AAA").pattern("ABA").pattern("AAA")
					.define('A', LHItems.MIRACLE_POWDER.get())
					.define('B', LCPandora.CHARM.get())
					.save(pvd, getID(LCPandora.CHARM.get(), "_renew"));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.CHARM.get(), 2)::unlockedBy, LCPandora.CHARM.get())
					.pattern("AAA").pattern("ABA").pattern("AAA")
					.define('A', LHItems.MIRACLE_POWDER.get())
					.define('B', LHItems.MIRACLE_INGOT.get())
					.save(pvd, getID(LCPandora.CHARM.get(), "_craft"));

			unlock(pvd, SimpleCookingRecipeBuilder.blasting(Ingredient.of(CoPTagGen.PANDORA_BASE),
					RecipeCategory.MISC, LCPandora.CHARM.get(), 1, 200)::unlockedBy, LCPandora.CHARM.get())
					.save(pvd, getID(LCPandora.CHARM.get(), "_smelt"));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.CHARM_HEALTH.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern("AAA").pattern("ABA").pattern("AAA")
					.define('A', LHTraits.TANK.get().asItem())
					.define('B', LCPandora.CHARM.get())
					.save(pvd, getID(LCPandora.CHARM_HEALTH.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.CHARM_ARMOR.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern("CAC").pattern("ABA").pattern("CAC")
					.define('A', LHTraits.TANK.get().asItem())
					.define('B', LCPandora.CHARM.get())
					.define('C', LHTraits.PROTECTION.get().asItem())
					.save(pvd, getID(LCPandora.CHARM_ARMOR.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.CHARM_SPEED.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern("AAA").pattern("ABA").pattern("AAA")
					.define('A', LHTraits.SPEEDY.get().asItem())
					.define('B', LCPandora.CHARM.get())
					.save(pvd, getID(LCPandora.CHARM_SPEED.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.CHARM_DAMAGE.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern("CAC").pattern("ABA").pattern("CAC")
					.define('A', LHTraits.FIERY.get().asItem())
					.define('B', LCPandora.CHARM.get())
					.define('C', LHTraits.STRIKE.get().asItem())
					.save(pvd, getID(LCPandora.CHARM_DAMAGE.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.CHARM_HEAVY.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern("CAC").pattern("ABA").pattern("CAC")
					.define('A', LHTraits.GRAVITY.get().asItem())
					.define('B', LCPandora.CHARM.get())
					.define('C', LHTraits.SLOWNESS.get().asItem())
					.save(pvd, getID(LCPandora.CHARM_HEAVY.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.CHARM_BOW.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern("CAC").pattern("ABA").pattern("CAC")
					.define('A', LHTraits.WEAKNESS.get().asItem())
					.define('B', LCPandora.CHARM.get())
					.define('C', LHTraits.SHULKER.get().asItem())
					.save(pvd, getID(LCPandora.CHARM_BOW.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.CHARM_CRIT.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern("CAC").pattern("ABA").pattern("CAC")
					.define('A', LHTraits.MOONWALK.get().asItem())
					.define('B', LCPandora.CHARM.get())
					.define('C', LHTraits.LEVITATION.get().asItem())
					.save(pvd, getID(LCPandora.CHARM_CRIT.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LCPandora.CHARM_ACCURACY.get())::unlockedBy, LCPandora.CHARM.get())
					.pattern("CAC").pattern("ABA").pattern("CAC")
					.define('A', LHTraits.FREEZING.get().asItem())
					.define('B', LCPandora.CHARM.get())
					.define('C', LHTraits.REFLECT.get().asItem())
					.save(pvd, getID(LCPandora.CHARM_ACCURACY.get()));
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
