package dev.xkmc.l2foundation.init.registrate;


import dev.xkmc.l2foundation.content.effect.force.ArmorReduceEffect;
import dev.xkmc.l2foundation.content.effect.force.FlameEffect;
import dev.xkmc.l2foundation.content.effect.force.IceEffect;
import dev.xkmc.l2foundation.content.effect.skill.EmeraldPopeEffect;
import dev.xkmc.l2foundation.content.effect.skill.QuickPullEffect;
import dev.xkmc.l2foundation.content.effect.skill.RunBowEffect;
import dev.xkmc.l2foundation.init.L2Foundation;
import dev.xkmc.l2library.repack.registrate.builders.NoConfigBuilder;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * handles enchantment, mob effects, and potions
 */
public class LFEffects {

	public static final RegistryEntry<FlameEffect> FLAME = genEffect("flame", () -> new FlameEffect(MobEffectCategory.HARMFUL, 0xFF0000));
	public static final RegistryEntry<EmeraldPopeEffect> EMERALD = genEffect("emerald", () -> new EmeraldPopeEffect(MobEffectCategory.NEUTRAL, 0x00FF00));
	public static final RegistryEntry<IceEffect> ICE = genEffect("frozen", () -> new IceEffect(MobEffectCategory.HARMFUL, 0x7f7fff));
	public static final RegistryEntry<ArmorReduceEffect> ARMOR_REDUCE = genEffect("armor_reduce", () -> new ArmorReduceEffect(MobEffectCategory.HARMFUL, 0xFFFFFF));
	public static final RegistryEntry<MobEffect> RUN_BOW = genEffect("run_bow", () -> new RunBowEffect(MobEffectCategory.BENEFICIAL, 0xffffff));
	public static final RegistryEntry<QuickPullEffect> QUICK_PULL = genEffect("quick_pull", () -> new QuickPullEffect(MobEffectCategory.BENEFICIAL, 0xFFFFFF));

	public static <T extends MobEffect> RegistryEntry<T> genEffect(String name, NonNullSupplier<T> sup) {
		return L2Foundation.REGISTRATE.entry(name, cb -> new NoConfigBuilder<>(L2Foundation.REGISTRATE, L2Foundation.REGISTRATE, name, cb, ForgeRegistries.Keys.MOB_EFFECTS, sup))
				.lang(MobEffect::getDescriptionId).register();
	}

	public static void register() {

	}

	public static void registerBrewingRecipe() {
	}

}
