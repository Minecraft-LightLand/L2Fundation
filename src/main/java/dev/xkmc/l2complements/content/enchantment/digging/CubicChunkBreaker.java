package dev.xkmc.l2complements.content.enchantment.digging;

import net.minecraft.core.BlockPos;

public record CubicChunkBreaker(int rank) implements SimpleNumberDesc {

	@Override
	public BlockBreakerInstance getInstance(DiggerContext ctx) {
		int r = 1 << (rank + ctx.level() - 1);
		return new WrappedInstance(pos -> modulate(r, pos), new RectInstance(0, r - 1, 0, r - 1, 0, r - 1));
	}

	public BlockPos modulate(int r, BlockPos pos) {
		int x = pos.getX() & -r;
		int y = pos.getY() & -r;
		int z = pos.getZ() & -r;
		return new BlockPos(x, y, z);
	}

	@Override
	public int range(int lv) {
		return 1 << (rank + lv - 1);
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public boolean ignoreHardness() {
		return true;
	}

}
