package dev.xkmc.l2complements.init.registrate;

import dev.xkmc.l2complements.content.particle.EmeraldParticle;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class LCParticle {

	public static final RegistryEntry<SimpleParticleType> EMERALD = L2Complements.REGISTRATE.simple("emerald", ForgeRegistries.Keys.PARTICLE_TYPES, () -> new SimpleParticleType(false));

	public static void register() {

	}

	@OnlyIn(Dist.CLIENT)
	public static void registerClient() {
		Minecraft.getInstance().particleEngine.register(EMERALD.get(), new EmeraldParticle.Factory());
	}

}
