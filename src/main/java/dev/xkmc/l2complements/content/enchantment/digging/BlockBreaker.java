package dev.xkmc.l2complements.content.enchantment.digging;

import net.minecraft.network.chat.Component;

import java.util.List;

public interface BlockBreaker {

	BlockBreakerInstance getInstance(DiggerContext ctx);

	int getMaxLevel();

	default boolean ignoreHardness() {
		return false;
	}

	List<Component> descFull(int lv, String key, boolean alt, boolean book);

}
