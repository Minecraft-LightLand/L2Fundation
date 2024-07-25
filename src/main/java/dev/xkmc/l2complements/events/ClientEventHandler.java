package dev.xkmc.l2complements.events;


import dev.xkmc.l2complements.content.feature.EntityFeature;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCKeys;
import dev.xkmc.l2complements.network.RotateDiggerToServer;
import dev.xkmc.l2core.util.Proxy;
import dev.xkmc.l2itemselector.events.GenericKeyEvent;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = L2Complements.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ClientEventHandler {

	@SubscribeEvent
	public static void onScreenEffect(RenderBlockScreenEffectEvent event) {
		if (event.getOverlayType() == RenderBlockScreenEffectEvent.OverlayType.FIRE) {
			if (EntityFeature.FIRE_REJECT.test(event.getPlayer())) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onInput(GenericKeyEvent event) {
		if (event.test(LCKeys.DIG.map.getKey()) && event.getAction() == 1) {
			if (Proxy.getClientPlayer() != null && Proxy.getClientPlayer().getMainHandItem().isEnchanted())
				L2Complements.HANDLER.toServer(new RotateDiggerToServer(Screen.hasShiftDown()));
		}
	}

}
