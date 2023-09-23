package dev.xkmc.l2complements.content.enchantment.digging;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public record RectInstance(int x0, int x1, int y0, int y1, int z0, int z1) implements BlockBreakerInstance {

	@Override
	public Collection<BlockPos> find(Level level, BlockPos pos, Predicate<BlockPos> pred) {
		List<BlockPos> list = new ArrayList<>();
		for (int x = x0; x <= x1; x++) {
			for (int y = y0; y <= y1; y++) {
				for (int z = z0; z <= z1; z++) {
					if (x == 0 && y == 0 && z == 0) continue;
					BlockPos i = pos.offset(x, y, z);
					if (pred.test(i)) {
						list.add(i);
					}
				}
			}
		}
		return list;
	}

}
