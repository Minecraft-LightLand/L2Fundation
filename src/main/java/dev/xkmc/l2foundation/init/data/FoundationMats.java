package dev.xkmc.l2foundation.init.data;

import dev.xkmc.l2foundation.content.item.equipments.*;
import dev.xkmc.l2foundation.content.item.generic.ArmorMat;
import dev.xkmc.l2foundation.content.item.generic.ExtraArmorConfig;
import dev.xkmc.l2foundation.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2foundation.init.L2Foundation;
import dev.xkmc.l2foundation.init.registrate.LFBlocks;
import dev.xkmc.l2foundation.init.registrate.LFItems;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;

import java.util.function.Supplier;

public enum FoundationMats implements IGeneralMats {
	TOTEMIUM("totemium", 3, SoundEvents.ARMOR_EQUIP_GOLD,
			new GenItem.ToolStats(1000, 12, new int[]{7, 10, 5, 5, 1},
					new float[]{1.6f, 1f, 1f, 1.2f, 4f}, 22),
			new GenItem.ArmorStats(15, new int[]{2, 5, 6, 2}, 0, 0, 25),
			GenItem.TOOL_GEN, GenItem.ARMOR_GEN,
			new TotemiumTool().setStick(e -> Items.EMERALD, true), new TotemiumArmor()),
	NEPTUNIUM("neptunium", 4, SoundEvents.ARMOR_EQUIP_GOLD,
			new GenItem.ToolStats(1500, 8, new int[]{7, 10, 5, 5, 1},
					new float[]{1.6f, 1f, 1f, 1.2f, 4f}, 14),
			new GenItem.ArmorStats(33, new int[]{3, 6, 8, 3}, 2, 0, 9),
			GenItem.TOOL_GEN, GenItem.ARMOR_GEN,
			new NeptuniumTool().setStick(e -> Items.PRISMARINE_SHARD, false), new NeptuniumArmor()),
	SHULKIUM("shulkium", 4, SoundEvents.ARMOR_EQUIP_IRON,
			new GenItem.ToolStats(4000, 8, new int[]{7, 10, 5, 5, 1},
					new float[]{1.6f, 1f, 1f, 1.2f, 4f}, 14),
			new GenItem.ArmorStats(400, new int[]{3, 6, 8, 3}, 2, 0, 9),
			GenItem.TOOL_GEN, GenItem.ARMOR_GEN,
			new ShulkiumTool().setStick(e -> Items.IRON_INGOT, false), new ShulkiumArmor()),
	SCULKIUM("sculkium", 4, SoundEvents.ARMOR_EQUIP_IRON,
			new GenItem.ToolStats(2000, 12, new int[]{9, 12, 6, 6, 4},
					new float[]{1.8f, 1.2f, 1.2f, 1.4f, 4f}, 15),
			new GenItem.ArmorStats(40, new int[]{5, 9, 10, 6}, 4, 0, 15),
			GenItem.TOOL_GEN, GenItem.ARMOR_GEN,
			new SculkiumTool().setStick(e -> Items.NETHERITE_INGOT, false)
					.setTier(e -> TagGen.REQUIRES_SCULK_TOOL), new SculkiumArmor()),
	ETERNIUM("eternium", 4, SoundEvents.ARMOR_EQUIP_IRON,
			new GenItem.ToolStats(9999, 8, new int[]{7, 10, 5, 5, 1},
					new float[]{1.6f, 1f, 1f, 1.2f, 4f}, 1),
			new GenItem.ArmorStats(9999, new int[]{3, 6, 8, 3}, 2, 0, 1),
			GenItem.TOOL_GEN, GenItem.ARMOR_GEN, new ExtraToolConfig().damageChance(0).repairChance(1),
			new ExtraArmorConfig().damageChance(0).repairChance(1));

	final String id;
	final Tier tier;
	final ArmorMaterial mat;
	final GenItem.ToolConfig tool_config;
	final GenItem.ArmorConfig armor_config;
	final GenItem.ToolStats tool_stats;
	final ExtraToolConfig tool_extra;
	final ExtraArmorConfig armor_extra;

	FoundationMats(String name, int level,
				   SoundEvent equip_sound, GenItem.ToolStats tool, GenItem.ArmorStats armor,
				   GenItem.ToolConfig tool_config, GenItem.ArmorConfig armor_config,
				   ExtraToolConfig tool_extra, ExtraArmorConfig armor_extra) {
		Supplier<Ingredient> ing = () -> Ingredient.of(LFItems.MAT_INGOTS[ordinal()].get());
		this.id = name;
		this.tier = new ForgeTier(level, tool.durability(), tool.speed(), 0, tool.enchant(),
				tool_extra.getTier(level), ing);
		this.mat = new ArmorMat(armorPrefix(), armor.durability(), armor.protection(),
				armor.enchant(), equip_sound, armor.tough(), armor.kb(), ing);
		this.tool_config = tool_config;
		this.armor_config = armor_config;
		this.tool_stats = tool;
		this.tool_extra = tool_extra;
		this.armor_extra = armor_extra;
	}

	public Item getIngot() {
		return LFItems.MAT_INGOTS[ordinal()].get();
	}

	public Item getNugget() {
		return LFItems.MAT_NUGGETS[ordinal()].get();
	}

	public Block getBlock() {
		return LFBlocks.GEN_BLOCK[ordinal()].get();
	}

	public String armorPrefix() {
		return L2Foundation.MODID + ":" + id;
	}

	@Override
	public ExtraToolConfig getExtraToolConfig() {
		return tool_extra;
	}

	@Override
	public ItemEntry<Item>[][] getGenerated() {
		return LFItems.GEN_ITEM;
	}

}
