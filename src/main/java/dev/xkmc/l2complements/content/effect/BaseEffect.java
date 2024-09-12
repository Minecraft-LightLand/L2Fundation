package dev.xkmc.l2complements.content.effect;

import dev.xkmc.l2core.base.effects.api.ForceEffect;
import dev.xkmc.l2core.base.effects.api.InherentEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BaseEffect extends InherentEffect implements ForceEffect {

	protected BaseEffect(MobEffectCategory category, int color) {
		super(category, color);
	}
}
