package dev.xkmc.l2complements.content.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.xkmc.l2complements.content.enchantment.digging.DiggerHelper;
import dev.xkmc.l2complements.content.enchantment.digging.RangeDiggingEnchantment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

import java.util.OptionalDouble;

public class RangeDiggingOutliner {

	private record CacheKey(RangeDiggingEnchantment ench, int lv, BlockPos pos, Direction dir, ItemStack stack) {

	}

	public static class DiggingRenderType extends RenderType {

		public DiggingRenderType(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
			super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
		}

		public static RenderType OUTLINE = create("highlight_lines",
				DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.LINES, 256, false, false,
				CompositeState.builder()
						.setShaderState(RENDERTYPE_LINES_SHADER)
						.setLineState(new LineStateShard(OptionalDouble.empty()))
						.setLayeringState(VIEW_OFFSET_Z_LAYERING)
						.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
						.setOutputState(ITEM_ENTITY_TARGET)
						.setWriteMaskState(COLOR_DEPTH_WRITE)
						.setCullState(NO_CULL)
						.setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
						.createCompositeState(false)
		);


	}

	private static CacheKey KEY = null;
	private static ClusterBitSet CACHE = null;
	private static int tick = 0;

	public static void renderMoreOutlines(Player player, BlockPos pos, MultiBufferSource.BufferSource buffer, PoseStack pose, float x, float y, float z, boolean outline) {
		ItemStack stack = player.getMainHandItem();
		var dir = Minecraft.getInstance().hitResult instanceof BlockHitResult bhit ? bhit.getDirection() : Direction.DOWN;
		var e = DiggerHelper.getDigger(stack);
		if (e == null) {
			KEY = null;
			CACHE = null;
			tick = 0;
			return;
		}
		if (player.tickCount >= tick) {
			CACHE = null;
			tick = player.tickCount + 10;
		}
		if (KEY == null || CACHE == null || e.digger() != KEY.ench() || e.level() != KEY.lv() ||
				!pos.equals(KEY.pos) || dir != KEY.dir() || stack != KEY.stack()) {
			KEY = new CacheKey(e.digger(), e.level(), pos, dir, stack);
			CACHE = ClusterBitSet.of(pos, e.digger().getTargets(player, pos, stack, e.level()));
		}
		if (outline) {
			RenderType type = DiggingRenderType.OUTLINE;
			var v = buffer.getBuffer(type);
			CACHE.render(false, (x0, y0, z0, x1, y1, z1) -> renderShape(pose, v, x0, y0, z0, x1, y1, z1, -x, -y, -z, 0.4f, 0.4f, 0.4f, 1));
			CACHE.render(true, (x0, y0, z0, x1, y1, z1) -> renderShape(pose, v, x0, y0, z0, x1, y1, z1, -x, -y, -z, 0.7f, 0.7f, 0.7f, 1));
		} else {
			RenderType type = RenderType.lines();
			var v = buffer.getBuffer(type);
			CACHE.render(true, (x0, y0, z0, x1, y1, z1) -> renderShape(pose, v, x0, y0, z0, x1, y1, z1, -x, -y, -z, 1, 1, 1, 1));

		}
	}

	public static void renderShape(
			PoseStack pose, VertexConsumer vc,
			float x0, float y0, float z0, float x1, float y1, float z1,
			float dx, float dy, float dz,
			float r, float g, float b, float a) {
		PoseStack.Pose mat = pose.last();
		float rx = x1 - x0;
		float ry = y1 - y0;
		float rz = z1 - z0;
		vc.addVertex(mat, x0 + dx, y0 + dy, z0 + dz).setColor(r, g, b, a).setNormal(mat, rx, ry, rz);
		vc.addVertex(mat, x1 + dx, y1 + dy, z1 + dz).setColor(r, g, b, a).setNormal(mat, rx, ry, rz);
	}


}
