package dev.xkmc.l2complements.content.effect;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2core.base.effects.api.IconOverlayEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class CurseEffect extends BaseEffect implements IconOverlayEffect {

	public CurseEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public DelayedEntityRender getIcon(LivingEntity entity, int lv) {
		return DelayedEntityRender.icon(entity, L2Complements.loc("textures/effect_overlay/curse.png"));
	}

}
