package dev.xkmc.l2magic.content.engine.instance.particle;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public record BlockParticleInstance(@Nullable Block block, String speed)
		implements ParticleInstance<BlockParticleInstance> {

	@Nullable
	@Override
	public ParticleOptions particle() {
		if (block == null) return null;
		return new BlockParticleOption(ParticleTypes.BLOCK, block.defaultBlockState());
	}

	@Override
	public boolean verify(Logger logger, String path) {
		if (block == null) {
			logger.error(path + ": [block] is not a valid block id");
			return false;
		}
		return true;
	}

}
