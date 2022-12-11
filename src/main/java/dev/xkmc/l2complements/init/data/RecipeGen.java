package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.content.enchantment.EnchantmentRecipeBuilder;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.registrate.LCBlocks;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2library.base.ingredients.EnchantmentIngredient;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2library.repack.registrate.util.DataIngredient;
import dev.xkmc.l2library.repack.registrate.util.entry.BlockEntry;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;

public class RecipeGen {

	@SuppressWarnings("unchecked")
	private static final TagKey<Item>[] TOOLS = List.of(
			Tags.Items.ARMORS_BOOTS,
			Tags.Items.ARMORS_LEGGINGS,
			Tags.Items.ARMORS_CHESTPLATES,
			Tags.Items.ARMORS_HELMETS,
			Tags.Items.TOOLS_SWORDS,
			Tags.Items.TOOLS_AXES,
			Tags.Items.TOOLS_SHOVELS,
			Tags.Items.TOOLS_PICKAXES,
			Tags.Items.TOOLS_HOES
	).toArray(TagKey[]::new);

	private static final String[] TOOL_NAME = {"boots", "leggings", "chestplate", "helmet", "sword", "axe", "shovel", "pickaxe", "hoe"};

	private static String currentFolder = "";

	@SuppressWarnings("ConstantConditions")
	public static void genRecipe(RegistrateRecipeProvider pvd) {

		// gen tool and storage
		{
			for (int i = 0; i < LCMats.values().length; i++) {
				LCMats mat = LCMats.values()[i];
				ItemEntry<?>[] arr = LCItems.GEN_ITEM[i];
				genTools(pvd, mat, arr);
			}

			currentFolder = "storage/";
			for (int i = 0; i < LCMats.values().length; i++) {
				storage(pvd, LCItems.MAT_NUGGETS[i], LCItems.MAT_INGOTS[i], LCBlocks.GEN_BLOCK[i]);
			}
		}

		currentFolder = "craft/";
		{
			unlock(pvd, new ShapelessRecipeBuilder(LCItems.WIND_BOTTLE.get(), 1)::unlockedBy, Items.GLASS_BOTTLE)
					.requires(Items.GLASS_BOTTLE)
					.requires(Items.PHANTOM_MEMBRANE)
					.save(pvd, getID(LCItems.WIND_BOTTLE.get()));

			unlock(pvd, new ShapedRecipeBuilder(LCBlocks.ETERNAL_ANVIL.get(), 1)::unlockedBy, LCMats.ETERNIUM.getIngot())
					.pattern("AAA").pattern(" B ").pattern("BBB")
					.define('A', LCMats.ETERNIUM.getBlock())
					.define('B', LCMats.ETERNIUM.getIngot())
					.save(pvd, getID(LCBlocks.ETERNAL_ANVIL.get().asItem()));

			unlock(pvd, new ShapedRecipeBuilder(LCMats.ETERNIUM.getNugget(), 1)::unlockedBy, LCItems.EXPLOSION_SHARD.get())
					.pattern("3C4").pattern("BAB").pattern("1C2")
					.define('A', LCItems.EXPLOSION_SHARD.get())
					.define('B', Items.ANVIL)
					.define('C', Items.ENDER_PEARL)
					.define('1', new EnchantmentIngredient(Enchantments.MENDING, 1))
					.define('2', new EnchantmentIngredient(Enchantments.INFINITY_ARROWS, 1))
					.define('3', new EnchantmentIngredient(Enchantments.ALL_DAMAGE_PROTECTION, 4))
					.define('4', new EnchantmentIngredient(Enchantments.UNBREAKING, 3))
					.save(pvd, getID(LCMats.ETERNIUM.getNugget()));

			blasting(pvd, Items.TOTEM_OF_UNDYING, LCMats.TOTEMIC_GOLD.getIngot(), 1);
			blasting(pvd, Items.TRIDENT, LCMats.POSEIDITE.getIngot(), 1);

			unlock(pvd, new ShapelessRecipeBuilder(LCMats.SHULKERATE.getIngot(), 1)::unlockedBy, LCItems.CAPTURED_BULLET.get())
					.requires(Items.SHULKER_SHELL, 2).requires(LCItems.CAPTURED_BULLET.get()).requires(Items.IRON_INGOT)
					.save(pvd, getID(LCMats.SHULKERATE.getIngot()));

			unlock(pvd, new ShapelessRecipeBuilder(LCMats.SCULKIUM.getIngot(), 2)::unlockedBy, LCItems.WARDEN_BONE_SHARD.get())
					.requires(Items.ECHO_SHARD).requires(LCItems.WARDEN_BONE_SHARD.get(), 2).requires(Items.COPPER_INGOT)
					.save(pvd, getID(LCMats.SCULKIUM.getIngot()));

		}

		currentFolder = "vanilla/renew/";
		// misc
		{
			unlock(pvd, new ShapelessRecipeBuilder(Items.ECHO_SHARD, 1)::unlockedBy, LCItems.RESONANT_FEATHER.get())
					.requires(LCItems.RESONANT_FEATHER.get())
					.requires(Items.AMETHYST_SHARD)
					.requires(Items.SCULK, 4)
					.save(pvd, getID(Items.ECHO_SHARD));
			unlock(pvd, new ShapedRecipeBuilder(Items.ELYTRA, 1)::unlockedBy, LCItems.SUN_MEMBRANE.get())
					.pattern("ABA").pattern("CEC").pattern("D D")
					.define('A', LCItems.EXPLOSION_SHARD.get())
					.define('B', LCItems.CAPTURED_WIND.get())
					.define('C', LCItems.SUN_MEMBRANE.get())
					.define('D', LCItems.RESONANT_FEATHER.get())
					.define('E', LCItems.STORM_CORE.get())
					.save(pvd, getID(Items.ELYTRA));
			unlock(pvd, new ShapedRecipeBuilder(Items.ANCIENT_DEBRIS, 1)::unlockedBy, LCItems.EXPLOSION_SHARD.get())
					.pattern("ABA").pattern("ACA").pattern("ADA")
					.define('A', LCItems.EXPLOSION_SHARD.get())
					.define('B', Items.NETHER_STAR)
					.define('C', Items.CRYING_OBSIDIAN)
					.define('D', LCItems.FORCE_FIELD.get())
					.save(pvd, getID(Items.ANCIENT_DEBRIS));
			unlock(pvd, new ShapedRecipeBuilder(Items.GILDED_BLACKSTONE, 1)::unlockedBy, LCItems.BLACKSTONE_CORE.get())
					.pattern("ABA").pattern("BCB").pattern("ABA")
					.define('A', Items.BLACKSTONE)
					.define('B', Items.GOLD_INGOT)
					.define('C', LCItems.BLACKSTONE_CORE.get())
					.save(pvd, getID(Items.GILDED_BLACKSTONE));
			unlock(pvd, new ShapedRecipeBuilder(Items.REINFORCED_DEEPSLATE,1)::unlockedBy, LCItems.WARDEN_BONE_SHARD.get())
					.pattern(" B ").pattern("BAB").pattern(" B ")
					.define('A', Items.DEEPSLATE)
					.define('B', LCItems.WARDEN_BONE_SHARD.get())
					.save(pvd, getID(Items.REINFORCED_DEEPSLATE));
		}

		currentFolder = "vanilla/upgrade/";
		{
			for (int i = 0; i < 9; i++) {
				smithing(pvd, TOOLS[i], Items.IRON_BLOCK, ForgeRegistries.ITEMS.getValue(new ResourceLocation("iron_" + TOOL_NAME[i])));
				smithing(pvd, TOOLS[i], Items.GOLD_BLOCK, ForgeRegistries.ITEMS.getValue(new ResourceLocation("golden_" + TOOL_NAME[i])));
				smithing(pvd, TOOLS[i], Items.DIAMOND_BLOCK, ForgeRegistries.ITEMS.getValue(new ResourceLocation("diamond_" + TOOL_NAME[i])));
			}
		}

		currentFolder = "enchantments/";
		// enchantments
		{
			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ENCH_PROJECTILE.get(), 1)::unlockedBy, LCItems.FORCE_FIELD.get())
					.pattern("1B1").pattern("BCB").pattern("2B2")
					.define('1', new EnchantmentIngredient(Enchantments.PROJECTILE_PROTECTION, 4))
					.define('2', new EnchantmentIngredient(Enchantments.ALL_DAMAGE_PROTECTION, 4))
					.define('B', LCItems.FORCE_FIELD.get())
					.define('C', new EnchantmentIngredient(Enchantments.INFINITY_ARROWS, 1))
					.save(pvd, getID(LCEnchantments.ENCH_PROJECTILE.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ENCH_EXPLOSION.get(), 1)::unlockedBy, LCItems.EXPLOSION_SHARD.get())
					.pattern("1B1").pattern("BCB").pattern("2B2")
					.define('1', new EnchantmentIngredient(Enchantments.BLAST_PROTECTION, 4))
					.define('2', new EnchantmentIngredient(Enchantments.ALL_DAMAGE_PROTECTION, 4))
					.define('B', LCItems.EXPLOSION_SHARD.get())
					.define('C', Items.CRYING_OBSIDIAN)
					.save(pvd, getID(LCEnchantments.ENCH_EXPLOSION.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ENCH_FIRE.get(), 1)::unlockedBy, LCItems.SUN_MEMBRANE.get())
					.pattern("1B1").pattern("BCB").pattern("2B2")
					.define('1', new EnchantmentIngredient(Enchantments.FIRE_PROTECTION, 4))
					.define('2', new EnchantmentIngredient(Enchantments.ALL_DAMAGE_PROTECTION, 4))
					.define('B', LCItems.SOUL_FLAME.get())
					.define('C', LCItems.HARD_ICE.get())
					.save(pvd, getID(LCEnchantments.ENCH_FIRE.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ENCH_ENVIRONMENT.get(), 1)::unlockedBy, LCItems.VOID_EYE.get())
					.pattern("1B1").pattern("BCB").pattern("2B2")
					.define('1', LCItems.SUN_MEMBRANE.get())
					.define('2', new EnchantmentIngredient(Enchantments.ALL_DAMAGE_PROTECTION, 4))
					.define('B', LCItems.VOID_EYE.get())
					.define('C', LCItems.CAPTURED_WIND.get())
					.save(pvd, getID(LCEnchantments.ENCH_ENVIRONMENT.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ENCH_MAGIC.get(), 1)::unlockedBy, LCItems.RESONANT_FEATHER.get())
					.pattern("1B1").pattern("BCB").pattern("2B2")
					.define('1', LCItems.VOID_EYE.get())
					.define('2', new EnchantmentIngredient(Enchantments.ALL_DAMAGE_PROTECTION, 4))
					.define('B', LCItems.RESONANT_FEATHER.get())
					.define('C', LCItems.FORCE_FIELD.get())
					.save(pvd, getID(LCEnchantments.ENCH_MAGIC.get()));
		}
	}

	private static ResourceLocation getID(Enchantment item) {
		return new ResourceLocation(L2Complements.MODID, currentFolder + ForgeRegistries.ENCHANTMENTS.getKey(item).getPath());
	}

	private static ResourceLocation getID(Item item) {
		return new ResourceLocation(L2Complements.MODID, currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath());
	}

	private static void storage(RegistrateRecipeProvider pvd, ItemEntry<?> nugget, ItemEntry<?> ingot, BlockEntry<?> block) {
		storage(pvd, nugget::get, ingot::get);
		storage(pvd, ingot::get, block::get);
	}

	public static void storage(RegistrateRecipeProvider pvd, NonNullSupplier<ItemLike> from, NonNullSupplier<ItemLike> to) {
		unlock(pvd, new ShapedRecipeBuilder(to.get(), 1)::unlockedBy, from.get().asItem())
				.pattern("XXX").pattern("XXX").pattern("XXX").define('X', from.get())
				.save(pvd, getID(to.get().asItem()) + "_storage");
		unlock(pvd, new ShapelessRecipeBuilder(from.get(), 9)::unlockedBy, to.get().asItem())
				.requires(to.get()).save(pvd, getID(to.get().asItem()) + "_unpack");
	}

	public static void genTools(RegistrateRecipeProvider pvd, LCMats mat, ItemEntry<?>[] arr) {
		currentFolder = "generated_tools/" + mat.name().toLowerCase(Locale.ROOT) + "/craft/";
		{
			Item ingot = mat.getIngot();
			unlock(pvd, new ShapedRecipeBuilder(arr[0].get(), 1)::unlockedBy, arr[0].get())
					.pattern("A A").pattern("A A").define('A', ingot).save(pvd, getID(arr[0].get()));
			unlock(pvd, new ShapedRecipeBuilder(arr[1].get(), 1)::unlockedBy, arr[1].get())
					.pattern("AAA").pattern("A A").pattern("A A").define('A', ingot).save(pvd, getID(arr[1].get()));
			unlock(pvd, new ShapedRecipeBuilder(arr[2].get(), 1)::unlockedBy, arr[2].get())
					.pattern("A A").pattern("AAA").pattern("AAA").define('A', ingot).save(pvd, getID(arr[2].get()));
			unlock(pvd, new ShapedRecipeBuilder(arr[3].get(), 1)::unlockedBy, arr[3].get())
					.pattern("AAA").pattern("A A").define('A', ingot).save(pvd, getID(arr[3].get()));
		}
		{

			Item ingot = mat.getToolIngot();
			Item stick = mat.getToolStick();
			unlock(pvd, new ShapedRecipeBuilder(arr[4].get(), 1)::unlockedBy, arr[4].get())
					.pattern("A").pattern("A").pattern("B").define('A', ingot).define('B', stick).save(pvd, getID(arr[4].get()));
			unlock(pvd, new ShapedRecipeBuilder(arr[5].get(), 1)::unlockedBy, arr[5].get())
					.pattern("AA").pattern("AB").pattern(" B").define('A', ingot).define('B', stick).save(pvd, getID(arr[5].get()));
			unlock(pvd, new ShapedRecipeBuilder(arr[6].get(), 1)::unlockedBy, arr[6].get())
					.pattern("A").pattern("B").pattern("B").define('A', ingot).define('B', stick).save(pvd, getID(arr[6].get()));
			unlock(pvd, new ShapedRecipeBuilder(arr[7].get(), 1)::unlockedBy, arr[7].get())
					.pattern("AAA").pattern(" B ").pattern(" B ").define('A', ingot).define('B', stick).save(pvd, getID(arr[7].get()));
			unlock(pvd, new ShapedRecipeBuilder(arr[8].get(), 1)::unlockedBy, arr[8].get())
					.pattern("AA").pattern(" B").pattern(" B").define('A', ingot).define('B', stick).save(pvd, getID(arr[8].get()));
		}
		currentFolder = "generated_tools/" + mat.name().toLowerCase(Locale.ROOT) + "/recycle/";
		Item nugget = mat.getNugget();
		for (int i = 0; i < 9; i++) {
			smelting(pvd, arr[i].get(), nugget, 0.1f);
		}
		currentFolder = "generated_tools/" + mat.name().toLowerCase(Locale.ROOT) + "/upgrade/";
		Item block = mat.getBlock().asItem();
		for (int i = 0; i < 9; i++) {
			smithing(pvd, TOOLS[i], block, arr[i].get());
		}

	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

	public static void smithing(RegistrateRecipeProvider pvd, TagKey<Item> in, Item mat, Item out) {
		unlock(pvd, UpgradeRecipeBuilder.smithing(Ingredient.of(in), Ingredient.of(mat), out)::unlocks, mat).save(pvd, getID(out));
	}

	public static void smelting(RegistrateRecipeProvider pvd, Item source, Item result, float experience) {
		unlock(pvd, SimpleCookingRecipeBuilder.cooking(Ingredient.of(source), result, experience, 200, RecipeSerializer.SMELTING_RECIPE)::unlockedBy, source)
				.save(pvd, getID(source));
	}

	public static void blasting(RegistrateRecipeProvider pvd, Item source, Item result, float experience) {
		unlock(pvd, SimpleCookingRecipeBuilder.cooking(Ingredient.of(source), result, experience, 200, RecipeSerializer.BLASTING_RECIPE)::unlockedBy, source)
				.save(pvd, getID(source));
	}

}
