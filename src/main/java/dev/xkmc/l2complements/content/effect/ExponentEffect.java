package dev.xkmc.l2complements.content.effect;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ExponentEffect extends BaseEffect {

	public static double curve(double amount, int amplifier) {
		return Math.pow(1 + amount, amplifier + 1) - 1;
	}

	protected ExponentEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public MobEffect addAttributeModifier(Holder<Attribute> attr, ResourceLocation id,
										  double val, AttributeModifier.Operation op) {
		return super.addAttributeModifier(attr, id, op, lv -> curve(val, lv));
	}

}
