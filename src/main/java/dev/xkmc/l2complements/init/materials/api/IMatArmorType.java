package dev.xkmc.l2complements.init.materials.api;

import dev.xkmc.l2complements.content.item.generic.ExtraArmorConfig;
import net.minecraft.world.item.ArmorMaterial;

public interface IMatArmorType {

	ArmorMaterial getArmorMaterial();

	ArmorConfig getArmorConfig();

	ExtraArmorConfig getExtraArmorConfig();

}
