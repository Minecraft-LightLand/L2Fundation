package dev.xkmc.l2complements.content.effect.force;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2library.base.effects.api.ForceEffect;
import dev.xkmc.l2library.base.effects.api.IconOverlayEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class StoneCageEffect extends InherentEffect implements ForceEffect, IconOverlayEffect {

	private static final UUID ID_SLOW = MathHelper.getUUIDFromString(L2Complements.MODID + ":stone_cage_slow");
	private static final UUID ID_FLY = MathHelper.getUUIDFromString(L2Complements.MODID + ":stone_cage_fly");
	private static final UUID ID_KB = MathHelper.getUUIDFromString(L2Complements.MODID + ":stone_cage_kb");
	private static final UUID ID_JUMP = MathHelper.getUUIDFromString(L2Complements.MODID + ":stone_cage_jump");

	public StoneCageEffect(MobEffectCategory type, int color) {
		super(type, color);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, ID_SLOW.toString(), -1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
		addAttributeModifier(Attributes.FLYING_SPEED, ID_FLY.toString(), -1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
		addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ID_KB.toString(), 1F, AttributeModifier.Operation.ADDITION);
		addAttributeModifier(ForgeMod.SWIM_SPEED.get(), ID_JUMP.toString(), -1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
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
		return DelayedEntityRender.icon(entity, new ResourceLocation(L2Complements.MODID,
				"textures/effect_overlay/stone_cage.png"));
	}
}
