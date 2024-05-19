package dev.xkmc.l2magic.content.engine.iterator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.*;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import dev.xkmc.l2magic.content.engine.variable.IntVariable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;

public record RingRandomIterator(DoubleVariable minRadius, DoubleVariable maxRadius,
								 DoubleVariable minAngle, DoubleVariable maxAngle,
								 IntVariable count,
								 EngineConfiguration<?> child, @Nullable String index)
		implements Iterator<RingRandomIterator> {

	public static Codec<RingRandomIterator> CODEC = RecordCodecBuilder.create(i -> i.group(
			DoubleVariable.CODEC.optionalFieldOf("minRadius").forGetter(e -> Optional.of(e.minRadius)),
			DoubleVariable.CODEC.fieldOf("maxRadius").forGetter(e -> e.maxRadius),
			DoubleVariable.CODEC.optionalFieldOf("minAngle").forGetter(e -> Optional.of(e.minAngle)),
			DoubleVariable.CODEC.optionalFieldOf("maxAngle").forGetter(e -> Optional.of(e.maxAngle)),
			IntVariable.CODEC.fieldOf("count").forGetter(e -> e.count),
			EngineConfiguration.CODEC.fieldOf("child").forGetter(e -> e.child),
			Codec.STRING.optionalFieldOf("index").forGetter(e -> Optional.ofNullable(e.index))
	).apply(i, (a, b, c, d, e, f, g) -> new RingRandomIterator(
			a.orElse(DoubleVariable.ZERO), b,
			c.orElse(DoubleVariable.of("-180")), d.orElse(DoubleVariable.of("180")),
			e, f, g.orElse(null))));

	@Override
	public ConfigurationType<RingRandomIterator> type() {
		return ConfigurationRegistry.RANDOM_FAN.get();
	}

	private static double randomRadius(double min, double max, RandomSource rand) {
		double a = rand.nextDouble();
		double b = rand.nextDouble();
		double mid = (max + min) / 2;
		a = min + (max - min) * a;
		b *= mid;
		if (b > a) {
			a = mid * 2 - a;
		}
		return a;
	}

	@Override
	public void execute(EngineContext ctx) {
		double minRadius = minRadius().eval(ctx);
		double maxRadius = maxRadius().eval(ctx);
		double minAngle = minAngle().eval(ctx);
		double maxAngle = maxAngle().eval(ctx);
		int count = count().eval(ctx);
		var ori = Orientation.fromNormal(ctx.loc().dir());
		for (int i = 0; i < count; i++) {
			double th = ctx.user().rand().nextDouble() * (maxAngle - minAngle) + minAngle;
			double r = randomRadius(minRadius, maxRadius, ctx.user().rand());
			Vec3 dir = ori.rotateDegrees(th);
			Vec3 off = dir.scale(r);
			var param = new LinkedHashMap<>(ctx.parameters());
			if (index != null && !index.isEmpty()) {
				param.put(index, (double) i);
				param.put(index + "_angle", th);
				param.put(index + "_radius", r);
			}
			child.execute(new EngineContext(ctx.user(), LocationContext.of(off, dir, ori.normal()), param));
		}
	}

	@Override
	public Set<String> params() {
		if (index == null) return Set.of();
		return Set.of(index, index + "_angle", index + "_radius");
	}

}
