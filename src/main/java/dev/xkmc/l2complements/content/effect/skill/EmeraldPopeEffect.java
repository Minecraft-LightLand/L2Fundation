package dev.xkmc.l2complements.content.effect.skill;

import dev.xkmc.l2complements.init.data.DamageTypeGen;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCParticle;
import dev.xkmc.l2core.base.effects.api.ClientRenderEffect;
import dev.xkmc.l2core.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2core.base.effects.api.FirstPlayerRenderEffect;
import dev.xkmc.l2core.util.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Consumer;

public class EmeraldPopeEffect extends MobEffect implements FirstPlayerRenderEffect, ClientRenderEffect {

	public EmeraldPopeEffect(MobEffectCategory type, int color) {
		super(type, color);
	}

	public boolean applyEffectTick(LivingEntity self, int level) {
		if (self.level().isClientSide())
			return true;
		int radius = (level + 1) * LCConfig.COMMON.emeraldBaseRange.get();
		var atk = self.getAttribute(Attributes.ATTACK_DAMAGE);
		int damage = (int) (LCConfig.COMMON.emeraldDamageFactor.get() * (atk == null ? 1 : atk.getValue()));
		DamageSource source = new DamageSource(DamageTypeGen.forKey(self.level(), DamageTypeGen.EMERALD), null, self);
		for (Entity e : self.level().getEntities(self, new AABB(self.blockPosition()).inflate(radius))) {
			if (e instanceof Enemy && !e.isAlliedTo(self) && ((LivingEntity) e).hurtTime == 0 &&
					e.position().distanceToSqr(self.position()) < radius * radius) {
				double dist = e.position().distanceTo(self.position());
				if (dist > 0.1) {
					((LivingEntity) e).knockback(0.4F, e.position().x - self.position().x, e.position().z - self.position().z);
				}
				e.hurt(source, damage);
			}
		}
		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return duration % 10 == 0;
	}

	@Override
	public void render(LivingEntity entity, int lv, Consumer<DelayedEntityRender> consumer) {
		if (entity == Proxy.getClientPlayer()) return;
		renderEffect(lv, entity);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void onClientLevelRender(AbstractClientPlayer player, MobEffectInstance ins) {
		renderEffect(ins.getAmplifier(), player);
	}

	private static void renderEffect(int lv, Entity entity) {
		if (Minecraft.getInstance().isPaused()) return;
		int r = (lv + 1) * LCConfig.COMMON.emeraldBaseRange.get();
		int count = (1 + lv) * (1 + lv) * 4;
		for (int i = 0; i < count; i++) {
			addParticle(entity.level(), entity.position(), r);
		}
	}

	private static void addParticle(Level w, Vec3 vec, int r) {
		float tpi = (float) (Math.PI * 2);
		Vec3 v0 = new Vec3(0, r, 0);
		Vec3 v1 = v0.xRot(tpi / 3).yRot((float) (Math.random() * tpi));
		float a0 = (float) (Math.random() * tpi);
		float b0 = (float) Math.acos(2 * Math.random() - 1);
		v0 = v0.xRot(a0).yRot(b0);
		v1 = v1.xRot(a0).yRot(b0);
		w.addAlwaysVisibleParticle(LCParticle.EMERALD.get(),
				vec.x + v0.x,
				vec.y + v0.y,
				vec.z + v0.z,
				vec.x + v1.x,
				vec.y + v1.y,
				vec.z + v1.z);
	}

}
