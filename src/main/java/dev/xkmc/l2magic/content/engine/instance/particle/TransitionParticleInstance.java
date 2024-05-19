package dev.xkmc.l2magic.content.engine.instance.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.ConfigurationRegistry;
import dev.xkmc.l2magic.content.engine.core.ConfigurationType;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import dev.xkmc.l2magic.content.engine.variable.ColorVariable;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;

public record TransitionParticleInstance(ColorVariable start, ColorVariable end, DoubleVariable scale,
										 DoubleVariable speed)
		implements ParticleInstance<TransitionParticleInstance> {

	public static final Codec<TransitionParticleInstance> CODEC = RecordCodecBuilder.create(i -> i.group(
			ColorVariable.CODEC.fieldOf("start").forGetter(e -> e.start),
			ColorVariable.CODEC.fieldOf("end").forGetter(e -> e.end),
			DoubleVariable.CODEC.fieldOf("scale").forGetter(e -> e.scale),
			DoubleVariable.CODEC.fieldOf("speed").forGetter(e -> e.speed)
	).apply(i, TransitionParticleInstance::new));

	@Override
	public ConfigurationType<TransitionParticleInstance> type() {
		return ConfigurationRegistry.TRANSITION_PARTICLE.get();
	}

	@Override
	public ParticleOptions particle(EngineContext ctx) {
		return new DustColorTransitionOptions(start.eval(ctx), end.eval(ctx), (float) scale.eval(ctx));
	}

}
