package dev.xkmc.l2complements.content.item.create;

import dev.xkmc.l2complements.init.data.LCDamageTypes;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class VoidEyeItem extends ShadowSteelItem {

	public VoidEyeItem(Properties properties, Supplier<MutableComponent> sup) {
		super(properties, sup);
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		super.onEntityItemUpdate(stack, entity);
		if (entity.getDeltaMovement().y() < 0.05) {
			entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.005, 0));
		}
		return false;
	}

	@Override
	public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
		if (!pLevel.isClientSide() && pEntity instanceof LivingEntity le && le.position().y() < pLevel.getMinBuildHeight()) {
			pStack.setCount(0);
			le.hurt(new DamageSource(LCDamageTypes.forKey(pLevel, LCDamageTypes.VOID_EYE)), le.getMaxHealth());
		}
	}

}
