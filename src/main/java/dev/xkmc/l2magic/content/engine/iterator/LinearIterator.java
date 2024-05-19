package dev.xkmc.l2magic.content.engine.iterator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.ConfigurationRegistry;
import dev.xkmc.l2magic.content.engine.core.ConfigurationType;
import dev.xkmc.l2magic.content.engine.core.EngineConfiguration;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.content.engine.variable.IntVariable;
import dev.xkmc.l2magic.content.engine.variable.Variable;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public record LinearIterator(DoubleVariable alongDir, Vec3 offset, DoubleVariable alongOffset, IntVariable step,
							 boolean startFromOrigin, EngineConfiguration<?> child, @Nullable String index)
		implements Iterator<LinearIterator> {

	public static Codec<LinearIterator> CODEC = RecordCodecBuilder.create(i -> i.group(
			DoubleVariable.CODEC.optionalFieldOf("alongDir").forGetter(e -> Optional.of(e.alongDir)),
			Vec3.CODEC.optionalFieldOf("offset").forGetter(e -> Optional.of(e.offset)),
			DoubleVariable.CODEC.optionalFieldOf("alongOffset").forGetter(e -> Optional.of(e.alongDir)),
			IntVariable.CODEC.fieldOf("step").forGetter(e -> e.step),
			Codec.BOOL.optionalFieldOf("startFromOrigin").forGetter(e -> Optional.of(e.startFromOrigin)),
			EngineConfiguration.CODEC.fieldOf("child").forGetter(e -> e.child),
			Codec.STRING.optionalFieldOf("index").forGetter(e -> Optional.ofNullable(e.index))
	).apply(i, (a, b, c, d, e, f, g) -> new LinearIterator(a.orElse(DoubleVariable.ZERO), b.orElse(Vec3.ZERO),
			c.orElse(DoubleVariable.ZERO), d, e.orElse(true), f, g.orElse(null))));

	@Override
	public ConfigurationType<LinearIterator> type() {
		return ConfigurationRegistry.ITERATE_LINEAR.get();
	}

	@Override
	public void execute(EngineContext ctx) {
		double alongDir = alongDir().eval(ctx);
		double alongOffset = alongOffset().eval(ctx);
		int step = step().eval(ctx);
		for (int i = 0; i < step; i++) {
			int x = startFromOrigin ? i : i + 1;
			Vec3 pos = ctx.loc().pos()
					.add(ctx.loc().dir().scale(x * alongDir))
					.add(offset.scale(x * alongOffset));
			child.execute(ctx.iterateOn(ctx.loc().with(pos), index, i));
		}
	}

}
