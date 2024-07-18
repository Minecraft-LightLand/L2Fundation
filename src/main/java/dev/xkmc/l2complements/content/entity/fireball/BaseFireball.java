package dev.xkmc.l2complements.content.entity.fireball;

import dev.xkmc.l2complements.content.entity.ISizedItemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BaseFireball<T extends BaseFireball<T>> extends Fireball implements ISizedItemEntity {

	public int lifetime = 200;
	private int life = 0;

	public BaseFireball(EntityType<T> type, Level level) {
		super(type, level);
	}

	public BaseFireball(EntityType<T> type, double x, double y, double z, Vec3 vec, Level level) {
		super(type, x, y, z, vec, level);
	}

	public BaseFireball(EntityType<T> type, LivingEntity owner, Vec3 vec, Level level) {
		super(type, owner, vec, level);
	}

	protected final void onHit(HitResult result) {
		super.onHit(result);
		if (!this.level().isClientSide) {
			onHitAction(result.getLocation());
			discard();
		}
	}

	protected final void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		if (!this.level().isClientSide) {
			onHitEntity(result.getEntity());
		}
	}

	protected final void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		if (!this.level().isClientSide) {
			onHitBlock(result.getBlockPos().relative(result.getDirection()));
		}
	}

	protected void onHitAction(Vec3 pos) {
	}

	protected void onHitEntity(Entity target) {
	}

	protected void onHitBlock(BlockPos pos) {
	}

	@Override
	protected float getInertia() {
		return 1;
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide)
			life++;
		if (life >= lifetime) {
			discard();
		}
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	public final boolean isPickable() {
		return false;
	}

	public final boolean hurt(DamageSource source, float damage) {
		return false;
	}

	@Override
	public float getSize() {
		return 1;
	}

}
