package dev.xkmc.l2complements.content.enchantment.core;

import net.minecraftforge.event.ItemAttributeModifierEvent;

public interface AttributeEnchantment {

	void addAttributes(int lv, ItemAttributeModifierEvent event);

}
