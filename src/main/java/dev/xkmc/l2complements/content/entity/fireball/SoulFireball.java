package dev.xkmc.l2complements.content.entity.fireball;

import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2complements.init.registrate.LCEntities;
import dev.xkmc.l2library.base.effects.EffectUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class SoulFireball extends BaseFireball<SoulFireball> {

	public SoulFireball(EntityType<SoulFireball> type, Level level) {
		super(type, level);
	}

	public SoulFireball(double x, double y, double z, double vx, double vy, double vz, Level level) {
		super(LCEntities.ETFB_SOUL.get(), x, y, z, vx, vy, vz, level);
	}

	public SoulFireball(LivingEntity owner, double vx, double vy, double vz, Level level) {
		super(LCEntities.ETFB_SOUL.get(), owner, vx, vy, vz, level);
	}

	protected void onHitEntity(Entity target) {
		target.hurt(level.damageSources().fireball(this, getOwner()), 6.0F);
		if (target instanceof LivingEntity le) {
			EffectUtil.addEffect(le, new MobEffectInstance(LCEffects.FLAME.get(), 60), EffectUtil.AddReason.NONE, getOwner());
		}
	}

}
