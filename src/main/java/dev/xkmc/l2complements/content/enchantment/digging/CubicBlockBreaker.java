package dev.xkmc.l2complements.content.enchantment.digging;

public record CubicBlockBreaker(int radius) implements BlockBreaker {

	@Override
	public BlockBreakerInstance getInstance(DiggerContext ctx) {
		int r = radius + ctx.level() - 1;
		return new RectInstance(-r, r, -r, r, -r, r);
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
