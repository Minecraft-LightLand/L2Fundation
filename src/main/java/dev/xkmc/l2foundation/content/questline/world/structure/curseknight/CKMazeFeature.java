package dev.xkmc.l2foundation.content.questline.world.structure.curseknight;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import dev.xkmc.l2library.idea.maze.generator.MazeConfig;
import dev.xkmc.l2foundation.content.questline.world.structure.BaseStructureFeature;
import dev.xkmc.l2foundation.init.worldgenreg.StructureRegistrate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.List;
import java.util.Optional;

public class CKMazeFeature extends BaseStructureFeature<CKMazeFeature> {

	public static Codec<CKMazeFeature> CODEC = simpleCodec(CKMazeFeature::new);

	protected CKMazeFeature(StructureSettings settings) {
		super(settings);
	}

	@Override
	public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
		Rotation rotation = Rotation.getRandom(context.random());
		BlockPos blockpos = this.getLowestYIn5by5BoxOffset7Blocks(context, rotation);
		return blockpos.getY() < 60 ? Optional.empty() : Optional.of(new Structure.GenerationStub(blockpos,
				(builder) -> generatePieces(builder, blockpos, context)));
	}

	@Override
	public StructureType<?> type() {
		return StructureRegistrate.CKMAZE.type;
	}

	private static void generatePieces(StructurePiecesBuilder builder, BlockPos blockpos, GenerationContext ctx) {
		MazeConfig config = new MazeConfig();
		config.invariant = 2;
		config.survive = 4;
		config.INVARIANCE_RIM = new int[][]{{0, 1, 2, 3, 4, 5, 6, 7}, {0, 4, 8, 12, 1, 2, 3, 5, 6, 7, 9, 10, 11, 13, 14, 15}};
		List<StructurePiece> list = Lists.newArrayList();
		CKMazeGenerator.addPieces(ctx.structureTemplateManager(), blockpos, list, ctx.random(), config);
		list.forEach(builder::addPiece);
	}

	@Override
	public void afterPlace(WorldGenLevel level, StructureManager manager, ChunkGenerator generator,
						   RandomSource random, BoundingBox box, ChunkPos chunkPos, PiecesContainer container) {
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int i = level.getMinBuildHeight();
		BoundingBox boundingbox = container.calculateBoundingBox();
		int j = boundingbox.minY();

		for (int k = box.minX(); k <= box.maxX(); ++k) {
			for (int l = box.minZ(); l <= box.maxZ(); ++l) {
				pos.set(k, j, l);
				while (boundingbox.isInside(pos) && !container.isInsidePiece(pos))
					pos.move(Direction.UP);
				if (!level.isEmptyBlock(pos) && boundingbox.isInside(pos)) {
					for (int i1 = pos.getY() - 1; i1 > i; --i1) {
						pos.setY(i1);
						if (!level.isEmptyBlock(pos) && !level.getBlockState(pos).getMaterial().isLiquid()) {
							break;
						}

						level.setBlock(pos, Blocks.COBBLESTONE.defaultBlockState(), 2);
					}
				}
			}
		}

	}

}
