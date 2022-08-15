package dev.xkmc.l2foundation.content.effect.skill;

import dev.xkmc.l2foundation.content.effect.SkillEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class QuickPullEffect extends InherentEffect implements SkillEffect<QuickPullEffect> {

	public QuickPullEffect(MobEffectCategory type, int color) {
		super(type, color);
	}

}
