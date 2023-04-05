package dev.xkmc.l2complements.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2complements.content.enchantment.armors.DurableArmorEnchantment;
import dev.xkmc.l2complements.content.enchantment.armors.FlameThornEnchantment;
import dev.xkmc.l2complements.content.enchantment.armors.IceThornEnchantment;
import dev.xkmc.l2complements.content.enchantment.armors.StableBodyEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.ImmuneEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.SingleLevelEnchantment;
import dev.xkmc.l2complements.content.enchantment.special.LegendaryEnchantment;
import dev.xkmc.l2complements.content.enchantment.special.LifeSyncEnchantment;
import dev.xkmc.l2complements.content.enchantment.special.SoulBindingEnchantment;
import dev.xkmc.l2complements.content.enchantment.weapon.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import static dev.xkmc.l2complements.init.L2Complements.REGISTRATE;

public class LCEnchantments {

	public static final EnchantmentCategory ALL = EnchantmentCategory.create("ALL", e -> e.getMaxStackSize() == 1);

	static {
		LCItems.TAB.setEnchantmentCategories(ALL);
	}

	public static final RegistryEntry<ImmuneEnchantment> ENCH_PROJECTILE = reg("projectile_reject");
	public static final RegistryEntry<ImmuneEnchantment> ENCH_FIRE = reg("fire_reject");
	public static final RegistryEntry<ImmuneEnchantment> ENCH_ENVIRONMENT = reg("environment_reject");
	public static final RegistryEntry<ImmuneEnchantment> ENCH_EXPLOSION = reg("explosion_reject");
	public static final RegistryEntry<ImmuneEnchantment> ENCH_MAGIC = reg("magic_reject");
	public static final RegistryEntry<ImmuneEnchantment> ENCH_INVINCIBLE = reg("invincible");

	public static final RegistryEntry<SingleLevelEnchantment> SHULKER_ARMOR = REGISTRATE
			.enchantment("shulker_armor", ALL, SingleLevelEnchantment::new)
			.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).lang("Transparent").register();

	public static final RegistryEntry<StableBodyEnchantment> STABLE_BODY = REGISTRATE
			.enchantment("stable_body", EnchantmentCategory.ARMOR_CHEST, StableBodyEnchantment::new)
			.addSlots(EquipmentSlot.CHEST).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<LifeSyncEnchantment> LIFE_SYNC = REGISTRATE
			.enchantment("life_sync", EnchantmentCategory.BREAKABLE, LifeSyncEnchantment::new)
			.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<LegendaryEnchantment> ETERNAL = REGISTRATE
			.enchantment("eternal", EnchantmentCategory.BREAKABLE, LegendaryEnchantment::new)
			.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<LegendaryEnchantment> HARDENED = REGISTRATE
			.enchantment("hardened", EnchantmentCategory.BREAKABLE, LegendaryEnchantment::new)
			.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<WindSweepEnchantment> WIND_SWEEP = REGISTRATE
			.enchantment("wind_sweep", EnchantmentCategory.WEAPON, WindSweepEnchantment::new)
			.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<SingleLevelEnchantment> ENDER_MASK = REGISTRATE
			.enchantment("ender_mask", EnchantmentCategory.ARMOR_HEAD, SingleLevelEnchantment::new)
			.addSlots(EquipmentSlot.HEAD).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<SingleLevelEnchantment> SHINNY = REGISTRATE
			.enchantment("shinny", EnchantmentCategory.WEARABLE, SingleLevelEnchantment::new)
			.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<SingleLevelEnchantment> SNOW_WALKER = REGISTRATE
			.enchantment("snow_walker", EnchantmentCategory.ARMOR_FEET, SingleLevelEnchantment::new)
			.addSlots(EquipmentSlot.FEET).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<SoulBindingEnchantment> SOUL_BOUND = REGISTRATE
			.enchantment("soul_bound", ALL, SoulBindingEnchantment::new)
			.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

	public static final RegistryEntry<SingleLevelEnchantment> DAMPENED = REGISTRATE
			.enchantment("dampened", EnchantmentCategory.WEARABLE, SingleLevelEnchantment::new)
			.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).register();

	public static final RegistryEntry<IceBladeEnchantment> ICE_BLADE = REGISTRATE
			.enchantment("ice_blade", EnchantmentCategory.WEAPON, IceBladeEnchantment::new)
			.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).register();

	public static final RegistryEntry<SoulFlameBladeEnchantment> FLAME_BLADE = REGISTRATE
			.enchantment("soul_flame_blade", EnchantmentCategory.WEAPON, SoulFlameBladeEnchantment::new)
			.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).register();

	public static final RegistryEntry<CurseBladeEnchantment> CURSE_BLADE = REGISTRATE
			.enchantment("cursed_blade", EnchantmentCategory.WEAPON, CurseBladeEnchantment::new)
			.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).register();

	public static final RegistryEntry<SharpBladeEnchantment> SHARP_BLADE = REGISTRATE
			.enchantment("sharp_blade", EnchantmentCategory.WEAPON, SharpBladeEnchantment::new)
			.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).register();

	public static final RegistryEntry<IceThornEnchantment> ICE_THORN = REGISTRATE
			.enchantment("ice_thorn", EnchantmentCategory.ARMOR, IceThornEnchantment::new)
			.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).register();

	public static final RegistryEntry<FlameThornEnchantment> FLAME_THORN = REGISTRATE
			.enchantment("soul_flame_thorn", EnchantmentCategory.ARMOR, FlameThornEnchantment::new)
			.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).register();

	public static final RegistryEntry<SingleLevelEnchantment> ENDER = REGISTRATE
			.enchantment("ender_reach", EnchantmentCategory.DIGGER, SingleLevelEnchantment::new)
			.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).register();

	public static final RegistryEntry<DurableArmorEnchantment> DURABLE_ARMOR = REGISTRATE
			.enchantment("durable_armor", EnchantmentCategory.ARMOR, DurableArmorEnchantment::new)
			.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).lang("Durable Armor").register();

	public static final RegistryEntry<SingleLevelEnchantment> SMELT = REGISTRATE
			.enchantment("smelt", EnchantmentCategory.DIGGER, SingleLevelEnchantment::new)
			.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).register();

	private static RegistryEntry<ImmuneEnchantment> reg(String id) {
		return REGISTRATE.enchantment(id, EnchantmentCategory.ARMOR, ImmuneEnchantment::new)
				.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();
	}

	public static void register() {
	}

}
