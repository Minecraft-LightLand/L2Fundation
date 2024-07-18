package dev.xkmc.l2complements.events;


import dev.xkmc.l2complements.content.feature.EntityFeature;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCKeys;
import dev.xkmc.l2complements.network.RotateDiggerToServer;
import dev.xkmc.l2core.util.Proxy;
import dev.xkmc.l2itemselector.events.GenericKeyEvent;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

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


	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void modifyItemTooltip(ItemTooltipEvent event) {
		/* TODO enchantment description
		var list = event.getToolTip();
		int n = list.size();
		if (!event.getItemStack().isEnchanted() && !event.getItemStack().is(Items.ENCHANTED_BOOK)) return;
		var map = EnchantmentHelper.getEnchantments(event.getItemStack());
		String prefix = "enchantment.l2complements.";
		String suffix = ".desc";
		boolean alt = Screen.hasAltDown();
		boolean flag = false;
		boolean book = event.getItemStack().is(Items.ENCHANTED_BOOK);
		List<Either<Component, List<Component>>> compound = new ArrayList<>();
		for (var e : list) {
			compound.add(Either.left(e));
		}
		for (int i = 0; i < n; i++) {
			Component comp = list.get(i);
			Component lit;
			if (comp.getContents() instanceof PlainTextContents.LiteralContents txt && comp.getSiblings().size() == 1) {
				comp = comp.getSiblings().get(0);
				lit = Component.literal(txt.text());
			} else lit = Component.empty();
			if (comp.getContents() instanceof TranslatableContents tr) {
				if (tr.getKey().startsWith(prefix) &&
						tr.getKey().endsWith(suffix)) {
					String id = tr.getKey().substring("enchantment.l2complements.".length(),
							tr.getKey().length() - suffix.length());
					Enchantment ench = BuiltinR.ENCHANTMENTS.getValue(new ResourceLocation(L2Complements.MODID, id));
					if (ench instanceof CustomDescEnchantment base) {
						int lv = map.getOrDefault(ench, 0);
						var es = base.descFull(lv, tr.getKey(), alt, book);
						compound.set(i, Either.right(es.stream().map(e -> (Component) lit.copy().append(e)).toList()));
						flag = true;
					}
				}
			}
		}
		if (flag) {
			list.clear();
			list.addAll(compound.stream().flatMap(e -> e.map(Stream::of, Collection::stream)).toList());
		}

		 */
	}

}
