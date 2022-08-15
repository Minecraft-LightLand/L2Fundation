package dev.xkmc.l2foundation.content.questline.world.structure;

import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;

public abstract class BaseStructureFeature<S extends BaseStructureFeature<S>> extends Structure {

	protected BaseStructureFeature(StructureSettings settings) {
		super(settings);
	}

	@SuppressWarnings("unchecked")
	public S getThis() {
		return (S) this;
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

}
