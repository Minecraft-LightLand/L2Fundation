package dev.xkmc.l2complements.events;

import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EnchantmentEventHandler {

	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event) {
		if (event.getSource().isBypassMagic() || event.getSource().isBypassInvul())
			return;
		if (EnchantmentHelper.getEnchantmentLevel(LCEnchantments.ENCH_MAGIC.get(), event.getEntity()) > 0) {
			if (event.getSource().isMagic()) event.setCanceled(true);
		}
		if (event.getSource().isBypassEnchantments())
			return;
		if (EnchantmentHelper.getEnchantmentLevel(LCEnchantments.ENCH_PROJECTILE.get(), event.getEntity()) > 0) {
			if (event.getSource().isProjectile()) event.setCanceled(true);
		}
		if (EnchantmentHelper.getEnchantmentLevel(LCEnchantments.ENCH_FIRE.get(), event.getEntity()) > 0) {
			if (event.getSource().isFire()) event.setCanceled(true);
		}
		if (EnchantmentHelper.getEnchantmentLevel(LCEnchantments.ENCH_EXPLOSION.get(), event.getEntity()) > 0) {
			if (event.getSource().isExplosion()) event.setCanceled(true);
		}
		if (EnchantmentHelper.getEnchantmentLevel(LCEnchantments.ENCH_ENVIRONMENT.get(), event.getEntity()) > 0) {
			if (event.getSource().getEntity() == null) event.setCanceled(true);
		}
	}

}
