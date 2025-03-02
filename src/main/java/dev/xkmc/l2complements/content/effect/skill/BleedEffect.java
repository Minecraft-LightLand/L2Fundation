package dev.xkmc.l2complements.content.effect.skill;

import dev.xkmc.l2complements.content.effect.force.NoSelfRenderEffect;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.DamageTypeGen;
import dev.xkmc.l2library.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2library.base.effects.api.ForceEffect;
import dev.xkmc.l2library.base.effects.api.IconOverlayEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class BleedEffect extends InherentEffect implements ForceEffect, IconOverlayEffect, SkillEffect, StackingEffect<BleedEffect> {

	private static final UUID ID_SLOW = MathHelper.getUUIDFromString(L2Complements.MODID + ":bleed_slow");
	private static final UUID ID_ATK = MathHelper.getUUIDFromString(L2Complements.MODID + ":bleed_atk");

	public BleedEffect(MobEffectCategory category, int color) {
		super(category, color);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, ID_SLOW.toString(), -0.1F, AttributeModifier.Operation.MULTIPLY_BASE);
		addAttributeModifier(Attributes.ATTACK_DAMAGE, ID_ATK.toString(), -0.1F, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public double getAttributeModifierValue(int amp, AttributeModifier mod) {
		return Math.pow(1 + mod.getAmount(), amp + 1) - 1;
	}

	@Override
	public void applyEffectTick(LivingEntity target, int level) {
		DamageSource source = new DamageSource(DamageTypeGen.forKey(target.level(), DamageTypeGen.BLEED));
		target.hurt(source, 6 * (level + 1));
	}

	@Override
	public boolean isDurationEffectTick(int tick, int level) {
		return tick % 60 == 0;
	}

	@Override
	public DelayedEntityRender getIcon(LivingEntity entity, int lv) {
		return DelayedEntityRender.icon(entity, new ResourceLocation(L2Complements.MODID,
				"textures/effect_overlay/bleed_" + Mth.clamp(lv, 0, 8) + ".png"));
	}

}
