package dev.xkmc.l2magic.content.engine.instance.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.ConfigurationRegistry;
import dev.xkmc.l2magic.content.engine.core.ConfigurationType;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public record BlockParticleInstance(Block block, DoubleVariable speed)
		implements ParticleInstance<BlockParticleInstance> {

	public static final Codec<BlockParticleInstance> CODEC = RecordCodecBuilder.create(i -> i.group(
			ForgeRegistries.BLOCKS.getCodec().fieldOf("block").forGetter(e -> e.block),
			DoubleVariable.CODEC.fieldOf("speed").forGetter(e -> e.speed)
	).apply(i, BlockParticleInstance::new));

	@Override
	public ConfigurationType<BlockParticleInstance> type() {
		return ConfigurationRegistry.BLOCK_PARTICLE.get();
	}

	@Override
	public ParticleOptions particle(EngineContext ctx) {
		return new BlockParticleOption(ParticleTypes.BLOCK, block.defaultBlockState());
	}

}
