package dev.xkmc.l2foundation.init.worldgenreg;

import com.mojang.serialization.Codec;
import dev.xkmc.l2foundation.content.questline.world.structure.BaseStructureFeature;
import dev.xkmc.l2foundation.content.questline.world.structure.curseknight.CKMazeFeature;
import dev.xkmc.l2foundation.content.questline.world.structure.curseknight.CKMazePiece;
import dev.xkmc.l2foundation.init.L2Foundation;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.ArrayList;
import java.util.List;

public class StructureRegistrate {

	static final List<StructureEntry<?, ?>> LIST = new ArrayList<>();

	public static class StructureEntry<S extends BaseStructureFeature<S>, P extends StructurePiece> {

		public StructurePieceType piece_type;
		public StructureType<S> type;

		final ResourceLocation feature_id, piece_id;
		final Codec<S> codec;

		private final StructurePieceType.StructureTemplateType piece_gen;


		StructureEntry(String feature_name, String piece_name,
					   StructurePieceType.StructureTemplateType piece_gen,
					   Codec<S> codec) {
			this.feature_id = new ResourceLocation(L2Foundation.MODID, feature_name);
			this.piece_id = new ResourceLocation(L2Foundation.MODID, piece_name);
			this.piece_gen = piece_gen;
			this.codec = codec;
			LIST.add(this);
		}

		void registerType() {
			this.piece_type = Registry.register(Registry.STRUCTURE_PIECE, piece_id, piece_gen);
			this.type = Registry.register(Registry.STRUCTURE_TYPES, feature_id, () -> codec);
		}

	}

	public static final StructureEntry<CKMazeFeature, CKMazePiece> CKMAZE =
			new StructureEntry<>("cursedknight_maze", "cursedknight_maze_piece",
					CKMazePiece::new, CKMazeFeature.CODEC);

	public static void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> LIST.forEach(StructureEntry::registerType));
	}

	public static void register() {
	}

}
