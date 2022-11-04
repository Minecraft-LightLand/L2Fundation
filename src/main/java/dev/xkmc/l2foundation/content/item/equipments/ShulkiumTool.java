package dev.xkmc.l2foundation.content.item.equipments;

import dev.xkmc.l2foundation.content.item.generic.ExtraToolConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ShulkiumTool extends ExtraToolConfig {

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity) {
		return super.damageItem(stack, 1, entity);
	}

}
