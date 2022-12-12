package dev.xkmc.l2complements.content.item.misc;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public interface ILCTotem {

	default void trigger(LivingEntity self, ItemStack holded, Consumer<ItemStack> second) {
		holded.shrink(1);
		self.setHealth(1.0F);
		self.removeAllEffects();
		self.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
		self.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
		self.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
		self.level.broadcastEntityEvent(self, (byte) 35);
	}

	default boolean allow(LivingEntity self, DamageSource pDamageSource) {
		return !pDamageSource.isBypassInvul();
	}

}
