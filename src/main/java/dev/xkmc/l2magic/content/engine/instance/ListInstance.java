package dev.xkmc.l2magic.content.engine.instance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.*;

import java.util.List;

public record ListInstance(List<EngineConfiguration<?>> children)
		implements EngineConfiguration<ListInstance> {

	public static final Codec<ListInstance> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.list(EngineConfiguration.CODEC).fieldOf("children").forGetter(e -> e.children)
	).apply(i, ListInstance::new));

	@Override
	public ConfigurationType<ListInstance> type() {
		return ConfigurationRegistry.LIST.get();
	}

	@Override
	public void execute(EngineContext ctx) {
		for (var e : children) {
			e.execute(ctx);
		}
	}

	@Override
	public boolean verify(BuilderContext ctx) {
		boolean ans = EngineConfiguration.super.verify(ctx);
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) == null) {
				ctx.error("entry at index " + i + " is null");
				ans = false;
				continue;
			}
			ans &= children.get(i).verify(ctx.of("children[" + i + "]"));
		}
		return ans;
	}

}
