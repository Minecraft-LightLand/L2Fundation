package dev.xkmc.l2complements.init.registrate;


import com.tterrag.registrate.builders.NoConfigBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2complements.content.effect.force.*;
import dev.xkmc.l2complements.content.effect.skill.BleedEffect;
import dev.xkmc.l2complements.content.effect.skill.CleanseEffect;
import dev.xkmc.l2complements.content.effect.skill.EmeraldPopeEffect;
import dev.xkmc.l2complements.init.L2Complements;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * handles enchantment, mob effects, and potions
 */
public class LCEffects {

	public static final List<RegistryEntry<? extends Potion>> POTION_LIST = new ArrayList<>();
	public static final Map<String, String> NAME_CACHE = new HashMap<>();

	public static final RegistryEntry<EmeraldPopeEffect> EMERALD = genEffect("emerald_splash", () -> new EmeraldPopeEffect(MobEffectCategory.NEUTRAL, 0x00FF00));
	public static final RegistryEntry<FlameEffect> FLAME = genEffect("flame", "Soul Burning", () -> new FlameEffect(MobEffectCategory.HARMFUL, 0xFF0000));
	public static final RegistryEntry<IceEffect> ICE = genEffect("frozen", "Frost", () -> new IceEffect(MobEffectCategory.HARMFUL, 0x7f7fff));
	public static final RegistryEntry<ArmorReduceEffect> ARMOR_REDUCE = genEffect("armor_reduce", "Armor Corrosion", () -> new ArmorReduceEffect(MobEffectCategory.HARMFUL, 0xFFFFFF));
	public static final RegistryEntry<StoneCageEffect> STONE_CAGE = genEffect("stone_cage", "Incarceration", () -> new StoneCageEffect(MobEffectCategory.HARMFUL, 0x000000));
	public static final RegistryEntry<CurseEffect> CURSE = genEffect("curse", "Cursed", () -> new CurseEffect(MobEffectCategory.HARMFUL, 0x3f3f3f));
	public static final RegistryEntry<BleedEffect> BLEED = genEffect("bleed", "Bleed", () -> new BleedEffect(MobEffectCategory.HARMFUL, 0x7f0000));
	public static final RegistryEntry<CleanseEffect> CLEANSE = genEffect("cleanse", "Cleansed", () -> new CleanseEffect(MobEffectCategory.NEUTRAL, 0xffff7f));

	public static <T extends MobEffect> RegistryEntry<T> genEffect(String name, NonNullSupplier<T> sup) {
		return L2Complements.REGISTRATE.entry(name, cb -> new NoConfigBuilder<>(L2Complements.REGISTRATE, L2Complements.REGISTRATE, name, cb, ForgeRegistries.Keys.MOB_EFFECTS, sup))
				.lang(MobEffect::getDescriptionId).register();
	}

	public static <T extends MobEffect> RegistryEntry<T> genEffect(String name, String lang, NonNullSupplier<T> sup) {
		NAME_CACHE.put(name, lang);
		return L2Complements.REGISTRATE.entry(name, cb -> new NoConfigBuilder<>(L2Complements.REGISTRATE, L2Complements.REGISTRATE, name, cb, ForgeRegistries.Keys.MOB_EFFECTS, sup))
				.lang(MobEffect::getDescriptionId, lang).register();
	}

	private static final List<Runnable> TEMP = new ArrayList<>();

	public static void registerBrewingRecipe() {
		TEMP.forEach(Runnable::run);
	}

	public static void register() {
		regPotion3("flame", FLAME::get, LCItems.SOUL_FLAME::get, 400, 600, 1000, 0, 1);
		regPotion2("frozen", ICE::get, LCItems.HARD_ICE::get, 3600, 9600);
		regPotion2("stone_cage", STONE_CAGE::get, LCItems.BLACKSTONE_CORE::get, 1200, 3600);
		regPotion2("curse", CURSE::get, LCItems.CURSED_DROPLET::get, 3600, 9600);
		regPotion2("cleanse", CLEANSE::get, LCItems.LIFE_ESSENCE::get, 3600, 9600);
		regPotion3("armor_reduce", ARMOR_REDUCE::get, 600, 1200, 3600, 0, 1,
				() -> Items.MAGMA_CREAM, Potions.WEAKNESS, Potions.LONG_WEAKNESS, null,
				() -> Items.FERMENTED_SPIDER_EYE, Potions.FIRE_RESISTANCE, Potions.LONG_FIRE_RESISTANCE, null
		);
		regPotion2("levitation", () -> MobEffects.LEVITATION, LCItems.CAPTURED_BULLET::get, 200, 600);
		regPotion3("resistance", () -> MobEffects.DAMAGE_RESISTANCE, LCItems.EXPLOSION_SHARD::get, 400, 600, 1200, 1, 2);
		regEmeraldPotion(EMERALD::get, LCItems.EMERALD::get);
	}

