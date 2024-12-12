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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
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
				RenderType.CompositeState.builder()
						.setShaderState(RENDERTYPE_LINES_SHADER)
						.setLineState(new RenderStateShard.LineStateShard(OptionalDouble.empty()))
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
	private static List<BlockPos> CACHE = null;
	private static int tick = 0;

	public static void renderMoreOutlines(Player player, BlockPos pos, MultiBufferSource.BufferSource buffer, PoseStack pose, double x, double y, double z, boolean outline) {
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
		if (KEY == null || CACHE == null || e.getFirst() != KEY.ench() || e.getSecond() != KEY.lv() ||
				!pos.equals(KEY.pos) || dir != KEY.dir() || stack != KEY.stack()) {
			KEY = new CacheKey(e.getFirst(), e.getSecond(), pos, dir, stack);
			CACHE = e.getFirst().getTargets(player, pos, stack, e.getSecond());
		}
		RenderType type;
		float a;
		if (outline) {
			type = DiggingRenderType.OUTLINE;
			a = 0.4f;
		} else {
			type = RenderType.lines();
			a = 1f;
		}
		var v = buffer.getBuffer(type);
		render(pose, v, x, y, z, pos, a);
		for (var p : CACHE) {
			render(pose, v, x, y, z, p, a);
		}
	}


	private static void render(PoseStack pose, VertexConsumer vc, double x, double y, double z, BlockPos pos, float a) {
		renderShape(pose, vc, Shapes.block(), (double) pos.getX() - x, (double) pos.getY() - y, (double) pos.getZ() - z, a, a, a, 1);
	}

	public static void renderShape(PoseStack pose, VertexConsumer vc, VoxelShape shape, double dx, double dy, double dz, float r, float g, float b, float a) {
		PoseStack.Pose mat = pose.last();
		shape.forAllEdges((x0, y0, z0, x1, y1, z1) -> {
			float rx = (float) (x1 - x0);
			float ry = (float) (y1 - y0);
			float rz = (float) (z1 - z0);
			float len = Mth.sqrt(rx * rx + ry * ry + rz * rz);
			rx /= len;
			ry /= len;
			rz /= len;
			vc.vertex(mat.pose(), (float) (x0 + dx), (float) (y0 + dy), (float) (z0 + dz)).color(r, g, b, a).normal(mat.normal(), rx, ry, rz).endVertex();
			vc.vertex(mat.pose(), (float) (x1 + dx), (float) (y1 + dy), (float) (z1 + dz)).color(r, g, b, a).normal(mat.normal(), rx, ry, rz).endVertex();
		});
	}


}
