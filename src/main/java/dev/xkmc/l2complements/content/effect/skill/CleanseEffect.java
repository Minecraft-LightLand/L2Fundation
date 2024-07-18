package dev.xkmc.l2complements.content.effect.skill;

import dev.xkmc.l2complements.events.MagicEventHandler;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2core.base.effects.api.ForceEffect;
import dev.xkmc.l2core.base.effects.api.IconOverlayEffect;
import dev.xkmc.l2core.base.effects.api.InherentEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

import java.util.ArrayList;
import java.util.List;

public class CleanseEffect extends InherentEffect implements ForceEffect, IconOverlayEffect, SkillEffect {

	private static int recursive = 0;

	public static void clearOnEntity(LivingEntity entity) {
		recursive++;
		List<MobEffectInstance> list = new ArrayList<>(entity.getActiveEffects());
		for (MobEffectInstance ins : list) {
			if (MagicEventHandler.isSkill(ins, entity)) continue;
			if (recursive <= 1)
				entity.removeEffect(ins.getEffect());
			if (entity.hasEffect(ins.getEffect())) {
				entity.getActiveEffectsMap().remove(ins.getEffect());
			}
		}
		recursive--;
	}

	public CleanseEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public DelayedEntityRender getIcon(LivingEntity entity, int lv) {
		return DelayedEntityRender.icon(entity, L2Complements.loc("textures/effect_overlay/cleanse.png"));
	}

	@Override
	public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
		clearOnEntity(livingEntity);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int tick, int amp) {
		return tick % 10 == 0;
	}

	@Override
	public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
		clearOnEntity(pLivingEntity);
		return true;
	}

}
