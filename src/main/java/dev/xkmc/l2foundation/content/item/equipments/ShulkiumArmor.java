package dev.xkmc.l2foundation.content.item.equipments;

import dev.xkmc.l2foundation.content.item.generic.ExtraArmorConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ShulkiumArmor extends ExtraArmorConfig {

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity) {
		return super.damageItem(stack, 1, entity);
	}

}
