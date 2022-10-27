package dev.xkmc.l2foundation.content.item.create;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;

public class RefinedRadianceItem extends NoGravMagicalDohickyItem {

	public RefinedRadianceItem(Properties properties) {
		super(properties);
	}

	@Override
	protected void onCreated(ItemEntity entity, CompoundTag persistentData) {
		super.onCreated(entity, persistentData);
		entity.setDeltaMovement(entity.getDeltaMovement()
				.add(0, .25f, 0));
	}

}
