package dev.xkmc.l2complements.content.enchantment.digging;

public record OreDigger(int r, int max) implements BlockBreaker {

	@Override
	public BlockBreakerInstance getInstance(DiggerContext ctx) {
		return new VienInstance(-r, r, -r, r, -r, r, max << (ctx.level() - 1), state -> state.getBlock() == ctx.state().getBlock() ? 1 : 0);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

}
