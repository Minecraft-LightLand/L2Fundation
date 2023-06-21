package dev.xkmc.l2complements.content.enchantment.core;

import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import net.minecraft.world.item.ItemStack;

public interface SourceModifierEnchantment {

	static void modifySource(ItemStack stack, CreateSourceEvent event) {
		for (var ent : stack.getAllEnchantments().entrySet()) {
			if (ent.getKey() instanceof SourceModifierEnchantment mod) {
				mod.modify(event, stack, ent.getValue());
			}
		}
	}

	void modify(CreateSourceEvent event, ItemStack stack, int value);

}
