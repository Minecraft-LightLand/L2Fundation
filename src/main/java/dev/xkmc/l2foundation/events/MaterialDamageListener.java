package dev.xkmc.l2foundation.events;

import dev.xkmc.l2foundation.init.data.LFConfig;
import dev.xkmc.l2foundation.init.registrate.LFItems;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.init.events.attack.AttackListener;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class MaterialDamageListener implements AttackListener {

	@Override
	public void onDamageFinalized(AttackCache cache, ItemStack weapon) {
		LivingDamageEvent event = cache.getLivingDamageEvent();
		if (event == null) return;
		if (cache.getAttackTarget() instanceof Player player) {
			float damage = cache.getDamageOriginal();
			if (event.getSource().isExplosion() && damage >= LFConfig.COMMON.explosionDamage.get()) {
				if (cache.getDamageDealt() < player.getHealth()) {
					player.getInventory().placeItemBackInInventory(LFItems.EXPLOSION_SHARD.asStack());
				}
			}
		}
		if (cache.getAttackTarget() instanceof Chicken chicken) {
			if (event.getSource().getMsgId().equals("sonic_boom")) {
				if (cache.getDamageDealt() < chicken.getHealth()) {
					chicken.spawnAtLocation(LFItems.RESONANT_FEATHER.asStack());
				}
			}
		}
		if (event.getSource().isProjectile() && event.getSource().getEntity() instanceof Player player) {
			if (cache.getDamageOriginal() >= LFConfig.COMMON.spaceDamage.get()) {
				cache.getAttackTarget().spawnAtLocation(LFItems.SPACE_SHARD.asStack());
			}
		}

	}
}
