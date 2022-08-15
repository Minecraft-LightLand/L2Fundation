package dev.xkmc.l2foundation.init.registrate;

import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2foundation.content.particle.EmeraldParticle;
import dev.xkmc.l2foundation.init.L2Foundation;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class LFParticle {

	public static final RegistryEntry<SimpleParticleType> EMERALD = L2Foundation.REGISTRATE.simple("emerald", ForgeRegistries.Keys.PARTICLE_TYPES, () -> new SimpleParticleType(false));

	public static void register() {

	}

	@OnlyIn(Dist.CLIENT)
	public static void registerClient() {
		Minecraft.getInstance().particleEngine.register(EMERALD.get(), new EmeraldParticle.Factory());
	}

}
