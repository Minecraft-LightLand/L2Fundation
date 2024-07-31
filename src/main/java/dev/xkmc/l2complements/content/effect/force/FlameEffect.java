package dev.xkmc.l2complements.content.effect.force;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCDamageTypes;
import dev.xkmc.l2core.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2core.base.effects.api.ForceEffect;
import dev.xkmc.l2core.base.effects.api.IconOverlayEffect;
import dev.xkmc.l2core.base.effects.api.InherentEffect;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FlameEffect extends InherentEffect implements ForceEffect, IconOverlayEffect {

	public FlameEffect(MobEffectCategory type, int color) {
		super(type, color);
	}

	@Override
	public boolean applyEffectTick(LivingEntity target, int level) {
		DamageSource source = new DamageSource(LCDamageTypes.forKey(target.level(), LCDamageTypes.SOUL_FLAME));
		target.hurt(source, 2 << level);
		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int tick, int level) {
		return tick % 20 == 0;
	}

	@Override
	public DelayedEntityRender getIcon(LivingEntity entity, int lv) {
		return DelayedEntityRender.icon(entity, L2Complements.loc("textures/effect_overlay/flame.png"));
	}
}
