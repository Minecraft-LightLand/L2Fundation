package dev.xkmc.l2complements.content.enchantment.data;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2core.serial.configval.DoubleConfigValue;
import net.minecraft.world.item.enchantment.LevelBasedValue;

import java.util.function.DoubleSupplier;

public record ConfigLinearValue(
		DoubleSupplier base, DoubleSupplier slope
) implements LevelBasedValue {

	public static final MapCodec<ConfigLinearValue> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			DoubleConfigValue.CODEC.fieldOf("base").forGetter(ConfigLinearValue::base),
			DoubleConfigValue.CODEC.fieldOf("slope").forGetter(ConfigLinearValue::slope)
	).apply(i, ConfigLinearValue::new));

	@Override
	public float calculate(int lv) {
		return (float) (base.getAsDouble() + lv * slope.getAsDouble());
	}

	@Override
	public MapCodec<? extends LevelBasedValue> codec() {
		return MAP_CODEC;
	}

}