	private static <T extends Potion> RegistryEntry<T> genPotion(String name, NonNullSupplier<T> sup) {
		RegistryEntry<T> ans = L2Complements.REGISTRATE.entry(name, (cb) -> new NoConfigBuilder<>(L2Complements.REGISTRATE, L2Complements.REGISTRATE, name, cb, ForgeRegistries.Keys.POTIONS, sup)).register();
		POTION_LIST.add(ans);
		return ans;
	}

	private static void regPotion2(String id, Supplier<MobEffect> sup, Supplier<Item> item, int dur, int durLong) {
		var potion = genPotion(id, () -> new Potion(new MobEffectInstance(sup.get(), dur)));
		var longPotion = genPotion("long_" + id, () -> new Potion(new MobEffectInstance(sup.get(), durLong)));
		TEMP.add(() -> {
			PotionBrewing.addMix(Potions.AWKWARD, item.get(), potion.get());
			PotionBrewing.addMix(potion.get(), Items.REDSTONE, longPotion.get());
		});
	}

	private static void regPotion3(String id, Supplier<MobEffect> sup, Supplier<Item> item, int durStrong, int dur, int durLong, int amp, int ampStrong) {
		var potion = genPotion(id, () -> new Potion(new MobEffectInstance(sup.get(), dur, amp)));
		var longPotion = genPotion("long_" + id, () -> new Potion(new MobEffectInstance(sup.get(), durLong, amp)));
		var strongPotion = genPotion("strong_" + id, () -> new Potion(new MobEffectInstance(sup.get(), durStrong, ampStrong)));
		TEMP.add(() -> {
			PotionBrewing.addMix(Potions.AWKWARD, item.get(), potion.get());
			PotionBrewing.addMix(potion.get(), Items.REDSTONE, longPotion.get());
			PotionBrewing.addMix(potion.get(), Items.GLOWSTONE_DUST, strongPotion.get());
		});
	}

	private static void regPotion3(String id, Supplier<MobEffect> sup, int durStrong, int dur, int durLong, int amp, int ampStrong,
								   Supplier<Item> a, Potion ap, Potion lap, @Nullable Potion sap,
								   Supplier<Item> b, Potion bp, Potion lbp, @Nullable Potion sbp) {
		var potion = genPotion(id, () -> new Potion(new MobEffectInstance(sup.get(), dur, amp)));
		var longPotion = genPotion("long_" + id, () -> new Potion(new MobEffectInstance(sup.get(), durLong, amp)));
		var strongPotion = genPotion("strong_" + id, () -> new Potion(new MobEffectInstance(sup.get(), durStrong, ampStrong)));

		TEMP.add(() -> {
			PotionBrewing.addMix(potion.get(), Items.REDSTONE, longPotion.get());
			PotionBrewing.addMix(potion.get(), Items.GLOWSTONE_DUST, strongPotion.get());
			PotionBrewing.addMix(ap, a.get(), potion.get());
			PotionBrewing.addMix(lap, a.get(), longPotion.get());
			PotionBrewing.addMix(bp, b.get(), potion.get());
			PotionBrewing.addMix(lbp, b.get(), longPotion.get());
			if (sap != null) PotionBrewing.addMix(sap, a.get(), strongPotion.get());
			if (sbp != null) PotionBrewing.addMix(sbp, b.get(), strongPotion.get());
		});
	}

	private static void regEmeraldPotion(Supplier<MobEffect> sup, Supplier<Item> item) {
		var potion = genPotion("emerald_splash", () -> new Potion(new MobEffectInstance(sup.get(), 1200, 0)));
		var longPotion = genPotion("long_" + "emerald_splash", () -> new Potion(new MobEffectInstance(sup.get(), 2400, 0)));
		var strongPotion = genPotion("strong_" + "emerald_splash", () -> new Potion(new MobEffectInstance(sup.get(), 1200, 1)));
		TEMP.add(() -> {
			PotionBrewing.addMix(Potions.AWKWARD, item.get(), potion.get());
			PotionBrewing.addMix(potion.get(), LCItems.FORCE_FIELD.get(), longPotion.get());
			PotionBrewing.addMix(potion.get(), LCItems.RESONANT_FEATHER.get(), strongPotion.get());
		});
	}

}

