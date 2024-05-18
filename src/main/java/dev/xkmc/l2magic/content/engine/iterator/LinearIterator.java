package dev.xkmc.l2magic.content.engine.iterator;

import dev.xkmc.l2magic.content.engine.core.EngineConfiguration;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public record LinearIterator(String alongDir, Vec3 offset, String alongOffset, String step,
							 boolean startFromOrigin, EngineConfiguration<?> child, @Nullable String index)
		implements Iterator<LinearIterator> {

	@Override
	public void execute(EngineContext ctx) {
		double alongDir = ctx.eval(alongDir());
		double alongOffset = ctx.eval(alongOffset());
		int step = (int) ctx.eval(step());
		for (int i = 0; i < step; i++) {
			int x = startFromOrigin ? i : i + 1;
			Vec3 pos = ctx.loc().pos()
					.add(ctx.loc().dir().scale(x * alongDir))
					.add(offset.scale(x * alongOffset));
			child.execute(ctx.iterateOn(ctx.loc().with(pos), index, i));
		}
	}

}
