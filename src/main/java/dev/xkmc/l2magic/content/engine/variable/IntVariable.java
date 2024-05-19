package dev.xkmc.l2magic.content.engine.variable;

import com.mojang.serialization.Codec;
import dev.xkmc.l2magic.content.engine.core.BuilderContext;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import net.minecraft.util.ExtraCodecs;

public record IntVariable(String str, ExpressionHolder exp) implements Variable {

	public static final Codec<IntVariable> CODEC = Codec.STRING.xmap(IntVariable::of, IntVariable::str);

	public static IntVariable of(String str) {
		return new IntVariable(str, ExpressionHolder.of(str));
	}

	public int eval(EngineContext ctx) {
		return (int) exp.eval(ctx);
	}

	@Override
	public boolean verify(BuilderContext ctx) {
		return exp.verify(ctx);
	}
}
