package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.content.item.generic.ExtraArmorConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TotemiumArmor extends ExtraArmorConfig {

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		if (player.tickCount % 100 == 0) {//TODO config
			player.heal(1);
		}
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity) {
		if (entity instanceof Player player) {
			player.heal(amount);
		}
		return super.damageItem(stack, amount, entity);
	}

}
