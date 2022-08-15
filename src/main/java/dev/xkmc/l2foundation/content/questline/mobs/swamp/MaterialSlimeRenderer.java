package dev.xkmc.l2foundation.content.questline.mobs.swamp;

import dev.xkmc.l2foundation.init.L2Foundation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;
import net.minecraftforge.registries.ForgeRegistries;

public class MaterialSlimeRenderer extends SlimeRenderer {

	public MaterialSlimeRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(Slime slime) {
		if (slime instanceof MaterialSlime<?>) {
			ResourceLocation rl = ForgeRegistries.ENTITY_TYPES.getKey(slime.getType());
			assert rl != null;
			return new ResourceLocation(L2Foundation.MODID, "textures/entity/slime/" + rl.getPath() + ".png");
		}
		return super.getTextureLocation(slime);
	}
}
