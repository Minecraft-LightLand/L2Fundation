package dev.xkmc.l2foundation.init.registrate;


import dev.xkmc.l2foundation.content.effect.force.ArmorReduceEffect;
import dev.xkmc.l2foundation.content.effect.force.FlameEffect;
import dev.xkmc.l2foundation.content.effect.force.IceEffect;
import dev.xkmc.l2foundation.content.effect.force.StoneCageEffect;
import dev.xkmc.l2foundation.content.effect.skill.EmeraldPopeEffect;
import dev.xkmc.l2foundation.init.L2Foundation;
import dev.xkmc.l2library.repack.registrate.builders.NoConfigBuilder;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * handles enchantment, mob effects, and potions
 */
public class LFEffects {

	public static final RegistryEntry<EmeraldPopeEffect> EMERALD = genEffect("emerald", () -> new EmeraldPopeEffect(MobEffectCategory.NEUTRAL, 0x00FF00));
	public static final RegistryEntry<FlameEffect> FLAME = genEffect("flame", () -> new FlameEffect(MobEffectCategory.HARMFUL, 0xFF0000));
	public static final RegistryEntry<IceEffect> ICE = genEffect("frozen", () -> new IceEffect(MobEffectCategory.HARMFUL, 0x7f7fff));
	public static final RegistryEntry<ArmorReduceEffect> ARMOR_REDUCE = genEffect("armor_reduce", () -> new ArmorReduceEffect(MobEffectCategory.HARMFUL, 0xFFFFFF));
	public static final RegistryEntry<StoneCageEffect> STONE_CAGE = genEffect("stone_cage", () -> new StoneCageEffect(MobEffectCategory.HARMFUL, 0x000000));

	public static final List<RegistryEntry<? extends Potion>> POTION_LIST = new ArrayList<>();

	public static <T extends MobEffect> RegistryEntry<T> genEffect(String name, NonNullSupplier<T> sup) {
		return L2Foundation.REGISTRATE.entry(name, cb -> new NoConfigBuilder<>(L2Foundation.REGISTRATE, L2Foundation.REGISTRATE, name, cb, ForgeRegistries.Keys.MOB_EFFECTS, sup))
				.lang(MobEffect::getDescriptionId).register();
	}

	private static final List<Runnable> TEMP = new ArrayList<>();

	public static void registerBrewingRecipe() {
		TEMP.forEach(Runnable::run);
	}

	public static void register() {
		regPotion3("flame", FLAME::get, LFItems.SOUL_FLAME::get, 400, 600, 1200, 0, 1);
		regPotion2("frozen", ICE::get, LFItems.HARD_ICE::get, 3600, 9600);
		regPotion2("stone_cage", STONE_CAGE::get, LFItems.BLACKSTONE_CORE::get, 1200, 3600);
		regPotion2("levitation", () -> MobEffects.LEVITATION, LFItems.CAPTURED_BULLET::get, 200, 600);
		regPotion3("resistance", () -> MobEffects.DAMAGE_RESISTANCE, LFItems.EXPLOSION_SHARD::get, 400, 600, 1200, 1, 2);
	}

	private static <T extends Potion> RegistryEntry<T> genPotion(String name, NonNullSupplier<T> sup) {
		RegistryEntry<T> ans = L2Foundation.REGISTRATE.entry(name, (cb) -> new NoConfigBuilder<>(L2Foundation.REGISTRATE, L2Foundation.REGISTRATE, name, cb, ForgeRegistries.Keys.POTIONS, sup)).register();
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


}

