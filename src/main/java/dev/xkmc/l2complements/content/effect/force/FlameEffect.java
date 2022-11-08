package dev.xkmc.l2complements.content.effect.force;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.base.effects.api.IconOverlayEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class FlameEffect extends InherentEffect implements IconOverlayEffect {

	public FlameEffect(MobEffectCategory type, int color) {
		super(type, color);
	}

	@Override
	public void applyEffectTick(LivingEntity target, int level) {
		DamageSource source = new IndirectEntityDamageSource("soul_flame",
				target, target.getLastHurtByMob()).bypassArmor().setMagic();
		if (target.hasEffect(MobEffects.FIRE_RESISTANCE))
			return;
		if (target.fireImmune()) {
			if (target.isSensitiveToWater()) {
				return;
			}
		} else {
			source.setIsFire();
		}
		target.hurt(source, 2 << level);
	}

	@Override
	public boolean isDurationEffectTick(int tick, int level) {
		return tick % 20 == 0;
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(L2Complements.MODID, "textures/effect_overlay/flame.png");
	}
}
