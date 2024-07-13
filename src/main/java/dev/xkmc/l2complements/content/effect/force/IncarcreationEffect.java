package dev.xkmc.l2complements.content.effect.force;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2core.base.effects.api.ForceEffect;
import dev.xkmc.l2core.base.effects.api.IconOverlayEffect;
import dev.xkmc.l2core.base.effects.api.InherentEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class IncarcreationEffect extends InherentEffect implements ForceEffect, IconOverlayEffect {

	private static final ResourceLocation ID_SLOW = L2Complements.loc("incarceration_slow");
	private static final ResourceLocation ID_FLY = L2Complements.loc("incarceration_fly");
	private static final ResourceLocation ID_KB = L2Complements.loc("incarceration_kb");
	private static final ResourceLocation ID_JUMP = L2Complements.loc("incarceration_jump");

	private static final ResourceLocation ICON = L2Complements.loc("textures/effect_overlay/stone_cage.png");

	public IncarcreationEffect(MobEffectCategory type, int color) {
		super(type, color);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, ID_SLOW.toString(), -1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		addAttributeModifier(Attributes.FLYING_SPEED, ID_FLY.toString(), -1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ID_KB.toString(), 1F, AttributeModifier.Operation.ADD_VALUE);
		addAttributeModifier(ForgeMod.SWIM_SPEED.get(), ID_JUMP.toString(), -1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int p_19468_) {
		entity.setDeltaMovement(0, 0, 0);
	}

	@Override
	public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
		return true;
	}

	@Override
	public DelayedEntityRender getIcon(LivingEntity entity, int lv) {
		return DelayedEntityRender.icon(entity, ICON);
	}
}
