package dev.xkmc.l2magic.content.engine.instance.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.ConfigurationRegistry;
import dev.xkmc.l2magic.content.engine.core.ConfigurationType;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import dev.xkmc.l2magic.content.engine.variable.ColorVariable;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import org.apache.logging.log4j.Logger;

public record DustParticleInstance(ColorVariable color, DoubleVariable scale, DoubleVariable speed)
		implements ParticleInstance<DustParticleInstance> {

	public static final Codec<DustParticleInstance> CODEC = RecordCodecBuilder.create(i -> i.group(
			ColorVariable.CODEC.fieldOf("color").forGetter(e -> e.color),
			DoubleVariable.CODEC.fieldOf("scale").forGetter(e -> e.scale),
			DoubleVariable.CODEC.fieldOf("speed").forGetter(e -> e.speed)
	).apply(i, DustParticleInstance::new));

	@Override
	public ConfigurationType<DustParticleInstance> type() {
		return ConfigurationRegistry.DUST_PARTICLE.get();
	}

	@Override
	public ParticleOptions particle(EngineContext ctx) {
		return new DustParticleOptions(color.eval(ctx), (float) scale.eval(ctx));
	}

}
