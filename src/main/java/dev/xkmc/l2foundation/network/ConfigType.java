package dev.xkmc.l2foundation.network;

import java.util.Locale;

public enum ConfigType {
	ARMOR_ENCHANT, ARMOR_WEIGHT,
	PRODUCT_TYPE, MAGIC_DATA,
	CONFIG_SKILL, CONFIG_SPELL, CONFIG_SPELL_ENTITY,
	POTION_SLIME_DROP;

	public String getID() {
		return name().toLowerCase(Locale.ROOT);
	}

}
