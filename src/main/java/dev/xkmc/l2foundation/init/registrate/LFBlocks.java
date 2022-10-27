package dev.xkmc.l2foundation.init.registrate;

import dev.xkmc.l2foundation.init.L2Foundation;
import dev.xkmc.l2library.repack.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * handles blocks and block entities
 */
public class LFBlocks {

	static {
		L2Foundation.REGISTRATE.creativeModeTab(() -> LFItems.TAB_GENERATED);
	}

	public static final BlockEntry<AnvilBlock> ETERNAL_ANVIL = L2Foundation.REGISTRATE
			.block("eternal_anvil", p -> new AnvilBlock(BlockBehaviour.Properties.copy(Blocks.ANVIL)))
			.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.getEntry(), pvd.models().withExistingParent(ctx.getName(), "anvil")))
			.item().build().register();

	public static final BlockEntry<Block>[] GEN_BLOCK = L2Foundation.MATS.genBlockMats();

	public static void register() {
	}

}
