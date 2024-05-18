package dev.xkmc.l2magic.content.engine.modifier;

import dev.xkmc.l2magic.content.engine.core.EngineConfiguration;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import org.apache.logging.log4j.Logger;

public record DelayModifier(String tick, EngineConfiguration<?> child)
		implements EngineConfiguration<DelayModifier> {

	@Override
	public void execute(EngineContext ctx) {
		ctx.schedule((int) ctx.eval(tick), () -> child.execute(ctx));
	}

	@Override
	public boolean verify(Logger logger, String path) {
		return true;
	}

}
