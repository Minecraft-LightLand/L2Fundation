package dev.xkmc.l2magic.content.engine.instance.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public record SimpleParticleInstance(@Nullable ParticleType<?> type, String speed)
		implements ParticleInstance<SimpleParticleInstance> {

	@Deprecated
	public SimpleParticleInstance {
	}

	public SimpleParticleInstance of(SimpleParticleType type, String speed) {
		return new SimpleParticleInstance(type, speed);
	}

	@Nullable
	@Override
	public ParticleOptions particle() {
		return type instanceof ParticleOptions opt ? opt : null;
	}

	@Override
	public boolean verify(Logger logger, String path) {
		boolean ans = true;
		if (type == null) {
			logger.error(path + ": [type] is not a valid particle type");
			ans = false;
		}
		if (particle() == null) {
			logger.error(path + ": <" + ForgeRegistries.PARTICLE_TYPES.getKey(type) + "> of class <" + type.getClass().getSimpleName() + ">is not a valid simple particle type");
			ans = false;
		}
		return ans;
	}

}
