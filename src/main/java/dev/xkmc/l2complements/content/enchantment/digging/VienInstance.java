package dev.xkmc.l2complements.content.enchantment.digging;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public record VienInstance(int x0, int x1, int y0, int y1, int z0, int z1, int max,
						   Function<BlockState, Integer> match) implements BlockBreakerInstance {

	@Override
	public Collection<BlockPos> find(Level level, BlockPos pos, Predicate<BlockPos> pred) {
		int rank = match.apply(level.getBlockState(pos));
		Set<BlockPos> added = new HashSet<>();
		List<BlockPos> list = new ArrayList<>();
		Queue<Pair<BlockPos, Integer>> queue = new ArrayDeque<>();
		queue.add(Pair.of(pos, rank));
		added.add(pos);
		if (rank <= 0) return list;
		while (!queue.isEmpty()) {
			var current = queue.poll();
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						BlockPos i = current.getFirst().offset(x, y, z);
						if (added.contains(i)) {
							continue;
						}
						added.add(i);
						if (i.getX() - pos.getX() < x0 || i.getX() - pos.getX() > x1 ||
								i.getY() - pos.getY() < y0 || i.getY() - pos.getY() > y1 ||
								i.getZ() - pos.getZ() < z0 || i.getZ() - pos.getZ() > z1
						) continue;
						int r = match.apply(level.getBlockState(i));
						if (r > 0 && r <= current.getSecond()) {
							list.add(i);
							queue.add(Pair.of(i, r));
							if (list.size() >= max) {
								return list;
							}
						}
					}
				}
			}
		}
		return list;
	}

}
