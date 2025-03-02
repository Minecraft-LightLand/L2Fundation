package dev.xkmc.l2complements.content.entity.fireball;

import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2complements.init.registrate.LCEntities;
import dev.xkmc.l2library.base.effects.EffectUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class BlackFireball extends BaseFireball<BlackFireball> {

	public BlackFireball(EntityType<BlackFireball> type, Level level) {
		super(type, level);
	}

	public BlackFireball(double x, double y, double z, double vx, double vy, double vz, Level level) {
		super(LCEntities.ETFB_BLACK.get(), x, y, z, vx, vy, vz, level);
	}

	public BlackFireball(LivingEntity owner, double vx, double vy, double vz, Level level) {
		super(LCEntities.ETFB_BLACK.get(), owner, vx, vy, vz, level);
	}

	protected void onHitEntity(Entity target) {
		if (target instanceof LivingEntity le) {
			EffectUtil.addEffect(le, new MobEffectInstance(LCEffects.STONE_CAGE.get(), 100), EffectUtil.AddReason.NONE, getOwner());
		}
		target.hurt(level().damageSources().fireball(this, getOwner()), 6.0F);
	}
}
