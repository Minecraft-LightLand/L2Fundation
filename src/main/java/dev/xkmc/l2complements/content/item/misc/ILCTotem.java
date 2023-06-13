package dev.xkmc.l2complements.content.item.misc;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.network.TotemUseToClient;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Consumer;

public interface ILCTotem {

	default void trigger(LivingEntity self, ItemStack holded, Consumer<ItemStack> second) {
		L2Complements.HANDLER.toTrackingPlayers(new TotemUseToClient(self, holded), self);
		holded.shrink(1);
		self.setHealth(1.0F);
		self.removeAllEffects();
		self.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
		self.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
		self.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
	}

	default boolean allow(LivingEntity self, DamageSource pDamageSource) {
		return !pDamageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
	}

	@OnlyIn(Dist.CLIENT)
	default void onClientTrigger(Entity entity, ItemStack item) {
		Minecraft.getInstance().particleEngine.createTrackingEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
		entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, entity.getSoundSource(), 1.0F, 1.0F, false);
		if (entity == Proxy.getClientPlayer()) {
			Minecraft.getInstance().gameRenderer.displayItemActivation(item);
		}
	}
}
