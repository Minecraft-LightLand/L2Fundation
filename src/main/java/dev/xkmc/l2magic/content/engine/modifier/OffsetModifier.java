package dev.xkmc.l2magic.content.engine.modifier;

import dev.xkmc.l2magic.content.engine.core.EngineConfiguration;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import dev.xkmc.l2magic.content.engine.core.LocationContext;
import net.minecraft.world.phys.Vec3;

public record OffsetModifier(Vec3 offset, EngineConfiguration<?> child)
		implements Modifier<OffsetModifier> {

	@Override
	public LocationContext modify(EngineContext ctx) {
		return ctx.loc().add(offset);
	}

}
