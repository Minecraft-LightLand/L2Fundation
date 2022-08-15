package dev.xkmc.l2foundation.content.questline.block;

import dev.xkmc.l2foundation.init.registrate.LFBlocks;
import dev.xkmc.l2foundation.init.registrate.LFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class LaylineChargerBlock extends Block {

	public LaylineChargerBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getItemInHand(hand);
		BlockState below = level.getBlockState(pos.below());
		if (below.is(LFBlocks.LAYROOT_HEAD.get())) {
			if (stack.is(LFItems.LAYLINE_ORB.get())) {
				stack.shrink(1);
				level.setBlockAndUpdate(pos.below(), LFBlocks.LAYLINE_HEAD.getDefaultState());
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

}
