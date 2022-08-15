package dev.xkmc.l2foundation.content.effect.assassin;

import dev.xkmc.l2foundation.init.registrate.LFEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;

public class TargetAttractorEffect extends MobEffect {

	public TargetAttractorEffect(MobEffectCategory type, int color) {
		super(type, color);
	}

	@Override
	public boolean isDurationEffectTick(int tick, int level) {
		return tick % 10 == 0;
	}

	@Override
	public void applyEffectTick(LivingEntity self, int level) {
		if (self.level.isClientSide()) return;
		int radius = 10 << level;
		double ar = radius / 2.0;
		for (Entity e : self.level.getEntities(self, new AABB(self.position(), self.position()).inflate(radius))) {
			if (!(e instanceof Mob mob)) continue;
			if (e.distanceToSqr(self) > radius * radius) continue;
			LivingEntity le = mob.getTarget();
			if (le != null) {
				if (le.hasEffect(LFEffects.T_SINK.get())) continue;
			}
			if (mob.hasEffect(LFEffects.T_SOURCE.get()) || e.distanceToSqr(self) < ar * ar) {
				mob.setTarget(self);
			}
		}

	}

}
