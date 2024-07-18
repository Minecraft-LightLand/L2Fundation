package dev.xkmc.l2complements.init.registrate;

import dev.xkmc.l2complements.content.particle.EmeraldParticle;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import dev.xkmc.l2core.init.reg.simple.Val;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class LCParticle {

	public static final Val<SimpleParticleType> EMERALD = L2Complements.REGISTRATE
			.particle("emerald_splash", () -> new SimpleParticleType(false),
					() -> L2Registrate.ParticleSupplier.spriteSet(new EmeraldParticle.Factory()));

	public static void register() {

	}

}
