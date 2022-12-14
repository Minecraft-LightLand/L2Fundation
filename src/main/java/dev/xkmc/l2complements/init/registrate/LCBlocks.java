package dev.xkmc.l2complements.init.registrate;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCMats;
import dev.xkmc.l2library.repack.registrate.util.entry.BlockEntry;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * handles blocks and block entities
 */
public class LCBlocks {

	static {
		L2Complements.REGISTRATE.creativeModeTab(() -> LCItems.TAB_GENERATED);
	}

	public static final BlockEntry<AnvilBlock> ETERNAL_ANVIL = L2Complements.REGISTRATE
			.block("eternal_anvil", p -> new AnvilBlock(BlockBehaviour.Properties.copy(Blocks.ANVIL)))
			.blockstate((ctx, pvd) -> pvd.horizontalBlock(ctx.getEntry(), pvd.models().withExistingParent(ctx.getName(), "anvil")))
			.tag(BlockTags.ANVIL, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL).item().tag(ItemTags.ANVIL).build().register();

	public static final BlockEntry<Block>[] GEN_BLOCK = L2Complements.MATS.genBlockMats(LCMats.values());

	public static void register() {
	}

}
