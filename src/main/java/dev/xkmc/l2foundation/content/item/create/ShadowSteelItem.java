package dev.xkmc.l2foundation.content.item.create;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.function.Supplier;

public class ShadowSteelItem extends NoGravMagicalDohickyItem {

	public ShadowSteelItem(Properties properties, Supplier<MutableComponent> sup) {
		super(properties, sup);
	}

	@Override
	protected void onCreated(ItemEntity entity, CompoundTag persistentData) {
		super.onCreated(entity, persistentData);
		float yMotion = (entity.fallDistance + 3) / 50f;
		entity.setDeltaMovement(0, yMotion, 0);
	}

	@Override
	protected float getIdleParticleChance(ItemEntity entity) {
		return (float) (Mth.clamp(entity.getItem()
				.getCount() - 10, Mth.clamp(entity.getDeltaMovement().y * 20, 5, 20), 100) / 64f);
	}

}
