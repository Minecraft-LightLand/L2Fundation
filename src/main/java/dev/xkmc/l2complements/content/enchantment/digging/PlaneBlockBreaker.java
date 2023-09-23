package dev.xkmc.l2complements.content.enchantment.digging;

public record PlaneBlockBreaker(int radius) implements BlockBreaker {

	@Override
	public BlockBreakerInstance getInstance(DiggerContext ctx) {
		int r = radius + ctx.level() - 1;
		int x = r * (1 - Math.abs(ctx.dire().getStepX()));
		int y = r * (1 - Math.abs(ctx.dire().getStepY()));
		int z = r * (1 - Math.abs(ctx.dire().getStepZ()));
		return new RectInstance(-x, x, -y, y, -z, z);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}
}
