package dev.xkmc.l2complements.content.enchantment.digging;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.function.Predicate;

public record VienInstance(int x0, int x1, int y0, int y1, int z0, int z1, int max,
						   Predicate<BlockState> match) implements BlockBreakerInstance {

	@Override
	public List<BlockPos> find(Level level, BlockPos pos, Predicate<BlockPos> pred) {
		List<BlockPos> list = new ArrayList<>();
		if (!match.test(level.getBlockState(pos))) {
			return list;
		}
		Set<BlockPos> added = new HashSet<>();
		Queue<BlockPos> queue = new ArrayDeque<>();
		queue.add(pos);
		added.add(pos);
		while (!queue.isEmpty()) {
			var current = queue.poll();
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						BlockPos i = current.offset(x, y, z);
						if (added.contains(i)) {
							continue;
						}
						added.add(i);
						if (i.getX() - pos.getX() < x0 || i.getX() - pos.getX() > x1 ||
								i.getY() - pos.getY() < y0 || i.getY() - pos.getY() > y1 ||
								i.getZ() - pos.getZ() < z0 || i.getZ() - pos.getZ() > z1
						) continue;
						if (match.test(level.getBlockState(i))) {
							list.add(i);
							queue.add(i);
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
