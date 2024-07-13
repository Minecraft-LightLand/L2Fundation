package dev.xkmc.l2complements.init.registrate;

import dev.xkmc.l2complements.content.particle.EmeraldParticle;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.init.reg.simple.SR;
import dev.xkmc.l2core.init.reg.simple.Val;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class LCParticle {

	public static final Val<SimpleParticleType> EMERALD = SR.of(L2Complements.REG, BuiltInRegistries.PARTICLE_TYPE)
			.reg("emerald_splash", () -> new SimpleParticleType(false));

	public static void register() {

	}

	@OnlyIn(Dist.CLIENT)
	public static void registerClient() {
		Minecraft.getInstance().particleEngine.register(EMERALD.get(), new EmeraldParticle.Factory());
	}

}
