package dev.xkmc.l2magic.content.engine.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.*;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;

public record RotationModifier(DoubleVariable degree, EngineConfiguration<?> child)
		implements Modifier<RotationModifier> {

	public static Codec<RotationModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
			DoubleVariable.CODEC.fieldOf("degree").forGetter(e -> e.degree),
			EngineConfiguration.CODEC.fieldOf("child").forGetter(e -> e.child)
	).apply(i, RotationModifier::new));

	@Override
	public ConfigurationType<RotationModifier> type() {
		return ConfigurationRegistry.ROTATE.get();
	}

	@Override
	public LocationContext modify(EngineContext ctx) {
		return ctx.loc().rotateDegree(degree.eval(ctx));
	}

}
