package dev.xkmc.l2magic.content.engine.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.*;
import dev.xkmc.l2magic.content.engine.variable.IntVariable;

public record DelayModifier(IntVariable tick, EngineConfiguration<?> child)
		implements EngineConfiguration<DelayModifier> {

	public static Codec<DelayModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
			IntVariable.CODEC.fieldOf("tick").forGetter(e -> e.tick),
			EngineConfiguration.CODEC.fieldOf("child").forGetter(e -> e.child)
	).apply(i, DelayModifier::new));

	@Override
	public ConfigurationType<DelayModifier> type() {
		return ConfigurationRegistry.DELAY.get();
	}

	@Override
	public void execute(EngineContext ctx) {
		ctx.schedule(tick.eval(ctx), () -> child.execute(ctx));
	}

	@Override
	public boolean verify(BuilderContext ctx) {
		return EngineConfiguration.super.verify(ctx) & child().verify(ctx.of("child"));
	}
}
