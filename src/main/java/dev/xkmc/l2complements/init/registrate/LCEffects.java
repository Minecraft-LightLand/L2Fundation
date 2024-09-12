package dev.xkmc.l2complements.init.registrate;


import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2complements.content.effect.*;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.init.reg.registrate.PotionBuilder;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

public class LCEffects {

	public static final SimpleEntry<MobEffect> EMERALD = genEffect("emerald_splash", "Emerald Splash", () -> new EmeraldPopeEffect(MobEffectCategory.NEUTRAL, 0x00FF00),
			"Attack all surrounding enemies. Damage is based on currently player stats and weapons.");
	public static final SimpleEntry<MobEffect> FLAME = genEffect("soul_burning", "Soul Burning", () -> new FlameEffect(MobEffectCategory.HARMFUL, 0xFF0000),
			"Continuously damage the entity. Bypass fire resistance.");
	public static final SimpleEntry<MobEffect> ICE = genEffect("frost", "Frost", () -> new IceEffect(MobEffectCategory.HARMFUL, 0x7f7fff),
			"Slow down entity, and freeze them as if they are on powdered snow.");
	public static final SimpleEntry<MobEffect> ARMOR_REDUCE = genEffect("armor_corrosion", "Armor Corrosion", () -> new ArmorReduceEffect(MobEffectCategory.HARMFUL, 0xFFFFFF),
			"Reduce armor value significantly.");
	public static final SimpleEntry<MobEffect> INCARCERATE = genEffect("incarceration", "Incarceration", () -> new IncarcreationEffect(MobEffectCategory.HARMFUL, 0x000000),
			"Immobilize the entity. Making it cannot move and unaffected by external forces.");
	public static final SimpleEntry<MobEffect> CURSE = genEffect("curse", "Curse", () -> new CurseEffect(MobEffectCategory.HARMFUL, 0x3f3f3f),
			"Make the entity cannot heal.");
	public static final SimpleEntry<MobEffect> BLEED = genEffect("bleed", "Bleed", () -> new BleedEffect(MobEffectCategory.HARMFUL, 0x7f0000),
			"Make the entity lose attack and speed, and damage the entity every 3 seconds. Stacks when applied.");
	public static final SimpleEntry<MobEffect> CLEANSE = genEffect("cleanse", "Cleanse", () -> new CleanseEffect(MobEffectCategory.NEUTRAL, 0xffff7f),
			"Clear all potion effects and make the entity immune to potion effects.");

	public static final PotionBuilder BUILDER = new PotionBuilder(L2Complements.REGISTRATE);

	static {
		BUILDER.regPotion3("soul_burning", FLAME, LCItems.SOUL_FLAME, 400, 600, 1000, 0, 1);
		BUILDER.regPotion2("frost", ICE, LCItems.HARD_ICE, 3600, 9600);
		BUILDER.regPotion2("incarceration", INCARCERATE, LCItems.BLACKSTONE_CORE, 1200, 3600);
		BUILDER.regPotion2("curse", CURSE, LCItems.CURSED_DROPLET, 3600, 9600);
		BUILDER.regPotion2("cleanse", CLEANSE, LCItems.LIFE_ESSENCE, 3600, 9600);
		BUILDER.interleave("armor_corrosion", ARMOR_REDUCE, 600, 1200, 3600, 0, 1,
				Items.MAGMA_CREAM, Potions.WEAKNESS, Potions.LONG_WEAKNESS, null,
				Items.FERMENTED_SPIDER_EYE, Potions.FIRE_RESISTANCE, Potions.LONG_FIRE_RESISTANCE, null
		);
		BUILDER.regPotion2("levitation", MobEffects.LEVITATION, LCItems.CAPTURED_BULLET, 200, 600);
		BUILDER.regPotion3("resistance", MobEffects.DAMAGE_RESISTANCE, LCItems.EXPLOSION_SHARD, 400, 600, 1200, 1, 2);
		BUILDER.regPotion3("emerald_splash", EMERALD, LCItems.EMERALD, 1200, 1200, 2400, 0, 1, LCItems.FORCE_FIELD, LCItems.RESONANT_FEATHER);
		L2Complements.REGISTRATE.addRegisterCallback(Registries.ITEM, () -> BUILDER.regTab(LCItems.TAB_ITEM.key()));
	}

	private static <T extends MobEffect> SimpleEntry<MobEffect> genEffect(String name, String lang, NonNullSupplier<T> sup, String desc) {
		return new SimpleEntry<>(L2Complements.REGISTRATE.effect(name, sup, desc).lang(MobEffect::getDescriptionId, lang).register());
	}

	public static void register() {
	}

}

