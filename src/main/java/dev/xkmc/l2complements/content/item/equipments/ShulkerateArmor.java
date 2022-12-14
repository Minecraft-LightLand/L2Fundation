package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.content.item.generic.ExtraArmorConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ShulkerateArmor extends ExtraArmorConfig {

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity) {
		return super.damageItem(stack, 1, entity);
	}

}
