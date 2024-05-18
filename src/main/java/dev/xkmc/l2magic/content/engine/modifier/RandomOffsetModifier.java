package dev.xkmc.l2magic.content.engine.modifier;

import dev.xkmc.l2magic.content.engine.core.EngineConfiguration;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import dev.xkmc.l2magic.content.engine.core.LocationContext;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.Logger;

public record RandomOffsetModifier(RandomOffsetModifier.Type type, Vec3 amount,
								   EngineConfiguration<?> child)
		implements Modifier<RandomOffsetModifier> {

	public enum Type {
		RECT, BALL, GAUSSIAN
	}

	@Override
	public LocationContext modify(EngineContext ctx) {
		Vec3 r = switch (type) {
			case RECT -> new Vec3(
					ctx.user().rand().nextDouble() * 2 - 1,
					ctx.user().rand().nextDouble() * 2 - 1,
					ctx.user().rand().nextDouble() * 2 - 1
			);
			case BALL -> new Vec3(
					ctx.user().rand().nextGaussian(),
					ctx.user().rand().nextGaussian(),
					ctx.user().rand().nextGaussian()
			).normalize();
			case GAUSSIAN -> new Vec3(
					ctx.user().rand().nextGaussian(),
					ctx.user().rand().nextGaussian(),
					ctx.user().rand().nextGaussian()
			);
		};
		return ctx.loc().add(r.multiply(amount));
	}

	@Override
	public boolean verify(Logger logger, String path) {
		boolean ans = Modifier.super.verify(logger, path);
		if (type == null) {
			logger.error(path + "/type: not a valid type. Type can be RECT, BALL, or GAUSSIAN");
			ans = false;
		}
		return ans;
	}
}
