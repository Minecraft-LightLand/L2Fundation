package dev.xkmc.l2complements.init.materials.api;

import dev.xkmc.l2complements.content.item.generic.ExtraArmorConfig;
import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Tier;

public interface IMatArmorType {

	ArmorMaterial getArmorMaterial();

	ArmorConfig getArmorConfig();

	ExtraArmorConfig getExtraArmorConfig();

}
