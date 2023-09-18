package dev.xkmc.l2complements.init.data;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2complements.content.enchantment.core.EnchantmentRecipeBuilder;
import dev.xkmc.l2complements.content.recipe.BurntRecipeBuilder;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2complements.init.registrate.LCBlocks;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2library.compat.jeed.JeedDataGenerator;
import dev.xkmc.l2library.serial.ingredients.EnchantmentIngredient;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;

import static dev.xkmc.l2library.serial.recipe.AbstractSmithingRecipe.TEMPLATE_PLACEHOLDER;

@SuppressWarnings("removal")
public class RecipeGen {

	@SuppressWarnings("unchecked")
	private static final TagKey<Item>[] TOOLS = List.of(
			Tags.Items.ARMORS_BOOTS,
			Tags.Items.ARMORS_LEGGINGS,
			Tags.Items.ARMORS_CHESTPLATES,
			Tags.Items.ARMORS_HELMETS,
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
					.define('1', new EnchantmentIngredient(Enchantments.MENDING, 1))
					.define('2', new EnchantmentIngredient(Enchantments.INFINITY_ARROWS, 1))
					.define('3', new EnchantmentIngredient(Enchantments.ALL_DAMAGE_PROTECTION, 4))
					.define('4', new EnchantmentIngredient(Enchantments.UNBREAKING, 3))
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
					.requires(TagGen.DELICATE_BONE)
					.requires(LCItems.RESONANT_FEATHER.get())
					.save(pvd, getID(LCItems.WARDEN_BONE_SHARD.get()));


		}

