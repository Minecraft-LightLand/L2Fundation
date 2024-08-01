package dev.xkmc.l2complements.content.effect.skill;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCDamageTypes;
import dev.xkmc.l2core.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2core.base.effects.api.ForceEffect;
import dev.xkmc.l2core.base.effects.api.IconOverlayEffect;
import dev.xkmc.l2core.base.effects.api.InherentEffect;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BleedEffect extends InherentEffect implements ForceEffect, IconOverlayEffect, SkillEffect, StackingEffect<BleedEffect> {

	private static final ResourceLocation ID_SLOW = L2Complements.loc("bleed_slow");
	private static final ResourceLocation ID_ATK = L2Complements.loc("bleed_atk");

	public BleedEffect(MobEffectCategory category, int color) {
		super(category, color);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, ID_SLOW, -0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
		addAttributeModifier(Attributes.ATTACK_DAMAGE, ID_ATK, -0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	}

	public double getAttributeModifierValue(double amount, int amplifier, Holder<Attribute> attribute, AttributeModifier.Operation operation, ResourceLocation id) {
		return Math.pow(1 + amount, amplifier + 1) - 1;
	}

	@Override
	public boolean applyEffectTick(LivingEntity target, int level) {
		DamageSource source = new DamageSource(LCDamageTypes.forKey(target.level(), LCDamageTypes.BLEED));
		target.hurt(source, 6 * (level + 1));
		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return duration % 60 == 0;
	}

	@Override
	public DelayedEntityRender getIcon(LivingEntity entity, int lv) {
		return DelayedEntityRender.icon(entity, L2Complements.loc("textures/effect_overlay/bleed_" + Mth.clamp(lv, 0, 8) + ".png"));
	}

}
