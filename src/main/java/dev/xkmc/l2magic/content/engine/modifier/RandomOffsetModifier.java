package dev.xkmc.l2magic.content.engine.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.*;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public record RandomOffsetModifier(RandomOffsetModifier.Type shape,
								   DoubleVariable x, DoubleVariable y, DoubleVariable z,
								   EngineConfiguration<?> child)
		implements Modifier<RandomOffsetModifier> {

	public enum Type {
		RECT, BALL, GAUSSIAN
	}

	public static Codec<RandomOffsetModifier> CODEC = RecordCodecBuilder.create(i -> i.group(
			ConfigurationAutomation.enumCodec(Type.class, Type.values()).fieldOf("shape").forGetter(e -> e.shape),
			DoubleVariable.CODEC.optionalFieldOf("x").forGetter(e -> Optional.of(e.x)),
			DoubleVariable.CODEC.optionalFieldOf("y").forGetter(e -> Optional.of(e.y)),
			DoubleVariable.CODEC.optionalFieldOf("z").forGetter(e -> Optional.of(e.z)),
			EngineConfiguration.CODEC.fieldOf("child").forGetter(e -> e.child)
	).apply(i, (t, x, y, z, c) -> new RandomOffsetModifier(t,
			x.orElse(DoubleVariable.ZERO),
			y.orElse(DoubleVariable.ZERO),
			z.orElse(DoubleVariable.ZERO), c)));

	@Override
	public ConfigurationType<RandomOffsetModifier> type() {
		return ConfigurationRegistry.RANDOM_OFFSET.get();
	}

	@Override
	public LocationContext modify(EngineContext ctx) {
		Vec3 r = switch (shape) {
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
		return ctx.loc().add(r.multiply(x.eval(ctx), y.eval(ctx), z.eval(ctx)));
	}

}
