package dev.xkmc.l2magic.content.engine.modifier;

import dev.xkmc.l2magic.content.engine.core.EngineConfiguration;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import dev.xkmc.l2magic.content.engine.core.LocationContext;

public record RotationModifier(String degree, EngineConfiguration<?> child)
		implements Modifier<RotationModifier> {

	@Override
	public LocationContext modify(EngineContext ctx) {
		return ctx.loc().rotateDegree(ctx.eval(degree));
	}

}
