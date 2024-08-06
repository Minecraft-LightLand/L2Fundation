package dev.xkmc.l2complements.content.effect;

import dev.xkmc.l2complements.init.L2Complements;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ArmorReduceEffect extends ExponentEffect {

	public ArmorReduceEffect(MobEffectCategory category, int color) {
		super(category, color);
		this.addAttributeModifier(Attributes.ARMOR, L2Complements.loc("armor_reduce"),
				-0.5f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}


}
