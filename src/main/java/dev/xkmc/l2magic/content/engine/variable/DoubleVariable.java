package dev.xkmc.l2magic.content.engine.variable;

import com.mojang.serialization.Codec;
import dev.xkmc.l2magic.content.engine.core.BuilderContext;
import dev.xkmc.l2magic.content.engine.core.EngineContext;

public record DoubleVariable(String str, ExpressionHolder exp) implements Variable {

	public static final Codec<DoubleVariable> CODEC = Codec.STRING.xmap(DoubleVariable::of, DoubleVariable::str);

	public static final DoubleVariable ZERO = of("0");

	public static DoubleVariable of(String str) {
		return new DoubleVariable(str, ExpressionHolder.of(str));
	}

	public double eval(EngineContext ctx) {
		return exp.eval(ctx);
	}

	@Override
	public boolean verify(BuilderContext ctx) {
		return exp.verify(ctx);
	}
}