		currentFolder = "vanilla/renew/";
		// misc
		{
			unlock(pvd, new ShapelessRecipeBuilder(RecipeCategory.MISC, Items.ECHO_SHARD, 1)::unlockedBy, LCItems.RESONANT_FEATHER.get())
					.requires(LCItems.RESONANT_FEATHER.get())
					.requires(Items.AMETHYST_SHARD)
					.requires(Items.SCULK, 4)
					.save(pvd, getID(Items.ECHO_SHARD));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.ELYTRA, 1)::unlockedBy, LCItems.SUN_MEMBRANE.get())
					.pattern("ABA").pattern("CEC").pattern("D D")
					.define('A', LCItems.EXPLOSION_SHARD.get())
					.define('B', LCItems.CAPTURED_WIND.get())
					.define('C', LCItems.SUN_MEMBRANE.get())
					.define('D', LCItems.RESONANT_FEATHER.get())
					.define('E', LCItems.STORM_CORE.get())
					.save(pvd, getID(Items.ELYTRA));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.ANCIENT_DEBRIS, 1)::unlockedBy, LCItems.EXPLOSION_SHARD.get())
					.pattern("ABA").pattern("ACA").pattern("ADA")
					.define('A', LCItems.EXPLOSION_SHARD.get())
					.define('B', Items.NETHER_STAR)
					.define('C', Items.CRYING_OBSIDIAN)
					.define('D', LCItems.FORCE_FIELD.get())
					.save(pvd, getID(Items.ANCIENT_DEBRIS));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.GILDED_BLACKSTONE, 1)::unlockedBy, LCItems.BLACKSTONE_CORE.get())
					.pattern("ABA").pattern("BCB").pattern("ABA")
					.define('A', Items.BLACKSTONE)
					.define('B', Items.GOLD_INGOT)
					.define('C', LCItems.BLACKSTONE_CORE.get())
					.save(pvd, getID(Items.GILDED_BLACKSTONE));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.REINFORCED_DEEPSLATE, 1)::unlockedBy, LCItems.WARDEN_BONE_SHARD.get())
					.pattern(" B ").pattern("BAB").pattern(" B ")
					.define('A', Items.DEEPSLATE)
					.define('B', LCItems.WARDEN_BONE_SHARD.get())
					.save(pvd, getID(Items.REINFORCED_DEEPSLATE));
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.HEART_OF_THE_SEA, 1)::unlockedBy, LCItems.GUARDIAN_EYE.get())
					.pattern("ABA").pattern("BCB").pattern("ABA")
					.define('A', Items.PRISMARINE_SHARD)
					.define('B', Items.PRISMARINE_CRYSTALS)
					.define('C', LCItems.GUARDIAN_EYE.get())
					.save(pvd, getID(Items.HEART_OF_THE_SEA));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1)::unlockedBy, LCItems.BLACKSTONE_CORE.get())
					.pattern("BAB").pattern("BCB").pattern("BBB")
					.define('B', Items.DIAMOND)
					.define('C', Items.NETHERRACK)
					.define('A', LCItems.BLACKSTONE_CORE.get())
					.save(pvd, getID(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE));
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

			// protections
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

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ENCH_INVINCIBLE.get(), 1)::unlockedBy, LCItems.SPACE_SHARD.get())
						.pattern("A1A").pattern("203").pattern("A4A")
						.define('A', LCItems.SPACE_SHARD.get())
						.define('0', new EnchantmentIngredient(LCEnchantments.ENCH_ENVIRONMENT.get(), 1))
						.define('1', new EnchantmentIngredient(LCEnchantments.ENCH_MAGIC.get(), 1))
						.define('2', new EnchantmentIngredient(LCEnchantments.ENCH_EXPLOSION.get(), 1))
						.define('3', new EnchantmentIngredient(LCEnchantments.ENCH_FIRE.get(), 1))
						.define('4', new EnchantmentIngredient(LCEnchantments.ENCH_PROJECTILE.get(), 1))
						.save(pvd, getID(LCEnchantments.ENCH_INVINCIBLE.get()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ENCH_MATES.get(), 1)::unlockedBy, Items.NETHER_STAR)
						.pattern("BAB").pattern("B1B").pattern("BAB")
						.define('1', new EnchantmentIngredient(Enchantments.ALL_DAMAGE_PROTECTION, 4))
						.define('A', Items.NETHER_STAR)
						.define('B', Items.END_ROD)
						.save(pvd, getID(LCEnchantments.ENCH_MATES.get()));
			}

			// misc
			{

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SHULKER_ARMOR.get(), 1)::unlockedBy, LCMats.SHULKERATE.getNugget())
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', LCMats.SHULKERATE.getNugget())
						.define('B', Items.BOOK)
						.define('C', Items.GLASS)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.SHULKER_ARMOR.get()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.STABLE_BODY.get(), 1)::unlockedBy, Items.CRYING_OBSIDIAN)
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', Items.OBSIDIAN)
						.define('B', Items.BOOK)
						.define('C', Items.CRYING_OBSIDIAN)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.STABLE_BODY.get()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.LIFE_SYNC.get(), 1)::unlockedBy, LCItems.FORCE_FIELD.get())
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', Items.WITHER_ROSE)
						.define('B', Items.BOOK)
						.define('C', LCItems.FORCE_FIELD.get())
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.LIFE_SYNC.get()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.LIFE_MENDING.get(), 1)::unlockedBy, Items.BOOK)
						.pattern("LCL").pattern("ABA").pattern("FCF")
						.define('A', Items.WEEPING_VINES)
						.define('B', Items.BOOK)
						.define('C', Items.TWISTING_VINES)
						.define('L', Items.LAPIS_LAZULI)
						.define('F', Items.ROTTEN_FLESH)
						.save(pvd, getID(LCEnchantments.LIFE_MENDING.get()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SAFEGUARD.get(), 1)::unlockedBy, Items.BOOK)
						.pattern("LCL").pattern("ABA").pattern("FCF")
						.define('A', LCMats.SHULKERATE.getNugget())
						.define('B', Items.BOOK)
						.define('C', Items.AMETHYST_SHARD)
						.define('L', Items.LAPIS_LAZULI)
						.define('F', Items.NETHERITE_SCRAP)
						.save(pvd, getID(LCEnchantments.SAFEGUARD.get()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ENDER_MASK.get(), 1)::unlockedBy, Items.ENDER_EYE)
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', Items.ENDER_EYE)
						.define('B', Items.BOOK)
						.define('C', Items.CARVED_PUMPKIN)
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

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ENDER.get(), 1)::unlockedBy, Items.ENDER_PEARL)
						.pattern("LAL").pattern("ABA").pattern("LCL")
						.define('A', Items.ENDER_PEARL)
						.define('B', Items.BOOK)
						.define('C', Items.HOPPER)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.ENDER.get()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.HARDENED.get(), 1)::unlockedBy, LCMats.SHULKERATE.getIngot())
						.pattern("SCS").pattern("ABA").pattern("LAL")
						.define('A', LCMats.SHULKERATE.getIngot())
						.define('S', LCItems.WARDEN_BONE_SHARD.get())
						.define('C', LCItems.EXPLOSION_SHARD.get())
						.define('B', Items.BOOK)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.HARDENED.get()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ETERNAL.get(), 1)::unlockedBy, LCItems.SPACE_SHARD.get())
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', LCMats.ETERNIUM.getIngot())
						.define('C', LCItems.SPACE_SHARD.get())
						.define('B', new EnchantmentIngredient(LCEnchantments.HARDENED.get(), 1))
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.ETERNAL.get()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.DURABLE_ARMOR.get(), 1)::unlockedBy, Items.DIAMOND)
						.pattern(" A ").pattern("LBL").pattern(" L ")
						.define('A', Items.DIAMOND)
						.define('B', new EnchantmentIngredient(Enchantments.UNBREAKING, 1))
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.DURABLE_ARMOR.get(), "_1"));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.DURABLE_ARMOR.get(), 2)::unlockedBy, Items.DIAMOND)
						.pattern("LAL").pattern("ABA").pattern("LAL")
						.define('A', Items.DIAMOND)
						.define('B', new EnchantmentIngredient(Enchantments.UNBREAKING, 2))
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.DURABLE_ARMOR.get(), "_2"));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.DURABLE_ARMOR.get(), 3)::unlockedBy, Items.DIAMOND)
						.pattern("L1L").pattern("2B3").pattern("L4L")
						.define('1', Items.DIAMOND_HELMET)
						.define('2', Items.DIAMOND_CHESTPLATE)
						.define('3', Items.DIAMOND_LEGGINGS)
						.define('4', Items.DIAMOND_BOOTS)
						.define('B', new EnchantmentIngredient(Enchantments.UNBREAKING, 3))
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.DURABLE_ARMOR.get(), "_3"));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SMELT.get(), 1)::unlockedBy, LCItems.SOUL_FLAME.get())
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', Items.LAVA_BUCKET)
						.define('C', LCItems.SOUL_FLAME.get())
						.define('B', Items.BOOK)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.SMELT.get()));


			}

			// offencive and defensive
			{

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.WIND_SWEEP.get(), 1)::unlockedBy, LCMats.SHULKERATE.getNugget())
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', LCMats.SHULKERATE.getNugget())
						.define('B', Items.BOOK)
						.define('C', LCItems.STORM_CORE.get())
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.WIND_SWEEP.get()));


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

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.ICE_THORN.get(), 1)::unlockedBy, LCItems.HARD_ICE.get())
						.pattern("LAL").pattern("ABA").pattern("LAL")
						.define('A', LCItems.HARD_ICE.get())
						.define('B', new EnchantmentIngredient(Enchantments.THORNS, 3))
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.ICE_THORN.get()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.FLAME_THORN.get(), 1)::unlockedBy, LCItems.SOUL_FLAME.get())
						.pattern("LAL").pattern("ABA").pattern("LAL")
						.define('A', LCItems.SOUL_FLAME.get())
						.define('B', new EnchantmentIngredient(Enchantments.THORNS, 3))
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.FLAME_THORN.get()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.SHARP_BLADE.get(), 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("LCL").pattern("ABA").pattern("LAL")
						.define('A', LCItems.EXPLOSION_SHARD.get())
						.define('C', LCItems.CURSED_DROPLET.get())
						.define('B', Items.BOOK)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.SHARP_BLADE.get()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.CURSE_BLADE.get(), 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("LCL").pattern("ABA").pattern("LCL")
						.define('A', Items.FERMENTED_SPIDER_EYE)
						.define('C', LCItems.CURSED_DROPLET.get())
						.define('B', Items.BOOK)
						.define('L', Items.LAPIS_LAZULI)
						.save(pvd, getID(LCEnchantments.CURSE_BLADE.get()));

				unlock(pvd, new EnchantmentRecipeBuilder(LCEnchantments.VOID_TOUCH.get(), 1)::unlockedBy, LCItems.RESONANT_FEATHER.get())
						.pattern("LCL").pattern("ABA").pattern("LCL")
						.define('A', LCItems.VOID_EYE.get())
						.define('C', LCItems.SUN_MEMBRANE.get())
						.define('B', Items.BOOK)
						.define('L', LCItems.RESONANT_FEATHER.get())
						.save(pvd, getID(LCEnchantments.VOID_TOUCH.get()));
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
		}

		// eggs
		{

			// undead
			/* zombie, husk, drowned, zombified piglin, skeleton, stray, wither skeleton, phantom, ghast*/
			currentFolder = "eggs/undead/";
			{
				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.ZOMBIE_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("AAA").pattern("ABA").pattern("ACA")
						.define('A', Items.ROTTEN_FLESH)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.ZOMBIE_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.HUSK_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("ADA").pattern("ABA").pattern("ACA")
						.define('A', Items.ROTTEN_FLESH)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.define('D', Items.SAND)
						.save(pvd, getID(Items.HUSK_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.DROWNED_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("ADA").pattern("ABA").pattern("ACA")
						.define('A', Items.ROTTEN_FLESH)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.define('D', Items.KELP)
						.save(pvd, getID(Items.DROWNED_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.ZOMBIFIED_PIGLIN_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("ADA").pattern("ABA").pattern("ACA")
						.define('A', Items.ROTTEN_FLESH)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.define('D', Items.GOLD_INGOT)
						.save(pvd, getID(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.SKELETON_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("AAA").pattern("ABA").pattern("ACA")
						.define('A', Items.BONE)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.SKELETON_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.STRAY_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("ADA").pattern("ABA").pattern("ACA")
						.define('A', Items.BONE)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.define('D', Items.SNOWBALL)
						.save(pvd, getID(Items.STRAY_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.WITHER_SKELETON_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("ADA").pattern("ABA").pattern("ACA")
						.define('A', Items.BONE)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.define('D', Items.WITHER_SKELETON_SKULL)
						.save(pvd, getID(Items.WITHER_SKELETON_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.PHANTOM_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("AAA").pattern("ABA").pattern("ACA")
						.define('A', Items.PHANTOM_MEMBRANE)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.PHANTOM_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.GHAST_SPAWN_EGG, 1)::unlockedBy, LCItems.CURSED_DROPLET.get())
						.pattern("AAA").pattern("ABA").pattern("ACA")
						.define('A', Items.GHAST_TEAR)
						.define('B', LCItems.CURSED_DROPLET.get())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.GHAST_SPAWN_EGG));
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
						.save(pvd, getID(Items.PIG_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.COW_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.BEEF)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.COW_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.MOOSHROOM_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.RED_MUSHROOM)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.COW_SPAWN_EGG)
						.save(pvd, getID(Items.MOOSHROOM_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.SHEEP_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.MUTTON)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.SHEEP_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.CHICKEN_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.CHICKEN)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.CHICKEN_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.RABBIT_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.RABBIT)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.RABBIT_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.BEE_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.HONEYCOMB)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.BEE_SPAWN_EGG));

				currentFolder = "eggs/aquatic_spawn/";
				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.COD_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.COD)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.COD_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.SALMON_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.SALMON)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.SALMON_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.TROPICAL_FISH_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.TROPICAL_FISH)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.TROPICAL_FISH_SPAWN_EGG));


				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.SQUID_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.INK_SAC)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.SQUID_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.GLOW_SQUID_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.GLOW_INK_SAC)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.GLOW_SQUID_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.FROG_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern(" A ").pattern("LBL").pattern(" C ")
						.define('A', Items.FROGSPAWN)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.FROG_SPAWN_EGG));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.TURTLE_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("AAA").pattern("LBL").pattern("ACA")
						.define('A', Items.SCUTE)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.EGG)
						.save(pvd, getID(Items.TURTLE_SPAWN_EGG));

				currentFolder = "eggs/aquatic_alternate/";
				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.COD_BUCKET, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern(" A ").pattern("LBL").pattern(" C ")
						.define('A', Items.COD)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.BUCKET)
						.save(pvd, getID(Items.COD_BUCKET));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.SALMON_BUCKET, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern(" A ").pattern("LBL").pattern(" C ")
						.define('A', Items.SALMON)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.BUCKET)
						.save(pvd, getID(Items.SALMON_BUCKET));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.TROPICAL_FISH_BUCKET, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern(" A ").pattern("LBL").pattern(" C ")
						.define('A', Items.TROPICAL_FISH)
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.BUCKET)
						.save(pvd, getID(Items.TROPICAL_FISH_BUCKET));

				unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, Items.TURTLE_SPAWN_EGG, 1)::unlockedBy, LCItems.LIFE_ESSENCE.get())
						.pattern("LBL").pattern(" C ")
						.define('B', LCItems.LIFE_ESSENCE.get())
						.define('L', LCMats.TOTEMIC_GOLD.getIngot())
						.define('C', Items.TURTLE_EGG)
						.save(pvd, getID(Items.TURTLE_SPAWN_EGG));

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

		// JEED
		{
			var jeed = new JeedDataGenerator(L2Complements.MODID);
			jeed.add(LCItems.SOUL_CHARGE.get(), LCEffects.FLAME.get());
			jeed.add(LCItems.BLACK_CHARGE.get(), LCEffects.STONE_CAGE.get());
			jeed.add(new EnchantmentIngredient(LCEnchantments.FLAME_BLADE.get(), 1), LCEffects.FLAME.get());
			jeed.add(new EnchantmentIngredient(LCEnchantments.FLAME_THORN.get(), 1), LCEffects.FLAME.get());
			jeed.add(new EnchantmentIngredient(LCEnchantments.ICE_BLADE.get(), 1), LCEffects.ICE.get());
			jeed.add(new EnchantmentIngredient(LCEnchantments.ICE_THORN.get(), 1), LCEffects.ICE.get());
			jeed.add(new EnchantmentIngredient(LCEnchantments.CURSE_BLADE.get(), 1), LCEffects.CURSE.get());
			jeed.add(new EnchantmentIngredient(LCEnchantments.SHARP_BLADE.get(), 1), LCEffects.BLEED.get());
			jeed.add(LCItems.TOTEM_OF_DREAM.get(), MobEffects.REGENERATION, MobEffects.ABSORPTION, MobEffects.FIRE_RESISTANCE);
			jeed.add(LCItems.TOTEM_OF_THE_SEA.get(), MobEffects.REGENERATION, MobEffects.ABSORPTION, MobEffects.FIRE_RESISTANCE);
			jeed.add(LCMats.POSEIDITE.getArmor(EquipmentSlot.HEAD), MobEffects.CONDUIT_POWER, MobEffects.DOLPHINS_GRACE);
			jeed.add(LCMats.POSEIDITE.getArmor(EquipmentSlot.CHEST), MobEffects.CONDUIT_POWER, MobEffects.DOLPHINS_GRACE);
			jeed.add(LCMats.POSEIDITE.getArmor(EquipmentSlot.LEGS), MobEffects.CONDUIT_POWER, MobEffects.DOLPHINS_GRACE);
			jeed.add(LCMats.POSEIDITE.getArmor(EquipmentSlot.FEET), MobEffects.CONDUIT_POWER, MobEffects.DOLPHINS_GRACE);
			jeed.generate(pvd);
		}

	}

	@SuppressWarnings("ConstantConditions")
	private static ResourceLocation getID(Enchantment item) {
		return new ResourceLocation(L2Complements.MODID, currentFolder + ForgeRegistries.ENCHANTMENTS.getKey(item).getPath());
	}

	@SuppressWarnings("ConstantConditions")
	private static ResourceLocation getID(Enchantment item, String suffix) {
		return new ResourceLocation(L2Complements.MODID, currentFolder + ForgeRegistries.ENCHANTMENTS.getKey(item).getPath() + suffix);
	}

	@SuppressWarnings("ConstantConditions")
	private static ResourceLocation getID(Item item) {
		return new ResourceLocation(L2Complements.MODID, currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath());
	}

	@SuppressWarnings("ConstantConditions")
	private static ResourceLocation getID(Item item, String suffix) {
		return new ResourceLocation(L2Complements.MODID, currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath() + suffix);
	}

	private static void convert(RegistrateRecipeProvider pvd, Item in, Item out, int count) {
		unlock(pvd, new BurntRecipeBuilder(Ingredient.of(in), out.getDefaultInstance(), count)::unlockedBy, in).save(pvd, getID(in));
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
		for (int i = 0; i < 9; i++) {
			smithing(pvd, TOOLS[i], block, arr[i].get());
		}

	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

	public static void smithing(RegistrateRecipeProvider pvd, TagKey<Item> in, Item mat, Item out) {
		unlock(pvd, SmithingTransformRecipeBuilder.smithing(TEMPLATE_PLACEHOLDER, Ingredient.of(in), Ingredient.of(mat), RecipeCategory.MISC, out)::unlocks, mat).save(pvd, getID(out));
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
