package dev.xkmc.l2magic.content.engine.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.*;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public record OffsetModifier(DoubleVariable x, DoubleVariable y, DoubleVariable z, EngineConfiguration<?> child)
		implements Modifier<OffsetModifier> {

	public static Codec<OffsetModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
			DoubleVariable.CODEC.optionalFieldOf("x").forGetter(e -> Optional.of(e.x)),
			DoubleVariable.CODEC.optionalFieldOf("y").forGetter(e -> Optional.of(e.y)),
			DoubleVariable.CODEC.optionalFieldOf("z").forGetter(e -> Optional.of(e.z)),
			EngineConfiguration.CODEC.fieldOf("child").forGetter(e -> e.child)
	).apply(i, (x, y, z, c) -> new OffsetModifier(
			x.orElse(DoubleVariable.ZERO),
			y.orElse(DoubleVariable.ZERO),
			z.orElse(DoubleVariable.ZERO), c)));

	@Override
	public ConfigurationType<OffsetModifier> type() {
		return ConfigurationRegistry.OFFSET.get();
	}

	@Override
	public LocationContext modify(EngineContext ctx) {
		return ctx.loc().add(new Vec3(
				x.eval(ctx), y.eval(ctx), z.eval(ctx)
		));
	}

}
