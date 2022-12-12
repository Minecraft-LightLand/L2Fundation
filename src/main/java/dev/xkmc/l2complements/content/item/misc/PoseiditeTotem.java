package dev.xkmc.l2complements.content.item.misc;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class PoseiditeTotem extends Item implements ILCTotem {

	public PoseiditeTotem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean allow(LivingEntity self, DamageSource pDamageSource) {
		return !pDamageSource.isBypassInvul() && self.isInWaterRainOrBubble();
	}

	@Override
	public void trigger(LivingEntity self, ItemStack holded, Consumer<ItemStack> second) {
		ILCTotem.super.trigger(self, holded, second);
		self.setHealth(self.getMaxHealth());
	}

}
