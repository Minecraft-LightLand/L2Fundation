package dev.xkmc.l2complements.content.enchantment.digging;

import net.minecraft.core.BlockPos;

public record PlaneChunkBreaker(int h) implements SimpleNumberDesc {

	@Override
	public BlockBreakerInstance getInstance(DiggerContext ctx) {
		int d = (h << (ctx.level() - 1)) - 1;
		int r = 15;
		int sx = ctx.dire().getStepX();
		int sy = ctx.dire().getStepY();
		int sz = ctx.dire().getStepZ();
		int x0, x1, y0, y1, z0, z1;

		x0 = sx > 0 ? -d : 0;
		x1 = sx == 0 ? r : sx < 0 ? d : 0;
		y0 = sy > 0 ? -d : 0;
		y1 = sy == 0 ? r : sy < 0 ? d : 0;
		z0 = sz > 0 ? -d : 0;
		z1 = sz == 0 ? r : sz < 0 ? d : 0;

		return new WrappedInstance(pos -> modulate(sx, sy, sz, pos), new RectInstance(x0, x1, y0, y1, z0, z1));
	}

	@Override
	public int range(int lv) {
		return h << (lv - 1);
	}

	private BlockPos modulate(int x, int y, int z, BlockPos pos) {
		int px = x == 0 ? pos.getX() & -16 : pos.getX();
		int py = y == 0 ? pos.getY() & -16 : pos.getY();
		int pz = z == 0 ? pos.getZ() & -16 : pos.getZ();
		return new BlockPos(px, py, pz);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

}
