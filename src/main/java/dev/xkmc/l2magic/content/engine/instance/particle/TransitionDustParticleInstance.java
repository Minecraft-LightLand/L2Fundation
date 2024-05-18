package dev.xkmc.l2magic.content.engine.instance.particle;

import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.Logger;

public record TransitionDustParticleInstance(String start, String end, float scale, String speed)
		implements ParticleInstance<TransitionDustParticleInstance> {

	@Override
	public ParticleOptions particle() {
		return new DustColorTransitionOptions(
				Vec3.fromRGB24(Integer.parseInt(start, 16)).toVector3f(),
				Vec3.fromRGB24(Integer.parseInt(end, 16)).toVector3f(),
				scale);
	}

	@Override
	public boolean verify(Logger logger, String path) {
		boolean ans = true;
		try {
			Integer.parseInt(start, 16);
		} catch (Exception e) {
			ans = false;
			logger.error(path + "start: color " + start + " is not a valid color hex string");
		}
		try {
			Integer.parseInt(end, 16);
		} catch (Exception e) {
			ans = false;
			logger.error(path + "end: color " + end + " is not a valid color hex string");
		}
		return ans;
	}

}
