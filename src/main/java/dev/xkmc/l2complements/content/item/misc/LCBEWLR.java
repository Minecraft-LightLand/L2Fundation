package dev.xkmc.l2complements.content.item.misc;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.WitherArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Supplier;

public class LCBEWLR extends BlockEntityWithoutLevelRenderer {

	public static final Supplier<BlockEntityWithoutLevelRenderer> INSTANCE = Suppliers.memoize(() ->
			new LCBEWLR(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

	public static final IClientItemExtensions EXTENSIONS = new IClientItemExtensions() {

		@Override
		public BlockEntityWithoutLevelRenderer getCustomRenderer() {
			return INSTANCE.get();
		}

	};

	private static final ResourceLocation WITHER_ARMOR = new ResourceLocation("textures/entity/wither/wither_armor.png");
	private static final ResourceLocation WITHER_LOCATION = new ResourceLocation("textures/entity/wither/wither.png");

	private final EntityModelSet entityModelSet;
	private WitherBossModel<WitherBoss> wither_model;
	private WitherArmorLayer wither_armor;
	private WitherBoss wither;

	public LCBEWLR(BlockEntityRenderDispatcher dispatcher, EntityModelSet set) {
		super(dispatcher, set);
		entityModelSet = set;
	}

	public void onResourceManagerReload(ResourceManager manager) {
		wither = null;
		wither_model = new WitherBossModel<>(entityModelSet.bakeLayer(ModelLayers.WITHER_ARMOR));
		wither_armor = new WitherArmorLayer(new RenderLayerParent<>() {
			@Override
			public WitherBossModel<WitherBoss> getModel() {
				return LCBEWLR.this.wither_model;
			}

			@Override
			public ResourceLocation getTextureLocation(WitherBoss boss) {
				return WITHER_LOCATION;
			}
		}, entityModelSet);
	}

	@Override
	public void renderByItem(ItemStack stack, ItemTransforms.TransformType type, PoseStack poseStack,
							 MultiBufferSource bufferSource, int light, int overlay) {
		if (stack.is(LCItems.FORCE_FIELD.get())) {
			renderWither(type, poseStack, bufferSource, light, overlay);
		}
	}

	private void renderWither(ItemTransforms.TransformType type, PoseStack poseStack,
							  MultiBufferSource bufferSource, int light, int overlay) {
		setUpWither();
		poseStack.pushPose();
		translateWither(poseStack, type);
		wither_armor.render(poseStack, bufferSource, light, wither,
				0, 0,
				Minecraft.getInstance().getPartialTick(), wither.tickCount,
				0, 0);
		poseStack.popPose();
	}

	private void setUpWither() {
		Level level = Minecraft.getInstance().level;
		if (level == null) {
			wither = null;
			return;
		}
		if (wither != null) {
			if (wither.level != level)
				wither = null;
		}
		if (wither == null) {
			wither = EntityType.WITHER.create(level);
		}
		if (wither == null) return;
		wither.tickCount = Proxy.getClientPlayer().tickCount;
		wither.setHealth(1);
	}

	private void translateWither(PoseStack stack, ItemTransforms.TransformType transform) {
		switch (transform) {
			case GUI:
			case FIRST_PERSON_LEFT_HAND:
			case FIRST_PERSON_RIGHT_HAND:
				break;
			case THIRD_PERSON_LEFT_HAND:
			case THIRD_PERSON_RIGHT_HAND: {
				stack.translate(0.25, 0.4, 0.5);
				float size = 0.625f;
				stack.scale(size, size, size);
				break;
			}
			case GROUND: {
				stack.translate(0.25, 0, 0.5);
				float size = 0.625f;
				stack.scale(size, size, size);
				break;
			}
			case NONE:
			case HEAD:
			case FIXED: {
				stack.translate(0.5, 0.5, 0.5);
				float size = 0.6f;
				stack.scale(size, -size, size);
				stack.translate(0, -0.45, 0);
				return;
			}
		}
		stack.mulPose(Vector3f.ZP.rotationDegrees(135));
		stack.mulPose(Vector3f.YP.rotationDegrees(-155));
		float size = 0.6f;
		stack.scale(size, size, size);
		stack.translate(0, -1.6, 0);
	}

}
