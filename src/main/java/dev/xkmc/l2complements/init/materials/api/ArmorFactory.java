package dev.xkmc.l2complements.init.materials.api;

import dev.xkmc.l2complements.init.materials.api.IMatVanillaType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

@FunctionalInterface
public interface ArmorFactory {

	ArmorItem get(IMatArmorType mat, EquipmentSlot slot, Item.Properties props);

}
