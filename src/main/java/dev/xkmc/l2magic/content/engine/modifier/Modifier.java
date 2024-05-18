package dev.xkmc.l2magic.content.engine.modifier;

import dev.xkmc.l2magic.content.engine.core.EngineConfiguration;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import dev.xkmc.l2magic.content.engine.core.LocationContext;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

public interface Modifier<T extends Record & Modifier<T>> extends EngineConfiguration<T> {

	EngineConfiguration<?> child();

	LocationContext modify(EngineContext ctx);

	@Override
	default void execute(EngineContext ctx) {
		child().execute(ctx.with(modify(ctx)));
	}

	@MustBeInvokedByOverriders
	@Override
	default boolean verify(Logger logger, String path) {
		return child().verify(logger, path + "/child");
	}

}
