package dev.xkmc.l2complements.content.client;

import net.minecraft.core.BlockPos;

import java.util.List;

public class ClusterBitSet {

	public static ClusterBitSet of(BlockPos center, List<BlockPos> list) {
		return new ClusterBitSet(center, list);
	}

	private boolean[][][] data;
	private int x0, y0, z0, x1, y1, z1, dx, dy, dz;

	private ClusterBitSet(BlockPos center, List<BlockPos> list) {
		x0 = x1 = center.getX();
		y0 = y1 = center.getY();
		z0 = z1 = center.getZ();
		for (var e : list) {
			x0 = Math.min(x0, e.getX());
			y0 = Math.min(y0, e.getY());
			z0 = Math.min(z0, e.getZ());
			x1 = Math.max(x1, e.getX());
			y1 = Math.max(y1, e.getY());
			z1 = Math.max(z1, e.getZ());
		}
		dx = x1 - x0 + 1;
		dy = y1 - y0 + 1;
		dz = z1 - z0 + 1;
		data = new boolean[dx][dy][dz];
		putData(center);
		for (var e : list) {
			putData(e);
		}
	}

	private void putData(BlockPos pos) {
		data[pos.getX() - x0][pos.getY() - y0][pos.getZ() - z0] = true;
	}

	private boolean data(int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 || x >= dx || y >= dy || z >= dz) return false;
		return data[x][y][z];
	}

	private boolean edge(boolean edge, boolean b00, boolean b01, boolean b10, boolean b11) {
		if (b00 && b01 && b10 && b11) return false;
		if (b00 && b01 && !b10 && !b11) return !edge;
		if (!b00 && !b01 && b10 && b11) return !edge;
		if (b00 & b10 && !b01 && !b11) return !edge;
		if (!b00 & !b10 && b01 && b11) return !edge;
		return b00 || b01 || b10 || b11;
	}

	public void render(boolean edge, Handle handle) {
		for (int x = 0; x <= dx; x++) {
			for (int y = 0; y <= dy; y++) {
				for (int z = 0; z <= dz; z++) {
					if (x < dx && edge(edge, data(x, y - 1, z - 1), data(x, y, z - 1), data(x, y - 1, z), data(x, y, z))) {
						handle.render(x0 + x, y0 + y, z0 + z, x0 + x + 1, y0 + y, z0 + z);
					}
					if (y < dy && edge(edge, data(x - 1, y, z - 1), data(x, y, z - 1), data(x - 1, y, z), data(x, y, z))) {
						handle.render(x0 + x, y0 + y, z0 + z, x0 + x, y0 + y + 1, z0 + z);
					}
					if (z < dz && edge(edge, data(x - 1, y - 1, z), data(x, y - 1, z), data(x - 1, y, z), data(x, y, z))) {
						handle.render(x0 + x, y0 + y, z0 + z, x0 + x, y0 + y, z0 + z + 1);
					}
				}
			}
		}
	}

	public interface Handle {

		void render(int x0, int y0, int z0, int x1, int y1, int z1);

	}

}
