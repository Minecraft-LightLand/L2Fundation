package dev.xkmc.l2complements.events;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.init.events.attack.AttackListener;
import net.minecraft.sounds.SoundEvents;
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
			if (event.getSource().isExplosion() && damage >= LCConfig.COMMON.explosionDamage.get()) {
				if (cache.getDamageDealt() < player.getHealth()) {
					player.getInventory().placeItemBackInInventory(LCItems.EXPLOSION_SHARD.asStack());
				}
			}
		}
		if (cache.getAttackTarget() instanceof Chicken chicken) {
			if (event.getSource().getMsgId().equals("sonic_boom")) {
				if (cache.getDamageDealt() < chicken.getHealth()) {
					chicken.spawnAtLocation(LCItems.RESONANT_FEATHER.asStack());
				}
			}
		}
		if (event.getSource().isProjectile() && event.getSource().getEntity() instanceof Player) {
			if (cache.getDamageOriginal() >= LCConfig.COMMON.spaceDamage.get()) {
				cache.getAttackTarget().spawnAtLocation(LCItems.SPACE_SHARD.asStack());
			}
		}

	}
}
