package dev.xkmc.l2magic.content.engine.iterator;

import dev.xkmc.l2magic.content.engine.core.EngineConfiguration;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

import javax.annotation.Nullable;

public interface Iterator<T extends Record & Iterator<T>> extends EngineConfiguration<T> {

	EngineConfiguration<?> child();

	@Nullable
	String index();

	@MustBeInvokedByOverriders
	default boolean verify(Logger logger, String path) {
		return child().verify(logger, path + "/child");
	}

}
