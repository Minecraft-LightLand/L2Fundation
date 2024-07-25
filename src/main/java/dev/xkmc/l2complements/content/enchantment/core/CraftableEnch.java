package dev.xkmc.l2complements.content.enchantment.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record CraftableEnch(int order, int color, int max) {

	public static final Codec<CraftableEnch> CODEC = RecordCodecBuilder.create(i -> i.group(
					Codec.INT.fieldOf("order").forGetter(e -> e.order),
					Codec.INT.fieldOf("color").forGetter(e -> e.color),
					Codec.INT.fieldOf("max").forGetter(e -> e.max)
			).apply(i, CraftableEnch::new)
	);

	public CraftableEnch(int order, int color) {
		this(order, color, 1);
	}

}
