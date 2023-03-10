package dev.xkmc.l2complements.content.effect.force;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.base.effects.api.ForceEffect;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class ArmorReduceEffect extends MobEffect implements ForceEffect {

	public static final UUID ID = MathHelper.getUUIDFromString(L2Complements.MODID + ":armor_reduce");

	public ArmorReduceEffect(MobEffectCategory category, int color) {
		super(category, color);
		this.addAttributeModifier(Attributes.ARMOR, ID.toString(), -0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier) {
		return Math.pow(1 + pModifier.getAmount(), pAmplifier + 1) - 1;
	}
}
