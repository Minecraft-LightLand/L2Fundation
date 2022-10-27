package dev.xkmc.l2foundation.content.effect.skill;

import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2library.util.code.Wrappers;

public interface SkillEffect<T extends InherentEffect> {

	default T getThis() {
		return Wrappers.cast(this);
	}

}
