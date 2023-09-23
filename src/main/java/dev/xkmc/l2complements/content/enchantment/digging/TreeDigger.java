package dev.xkmc.l2complements.content.enchantment.digging;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.TagGen;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;

public record TreeDigger() implements BlockBreaker {
	@Override
	public BlockBreakerInstance getInstance(DiggerContext ctx) {
		int r = LCConfig.COMMON.treeChopMaxRadius.get();
		int h = LCConfig.COMMON.treeChopMaxHeight.get();
		int max = LCConfig.COMMON.treeChopMaxBlock.get();
		return new VienInstance(-r, r, -r, h, -r, r, max, this::match);
	}

	public int match(BlockState state) {
		if (state.is(BlockTags.LOGS)) {
			return 2;
		}
		if (state.is(TagGen.AS_LEAF)) {
			return 1;
		}
		return 0;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}
}
