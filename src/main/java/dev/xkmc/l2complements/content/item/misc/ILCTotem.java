package dev.xkmc.l2complements.content.item.misc;

import dev.xkmc.l2damagetracker.contents.curios.L2Totem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public interface ILCTotem extends L2Totem {

	@Override
	default void trigger(LivingEntity self, ItemStack holded, Consumer<ItemStack> second) {
		L2Totem.super.trigger(self, holded, second);
	}

	@Override
	default boolean allow(LivingEntity self, DamageSource pDamageSource) {
		return L2Totem.super.allow(self, pDamageSource);
	}

	@Override
	default void onClientTrigger(Entity entity, ItemStack item) {
		L2Totem.super.onClientTrigger(entity, item);
	}

}
