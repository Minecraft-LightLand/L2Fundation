package dev.xkmc.l2complements.content.enchantment.data;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2core.serial.configval.DoubleConfigValue;
import net.minecraft.world.item.enchantment.LevelBasedValue;

import java.util.function.DoubleSupplier;

public record ConfigExponentValue(
		DoubleSupplier base
) implements LevelBasedValue {

	public static final MapCodec<ConfigExponentValue> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			DoubleConfigValue.CODEC.fieldOf("base").forGetter(ConfigExponentValue::base)
	).apply(i, ConfigExponentValue::new));

	@Override
	public float calculate(int lv) {
		return (float) (base.getAsDouble() * (1 << lv));
	}

	@Override
	public MapCodec<? extends LevelBasedValue> codec() {
		return MAP_CODEC;
	}

}
