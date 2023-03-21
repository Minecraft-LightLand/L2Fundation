package dev.xkmc.l2complements.content.effect.skill;

import dev.xkmc.l2complements.content.effect.skill.BleedEffect;
import dev.xkmc.l2complements.content.effect.skill.SkillEffect;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.base.effects.api.ForceEffect;
import dev.xkmc.l2library.base.effects.api.IconOverlayEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class CleanseEffect extends InherentEffect implements ForceEffect, IconOverlayEffect, SkillEffect {

	public CleanseEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(L2Complements.MODID, "textures/effect_overlay/cleanse.png");
	}

	@Override
	public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {

	}
}
