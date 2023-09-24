package dev.xkmc.l2complements.content.enchantment.digging;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public record WrappedInstance(Function<BlockPos, BlockPos> modulator, RectInstance ins)
		implements BlockBreakerInstance {

	@Override
	public List<BlockPos> find(Level level, BlockPos pos, Predicate<BlockPos> pred) {
		return ins.find(level, modulator.apply(pos), pred);
	}

}
