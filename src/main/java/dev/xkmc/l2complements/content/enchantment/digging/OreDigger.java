package dev.xkmc.l2complements.content.enchantment.digging;

public record OreDigger(int r, int max) implements SimpleNumberDesc {

	@Override
	public BlockBreakerInstance getInstance(DiggerContext ctx) {
		return new VienInstance(-r, r, -r, r, -r, r, max << (ctx.level() - 1), state -> state.getBlock() == ctx.state().getBlock());
	}

	@Override
	public int range(int lv) {
		return max << (lv - 1);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

}
