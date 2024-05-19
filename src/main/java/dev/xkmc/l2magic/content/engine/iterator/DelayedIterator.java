package dev.xkmc.l2magic.content.engine.iterator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.ConfigurationRegistry;
import dev.xkmc.l2magic.content.engine.core.ConfigurationType;
import dev.xkmc.l2magic.content.engine.core.EngineConfiguration;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import dev.xkmc.l2magic.content.engine.variable.IntVariable;
import dev.xkmc.l2magic.content.engine.variable.Variable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public record DelayedIterator(IntVariable step, IntVariable delay, EngineConfiguration<?> child, @Nullable String index)
		implements Iterator<DelayedIterator> {

	public static Codec<DelayedIterator> CODEC = RecordCodecBuilder.create(i -> i.group(
			IntVariable.CODEC.fieldOf("step").forGetter(e -> e.step),
			IntVariable.CODEC.fieldOf("delay").forGetter(e -> e.delay),
			EngineConfiguration.CODEC.fieldOf("child").forGetter(e -> e.child),
			Codec.STRING.optionalFieldOf("index").forGetter(e -> Optional.ofNullable(e.index))
	).apply(i, (d, e, f, g) -> new DelayedIterator(d, e, f, g.orElse(null))));

	@Override
	public ConfigurationType<DelayedIterator> type() {
		return ConfigurationRegistry.ITERATE_DELAY.get();
	}

	@Override
	public void execute(EngineContext ctx) {
		int step = step().eval(ctx);
		for (int i = 0; i < step; i++) {
			int I = i;
			if (i == 0) child.execute(ctx.iterateOn(ctx.loc(), index, 0));
			else ctx.schedule(i, () -> child.execute(ctx.iterateOn(ctx.loc(), index, I)));
		}
	}

}
