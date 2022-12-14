package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.content.item.generic.ExtraArmorConfig;
import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

public interface IGeneralMats {

	int ordinal();

	ExtraToolConfig getExtraToolConfig();

	ItemEntry<Item>[][] getGenerated();

	String armorPrefix();

	Item getIngot();

	Item getNugget();

	Block getBlock();

	default Item getArmor(EquipmentSlot slot) {
		return getGenerated()[ordinal()][slot.getIndex()].get();
	}

	default Item getTool(GenItem.Tools tool) {
		return getGenerated()[ordinal()][4 + tool.ordinal()].get();
	}

	default Item getToolIngot() {
		var tool_extra = getExtraToolConfig();
		return tool_extra.reversed ? tool_extra.stick.apply(this) : getIngot();
	}

	default Item getToolStick() {
		var tool_extra = getExtraToolConfig();
		return tool_extra.reversed ? getIngot() : tool_extra.stick.apply(this);
	}

	String getID();

	GenItem.ArmorConfig getArmorConfig();

	GenItem.ToolConfig getToolConfig();

	GenItem.ToolStats getToolStats();

	Tier getTier();

	ExtraArmorConfig getExtraArmorConfig();

	ArmorMaterial getArmorMaterial();
}
