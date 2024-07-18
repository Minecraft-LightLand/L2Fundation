package dev.xkmc.l2complements.content.effect.force;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.base.effects.api.ForceEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ArmorReduceEffect extends MobEffect implements ForceEffect {

	public ArmorReduceEffect(MobEffectCategory category, int color) {
		super(category, color);
		this.addAttributeModifier(Attributes.ARMOR, L2Complements.loc("armor_reduce"), -0.5f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	@Override
	public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier) {
		return Math.pow(1 + pModifier.getAmount(), pAmplifier + 1) - 1;
	}
}
