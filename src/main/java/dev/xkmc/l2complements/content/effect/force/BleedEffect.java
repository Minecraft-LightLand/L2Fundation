package dev.xkmc.l2complements.content.effect.force;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.base.effects.api.ForceEffect;
import dev.xkmc.l2library.base.effects.api.IconOverlayEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;

public class BleedEffect extends InherentEffect implements ForceEffect, IconOverlayEffect {

	public BleedEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(L2Complements.MODID, "textures/effect_overlay/bleed.png");
	}

}
