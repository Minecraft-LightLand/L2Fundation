package dev.xkmc.l2magic.content.engine.instance.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.BuilderContext;
import dev.xkmc.l2magic.content.engine.core.ConfigurationRegistry;
import dev.xkmc.l2magic.content.engine.core.ConfigurationType;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public record SimpleParticleInstance(ParticleType<?> particle, DoubleVariable speed)
		implements ParticleInstance<SimpleParticleInstance> {

	public static final Codec<SimpleParticleInstance> CODEC = RecordCodecBuilder.create(i -> i.group(
			ForgeRegistries.PARTICLE_TYPES.getCodec().fieldOf("particle").forGetter(e -> e.particle),
			DoubleVariable.CODEC.fieldOf("speed").forGetter(e -> e.speed)
	).apply(i, SimpleParticleInstance::new));

	@Override
	public ConfigurationType<SimpleParticleInstance> type() {
		return ConfigurationRegistry.SIMPLE_PARTICLE.get();
	}

	@Nullable
	@Override
	public ParticleOptions particle(EngineContext ctx) {
		return particle instanceof ParticleOptions opt ? opt : null;
	}

	@Override
	public boolean verify(BuilderContext ctx) {
		if (!(particle instanceof ParticleOptions)) {
			ctx.of("particle").error("Invalid particle type");
			ParticleInstance.super.verify(ctx);
			return false;
		}
		return ParticleInstance.super.verify(ctx);
	}
}
