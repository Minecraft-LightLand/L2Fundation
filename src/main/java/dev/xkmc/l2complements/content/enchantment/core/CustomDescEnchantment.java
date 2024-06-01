package dev.xkmc.l2complements.content.enchantment.core;

import net.minecraft.network.chat.Component;

import java.util.List;

public interface CustomDescEnchantment {

	List<Component> descFull(int lv, String key, boolean alt, boolean book);

}
