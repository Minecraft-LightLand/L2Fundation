package dev.xkmc.l2magic.content.engine.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.*;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;

public record ForwardOffsetModifier(DoubleVariable distance, EngineConfiguration<?> child)
		implements Modifier<ForwardOffsetModifier> {

	public static Codec<ForwardOffsetModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
			DoubleVariable.CODEC.fieldOf("distance").forGetter(e -> e.distance),
			EngineConfiguration.CODEC.fieldOf("child").forGetter(e -> e.child)
	).apply(i, ForwardOffsetModifier::new));

	@Override
	public ConfigurationType<ForwardOffsetModifier> type() {
		return ConfigurationRegistry.FORWARD.get();
	}

	@Override
	public LocationContext modify(EngineContext ctx) {
		return ctx.loc().add(ctx.loc().dir().scale(distance.eval(ctx)));
	}

}
