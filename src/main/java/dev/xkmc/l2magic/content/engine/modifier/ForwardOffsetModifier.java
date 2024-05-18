package dev.xkmc.l2magic.content.engine.modifier;

import dev.xkmc.l2magic.content.engine.core.EngineConfiguration;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import dev.xkmc.l2magic.content.engine.core.LocationContext;

public record ForwardOffsetModifier(String distance, EngineConfiguration<?> child)
		implements Modifier<ForwardOffsetModifier> {

	@Override
	public LocationContext modify(EngineContext ctx) {
		return ctx.loc().add(ctx.loc().dir().scale(ctx.eval(distance)));
	}

}
