package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.content.enchantment.core.EnchantmentRecipeBuilder;
import dev.xkmc.l2complements.content.recipe.BurntRecipeBuilder;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.materials.LCMats;
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
import net.minecraft.tags.ItemTags;
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

			unlock(pvd, new ShapelessRecipeBuilder(LCItems.FRAGILE_WARP_STONE.get(), 1)::unlockedBy, LCItems.VOID_EYE.get())
					.requires(Items.ECHO_SHARD).requires(LCItems.VOID_EYE.get(), 1).requires(Items.ENDER_PEARL)
					.save(pvd, getID(LCItems.FRAGILE_WARP_STONE.get()));

			smithing(pvd, LCItems.FRAGILE_WARP_STONE.get(), LCMats.SHULKERATE.getIngot(), LCItems.REINFORCED_WARP_STONE.get());

			unlock(pvd, new ShapedRecipeBuilder(LCItems.TOTEM_OF_DREAM.get(), 1)::unlockedBy, LCItems.FRAGILE_WARP_STONE.get())
					.pattern("CAC").pattern("ABA").pattern("CAC")
					.define('A', LCMats.TOTEMIC_GOLD.getIngot())
					.define('B', LCItems.FRAGILE_WARP_STONE.get())
					.define('C', Items.ENDER_PEARL)
					.save(pvd, getID(LCItems.TOTEM_OF_DREAM.get()));

			unlock(pvd, new ShapedRecipeBuilder(LCItems.TOTEM_OF_THE_SEA.get(), 1)::unlockedBy, LCMats.POSEIDITE.getIngot())
					.pattern("CAC").pattern("ABA").pattern("CAC")
					.define('A', LCMats.TOTEMIC_GOLD.getIngot())
					.define('B', Items.HEART_OF_THE_SEA)
					.define('C', LCMats.POSEIDITE.getIngot())
					.save(pvd, getID(LCItems.TOTEM_OF_THE_SEA.get()));

			unlock(pvd, new ShapelessRecipeBuilder(LCItems.SOUL_CHARGE.get(), 2)::unlockedBy, Items.BLAZE_POWDER)
					.requires(ItemTags.SOUL_FIRE_BASE_BLOCKS)
					.requires(Items.BLAZE_POWDER, 2)
					.requires(Items.GUNPOWDER, 2)
					.save(pvd, getID(LCItems.SOUL_CHARGE.get()));

			unlock(pvd, new ShapelessRecipeBuilder(LCItems.BLACK_CHARGE.get(), 2)::unlockedBy, Items.BLAZE_POWDER)
					.requires(Items.BLACKSTONE)
					.requires(Items.BLAZE_POWDER, 2)
					.requires(Items.GUNPOWDER, 2)
					.save(pvd, getID(LCItems.BLACK_CHARGE.get()));

			unlock(pvd, new ShapelessRecipeBuilder(LCItems.STRONG_CHARGE.get(), 2)::unlockedBy, Items.BLAZE_POWDER)
					.requires(Ingredient.of(Items.COAL, Items.CHARCOAL))
					.requires(Items.BLAZE_POWDER, 2)
					.requires(Items.GUNPOWDER, 2)
					.save(pvd, getID(LCItems.STRONG_CHARGE.get()));


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
			unlock(pvd, new ShapedRecipeBuilder(Items.REINFORCED_DEEPSLATE, 1)::unlockedBy, LCItems.WARDEN_BONE_SHARD.get())
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

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SHULKER_ARMOR.get(), 1)::unlockedBy, LCMats.SHULKERATE.getNugget())
					.pattern("LCL").pattern("ABA").pattern("LAL")
					.define('A', LCMats.SHULKERATE.getNugget())
					.define('B', Items.BOOK)
					.define('C', Items.GLASS)
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LCEnchantments.SHULKER_ARMOR.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.STABLE_BODY.get(), 1)::unlockedBy, LCMats.SHULKERATE.getIngot())
					.pattern("LCL").pattern("ABA").pattern("LAL")
					.define('A', LCMats.SHULKERATE.getIngot())
					.define('B', Items.BOOK)
					.define('C', LCMats.SCULKIUM.getIngot())
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LCEnchantments.STABLE_BODY.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.LIFE_SYNC.get(), 1)::unlockedBy, LCMats.ETERNIUM.getIngot())
					.pattern("LCL").pattern("ABA").pattern("LAL")
					.define('A', LCMats.ETERNIUM.getIngot())
					.define('B', Items.BOOK)
					.define('C', LCItems.FORCE_FIELD.get())
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LCEnchantments.LIFE_SYNC.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.WIND_SWEEP.get(), 1)::unlockedBy, LCMats.SHULKERATE.getNugget())
					.pattern("LCL").pattern("ABA").pattern("LAL")
					.define('A', LCMats.SHULKERATE.getNugget())
					.define('B', Items.BOOK)
					.define('C', LCItems.STORM_CORE.get())
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LCEnchantments.WIND_SWEEP.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ENDER_MASK.get(), 1)::unlockedBy, Items.ENDER_EYE)
					.pattern("LAL").pattern("ABA").pattern("LAL")
					.define('A', Items.ENDER_EYE)
					.define('B', Items.BOOK)
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LCEnchantments.ENDER_MASK.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SHINNY.get(), 1)::unlockedBy, Items.GOLD_INGOT)
					.pattern("LAL").pattern("ABA").pattern("LAL")
					.define('A', Items.GOLD_INGOT)
					.define('B', Items.BOOK)
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LCEnchantments.SHINNY.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SNOW_WALKER.get(), 1)::unlockedBy, Items.LEATHER)
					.pattern("LAL").pattern("ABA").pattern("LAL")
					.define('A', Items.LEATHER)
					.define('B', Items.BOOK)
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LCEnchantments.SNOW_WALKER.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SOUL_BOUND.get(), 1)::unlockedBy, LCItems.VOID_EYE.get())
					.pattern("LAL").pattern("ABA").pattern("LAL")
					.define('A', LCItems.VOID_EYE.get())
					.define('B', new EnchantmentIngredient(Enchantments.BINDING_CURSE, 1))
					.define('L', Items.ECHO_SHARD)
					.save(pvd, getID(LCEnchantments.SOUL_BOUND.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.DAMPENED.get(), 1)::unlockedBy, LCMats.SCULKIUM.getNugget())
					.pattern("LAL").pattern("ABA").pattern("LAL")
					.define('A', LCMats.SCULKIUM.getNugget())
					.define('B', Items.BOOK)
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LCEnchantments.DAMPENED.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ICE_BLADE.get(), 1)::unlockedBy, LCItems.HARD_ICE.get())
					.pattern("LAL").pattern("ABA").pattern("LAL")
					.define('A', LCItems.HARD_ICE.get())
					.define('B', Items.BOOK)
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LCEnchantments.ICE_BLADE.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.FLAME_BLADE.get(), 1)::unlockedBy, LCItems.SOUL_FLAME.get())
					.pattern("LAL").pattern("ABA").pattern("LAL")
					.define('A', LCItems.SOUL_FLAME.get())
					.define('B', new EnchantmentIngredient(Enchantments.FIRE_ASPECT, 2))
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LCEnchantments.FLAME_BLADE.get()));

			unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ENDER.get(), 1)::unlockedBy, LCItems.VOID_EYE.get())
					.pattern("LCL").pattern("ABA").pattern("LAL")
					.define('A', Items.ENDER_EYE)
					.define('C', LCItems.VOID_EYE.get())
					.define('B', Items.BOOK)
					.define('L', Items.LAPIS_LAZULI)
					.save(pvd, getID(LCEnchantments.ENDER.get()));
		}

		currentFolder = "burnt/";
		{
			convert(pvd, Items.EMERALD, LCItems.EMERALD.get(), 64 * 27 * 9);
			convert(pvd, Items.EMERALD_BLOCK, LCItems.EMERALD.get(), 64 * 27);
			convert(pvd, Items.ROTTEN_FLESH, LCItems.CURSED_DROPLET.get(), 64 * 27 * 9);
		}

		currentFolder = "eggs/";
		{
			unlock(pvd, new ShapedRecipeBuilder(Items.ZOMBIE_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
					.pattern("AAA").pattern("ABA").pattern("ACA")
					.define('A', Items.ROTTEN_FLESH)
					.define('B', LCItems.CURSED_DROPLET.get())
					.define('C', Items.EGG)
					.save(pvd, getID(Items.ZOMBIE_SPAWN_EGG));

			unlock(pvd, new ShapedRecipeBuilder(Items.HUSK_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
					.pattern("ADA").pattern("ABA").pattern("ACA")
					.define('A', Items.ROTTEN_FLESH)
					.define('B', LCItems.CURSED_DROPLET.get())
					.define('C', Items.EGG)
					.define('D', Items.SAND)
					.save(pvd, getID(Items.HUSK_SPAWN_EGG));

			unlock(pvd, new ShapedRecipeBuilder(Items.DROWNED_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
					.pattern("ADA").pattern("ABA").pattern("ACA")
					.define('A', Items.ROTTEN_FLESH)
					.define('B', LCItems.CURSED_DROPLET.get())
					.define('C', Items.EGG)
					.define('D', Items.KELP)
					.save(pvd, getID(Items.DROWNED_SPAWN_EGG));

			unlock(pvd, new ShapedRecipeBuilder(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
					.pattern("ADA").pattern("ABA").pattern("ACA")
					.define('A', Items.ROTTEN_FLESH)
					.define('B', LCItems.CURSED_DROPLET.get())
					.define('C', Items.EGG)
					.define('D', Items.GOLD_INGOT)
					.save(pvd, getID(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG));

			unlock(pvd, new ShapedRecipeBuilder(Items.SKELETON_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
					.pattern("AAA").pattern("ABA").pattern("ACA")
					.define('A', Items.BONE)
					.define('B', LCItems.CURSED_DROPLET.get())
					.define('C', Items.EGG)
					.save(pvd, getID(Items.SKELETON_SPAWN_EGG));

			unlock(pvd, new ShapedRecipeBuilder(Items.STRAY_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
					.pattern("ADA").pattern("ABA").pattern("ACA")
					.define('A', Items.BONE)
					.define('B', LCItems.CURSED_DROPLET.get())
					.define('C', Items.EGG)
					.define('D', Items.SNOWBALL)
					.save(pvd, getID(Items.STRAY_SPAWN_EGG));

			unlock(pvd, new ShapedRecipeBuilder(Items.WITHER_SKELETON_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
					.pattern("ADA").pattern("ABA").pattern("ACA")
					.define('A', Items.BONE)
					.define('B', LCItems.CURSED_DROPLET.get())
					.define('C', Items.EGG)
					.define('D', Items.WITHER_SKELETON_SKULL)
					.save(pvd, getID(Items.WITHER_SKELETON_SPAWN_EGG));

			unlock(pvd, new ShapedRecipeBuilder(Items.PHANTOM_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
					.pattern("AAA").pattern("ABA").pattern("ACA")
					.define('A', Items.PHANTOM_MEMBRANE)
					.define('B', LCItems.CURSED_DROPLET.get())
					.define('C', Items.EGG)
					.save(pvd, getID(Items.PHANTOM_SPAWN_EGG));
		}

	}

	private static ResourceLocation getID(Enchantment item) {
		return new ResourceLocation(L2Complements.MODID, currentFolder + ForgeRegistries.ENCHANTMENTS.getKey(item).getPath());
	}

	private static ResourceLocation getID(Item item) {
		return new ResourceLocation(L2Complements.MODID, currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath());
	}

	private static void convert(RegistrateRecipeProvider pvd, Item in, Item out, int count) {
		unlock(pvd, new BurntRecipeBuilder(Ingredient.of(in), out.getDefaultInstance(), count)::unlockedBy, in).save(pvd, getID(in));
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

	public static void smithing(RegistrateRecipeProvider pvd, Item in, Item mat, Item out) {
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
