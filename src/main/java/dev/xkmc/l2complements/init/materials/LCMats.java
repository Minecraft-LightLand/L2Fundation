package dev.xkmc.l2complements.init.materials;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2complements.content.item.equipments.*;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCTagGen;
import dev.xkmc.l2complements.init.registrate.LCBlocks;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import dev.xkmc.l2damagetracker.contents.materials.api.*;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraArmorConfig;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import dev.xkmc.l2damagetracker.contents.materials.vanilla.GenItemVanillaType;
import dev.xkmc.l2damagetracker.contents.materials.vanilla.ToolStats;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.SimpleTier;

import java.util.List;
import java.util.function.Supplier;

public enum LCMats implements IMatVanillaType {
	TOTEMIC_GOLD("totemic_gold", 3, SoundEvents.ARMOR_EQUIP_GOLD,
			new ToolStats(1000, 12, 7, 1, 22),
			new ArmorStats(15, new int[]{2, 5, 6, 2}, 0, 0, 25),
			GenItemVanillaType.TOOL_GEN, GenItemVanillaType.ARMOR_GEN,
			new TotemicTool().setStick(e -> Items.EMERALD, true), new TotemicArmor(),
			ChatFormatting.YELLOW),
	POSEIDITE("poseidite", 4, SoundEvents.ARMOR_EQUIP_IRON,
			new ToolStats(1500, 8, 7, 1, 14),
			new ArmorStats(33, new int[]{3, 6, 8, 3}, 2, 0, 9),
			GenItemVanillaType.TOOL_GEN, GenItemVanillaType.ARMOR_GEN,
			new PoseiditeTool().setStick(e -> Items.PRISMARINE_SHARD, false), new PoseiditeArmor(),
			ChatFormatting.AQUA),
	SHULKERATE("shulkerate", 4, SoundEvents.ARMOR_EQUIP_IRON,
			new ToolStats(4000, 8, 7, 1, 14),
			new ArmorStats(400, new int[]{3, 6, 8, 3}, 2, 0, 9),
			GenItemVanillaType.TOOL_GEN, GenItemVanillaType.ARMOR_GEN,
			new ShulkerateTool().setStick(e -> Items.IRON_INGOT, false).tags(LCTagGen.HIDE_WITH_INVISIBILITY),
			new ShulkerateArmor().tags(LCTagGen.HIDE_WITH_INVISIBILITY),
			ChatFormatting.LIGHT_PURPLE),
	SCULKIUM("sculkium", 5, SoundEvents.ARMOR_EQUIP_IRON,
			new ToolStats(2000, 8, 9, 1.2f, 15),
			new ArmorStats(100, new int[]{5, 9, 10, 6}, 4, 1, 15),
			GenItemVanillaType.TOOL_GEN, GenItemVanillaType.ARMOR_GEN,
			new SculkiumTool().setStick(e -> Items.NETHERITE_INGOT, false)
					.setTier(e -> LCTagGen.REQUIRES_SCULK_TOOL),
			new SculkiumArmor().tags(LCTagGen.DAMPENS_VIBRATION),
			ChatFormatting.DARK_AQUA),
	ETERNIUM("eternium", 5, SoundEvents.ARMOR_EQUIP_IRON,
			new ToolStats(9999, 8, 7, 1, 1),
			new ArmorStats(9999, new int[]{3, 6, 8, 3}, 10, 1, 1),
			GenItemVanillaType.TOOL_GEN, GenItemVanillaType.ARMOR_GEN,
			new EterniumTool().setStick(e -> LCItems.EXPLOSION_SHARD.get(), false),
			new EterniumArmor(),
			ChatFormatting.BLUE);

	final String id;
	final Tier tier;
	final SimpleEntry<ArmorMaterial> mat;
	final ToolConfig tool_config;
	final ArmorConfig armor_config;
	final IToolStats tool_stats;
	final ExtraToolConfig tool_extra;
	final ExtraArmorConfig armor_extra;
	public final ChatFormatting trim_text_color;
	final int durability;

	LCMats(String name, int level,
		   Holder<SoundEvent> equip_sound, IToolStats tool, ArmorStats armor,
		   ToolConfig tool_config, ArmorConfig armor_config,
		   ExtraToolConfig tool_extra, ExtraArmorConfig armor_extra, ChatFormatting trimTextColor) {
		trim_text_color = trimTextColor;
		Supplier<Ingredient> ing = () -> Ingredient.of(LCItems.MAT_INGOTS[ordinal()].get());
		this.id = name;
		this.tier = new SimpleTier(tool_extra.getTier(level), tool.durability(), tool.speed(), 0, tool.enchant(), ing);
		this.mat = new SimpleEntry<>(L2Complements.REGISTRATE.simple(name, Registries.ARMOR_MATERIAL, () -> new ArmorMaterial(armor.defense(), armor.enchant(), equip_sound, ing,
				List.of(new ArmorMaterial.Layer(L2Complements.loc(name))),
				armor.tough(), armor.kb())));
		this.tool_config = tool_config;
		this.armor_config = armor_config;
		this.tool_stats = tool;
		this.tool_extra = tool_extra;
		this.armor_extra = armor_extra;
		this.durability = armor.durability();
	}

	public Item getIngot() {
		return LCItems.MAT_INGOTS[ordinal()].get();
	}

	public Item getNugget() {
		return LCItems.MAT_NUGGETS[ordinal()].get();
	}

	public Block getBlock() {
		return LCBlocks.GEN_BLOCK[ordinal()].get();
	}

	public ResourceLocation id() {
		return L2Complements.loc(id);
	}

	// --- interface ---

	public String getID() {
		return id;
	}

	@Override
	public ArmorConfig getArmorConfig() {
		return armor_config;
	}

	@Override
	public ToolConfig getToolConfig() {
		return tool_config;
	}

	@Override
	public IToolStats getToolStats() {
		return tool_stats;
	}

	@Override
	public Tier getTier() {
		return tier;
	}

	@Override
	public ExtraArmorConfig getExtraArmorConfig() {
		return armor_extra;
	}

	@Override
	public int armorDurability() {
		return durability;
	}

	@Override
	public Holder<ArmorMaterial> getArmorMaterial() {
		return mat.holder();
	}

	@Override
	public ExtraToolConfig getExtraToolConfig() {
		return tool_extra;
	}

	@Override
	public ItemEntry<Item>[][] getGenerated() {
		return LCItems.GEN_ITEM;
	}

}
