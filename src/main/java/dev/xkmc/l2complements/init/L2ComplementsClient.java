package dev.xkmc.l2complements.init;

import dev.xkmc.l2complements.content.client.EnchStackDeco;
import dev.xkmc.l2complements.content.client.RangeDiggingOverlay;
import dev.xkmc.l2complements.content.item.misc.LCBEWLR;
import dev.xkmc.l2complements.init.data.LCKeys;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2complements.init.registrate.LCParticle;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(value = Dist.CLIENT, modid = L2Complements.MODID, bus = EventBusSubscriber.Bus.MOD)
public class L2ComplementsClient {

	@SubscribeEvent
	public static void onParticleRegistryEvent(RegisterParticleProvidersEvent event) {
		LCParticle.registerClient();
	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ItemProperties.register(LCItems.SONIC_SHOOTER.get(), L2Complements.loc("shoot"),
					(stack, level, user, index) -> user == null || !user.isUsingItem() ? 0 :
							1 - 1f * user.getUseItemRemainingTicks() / stack.getUseDuration(user));
		});
	}


	@SubscribeEvent
	public static void registerOverlay(RegisterGuiLayersEvent event) {
		event.registerAbove(VanillaGuiLayers.CROSSHAIR, L2Complements.loc("range_digging"), new RangeDiggingOverlay());
	}

	@SubscribeEvent
	public static void registerItemDecoration(RegisterItemDecorationsEvent event) {
		event.register(Items.ENCHANTED_BOOK, new EnchStackDeco());
	}


	@SubscribeEvent
	public static void onResourceReload(RegisterClientReloadListenersEvent event) {
		event.registerReloadListener(LCBEWLR.INSTANCE.get());
	}


	@SubscribeEvent
	public static void registerKeyMaps(RegisterKeyMappingsEvent event) {
		for (var e : LCKeys.values()) {
			event.register(e.map);
		}
	}

}
