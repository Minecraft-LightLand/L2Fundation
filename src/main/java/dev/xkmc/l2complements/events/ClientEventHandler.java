package dev.xkmc.l2complements.events;


import dev.xkmc.l2complements.content.client.RangeDiggingOutliner;
import dev.xkmc.l2complements.content.feature.EntityFeature;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCKeys;
import dev.xkmc.l2complements.network.RotateDiggerToServer;
import dev.xkmc.l2core.util.Proxy;
import dev.xkmc.l2itemselector.events.GenericKeyEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

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
		var vec = cam.getPosition().toVector3f();
		var buffer = Minecraft.getInstance().renderBuffers().bufferSource();
		RangeDiggingOutliner.renderMoreOutlines(player, pos, buffer, event.getPoseStack(), vec.x, vec.y, vec.z, outline);
	}

}
