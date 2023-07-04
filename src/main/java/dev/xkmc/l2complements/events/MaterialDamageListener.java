package dev.xkmc.l2complements.events;

import dev.xkmc.l2complements.content.enchantment.core.SourceModifierEnchantment;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.AttackListener;
import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class MaterialDamageListener implements AttackListener {

	@Override
	public void onCreateSource(CreateSourceEvent event) {
		if (event.getOriginal().equals(DamageTypes.MOB_ATTACK) || event.getOriginal().equals(DamageTypes.PLAYER_ATTACK)) {
			ItemStack stack = event.getAttacker().getMainHandItem();
			SourceModifierEnchantment.modifySource(stack, event);
		}
	}

	@Override
	public void onDamageFinalized(AttackCache cache, ItemStack weapon) {
		LivingDamageEvent event = cache.getLivingDamageEvent();
		if (event == null) return;
		if (cache.getAttackTarget() instanceof Player player) {
			float damage = cache.getPreDamage();
			if (event.getSource().is(DamageTypeTags.IS_EXPLOSION) && damage >= LCConfig.COMMON.explosionDamage.get()) {
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
		if (event.getSource().is(DamageTypeTags.IS_PROJECTILE) && event.getSource().getEntity() instanceof Player) {
			if (cache.getPreDamage() >= LCConfig.COMMON.spaceDamage.get()) {
				cache.getAttackTarget().spawnAtLocation(LCItems.SPACE_SHARD.asStack());
			}
		}

	}

	@Override
	public void onHurt(AttackCache cache, ItemStack weapon) {
		if (!weapon.isEmpty()) {
			LCEnchantments.VOID_TOUCH.get().initAttack(cache, weapon);
		}
	}

	@Override
	public void onDamage(AttackCache cache, ItemStack weapon) {
		if (!weapon.isEmpty()) {
			LCEnchantments.VOID_TOUCH.get().initDamage(cache, weapon);
		}
	}

}
