package dev.xkmc.l2foundation.content.effect.force;

import dev.xkmc.l2foundation.init.L2Foundation;
import dev.xkmc.l2library.base.effects.api.ForceEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class StoneCageEffect extends InherentEffect implements ForceEffect {

	private static final UUID ID_SLOW = MathHelper.getUUIDFromString(L2Foundation.MODID + ":stone_cage_slow");
	private static final UUID ID_FLY = MathHelper.getUUIDFromString(L2Foundation.MODID + ":stone_cage_fly");
	private static final UUID ID_KB = MathHelper.getUUIDFromString(L2Foundation.MODID + ":stone_cage_kb");

	public StoneCageEffect(MobEffectCategory type, int color) {
		super(type, color);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, ID_SLOW.toString(), -1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
		addAttributeModifier(Attributes.FLYING_SPEED, ID_FLY.toString(), -1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
		addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ID_KB.toString(), 1F, AttributeModifier.Operation.ADDITION);
	}

}
