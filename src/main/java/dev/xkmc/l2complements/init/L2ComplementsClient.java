package dev.xkmc.l2complements.init;

import dev.xkmc.l2complements.init.registrate.LCParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class L2ComplementsClient {

	public static void onCtorClient(IEventBus bus, IEventBus eventBus) {
		bus.addListener(L2ComplementsClient::clientSetup);
		bus.addListener(L2ComplementsClient::onParticleRegistryEvent);
	}


	public static void onParticleRegistryEvent(RegisterParticleProvidersEvent event) {
		LCParticle.registerClient();
	}

	public static void clientSetup(FMLClientSetupEvent event) {
		L2ComplementsClient.registerItemProperties();
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerItemProperties() {
	}

}
