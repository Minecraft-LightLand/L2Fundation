package dev.xkmc.l2magic.content.engine.core;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2magic.content.engine.instance.ListInstance;
import dev.xkmc.l2magic.content.engine.instance.particle.*;
import dev.xkmc.l2magic.content.engine.iterator.DelayedIterator;
import dev.xkmc.l2magic.content.engine.iterator.LinearIterator;
import dev.xkmc.l2magic.content.engine.iterator.LoopIterator;
import dev.xkmc.l2magic.content.engine.iterator.RingRandomIterator;
import dev.xkmc.l2magic.content.engine.modifier.*;
import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;

import java.util.function.Supplier;

public class ConfigurationRegistry {

	public static final ResourceKey<Registry<ConfigurationType<?>>> CONFIG
			= L2Complements.REGISTRATE.makeRegistry("engine_configuration", RegistryBuilder::new);

	public static final Supplier<Registry<ConfigurationType<?>>> REGISTRY =
			Suppliers.memoize(() -> Wrappers.cast(RegistryManager.ACTIVE.getRegistry(CONFIG)));

	public static final RegistryEntry<ConfigurationType<ListInstance>> LIST = register("list", ListInstance.CODEC);

	public static final RegistryEntry<ConfigurationType<DelayModifier>> DELAY = register("delay", DelayModifier.CODEC);
	public static final RegistryEntry<ConfigurationType<ForwardOffsetModifier>> FORWARD = register("forward", ForwardOffsetModifier.CODEC);
	public static final RegistryEntry<ConfigurationType<RotationModifier>> ROTATE = register("rotate", RotationModifier.CODEC);
	public static final RegistryEntry<ConfigurationType<OffsetModifier>> OFFSET = register("offset", OffsetModifier.CODEC);
	public static final RegistryEntry<ConfigurationType<RandomOffsetModifier>> RANDOM_OFFSET = register("random_offset", RandomOffsetModifier.CODEC);

	public static final RegistryEntry<ConfigurationType<LoopIterator>> ITERATE = register("iterate", LoopIterator.CODEC);
	public static final RegistryEntry<ConfigurationType<DelayedIterator>> ITERATE_DELAY = register("iterate_delayed", DelayedIterator.CODEC);
	public static final RegistryEntry<ConfigurationType<LinearIterator>> ITERATE_LINEAR = register("iterate_linear", LinearIterator.CODEC);
	public static final RegistryEntry<ConfigurationType<RingRandomIterator>> RANDOM_FAN = register("random_pos_fan", RingRandomIterator.CODEC);

	public static final RegistryEntry<ConfigurationType<SimpleParticleInstance>> SIMPLE_PARTICLE = register("particle", SimpleParticleInstance.CODEC);
	public static final RegistryEntry<ConfigurationType<BlockParticleInstance>> BLOCK_PARTICLE = register("block_particle", BlockParticleInstance.CODEC);
	public static final RegistryEntry<ConfigurationType<ItemParticleInstance>> ITEM_PARTICLE = register("item_particle", ItemParticleInstance.CODEC);
	public static final RegistryEntry<ConfigurationType<DustParticleInstance>> DUST_PARTICLE = register("dust_particle", DustParticleInstance.CODEC);
	public static final RegistryEntry<ConfigurationType<TransitionParticleInstance>> TRANSITION_PARTICLE = register("transition_particle", TransitionParticleInstance.CODEC);

	private static <T extends Record & EngineConfiguration<T>> RegistryEntry<ConfigurationType<T>>
	register(String id, Codec<T> codec) {
		return L2Complements.REGISTRATE.simple(id, CONFIG, () -> () -> codec);
	}


}
