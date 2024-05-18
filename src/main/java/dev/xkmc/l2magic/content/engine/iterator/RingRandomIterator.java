package dev.xkmc.l2magic.content.engine.iterator;

import dev.xkmc.l2magic.content.engine.core.EngineConfiguration;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import dev.xkmc.l2magic.content.engine.core.LocationContext;
import dev.xkmc.l2magic.content.engine.core.Orientation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;

public record RingRandomIterator(String minRadius, String maxRadius,
								 String minAngle, String maxAngle,
								 String count,
								 EngineConfiguration<?> child, @Nullable String index)
		implements Iterator<RingRandomIterator> {

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
		double minRadius = ctx.eval(minRadius());
		double maxRadius = ctx.eval(maxRadius());
		double minAngle = ctx.eval(minAngle());
		double maxAngle = ctx.eval(maxAngle());
		int count = (int) ctx.eval(count());
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

}
