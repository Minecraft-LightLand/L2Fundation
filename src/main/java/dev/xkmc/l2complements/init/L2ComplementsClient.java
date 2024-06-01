package dev.xkmc.l2complements.init;

import dev.xkmc.l2complements.content.client.EnchStackDeco;
import dev.xkmc.l2complements.content.client.RangeDiggingOverlay;
import dev.xkmc.l2complements.content.item.misc.LCBEWLR;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2complements.init.registrate.LCParticle;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = L2Complements.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class L2ComplementsClient {

	public enum LCKeys {
		DIG("key.l2mods.dig", "Range Digging Toggle", GLFW.GLFW_KEY_GRAVE_ACCENT);

		public final String id, def;
		public final int key;
		public final KeyMapping map;

		LCKeys(String id, String def, int key) {
			this.id = id;
			this.def = def;
			this.key = key;
			this.map = new KeyMapping(id, key, "key.categories.l2mods");
		}
	}


	@SubscribeEvent
	public static void onParticleRegistryEvent(RegisterParticleProvidersEvent event) {
		LCParticle.registerClient();
	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ItemProperties.register(LCItems.SONIC_SHOOTER.get(), new ResourceLocation(L2Complements.MODID, "shoot"),
					(stack, level, user, index) -> user == null || !user.isUsingItem() ? 0 :
							1 - 1f * user.getUseItemRemainingTicks() / stack.getUseDuration());
		});
	}


	@SubscribeEvent
	public static void registerOverlay(RegisterGuiOverlaysEvent event) {
		event.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(), "range_digging", new RangeDiggingOverlay());
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
