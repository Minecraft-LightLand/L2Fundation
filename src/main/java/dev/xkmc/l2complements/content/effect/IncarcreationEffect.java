package dev.xkmc.l2complements.content.effect;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2core.base.effects.api.IconOverlayEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;

public class IncarcreationEffect extends BaseEffect implements IconOverlayEffect {

	private static final ResourceLocation ID_SLOW = L2Complements.loc("incarceration_slow");
	private static final ResourceLocation ID_FLY = L2Complements.loc("incarceration_fly");
	private static final ResourceLocation ID_KB = L2Complements.loc("incarceration_kb");
	private static final ResourceLocation ID_JUMP = L2Complements.loc("incarceration_jump");

	private static final ResourceLocation ICON = L2Complements.loc("textures/effect_overlay/stone_cage.png");

	public IncarcreationEffect(MobEffectCategory type, int color) {
		super(type, color);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, ID_SLOW, -1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		addAttributeModifier(Attributes.FLYING_SPEED, ID_FLY, -1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ID_KB, 1F, AttributeModifier.Operation.ADD_VALUE);
		addAttributeModifier(NeoForgeMod.SWIM_SPEED.getDelegate(), ID_JUMP, -1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int p_19468_) {
		entity.setDeltaMovement(0, 0, 0);
		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public DelayedEntityRender getIcon(LivingEntity entity, int lv) {
		return DelayedEntityRender.icon(entity, ICON);
	}
}
