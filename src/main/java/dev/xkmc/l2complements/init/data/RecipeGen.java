package dev.xkmc.l2complements.init.data;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2complements.content.enchantment.core.EnchantmentRecipeBuilder;
import dev.xkmc.l2complements.content.recipe.BurntRecipeBuilder;
import dev.xkmc.l2complements.content.recipe.DiffusionRecipeBuilder;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2complements.init.registrate.LCBlocks;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.serial.configval.BooleanValueCondition;
import dev.xkmc.l2core.serial.ingredients.EnchantmentIngredient;
import dev.xkmc.l2core.serial.recipe.ConditionalRecipeWrapper;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;

import static dev.xkmc.l2core.serial.recipe.AbstractSmithingRecipe.TEMPLATE_PLACEHOLDER;

public class RecipeGen {

	@SuppressWarnings("unchecked")
	private static final TagKey<Item>[] TOOLS = List.of(
			ItemTags.HEAD_ARMOR,
			ItemTags.CHEST_ARMOR,
			ItemTags.LEG_ARMOR,
			ItemTags.FOOT_ARMOR,
			ItemTags.SWORDS,
			ItemTags.AXES,
			ItemTags.SHOVELS,
			ItemTags.PICKAXES,
			ItemTags.HOES
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
			unlock(pvd, new ShapelessRecipeBuilder(RecipeCategory.MISC, LCItems.WIND_BOTTLE.get(), 1)::unlockedBy, Items.GLASS_BOTTLE)
					.requires(Items.GLASS_BOTTLE)
					.requires(Items.PHANTOM_MEMBRANE)
					.save(pvd, getID(LCItems.WIND_BOTTLE.get()));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCBlocks.ETERNAL_ANVIL.get(), 1)::unlockedBy, LCMats.ETERNIUM.getIngot())
					.pattern("AAA").pattern(" B ").pattern("BBB")
					.define('A', LCMats.ETERNIUM.getBlock())
					.define('B', LCMats.ETERNIUM.getIngot())
					.save(pvd, getID(LCBlocks.ETERNAL_ANVIL.get().asItem()));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCMats.ETERNIUM.getNugget(), 1)::unlockedBy, LCItems.EXPLOSION_SHARD.get())
					.pattern("3C4").pattern("BAB").pattern("1C2")
					.define('A', LCItems.EXPLOSION_SHARD.get())
					.define('B', Items.ANVIL)
					.define('C', Items.ENDER_PEARL)
					.define('1', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.MENDING, 1))
					.define('2', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.INFINITY, 1))
					.define('3', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.PROTECTION, 4))
					.define('4', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.UNBREAKING, 3))
					.save(pvd, getID(LCMats.ETERNIUM.getNugget()));

			blasting(pvd, Items.TOTEM_OF_UNDYING, LCMats.TOTEMIC_GOLD.getIngot(), 1);
			blasting(pvd, Items.TRIDENT, LCMats.POSEIDITE.getIngot(), 1);

			unlock(pvd, new ShapelessRecipeBuilder(RecipeCategory.MISC, LCMats.SHULKERATE.getIngot(), 1)::unlockedBy, LCItems.CAPTURED_BULLET.get())
					.requires(Items.SHULKER_SHELL, 2).requires(LCItems.CAPTURED_BULLET.get()).requires(Items.IRON_INGOT)
					.save(pvd, getID(LCMats.SHULKERATE.getIngot()));

			unlock(pvd, new ShapelessRecipeBuilder(RecipeCategory.MISC, LCMats.SCULKIUM.getIngot(), 2)::unlockedBy, LCItems.WARDEN_BONE_SHARD.get())
					.requires(Items.ECHO_SHARD).requires(LCItems.WARDEN_BONE_SHARD.get(), 2).requires(Items.COPPER_INGOT)
					.save(pvd, getID(LCMats.SCULKIUM.getIngot()));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCItems.GUARDIAN_RUNE.get(), 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
					.pattern("DAD").pattern("CBC").pattern("DCD")
					.define('A', LCItems.CURSED_DROPLET.get())
					.define('B', LCMats.POSEIDITE.getIngot())
					.define('C', Items.NAUTILUS_SHELL)
					.define('D', Items.PRISMARINE_SHARD)
					.save(pvd, getID(LCItems.GUARDIAN_RUNE.get()));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCItems.PIGLIN_RUNE.get(), 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
					.pattern("DAD").pattern("CBC").pattern("DCD")
					.define('A', LCItems.CURSED_DROPLET.get())
					.define('B', Items.NETHER_STAR)
					.define('C', Items.NETHERITE_SCRAP)
					.define('D', Items.BLACKSTONE)
					.save(pvd, getID(LCItems.PIGLIN_RUNE.get()));

			unlock(pvd, new ShapelessRecipeBuilder(RecipeCategory.MISC, LCItems.FRAGILE_WARP_STONE.get(), 1)::unlockedBy, LCItems.VOID_EYE.get())
					.requires(Items.ECHO_SHARD).requires(LCItems.VOID_EYE.get(), 1).requires(Items.ENDER_PEARL)
					.save(pvd, getID(LCItems.FRAGILE_WARP_STONE.get()));

			smithing(pvd, LCItems.FRAGILE_WARP_STONE.get(), LCMats.SHULKERATE.getIngot(), LCItems.REINFORCED_WARP_STONE.get());

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCItems.TOTEM_OF_DREAM.get(), 1)::unlockedBy, LCItems.FRAGILE_WARP_STONE.get())
					.pattern("CAC").pattern("ABA").pattern("CAC")
					.define('A', LCMats.TOTEMIC_GOLD.getIngot())
					.define('B', LCItems.FRAGILE_WARP_STONE.get())
					.define('C', Items.ENDER_PEARL)
					.save(pvd, getID(LCItems.TOTEM_OF_DREAM.get()));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCItems.TOTEM_OF_THE_SEA.get(), 1)::unlockedBy, LCMats.POSEIDITE.getIngot())
					.pattern("ACA").pattern("ABA").pattern("ACA")
					.define('A', LCMats.TOTEMIC_GOLD.getIngot())
					.define('B', Items.HEART_OF_THE_SEA)
					.define('C', LCMats.POSEIDITE.getIngot())
					.save(pvd, getID(LCItems.TOTEM_OF_THE_SEA.get()));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCItems.ETERNAL_TOTEM.get(), 1)::unlockedBy, LCItems.FRAGILE_WARP_STONE.get())
					.pattern("ACA").pattern("ABA").pattern("ACA")
					.define('A', LCMats.ETERNIUM.getIngot())
					.define('B', LCItems.TOTEM_OF_DREAM.get())
					.define('C', LCItems.FORCE_FIELD)
					.save(pvd, getID(LCItems.ETERNAL_TOTEM.get()));

			unlock(pvd, new ShapelessRecipeBuilder(RecipeCategory.MISC, LCItems.SOUL_CHARGE.get(), 2)::unlockedBy, Items.BLAZE_POWDER)
					.requires(ItemTags.SOUL_FIRE_BASE_BLOCKS)
					.requires(Items.BLAZE_POWDER, 2)
					.requires(Items.GUNPOWDER, 2)
					.save(pvd, getID(LCItems.SOUL_CHARGE.get()));

			unlock(pvd, new ShapelessRecipeBuilder(RecipeCategory.MISC, LCItems.BLACK_CHARGE.get(), 2)::unlockedBy, Items.BLAZE_POWDER)
					.requires(Items.BLACKSTONE)
					.requires(Items.BLAZE_POWDER, 2)
					.requires(Items.GUNPOWDER, 2)
					.save(pvd, getID(LCItems.BLACK_CHARGE.get()));

			unlock(pvd, new ShapelessRecipeBuilder(RecipeCategory.MISC, LCItems.STRONG_CHARGE.get(), 2)::unlockedBy, Items.BLAZE_POWDER)
					.requires(Ingredient.of(Items.COAL, Items.CHARCOAL))
					.requires(Items.BLAZE_POWDER, 2)
					.requires(Items.GUNPOWDER, 2)
					.save(pvd, getID(LCItems.STRONG_CHARGE.get()));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCItems.TOTEMIC_CARROT.get(), 1)::unlockedBy, LCMats.TOTEMIC_GOLD.getIngot())
					.pattern("AAA").pattern("ABA").pattern("AAA")
					.define('A', LCMats.TOTEMIC_GOLD.getNugget())
					.define('B', Items.CARROT)
					.save(pvd, getID(LCItems.TOTEMIC_CARROT.get()));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCItems.ENCHANT_TOTEMIC_CARROT.get(), 1)::unlockedBy, LCMats.TOTEMIC_GOLD.getIngot())
					.pattern("ACA").pattern("ABA").pattern("AAA")
					.define('A', LCMats.TOTEMIC_GOLD.getIngot())
					.define('B', Items.CARROT)
					.define('C', LCItems.LIFE_ESSENCE.get())
					.save(pvd, getID(LCItems.ENCHANT_TOTEMIC_CARROT.get()));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCItems.TOTEMIC_APPLE.get(), 1)::unlockedBy, LCMats.TOTEMIC_GOLD.getIngot())
					.pattern("ACA").pattern("ABA").pattern("AAA")
					.define('A', LCMats.TOTEMIC_GOLD.getIngot())
					.define('B', Items.APPLE)
					.define('C', LCItems.LIFE_ESSENCE.get())
					.save(pvd, getID(LCItems.TOTEMIC_APPLE.get()));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCItems.ENCHANTED_TOTEMIC_APPLE.get(), 1)::unlockedBy, LCMats.TOTEMIC_GOLD.getIngot())
					.pattern("ACA").pattern("CBC").pattern("ACA")
					.define('A', LCMats.TOTEMIC_GOLD.getBlock())
					.define('B', Items.APPLE)
					.define('C', LCItems.LIFE_ESSENCE.get())
					.save(pvd, getID(LCItems.ENCHANTED_TOTEMIC_APPLE.get()));

			unlock(pvd, new ShapelessRecipeBuilder(RecipeCategory.MISC, LCItems.WARDEN_BONE_SHARD.get(), 1)::unlockedBy, LCItems.RESONANT_FEATHER.get())
					.requires(LCTagGen.DELICATE_BONE)
					.requires(LCItems.RESONANT_FEATHER.get())
					.save(pvd, getID(LCItems.WARDEN_BONE_SHARD.get()));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCItems.SONIC_SHOOTER.get(), 1)::unlockedBy, LCMats.SCULKIUM.getIngot())
					.pattern("ABB").pattern("III").pattern("IC ")
					.define('I', LCMats.SCULKIUM.getIngot())
					.define('A', LCItems.VOID_EYE)
					.define('B', LCItems.RESONANT_FEATHER.get())
					.define('C', LCItems.EXPLOSION_SHARD.get())
					.save(pvd, getID(LCItems.SONIC_SHOOTER.get()));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCItems.HELLFIRE_WAND.get(), 1)::unlockedBy, LCItems.SUN_MEMBRANE.get())
					.pattern(" FM").pattern(" CF").pattern("C  ")
					.define('F', LCItems.SOUL_FLAME.get())
					.define('M', LCItems.SUN_MEMBRANE.get())
					.define('C', LCItems.EXPLOSION_SHARD.get())
					.save(pvd, getID(LCItems.HELLFIRE_WAND.get()));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCItems.WINTERSTORM_WAND.get(), 1)::unlockedBy, LCItems.HARD_ICE.get())
					.pattern(" FM").pattern(" CF").pattern("C  ")
					.define('F', LCItems.HARD_ICE.get())
					.define('M', LCItems.STORM_CORE.get())
					.define('C', Items.STICK)
					.save(pvd, getID(LCItems.WINTERSTORM_WAND.get()));


			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, LCItems.DIFFUSION_WAND.get(), 1)::unlockedBy, LCItems.STORM_CORE.get())
					.pattern(" FM").pattern(" CF").pattern("C  ")
					.define('F', Items.DIAMOND)
					.define('M', LCItems.STORM_CORE.get())
					.define('C', Items.STICK)
					.save(pvd, getID(LCItems.DIFFUSION_WAND.get()));
		}

		currentFolder = "vanilla/renew/";
		// misc
		{
			var cond = ConditionalRecipeWrapper.of(pvd, BooleanValueCondition.of(LCConfig.SERVER.getPath(), LCConfig.SERVER.enableVanillaItemRecipe, true));


			unlock(pvd, new ShapelessRecipeBuilder(RecipeCategory.MISC, Items.ECHO_SHARD, 1)::unlockedBy, LCItems.RESONANT_FEATHER.get())
					.requires(LCItems.RESONANT_FEATHER.get())
					.requires(Items.AMETHYST_SHARD)
					.requires(Items.SCULK, 4)
					.save(cond, getID(Items.ECHO_SHARD));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.ELYTRA, 1)::unlockedBy, LCItems.SUN_MEMBRANE.get())
					.pattern("ABA").pattern("CEC").pattern("D D")
					.define('A', LCItems.EXPLOSION_SHARD.get())
					.define('B', LCItems.CAPTURED_WIND.get())
					.define('C', LCItems.SUN_MEMBRANE.get())
					.define('D', LCItems.RESONANT_FEATHER.get())
					.define('E', LCItems.STORM_CORE.get())
					.save(cond, getID(Items.ELYTRA));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.ANCIENT_DEBRIS, 1)::unlockedBy, LCItems.EXPLOSION_SHARD.get())
					.pattern("ABA").pattern("ACA").pattern("ADA")
					.define('A', LCItems.EXPLOSION_SHARD.get())
					.define('B', Items.NETHER_STAR)
					.define('C', Items.CRYING_OBSIDIAN)
					.define('D', LCItems.FORCE_FIELD.get())
					.save(cond, getID(Items.ANCIENT_DEBRIS));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.GILDED_BLACKSTONE, 1)::unlockedBy, LCItems.BLACKSTONE_CORE.get())
					.pattern("ABA").pattern("BCB").pattern("ABA")
					.define('A', Items.BLACKSTONE)
					.define('B', Items.GOLD_INGOT)
					.define('C', LCItems.BLACKSTONE_CORE.get())
					.save(cond, getID(Items.GILDED_BLACKSTONE));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.REINFORCED_DEEPSLATE, 1)::unlockedBy, LCItems.WARDEN_BONE_SHARD.get())
					.pattern(" B ").pattern("BAB").pattern(" B ")
					.define('A', Items.DEEPSLATE)
					.define('B', LCItems.WARDEN_BONE_SHARD.get())
					.save(cond, getID(Items.REINFORCED_DEEPSLATE));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.HEART_OF_THE_SEA, 1)::unlockedBy, LCItems.GUARDIAN_EYE.get())
					.pattern("ABA").pattern("BCB").pattern("ABA")
					.define('A', Items.PRISMARINE_SHARD)
					.define('B', Items.PRISMARINE_CRYSTALS)
					.define('C', LCItems.GUARDIAN_EYE.get())
					.save(cond, getID(Items.HEART_OF_THE_SEA));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1)::unlockedBy, LCItems.BLACKSTONE_CORE.get())
					.pattern("BAB").pattern("BCB").pattern("BBB")
					.define('B', Items.DIAMOND)
					.define('C', Items.NETHERRACK)
					.define('A', LCItems.BLACKSTONE_CORE.get())
					.save(cond, getID(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE));
		}

		currentFolder = "vanilla/upgrade/";
		{

			var cond = ConditionalRecipeWrapper.of(pvd, BooleanValueCondition.of(LCConfig.SERVER.getPath(), LCConfig.SERVER.enableToolRecraftRecipe, true));

			for (int i = 0; i < 9; i++) {
				smithing(pvd, TOOLS[i], Items.IRON_BLOCK, BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace("iron_" + TOOL_NAME[i])), cond);
				smithing(pvd, TOOLS[i], Items.GOLD_BLOCK, BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace("golden_" + TOOL_NAME[i])), cond);
				smithing(pvd, TOOLS[i], Items.DIAMOND_BLOCK, BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace("diamond_" + TOOL_NAME[i])), cond);
			}
		}

		currentFolder = "enchantments/";
		// enchantments
		{

			// protections
			{

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.IMM_PROJECTILE.holder(), 1)::unlockedBy, LCItems.FORCE_FIELD.get())
						.pattern("1B1").pattern("BCB").pattern("2B2")
						.define('1', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.PROJECTILE_PROTECTION, 4))
						.define('2', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.PROTECTION, 4))
						.define('B', LCItems.FORCE_FIELD.get())
						.define('C', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.INFINITY, 1))
						.save(pvd, getID(LCEnchantments.IMM_PROJECTILE.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.IMM_EXPLOSION.holder(), 1)::unlockedBy, LCItems.EXPLOSION_SHARD.get())
						.pattern("1B1").pattern("BCB").pattern("2B2")
						.define('1', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.BLAST_PROTECTION, 4))
						.define('2', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.PROTECTION, 4))
						.define('B', LCItems.EXPLOSION_SHARD.get())
						.define('C', Items.CRYING_OBSIDIAN)
						.save(pvd, getID(LCEnchantments.IMM_EXPLOSION.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.IMM_FIRE.holder(), 1)::unlockedBy, LCItems.SUN_MEMBRANE.get())
						.pattern("1B1").pattern("BCB").pattern("2B2")
						.define('1', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.FIRE_PROTECTION, 4))
						.define('2', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.PROTECTION, 4))
						.define('B', LCItems.SOUL_FLAME.get())
						.define('C', LCItems.HARD_ICE.get())
						.save(pvd, getID(LCEnchantments.IMM_FIRE.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.IMM_ENVIRONMENT.holder(), 1)::unlockedBy, LCItems.VOID_EYE.get())
						.pattern("1B1").pattern("BCB").pattern("2B2")
						.define('1', LCItems.SUN_MEMBRANE.get())
						.define('2', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.PROTECTION, 4))
						.define('B', LCItems.VOID_EYE.get())
						.define('C', LCItems.CAPTURED_WIND.get())
						.save(pvd, getID(LCEnchantments.IMM_ENVIRONMENT.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.IMM_MAGIC.holder(), 1)::unlockedBy, LCItems.RESONANT_FEATHER.get())
						.pattern("1B1").pattern("BCB").pattern("2B2")
						.define('1', LCItems.VOID_EYE.get())
						.define('2', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.PROTECTION, 4))
						.define('B', LCItems.RESONANT_FEATHER.get())
						.define('C', LCItems.FORCE_FIELD.get())
						.save(pvd, getID(LCEnchantments.IMM_MAGIC.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.INVINCIBLE.holder(), 1)::unlockedBy, LCItems.SPACE_SHARD.get())
						.pattern("A1A").pattern("203").pattern("A4A")
						.define('A', LCItems.SPACE_SHARD.get())
						.define('0', EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.IMM_ENVIRONMENT.id(), 1))
						.define('1', EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.IMM_MAGIC.id(), 1))
						.define('2', EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.IMM_EXPLOSION.id(), 1))
						.define('3', EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.IMM_FIRE.id(), 1))
						.define('4', EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.IMM_PROJECTILE.id(), 1))
						.save(pvd, getID(LCEnchantments.INVINCIBLE.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.IMM_MATES.holder(), 1)::unlockedBy, Items.NETHER_STAR)
						.pattern("BAB").pattern("B1B").pattern("BAB")
						.define('1', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.PROTECTION, 4))
						.define('A', Items.NETHER_STAR)
						.define('B', Items.END_ROD)
						.save(pvd, getID(LCEnchantments.IMM_MATES.id()));
			}

			// misc
			{

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.TRANSPARENT.holder(), 1)::unlockedBy, LCMats.SHULKERATE.getNugget())
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', LCMats.SHULKERATE.getNugget())
						.define('B', Items.BOOK)
						.define('C', Items.GLASS)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.TRANSPARENT.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.STABLE_BODY.holder(), 1)::unlockedBy, Items.CRYING_OBSIDIAN)
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', Items.OBSIDIAN)
						.define('B', Items.BOOK)
						.define('C', Items.CRYING_OBSIDIAN)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.STABLE_BODY.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.LIFE_SYNC.holder(), 1)::unlockedBy, LCItems.FORCE_FIELD.get())
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', Items.WITHER_ROSE)
						.define('B', Items.BOOK)
						.define('C', LCItems.FORCE_FIELD.get())
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.LIFE_SYNC.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.LIFE_MENDING.holder(), 1)::unlockedBy, Items.BOOK)
						.pattern("LCL").pattern("ABA").pattern("FCF")
						.define('A', Items.WEEPING_VINES)
						.define('B', Items.BOOK)
						.define('C', Items.TWISTING_VINES)
						.define('L', Items.LAPIS_LAZULI)
						.define('F', Items.ROTTEN_FLESH)
						.save(pvd, getID(LCEnchantments.LIFE_MENDING.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SAFEGUARD.holder(), 1)::unlockedBy, Items.BOOK)
						.pattern("LCL").pattern("ABA").pattern("FCF")
						.define('A', LCMats.SHULKERATE.getNugget())
						.define('B', Items.BOOK)
						.define('C', Items.AMETHYST_SHARD)
						.define('L', Items.LAPIS_LAZULI)
						.define('F', Items.NETHERITE_SCRAP)
						.save(pvd, getID(LCEnchantments.SAFEGUARD.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ENDER_MASK.holder(), 1)::unlockedBy, Items.ENDER_EYE)
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', Items.ENDER_EYE)
						.define('B', Items.BOOK)
						.define('C', Items.CARVED_PUMPKIN)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.ENDER_MASK.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SHINNY.holder(), 1)::unlockedBy, Items.GOLD_INGOT)
						.pattern("LAL").pattern("ABA").pattern("LAL")
						.define('A', Items.GOLD_INGOT)
						.define('B', Items.BOOK)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.SHINNY.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SNOW_WALKER.holder(), 1)::unlockedBy, Items.LEATHER)
						.pattern("LAL").pattern("ABA").pattern("LAL")
						.define('A', Items.LEATHER)
						.define('B', Items.BOOK)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.SNOW_WALKER.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SOUL_BOUND.holder(), 1)::unlockedBy, LCItems.VOID_EYE.get())
						.pattern("LAL").pattern("ABA").pattern("LAL")
						.define('A', LCItems.VOID_EYE.get())
						.define('B', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.BINDING_CURSE, 1))
						.define('L', Items.ECHO_SHARD)
						.save(pvd, getID(LCEnchantments.SOUL_BOUND.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.DAMPENED.holder(), 1)::unlockedBy, LCMats.SCULKIUM.getNugget())
						.pattern("LAL").pattern("ABA").pattern("LAL")
						.define('A', LCMats.SCULKIUM.getNugget())
						.define('B', Items.BOOK)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.DAMPENED.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ENDER_TOUCH.holder(), 1)::unlockedBy, Items.ENDER_PEARL)
						.pattern("LAL").pattern("ABA").pattern("LCL")
						.define('A', Items.ENDER_PEARL)
						.define('B', Items.BOOK)
						.define('C', Items.HOPPER)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.ENDER_TOUCH.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.HARDENED.holder(), 1)::unlockedBy, LCMats.SHULKERATE.getIngot())
						.pattern("SCS").pattern("ABA").pattern("LAL")
						.define('A', LCMats.SHULKERATE.getIngot())
						.define('S', LCItems.WARDEN_BONE_SHARD.get())
						.define('C', LCItems.EXPLOSION_SHARD.get())
						.define('B', Items.BOOK)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.HARDENED.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ETERNAL.holder(), 1)::unlockedBy, LCItems.SPACE_SHARD.get())
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', LCMats.ETERNIUM.getIngot())
						.define('C', LCItems.SPACE_SHARD.get())
						.define('B', EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.HARDENED.id(), 1))
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.ETERNAL.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.DURABLE_ARMOR.holder(), 1)::unlockedBy, Items.DIAMOND)
						.pattern(" A ").pattern("LBL").pattern(" L ")
						.define('A', Items.DIAMOND)
						.define('B', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.UNBREAKING, 1))
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.DURABLE_ARMOR.id(), "_1"));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.DURABLE_ARMOR.holder(), 2)::unlockedBy, Items.DIAMOND)
						.pattern("LAL").pattern("ABA").pattern("LAL")
						.define('A', Items.DIAMOND)
						.define('B', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.UNBREAKING, 2))
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.DURABLE_ARMOR.id(), "_2"));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.DURABLE_ARMOR.holder(), 3)::unlockedBy, Items.DIAMOND)
						.pattern("L1L").pattern("2B3").pattern("L4L")
						.define('1', Items.DIAMOND_HELMET)
						.define('2', Items.DIAMOND_CHESTPLATE)
						.define('3', Items.DIAMOND_LEGGINGS)
						.define('4', Items.DIAMOND_BOOTS)
						.define('B', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.UNBREAKING, 3))
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.DURABLE_ARMOR.id(), "_3"));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SMELT.holder(), 1)::unlockedBy, LCItems.SOUL_FLAME.get())
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', Items.LAVA_BUCKET)
						.define('C', LCItems.SOUL_FLAME.get())
						.define('B', Items.BOOK)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.SMELT.id()));


			}

			// offencive and defensive
			{

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.WIND_SWEEP.holder(), 1)::unlockedBy, LCMats.SHULKERATE.getNugget())
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', LCMats.SHULKERATE.getNugget())
						.define('B', Items.BOOK)
						.define('C', LCItems.STORM_CORE.get())
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.WIND_SWEEP.id()));


				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ICE_BLADE.holder(), 1)::unlockedBy, LCItems.HARD_ICE.get())
						.pattern("LAL").pattern("ABA").pattern("LAL")
						.define('A', LCItems.HARD_ICE.get())
						.define('B', Items.BOOK)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.ICE_BLADE.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.HELLFIRE_BLADE.holder(), 1)::unlockedBy, LCItems.SOUL_FLAME.get())
						.pattern("LAL").pattern("ABA").pattern("LAL")
						.define('A', LCItems.SOUL_FLAME.get())
						.define('B', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.FIRE_ASPECT, 2))
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.HELLFIRE_BLADE.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ICE_THORN.holder(), 1)::unlockedBy, LCItems.HARD_ICE.get())
						.pattern("LAL").pattern("ABA").pattern("LAL")
						.define('A', LCItems.HARD_ICE.get())
						.define('B', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.THORNS, 3))
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.ICE_THORN.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.HELLFIRE_THORN.holder(), 1)::unlockedBy, LCItems.SOUL_FLAME.get())
						.pattern("LAL").pattern("ABA").pattern("LAL")
						.define('A', LCItems.SOUL_FLAME.get())
						.define('B', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.THORNS, 3))
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.HELLFIRE_THORN.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SHARP_BLADE.holder(), 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', LCItems.EXPLOSION_SHARD.get())
						.define('C', LCItems.CURSED_DROPLET.get())
						.define('B', Items.BOOK)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.SHARP_BLADE.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.CURSE_BLADE.holder(), 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("LCL").pattern("ABA").pattern("LCL")
						.define('A', Items.FERMENTED_SPIDER_EYE)
						.define('C', LCItems.CURSED_DROPLET.get())
						.define('B', Items.BOOK)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.CURSE_BLADE.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.VOID_TOUCH.holder(), 1)::unlockedBy, LCItems.RESONANT_FEATHER.get())
						.pattern("LCL").pattern("ABA").pattern("LCL")
						.define('A', LCItems.VOID_EYE.get())
						.define('C', LCItems.SUN_MEMBRANE.get())
						.define('B', Items.BOOK)
						.define('L', LCItems.RESONANT_FEATHER.get())
						.save(pvd, getID(LCEnchantments.VOID_TOUCH.id()));
			}

			// digging
			{

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.CUBIC.holder(), 1)::unlockedBy, LCItems.STORM_CORE.get())
						.pattern("ECE").pattern("BAB").pattern("DBD")
						.define('A', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.EFFICIENCY, 1))
						.define('B', Items.STONE_PICKAXE)
						.define('C', LCItems.STORM_CORE.get())
						.define('D', Items.LAPIS_LAZULI)
						.define('E', Items.IRON_INGOT)
						.save(pvd, getID(LCEnchantments.CUBIC.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.PLANE.holder(), 1)::unlockedBy, LCItems.STORM_CORE.get())
						.pattern("ECE").pattern("DAD").pattern("BBB")
						.define('A', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.EFFICIENCY, 1))
						.define('B', Items.STONE_HOE)
						.define('C', LCItems.STORM_CORE.get())
						.define('D', Items.LAPIS_LAZULI)
						.define('E', Items.IRON_INGOT)
						.save(pvd, getID(LCEnchantments.PLANE.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.DRILL.holder(), 1)::unlockedBy, LCItems.STORM_CORE.get())
						.pattern("ECB").pattern("DAB").pattern("EDB")
						.define('A', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.EFFICIENCY, 1))
						.define('B', Items.STONE_SHOVEL)
						.define('C', LCItems.STORM_CORE.get())
						.define('D', Items.LAPIS_LAZULI)
						.define('E', Items.IRON_INGOT)
						.save(pvd, getID(LCEnchantments.DRILL.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.VIEN.holder(), 1)::unlockedBy, LCItems.STORM_CORE.get())
						.pattern("ECE").pattern("BAB").pattern("DBD")
						.define('A', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.EFFICIENCY, 1))
						.define('B', Items.IRON_PICKAXE)
						.define('C', LCItems.STORM_CORE.get())
						.define('D', Items.LAPIS_LAZULI)
						.define('E', Items.GOLD_INGOT)
						.save(pvd, getID(LCEnchantments.VIEN.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.TREE.holder(), 1)::unlockedBy, LCItems.STORM_CORE.get())
						.pattern("ECE").pattern("BAB").pattern("DBD")
						.define('A', EnchantmentIngredient.of(pvd.getProvider(), Enchantments.EFFICIENCY, 1))
						.define('B', Items.IRON_AXE)
						.define('C', LCItems.STORM_CORE.get())
						.define('D', Items.LAPIS_LAZULI)
						.define('E', Items.GOLD_INGOT)
						.save(pvd, getID(LCEnchantments.TREE.id()));


				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.CHUNK_CUBIC.holder(), 1)::unlockedBy, LCItems.BLACKSTONE_CORE.get())
						.pattern("ECE").pattern("BAB").pattern("DBD")
						.define('A', EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.CUBIC.id(), 1))
						.define('B', Items.NETHERITE_PICKAXE)
						.define('C', LCItems.BLACKSTONE_CORE.get())
						.define('D', Items.LAPIS_LAZULI)
						.define('E', LCMats.SCULKIUM.getIngot())
						.save(pvd, getID(LCEnchantments.CHUNK_CUBIC.id()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.CHUNK_PLANE.holder(), 1)::unlockedBy, LCItems.BLACKSTONE_CORE.get())
						.pattern("ECE").pattern("DAD").pattern("BBB")
						.define('A', EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.PLANE.id(), 1))
						.define('B', Items.NETHERITE_HOE)
						.define('C', LCItems.BLACKSTONE_CORE.get())
						.define('D', Items.LAPIS_LAZULI)
						.define('E', LCMats.SCULKIUM.getIngot())
						.save(pvd, getID(LCEnchantments.CHUNK_PLANE.id()));

			}


		}

		currentFolder = "burnt/";
		{
			convert(pvd, Items.EMERALD, LCItems.EMERALD.get(), 64 * 27 * 9);
			convert(pvd, Items.EMERALD_BLOCK, LCItems.EMERALD.get(), 64 * 27);
			convert(pvd, Items.ROTTEN_FLESH, LCItems.CURSED_DROPLET.get(), 64 * 27 * 9);
			convert(pvd, Items.SOUL_SAND, LCItems.CURSED_DROPLET.get(), 64 * 27 * 16);
			convert(pvd, Items.SOUL_SOIL, LCItems.CURSED_DROPLET.get(), 64 * 27 * 16);
			convert(pvd, Items.GHAST_TEAR, LCItems.CURSED_DROPLET.get(), 64 * 9);
			convert(pvd, Items.NETHER_STAR, LCItems.CURSED_DROPLET.get(), 64);

			convert(pvd, Items.COOKED_BEEF, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.COOKED_CHICKEN, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.COOKED_MUTTON, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.COOKED_PORKCHOP, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.COOKED_RABBIT, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.COOKED_COD, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.COOKED_SALMON, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.BEEF, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.CHICKEN, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.MUTTON, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.PORKCHOP, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.RABBIT, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.COD, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.SALMON, LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9);
			convert(pvd, Items.TOTEM_OF_UNDYING, LCItems.LIFE_ESSENCE.get(), 64);

			/* TODO NetherDelight
			if (ModList.get().isLoaded(NethersDelight.MODID)) {
				convert(pvd, NDItems.HOGLIN_LOIN.get(), LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9, NethersDelight.MODID);
				convert(pvd, NDItems.HOGLIN_SIRLOIN.get(), LCItems.LIFE_ESSENCE.get(), 64 * 27 * 9, NethersDelight.MODID);
			}*/
		}

		currentFolder = "diffusion/";
		{
			diffuse(pvd, Blocks.LAPIS_BLOCK, Blocks.STONE, Blocks.LAPIS_ORE);
			diffuse(pvd, Blocks.LAPIS_BLOCK, Blocks.DEEPSLATE, Blocks.DEEPSLATE_LAPIS_ORE);

			diffuse(pvd, Blocks.REDSTONE_BLOCK, Blocks.STONE, Blocks.REDSTONE_ORE);
			diffuse(pvd, Blocks.REDSTONE_BLOCK, Blocks.DEEPSLATE, Blocks.DEEPSLATE_REDSTONE_ORE);

			diffuse(pvd, Blocks.DIAMOND_BLOCK, Blocks.STONE, Blocks.DIAMOND_ORE);
			diffuse(pvd, Blocks.DIAMOND_BLOCK, Blocks.DEEPSLATE, Blocks.DEEPSLATE_DIAMOND_ORE);

			diffuse(pvd, Blocks.EMERALD_BLOCK, Blocks.STONE, Blocks.EMERALD_ORE);
			diffuse(pvd, Blocks.EMERALD_BLOCK, Blocks.DEEPSLATE, Blocks.DEEPSLATE_EMERALD_ORE);

			diffuse(pvd, Blocks.QUARTZ_BLOCK, Blocks.NETHERRACK, Blocks.NETHER_QUARTZ_ORE);
		}

		// eggs
		{

			var cond = ConditionalRecipeWrapper.of(pvd, BooleanValueCondition.of(LCConfig.SERVER.getPath(), LCConfig.SERVER.enableSpawnEggRecipe, true));

			// undead
			/* zombie, husk, drowned, zombified piglin, skeleton, stray, wither skeleton, phantom, ghast*/
			currentFolder = "eggs/undead/";
			{
				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.ZOMBIE_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("AAA").pattern("ABA").pattern("ACA")
						.define('A', Items.ROTTEN_FLESH)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.save(cond, getID(Items.ZOMBIE_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.HUSK_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("ADA").pattern("ABA").pattern("ACA")
						.define('A', Items.ROTTEN_FLESH)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.define('D', Items.SAND)
						.save(cond, getID(Items.HUSK_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.DROWNED_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("ADA").pattern("ABA").pattern("ACA")
						.define('A', Items.ROTTEN_FLESH)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.define('D', Items.KELP)
						.save(cond, getID(Items.DROWNED_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.ZOMBIFIED_PIGLIN_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("ADA").pattern("ABA").pattern("ACA")
						.define('A', Items.ROTTEN_FLESH)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.define('D', Items.GOLD_INGOT)
						.save(cond, getID(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.SKELETON_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("AAA").pattern("ABA").pattern("ACA")
						.define('A', Items.BONE)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.save(cond, getID(Items.SKELETON_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.STRAY_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("ADA").pattern("ABA").pattern("ACA")
						.define('A', Items.BONE)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.define('D', Items.SNOWBALL)
						.save(cond, getID(Items.STRAY_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.WITHER_SKELETON_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("ADA").pattern("ABA").pattern("ACA")
						.define('A', Items.BONE)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.define('D', Items.WITHER_SKELETON_SKULL)
						.save(cond, getID(Items.WITHER_SKELETON_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.PHANTOM_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("AAA").pattern("ABA").pattern("ACA")
						.define('A', Items.PHANTOM_MEMBRANE)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.save(cond, getID(Items.PHANTOM_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.GHAST_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("AAA").pattern("ABA").pattern("ACA")
						.define('A', Items.GHAST_TEAR)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.save(cond, getID(Items.GHAST_SPAWN_EGG));
			}

			// passive
			/*pig, cow, mooshroom, chicken, rabbit, bee, cod, salmon, tropical fish, squid, glow squid, frog, turtle*/
			{

				currentFolder = "eggs/animal/";
				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.PIG_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.PORKCHOP)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(cond, getID(Items.PIG_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.COW_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.BEEF)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(cond, getID(Items.COW_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.MOOSHROOM_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.RED_MUSHROOM)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.COW_SPAWN_EGG)
						.save(cond, getID(Items.MOOSHROOM_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.SHEEP_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.MUTTON)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(cond, getID(Items.SHEEP_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.CHICKEN_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.CHICKEN)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(cond, getID(Items.CHICKEN_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.RABBIT_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.RABBIT)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(cond, getID(Items.RABBIT_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.BEE_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.HONEYCOMB)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(cond, getID(Items.BEE_SPAWN_EGG));

				currentFolder = "eggs/aquatic_spawn/";
				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.COD_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.COD)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(cond, getID(Items.COD_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.SALMON_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.SALMON)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(cond, getID(Items.SALMON_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.TROPICAL_FISH_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.TROPICAL_FISH)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(cond, getID(Items.TROPICAL_FISH_SPAWN_EGG));


				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.SQUID_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.INK_SAC)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(cond, getID(Items.SQUID_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.GLOW_SQUID_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.GLOW_INK_SAC)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(cond, getID(Items.GLOW_SQUID_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.FROG_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern(" A ").pattern("LBL").pattern(" C ")
						.define('A', Items.FROGSPAWN)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(cond, getID(Items.FROG_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.TURTLE_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.TURTLE_SCUTE)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(cond, getID(Items.TURTLE_SPAWN_EGG));

				currentFolder = "eggs/aquatic_alternate/";
				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.COD_BUCKET, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern(" A ").pattern("LBL").pattern(" C ")
						.define('A', Items.COD)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.BUCKET)
						.save(cond, getID(Items.COD_BUCKET));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.SALMON_BUCKET, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern(" A ").pattern("LBL").pattern(" C ")
						.define('A', Items.SALMON)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.BUCKET)
						.save(cond, getID(Items.SALMON_BUCKET));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.TROPICAL_FISH_BUCKET, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern(" A ").pattern("LBL").pattern(" C ")
						.define('A', Items.TROPICAL_FISH)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.BUCKET)
						.save(cond, getID(Items.TROPICAL_FISH_BUCKET));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.TURTLE_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("LBL").pattern(" C ")
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.TURTLE_EGG)
						.save(cond, getID(Items.TURTLE_SPAWN_EGG));

				// panda
				// polar bear
				// fox
				// cat
				// dog
				// goat
				// horse
				// donkey
				// mule
				// llama
				// ocelot
				// parrot
				// panda
				// dolphin
				// bat
				// axolotl

			}

			/*
			*
			// allay
			// blaze
			// cave spider
			// creeper
			// elder guardian
			// enderman
			// endermite
			// evoker
			// guardian
			// hoglin
			// magma cube
			// piglin
			// piglin brute
			//
			* */

		}

		/* TODO JEED
		{
			var jeed = new JeedDataGenerator(L2Complements.MODID);
			jeed.add(LCItems.SOUL_CHARGE.get(), LCEffects.FLAME.get());
			jeed.add(LCItems.BLACK_CHARGE.get(), LCEffects.INCARCERATE.get());
			jeed.add(EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.FLAME_BLADE.get(), 1), LCEffects.FLAME.get());
			jeed.add(EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.FLAME_THORN.get(), 1), LCEffects.FLAME.get());
			jeed.add(EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.ICE_BLADE.get(), 1), LCEffects.ICE.get());
			jeed.add(EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.ICE_THORN.get(), 1), LCEffects.ICE.get());
			jeed.add(EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.CURSE_BLADE.get(), 1), LCEffects.CURSE.get());
			jeed.add(EnchantmentIngredient.of(pvd.getProvider(), LCEnchantments.SHARP_BLADE.get(), 1), LCEffects.BLEED.get());
			jeed.add(LCItems.TOTEM_OF_DREAM.get(), MobEffects.REGENERATION, MobEffects.ABSORPTION, MobEffects.FIRE_RESISTANCE);
			jeed.add(LCItems.TOTEM_OF_THE_SEA.get(), MobEffects.REGENERATION, MobEffects.ABSORPTION, MobEffects.FIRE_RESISTANCE);
			jeed.add(LCMats.POSEIDITE.getArmor(EquipmentSlot.HEAD), MobEffects.CONDUIT_POWER, MobEffects.DOLPHINS_GRACE);
			jeed.add(LCMats.POSEIDITE.getArmor(EquipmentSlot.CHEST), MobEffects.CONDUIT_POWER, MobEffects.DOLPHINS_GRACE);
			jeed.add(LCMats.POSEIDITE.getArmor(EquipmentSlot.LEGS), MobEffects.CONDUIT_POWER, MobEffects.DOLPHINS_GRACE);
			jeed.add(LCMats.POSEIDITE.getArmor(EquipmentSlot.FEET), MobEffects.CONDUIT_POWER, MobEffects.DOLPHINS_GRACE);
			jeed.generate(pvd);
		}

		 */

	}

	@SuppressWarnings("ConstantConditions")
	private static ResourceLocation getID(ResourceKey<Enchantment> item) {
		return item.location().withPrefix(currentFolder);
	}

	@SuppressWarnings("ConstantConditions")
	private static ResourceLocation getID(ResourceKey<Enchantment> item, String suffix) {
		return getID(item).withSuffix(suffix);
	}

	@SuppressWarnings("ConstantConditions")
	private static ResourceLocation getID(Item item) {
		return L2Complements.loc(currentFolder + BuiltInRegistries.ITEM.getKey(item).getPath());
	}

	@SuppressWarnings("ConstantConditions")
	private static ResourceLocation getID(Item item, String suffix) {
		return L2Complements.loc(currentFolder + BuiltInRegistries.ITEM.getKey(item).getPath() + suffix);
	}

	private static void convert(RegistrateRecipeProvider pvd, Item in, Item out, int count) {
		unlock(pvd, new BurntRecipeBuilder(Ingredient.of(in), out.getDefaultInstance(), count)::unlockedBy, in).save(pvd, getID(in));
	}

	private static void convert(RegistrateRecipeProvider pvd, Item in, Item out, int count, String modid) {
		unlock(pvd, new BurntRecipeBuilder(Ingredient.of(in), out.getDefaultInstance(), count)::unlockedBy, in).save(
				ConditionalRecipeWrapper.mod(pvd, modid), getID(in));
	}

	private static void diffuse(RegistrateRecipeProvider pvd, Block in, Block base, Block out) {
		unlock(pvd, new DiffusionRecipeBuilder(in, base, out)::unlockedBy, in.asItem()).save(pvd, getID(out.asItem()));
	}

	private static void storage(RegistrateRecipeProvider pvd, ItemEntry<?> nugget, ItemEntry<?> ingot, BlockEntry<?> block) {
		storage(pvd, nugget::get, ingot::get);
		storage(pvd, ingot::get, block::get);
	}

	public static void storage(RegistrateRecipeProvider pvd, NonNullSupplier<ItemLike> from, NonNullSupplier<ItemLike> to) {
		unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, to.get(), 1)::unlockedBy, from.get().asItem())
				.pattern("XXX").pattern("XXX").pattern("XXX").define('X', from.get())
				.save(pvd, getID(to.get().asItem()) + "_storage");
		unlock(pvd, new ShapelessRecipeBuilder(RecipeCategory.MISC, from.get(), 9)::unlockedBy, to.get().asItem())
				.requires(to.get()).save(pvd, getID(to.get().asItem()) + "_unpack");
	}

	public static void genTools(RegistrateRecipeProvider pvd, LCMats mat, ItemEntry<?>[] arr) {
		currentFolder = "generated_tools/" + mat.name().toLowerCase(Locale.ROOT) + "/craft/";
		{
			Item ingot = mat.getIngot();
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.COMBAT, arr[0].get(), 1)::unlockedBy, arr[0].get())
					.pattern("A A").pattern("A A").define('A', ingot).save(pvd, getID(arr[0].get()));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.COMBAT, arr[1].get(), 1)::unlockedBy, arr[1].get())
					.pattern("AAA").pattern("A A").pattern("A A").define('A', ingot).save(pvd, getID(arr[1].get()));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.COMBAT, arr[2].get(), 1)::unlockedBy, arr[2].get())
					.pattern("A A").pattern("AAA").pattern("AAA").define('A', ingot).save(pvd, getID(arr[2].get()));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.COMBAT, arr[3].get(), 1)::unlockedBy, arr[3].get())
					.pattern("AAA").pattern("A A").define('A', ingot).save(pvd, getID(arr[3].get()));
		}
		{

			Item ingot = mat.getToolIngot();
			Item stick = mat.getToolStick();
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.COMBAT, arr[4].get(), 1)::unlockedBy, arr[4].get())
					.pattern("A").pattern("A").pattern("B").define('A', ingot).define('B', stick).save(pvd, getID(arr[4].get()));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.TOOLS, arr[5].get(), 1)::unlockedBy, arr[5].get())
					.pattern("AA").pattern("AB").pattern(" B").define('A', ingot).define('B', stick).save(pvd, getID(arr[5].get()));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.TOOLS, arr[6].get(), 1)::unlockedBy, arr[6].get())
					.pattern("A").pattern("B").pattern("B").define('A', ingot).define('B', stick).save(pvd, getID(arr[6].get()));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.TOOLS, arr[7].get(), 1)::unlockedBy, arr[7].get())
					.pattern("AAA").pattern(" B ").pattern(" B ").define('A', ingot).define('B', stick).save(pvd, getID(arr[7].get()));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.TOOLS, arr[8].get(), 1)::unlockedBy, arr[8].get())
					.pattern("AA").pattern(" B").pattern(" B").define('A', ingot).define('B', stick).save(pvd, getID(arr[8].get()));
		}
		currentFolder = "generated_tools/" + mat.name().toLowerCase(Locale.ROOT) + "/recycle/";
		Item nugget = mat.getNugget();
		for (int i = 0; i < 9; i++) {
			smelting(pvd, arr[i].get(), nugget, 0.1f);
		}
		currentFolder = "generated_tools/" + mat.name().toLowerCase(Locale.ROOT) + "/upgrade/";
		Item block = mat.getBlock().asItem();
		var cond = ConditionalRecipeWrapper.of(pvd, BooleanValueCondition.of(LCConfig.SERVER.getPath(), LCConfig.SERVER.enableToolRecraftRecipe, true));
		for (int i = 0; i < 9; i++) {
			smithing(pvd, TOOLS[i], block, arr[i].get(), cond);
		}

	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, Criterion<InventoryChangeTrigger.TriggerInstance>, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCriterion(pvd));
	}

	public static void smithing(RegistrateRecipeProvider pvd, TagKey<Item> in, Item mat, Item out) {
		smithing(pvd, in, mat, out, pvd);
	}

	public static void smithing(RegistrateRecipeProvider pvd, TagKey<Item> in, Item mat, Item out, RecipeOutput cons) {
		unlock(pvd, SmithingTransformRecipeBuilder.smithing(TEMPLATE_PLACEHOLDER, Ingredient.of(in), Ingredient.of(mat), RecipeCategory.MISC, out)::unlocks, mat).save(cons, getID(out));
	}

	public static void smithing(RegistrateRecipeProvider pvd, Item in, Item mat, Item out) {
		unlock(pvd, SmithingTransformRecipeBuilder.smithing(TEMPLATE_PLACEHOLDER, Ingredient.of(in), Ingredient.of(mat), RecipeCategory.MISC, out)::unlocks, mat).save(pvd, getID(out));
	}

	public static void smelting(RegistrateRecipeProvider pvd, Item source, Item result, float experience) {
		unlock(pvd, SimpleCookingRecipeBuilder.smelting(Ingredient.of(source), RecipeCategory.MISC, result, experience, 200)::unlockedBy, source)
				.save(pvd, getID(source));
	}

	public static void blasting(RegistrateRecipeProvider pvd, Item source, Item result, float experience) {
		unlock(pvd, SimpleCookingRecipeBuilder.blasting(Ingredient.of(source), RecipeCategory.MISC, result, experience, 200)::unlockedBy, source)
				.save(pvd, getID(source));
	}

}
