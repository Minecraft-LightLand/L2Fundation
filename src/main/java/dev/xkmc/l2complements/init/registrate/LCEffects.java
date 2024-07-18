package dev.xkmc.l2complements.init.registrate;


import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2complements.content.effect.force.*;
import dev.xkmc.l2complements.content.effect.skill.BleedEffect;
import dev.xkmc.l2complements.content.effect.skill.CleanseEffect;
import dev.xkmc.l2complements.content.effect.skill.EmeraldPopeEffect;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LCEffects {

	public static final SimpleEntry<MobEffect> EMERALD = genEffect("emerald_splash", () -> new EmeraldPopeEffect(MobEffectCategory.NEUTRAL, 0x00FF00),
			"Attack all surrounding enemies. Damage is based on currently player stats and weapons.");
	public static final SimpleEntry<MobEffect> FLAME = genEffect("flame", "Soul Burning", () -> new FlameEffect(MobEffectCategory.HARMFUL, 0xFF0000),
			"Continuously damage the entity. Bypass fire resistance, but fire-based mobs are immune to this.");
	public static final SimpleEntry<MobEffect> ICE = genEffect("frozen", "Frost", () -> new IceEffect(MobEffectCategory.HARMFUL, 0x7f7fff),
			"Slow down entity, and freeze them as if they are on powdered snow.");
	public static final SimpleEntry<MobEffect> ARMOR_REDUCE = genEffect("armor_reduce", "Armor Corrosion", () -> new ArmorReduceEffect(MobEffectCategory.HARMFUL, 0xFFFFFF),
			"Reduce armor value significantly.");
	public static final SimpleEntry<MobEffect> INCARCERATE = genEffect("stone_cage", "Incarceration", () -> new IncarcreationEffect(MobEffectCategory.HARMFUL, 0x000000),
			"Immobilize the entity. Making it cannot move and unaffected by external forces.");
	public static final SimpleEntry<MobEffect> CURSE = genEffect("curse", "Cursed", () -> new CurseEffect(MobEffectCategory.HARMFUL, 0x3f3f3f),
			"Make the entity cannot heal.");
	public static final SimpleEntry<MobEffect> BLEED = genEffect("bleed", "Bleed", () -> new BleedEffect(MobEffectCategory.HARMFUL, 0x7f0000),
			"Make the entity lose attack and speed, and damage the entity every 3 seconds. Stacks when applied.");
	public static final SimpleEntry<MobEffect> CLEANSE = genEffect("cleanse", "Cleansed", () -> new CleanseEffect(MobEffectCategory.NEUTRAL, 0xffff7f),
			"Clear all potion effects and make the entity immune to potion effects.");

	private static <T extends MobEffect> SimpleEntry<MobEffect> genEffect(String name, NonNullSupplier<T> sup, String desc) {
		return new SimpleEntry<>(L2Complements.REGISTRATE.effect(name, sup, desc).lang(MobEffect::getDescriptionId).register());
	}

	private static <T extends MobEffect> SimpleEntry<MobEffect> genEffect(String name, String lang, NonNullSupplier<T> sup, String desc) {
		return new SimpleEntry<>(L2Complements.REGISTRATE.effect(name, sup, desc).lang(MobEffect::getDescriptionId, lang).register());
	}

	private static final List<Consumer<PotionBrewing.Builder>> TEMP = new ArrayList<>();

	public static void registerBrewingRecipe(RegisterBrewingRecipesEvent event) {
		var builder = event.getBuilder();
		TEMP.forEach(e->e.accept(builder));
	}

	public static void register() {
		regPotion3("flame", FLAME::holder, LCItems.SOUL_FLAME::get, 400, 600, 1000, 0, 1);
		regPotion2("frozen", ICE::holder, LCItems.HARD_ICE::get, 3600, 9600);
		regPotion2("stone_cage", INCARCERATE::holder, LCItems.BLACKSTONE_CORE::get, 1200, 3600);
		regPotion2("curse", CURSE::holder, LCItems.CURSED_DROPLET::get, 3600, 9600);
		regPotion2("cleanse", CLEANSE::holder, LCItems.LIFE_ESSENCE::get, 3600, 9600);
		regPotion3("armor_reduce", ARMOR_REDUCE::holder, 600, 1200, 3600, 0, 1,
				() -> Items.MAGMA_CREAM, () -> Potions.WEAKNESS, () -> Potions.LONG_WEAKNESS, null,
				() -> Items.FERMENTED_SPIDER_EYE, () -> Potions.FIRE_RESISTANCE, () -> Potions.LONG_FIRE_RESISTANCE, null
		);
		regPotion2("levitation", () -> MobEffects.LEVITATION, LCItems.CAPTURED_BULLET::get, 200, 600);
		regPotion3("resistance", () -> MobEffects.DAMAGE_RESISTANCE, LCItems.EXPLOSION_SHARD::get, 400, 600, 1200, 1, 2);
		regEmeraldPotion(EMERALD::holder, LCItems.EMERALD::get);
	}

	private static <T extends Potion> SimpleEntry<Potion> genPotion(String name, NonNullSupplier<T> sup) {
		var ans = L2Complements.REGISTRATE.potion(name, sup);
		return ans;
	}

	private static void regPotion2(String id, Supplier<Holder<MobEffect>> sup, Supplier<Item> item, int dur, int durLong) {
		var potion = genPotion(id, () -> new Potion(new MobEffectInstance(sup.get(), dur)));
		var longPotion = genPotion("long_" + id, () -> new Potion(new MobEffectInstance(sup.get(), durLong)));
		TEMP.add(e -> {
			e.addMix(Potions.AWKWARD, item.get(), potion.holder());
			e.addMix(potion.holder(), Items.REDSTONE, longPotion.holder());
		});
	}

	private static void regPotion3(String id, Supplier<Holder<MobEffect>> sup, Supplier<Item> item, int durStrong, int dur, int durLong, int amp, int ampStrong) {
		var potion = genPotion(id, () -> new Potion(new MobEffectInstance(sup.get(), dur, amp)));
		var longPotion = genPotion("long_" + id, () -> new Potion(new MobEffectInstance(sup.get(), durLong, amp)));
		var strongPotion = genPotion("strong_" + id, () -> new Potion(new MobEffectInstance(sup.get(), durStrong, ampStrong)));
		TEMP.add(e -> {
			e.addMix(Potions.AWKWARD, item.get(), potion.holder());
			e.addMix(potion.holder(), Items.REDSTONE, longPotion.holder());
			e.addMix(potion.holder(), Items.GLOWSTONE_DUST, strongPotion.holder());
		});
	}

	private static void regPotion3(String id, Supplier<Holder<MobEffect>> sup, int durStrong, int dur, int durLong, int amp, int ampStrong,
								   Supplier<Item> a, Supplier<Holder<Potion>> ap, Supplier<Holder<Potion>> lap, @Nullable Supplier<Holder<Potion>> sap,
								   Supplier<Item> b, Supplier<Holder<Potion>> bp, Supplier<Holder<Potion>> lbp, @Nullable Supplier<Holder<Potion>> sbp) {
		var potion = genPotion(id, () -> new Potion(new MobEffectInstance(sup.get(), dur, amp)));
		var longPotion = genPotion("long_" + id, () -> new Potion(new MobEffectInstance(sup.get(), durLong, amp)));
		var strongPotion = genPotion("strong_" + id, () -> new Potion(new MobEffectInstance(sup.get(), durStrong, ampStrong)));

		TEMP.add(e -> {
			e.addMix(potion.holder(), Items.REDSTONE, longPotion.holder());
			e.addMix(potion.holder(), Items.GLOWSTONE_DUST, strongPotion.holder());
			e.addMix(ap.get(), a.get(), potion.holder());
			e.addMix(lap.get(), a.get(), longPotion.holder());
			e.addMix(bp.get(), b.get(), potion.holder());
			e.addMix(lbp.get(), b.get(), longPotion.holder());
			if (sap != null) e.addMix(sap.get(), a.get(), strongPotion.holder());
			if (sbp != null) e.addMix(sbp.get(), b.get(), strongPotion.holder());
		});
	}

	private static void regEmeraldPotion(Supplier<Holder<MobEffect>> sup, Supplier<Item> item) {
		var potion = genPotion("emerald_splash", () -> new Potion(new MobEffectInstance(sup.get(), 1200, 0)));
		var longPotion = genPotion("long_" + "emerald_splash", () -> new Potion(new MobEffectInstance(sup.get(), 2400, 0)));
		var strongPotion = genPotion("strong_" + "emerald_splash", () -> new Potion(new MobEffectInstance(sup.get(), 1200, 1)));
		TEMP.add(e -> {
			e.addMix(Potions.AWKWARD, item.get(), potion.holder());
			e.addMix(potion.holder(), LCItems.FORCE_FIELD.get(), longPotion.holder());
			e.addMix(potion.holder(), LCItems.RESONANT_FEATHER.get(), strongPotion.holder());
		});
	}

}

