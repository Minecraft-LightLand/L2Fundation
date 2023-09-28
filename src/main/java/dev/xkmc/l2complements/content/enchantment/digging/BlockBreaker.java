package dev.xkmc.l2complements.content.enchantment.digging;

public interface BlockBreaker {

	BlockBreakerInstance getInstance(DiggerContext ctx);

	int getMaxLevel();

	default boolean ignoreHardness() {
		return false;
	}

}
