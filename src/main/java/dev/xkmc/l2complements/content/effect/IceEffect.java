package dev.xkmc.l2complements.content.effect;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2core.base.effects.api.IconOverlayEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class IceEffect extends BaseEffect implements IconOverlayEffect {

	public IceEffect(MobEffectCategory type, int color) {
		super(type, color);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, L2Complements.loc("freezing"), -0.6F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@Override
	public boolean applyEffectTick(LivingEntity target, int level) {
		target.setIsInPowderSnow(true);
		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int tick, int level) {
		return true;
	}

	@Override
	public DelayedEntityRender getIcon(LivingEntity entity, int lv) {
		return DelayedEntityRender.icon(entity, L2Complements.loc("textures/effect_overlay/ice.png"));
	}
}
