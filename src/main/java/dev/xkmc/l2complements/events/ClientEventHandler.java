package dev.xkmc.l2complements.events;


import com.mojang.datafixers.util.Either;
import dev.xkmc.l2complements.content.client.RangeDiggingOutliner;
import dev.xkmc.l2complements.content.enchantment.core.CustomDescEnchantment;
import dev.xkmc.l2complements.content.feature.EntityFeature;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCKeys;
import dev.xkmc.l2complements.network.RotateDiggerToServer;
import dev.xkmc.l2itemselector.events.GenericKeyEvent;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = L2Complements.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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
			if (comp.getContents() instanceof LiteralContents txt && comp.getSiblings().size() == 1) {
				comp = comp.getSiblings().get(0);
				lit = Component.literal(txt.text());
			} else lit = Component.empty();
			if (comp.getContents() instanceof TranslatableContents tr) {
				if (tr.getKey().startsWith(prefix) &&
						tr.getKey().endsWith(suffix)) {
					String id = tr.getKey().substring("enchantment.l2complements.".length(),
							tr.getKey().length() - suffix.length());
					Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(L2Complements.MODID, id));
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
	}

	@SubscribeEvent
	public static void renderLevel(RenderLevelStageEvent event) {
		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES)
			renderOutline(event, true);
		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES)
			renderOutline(event, false);
	}

	private static void renderOutline(RenderLevelStageEvent event, boolean outline) {
		var level = Minecraft.getInstance().level;
		if (level == null) return;
		var cam = event.getCamera();
		if (!(cam.getEntity() instanceof Player player)) return;
		if (!Minecraft.getInstance().gameRenderer.shouldRenderBlockOutline()) return;
		if (!(Minecraft.getInstance().hitResult instanceof BlockHitResult bhit)) return;
		BlockPos pos = bhit.getBlockPos();
		BlockState state = level.getBlockState(pos);
		if (state.isAir() || !level.getWorldBorder().isWithinBounds(pos)) return;
		var vec = cam.getPosition();
		double x = vec.x;
		double y = vec.y;
		double z = vec.z;
		RangeDiggingOutliner.renderMoreOutlines(player, pos, Minecraft.getInstance().renderBuffers().bufferSource(), event.getPoseStack(), x, y, z, outline);
	}

}
