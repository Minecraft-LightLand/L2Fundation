package dev.xkmc.l2complements.events;

import dev.xkmc.l2complements.content.item.generic.GenericArmorItem;
import dev.xkmc.l2complements.content.item.generic.GenericTieredItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ArmorVisibilityEvent {

	public static boolean isVisible(LivingEntity entity, ItemStack stack) {
		if (entity.hasEffect(MobEffects.INVISIBILITY)){
			if (stack.getItem() instanceof GenericTieredItem item){
				if (item.getExtraConfig().hideWithEffect())
					return false;
			}
			if (stack.getItem() instanceof GenericArmorItem item){
				if (item.getConfig().hideWithEffect())
					return false;
			}
		}
		return true;
	}

}
