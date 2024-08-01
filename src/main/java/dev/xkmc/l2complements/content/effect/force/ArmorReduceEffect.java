package dev.xkmc.l2complements.content.effect.force;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.base.effects.api.ForceEffect;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ArmorReduceEffect extends MobEffect implements ForceEffect {

	public ArmorReduceEffect(MobEffectCategory category, int color) {
		super(category, color);
		this.addAttributeModifier(Attributes.ARMOR, L2Complements.loc("armor_reduce"), -0.5f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	public double getAttributeModifierValue(double amount, int amplifier, Holder<Attribute> attribute, AttributeModifier.Operation operation, ResourceLocation id)  {
		return Math.pow(1 + amount, amplifier + 1) - 1;
	}

}
