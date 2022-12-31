package dev.xkmc.l2complements.init.registrate;

import dev.xkmc.l2complements.content.enchantment.core.ImmuneEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.SingleLevelEnchantment;
import dev.xkmc.l2complements.content.enchantment.special.*;
import dev.xkmc.l2complements.content.enchantment.weapon.WindSweepEnchantment;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import static dev.xkmc.l2complements.init.L2Complements.REGISTRATE;

public class LCEnchantments {

	public static final RegistryEntry<ImmuneEnchantment> ENCH_PROJECTILE = reg("projectile_reject");
	public static final RegistryEntry<ImmuneEnchantment> ENCH_FIRE = reg("fire_reject");
	public static final RegistryEntry<ImmuneEnchantment> ENCH_ENVIRONMENT = reg("environment_reject");
	public static final RegistryEntry<ImmuneEnchantment> ENCH_EXPLOSION = reg("explosion_reject");
	public static final RegistryEntry<ImmuneEnchantment> ENCH_MAGIC = reg("magic_reject");

	public static final RegistryEntry<SingleLevelEnchantment> SHULKER_ARMOR = REGISTRATE
			.enchantment("shulker_armor", EnchantmentCategory.BREAKABLE, SingleLevelEnchantment::new)
			.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).lang("Transparent").register();

	public static final RegistryEntry<StableBodyEnchantment> STABLE_BODY = REGISTRATE
			.enchantment("stable_body", EnchantmentCategory.ARMOR_CHEST, StableBodyEnchantment::new)
			.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<LifeSyncEnchantment> LIFE_SYNC = REGISTRATE
			.enchantment("life_sync", EnchantmentCategory.BREAKABLE, LifeSyncEnchantment::new)
			.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<WindSweepEnchantment> WIND_SWEEP = REGISTRATE
			.enchantment("wind_sweep", EnchantmentCategory.BREAKABLE, WindSweepEnchantment::new)
			.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<SingleLevelEnchantment> ENDER_MASK = REGISTRATE
			.enchantment("ender_mask", EnchantmentCategory.ARMOR_HEAD, SingleLevelEnchantment::new)
			.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<SingleLevelEnchantment> SHINNY = REGISTRATE
			.enchantment("shinny", EnchantmentCategory.WEARABLE, SingleLevelEnchantment::new)
			.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<SingleLevelEnchantment> SNOW_WALKER = REGISTRATE
			.enchantment("snow_walker", EnchantmentCategory.ARMOR_FEET, SingleLevelEnchantment::new)
			.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	private static RegistryEntry<ImmuneEnchantment> reg(String id) {
		return REGISTRATE.enchantment(id, EnchantmentCategory.ARMOR, ImmuneEnchantment::new)
				.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();
	}

	public static void register() {
	}

}
