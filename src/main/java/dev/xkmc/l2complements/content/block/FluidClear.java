package dev.xkmc.l2complements.content.block;

import dev.xkmc.l2library.block.mult.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Supplier;

public class FluidClear {

	private static final int MAX = 15, DELAY = 2;

	public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 0, MAX);

	public static final FluidClearProperty WATER_CLEAR = new FluidClearProperty(ForgeMod.WATER_TYPE);
	public static final FluidClearProperty LAVA_CLEAR = new FluidClearProperty(ForgeMod.LAVA_TYPE);

	public record FluidClearProperty(Supplier<FluidType> fluid) implements
			ScheduleTickBlockMethod,
			CreateBlockStateBlockMethod,
			DefaultStateBlockMethod,
			PlacementBlockMethod,
			NeighborUpdateBlockMethod {

		@Override
		public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
			boolean clear = false;
			int dist = blockState.getValue(DISTANCE);
			for (Direction dire : Direction.values()) {
				BlockPos next = blockPos.relative(dire);
				BlockState state = serverLevel.getBlockState(next);
				FluidState fluidState = serverLevel.getFluidState(next);
				boolean block = fluidState.getFluidType() == fluid.get();
				clear |= block;
				if (dist < MAX && state.getBlock() instanceof LiquidBlock) {
					serverLevel.setBlock(next, blockState.setValue(DISTANCE, dist + 1), 3);
				}
			}
			if (!clear) {
				serverLevel.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
			}
		}

		@Override
		public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
			builder.add(DISTANCE);
		}

		@Override
		public BlockState getDefaultState(BlockState blockState) {
			return blockState.setValue(DISTANCE, 0);
		}

		@Override
		public BlockState getStateForPlacement(BlockState blockState, BlockPlaceContext blockPlaceContext) {
			return blockState;
		}

		@Override
		public void neighborChanged(Block block, BlockState blockState, Level level, BlockPos blockPos, Block block1, BlockPos blockPos1, boolean b) {
			level.scheduleTick(blockPos, block, DELAY);
		}
	}

}
