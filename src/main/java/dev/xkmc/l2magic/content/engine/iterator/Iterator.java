package dev.xkmc.l2magic.content.engine.iterator;

import dev.xkmc.l2magic.content.engine.core.BuilderContext;
import dev.xkmc.l2magic.content.engine.core.ConfigurationType;
import dev.xkmc.l2magic.content.engine.core.EngineConfiguration;
import dev.xkmc.l2magic.content.engine.variable.Variable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public interface Iterator<T extends Record & Iterator<T>> extends EngineConfiguration<T> {

	EngineConfiguration<?> child();

	@Nullable
	String index();

	default Set<String> params() {
		String str = index();
		return str == null ? Set.of() : Set.of(str);
	}

	@Override
	default boolean verify(BuilderContext ctx) {
		boolean verify = EngineConfiguration.super.verify(ctx);
		return verify & child().verify(index() == null ? ctx.of("child") : ctx.of("child", params()));
	}

}
