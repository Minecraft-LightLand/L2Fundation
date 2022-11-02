package dev.xkmc.l2foundation.init;

import dev.xkmc.l2foundation.init.registrate.LFParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class L2FoundationClient {

	public static void onCtorClient(IEventBus bus, IEventBus eventBus) {
		bus.addListener(L2FoundationClient::clientSetup);
		bus.addListener(L2FoundationClient::onParticleRegistryEvent);
	}


	public static void onParticleRegistryEvent(RegisterParticleProvidersEvent event) {
		LFParticle.registerClient();
	}

	public static void clientSetup(FMLClientSetupEvent event) {
		L2FoundationClient.registerItemProperties();
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerItemProperties() {
	}

}
