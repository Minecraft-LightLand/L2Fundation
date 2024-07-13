package dev.xkmc.l2complements.content.enchantment.core;

import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import net.minecraft.world.item.ItemStack;

public interface SourceModifierEnchantment {

	void modify(CreateSourceEvent event, ItemStack stack, int value);

}
