package dev.xkmc.l2complements.content.enchantment.digging;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public record TreeInstance(int x0, int x1, int y0, int y1, int z0, int z1, int max,
						   Function<BlockState, Integer> match) implements BlockBreakerInstance {

	@Override
	public List<BlockPos> find(Level level, BlockPos pos, Predicate<BlockPos> pred) {
		int rank = match.apply(level.getBlockState(pos));
		Set<BlockPos> added = new HashSet<>();
		List<BlockPos> l2 = new ArrayList<>();
		List<BlockPos> l1 = new ArrayList<>();
		if (rank <= 0) return l1;
		Queue<BlockPos> q2 = new ArrayDeque<>();
		Queue<BlockPos> q1 = new ArrayDeque<>();
		BlockPos npos = pos;
		if (rank == 2) {
			while (match.apply(level.getBlockState(npos.above())) == 2 && npos.getY() - pos.getY() < y1) {
				npos = npos.above();
			}
		}
		(rank == 2 ? q2 : q1).add(npos);

		while (!q2.isEmpty()) {
			var current = q2.poll();
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
						int r = match.apply(level.getBlockState(i));
						if (r <= 0) continue;
						(r == 2 ? q2 : q1).add(i);
						if (i.equals(pos)) continue;
						(r == 2 ? l2 : l1).add(i);
						if (l2.size() >= max) {
							return l2;
						}
					}
				}
			}
		}

		while (!q1.isEmpty()) {
			var current = q1.poll();
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
						int r = match.apply(level.getBlockState(i));
						if (r != 1) continue;
						q1.add(i);
						if (i.equals(pos)) continue;
						l1.add(i);
						if (l2.size() + l1.size() >= max) {
							l2.addAll(l1);
							return l2;
						}
					}
				}
			}
		}
		l2.addAll(l1);
		return l2;
	}

}
