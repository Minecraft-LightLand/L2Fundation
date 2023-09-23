package dev.xkmc.l2complements.content.enchantment.digging;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Predicate;

public interface BlockBreakerInstance {

	List<BlockPos> find(Level level, BlockPos pos, Predicate<BlockPos> pred);

}
