package dev.xkmc.l2foundation.content.effect.assassin;

import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2foundation.content.effect.SkillEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;

public class TargetHideEffect extends InherentEffect implements SkillEffect<TargetHideEffect> {

	public TargetHideEffect(MobEffectCategory type, int color) {
		super(type, color);
	}

	@Override
	public boolean isDurationEffectTick(int tick, int level) {
		return tick % 10 == 0;
	}

	@Override
	public void applyEffectTick(LivingEntity self, int level) {
		if (self.level.isClientSide()) return;
		int radius = 20 << level;
		for (Entity e : self.level.getEntities(self, new AABB(self.position(), self.position()).inflate(radius))) {
			if (!(e instanceof Mob mob)) continue;
			if (e.distanceToSqr(self) > radius * radius) continue;
			LivingEntity le = mob.getTarget();
			if (le != self) continue;
			mob.setTarget(null);
		}

	}

}
