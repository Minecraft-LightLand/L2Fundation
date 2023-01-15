package dev.xkmc.l2complements.init.materials.api;

import dev.xkmc.l2complements.content.item.generic.ExtraArmorConfig;
import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Tier;

public interface IMatToolType {

	Tier getTier();

	IToolStats getToolStats();

	ToolConfig getToolConfig();

	ExtraToolConfig getExtraToolConfig();

}
