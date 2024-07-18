package dev.xkmc.l2complements.content.entity.fireball;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class StrongFireball extends BaseFireball<StrongFireball> {

	public StrongFireball(EntityType<StrongFireball> type, Level level) {
		super(type, level);
	}

	public StrongFireball(double x, double y, double z, Vec3 vec, Level level) {
		super(LCEntities.ETFB_STRONG.get(), x, y, z, vec, level);
	}

	public StrongFireball(LivingEntity owner, Vec3 vec, Level level) {
		super(LCEntities.ETFB_STRONG.get(), owner, vec, level);
	}

	protected void onHitAction(Vec3 pos) {
		int val = LCConfig.COMMON.strongFireChargePower.get();
		boolean breaking = LCConfig.COMMON.strongFireChargeBreakBlock.get();
		this.level().explode(this, pos.x, pos.y, pos.z, val,
				breaking ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE);
	}

}
