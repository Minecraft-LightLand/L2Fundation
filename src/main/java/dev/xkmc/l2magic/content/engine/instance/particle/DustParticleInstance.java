package dev.xkmc.l2magic.content.engine.instance.particle;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.Logger;

public record DustParticleInstance(String color, float scale, String speed)
		implements ParticleInstance<DustParticleInstance> {

	@Override
	public ParticleOptions particle() {
		return new DustParticleOptions(Vec3.fromRGB24(Integer.parseInt(color, 16)).toVector3f(), scale);
	}

	@Override
	public boolean verify(Logger logger, String path) {
		try {
			Integer.parseInt(color, 16);
		} catch (Exception e) {
			logger.error(path + ": color " + color + " is not a valid color hex string");
			return false;
		}
		return true;
	}

}
