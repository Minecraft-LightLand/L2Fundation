package dev.xkmc.l2foundation.content.effect.skill;

import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2foundation.content.effect.SkillEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class NoKnockBackEffect extends InherentEffect implements SkillEffect<NoKnockBackEffect> {

	public NoKnockBackEffect(MobEffectCategory type, int color) {
		super(type, color);
	}
}
