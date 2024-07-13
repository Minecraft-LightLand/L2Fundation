package dev.xkmc.l2complements.events;

import dev.xkmc.l2complements.content.enchantment.core.SourceModifierEnchantment;
import dev.xkmc.l2complements.content.enchantment.weapon.VoidTouchEnchantment;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.init.L2LibReg;
import dev.xkmc.l2damagetracker.contents.attack.AttackListener;
import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.fml.ModList;

import java.util.HashSet;

public class MaterialDamageListener implements AttackListener {

	public static final HashSet<String> BAN_SPACE_SHARD = new HashSet<>();

	static {
		BAN_SPACE_SHARD.add("l2artifacts");
		BAN_SPACE_SHARD.add("l2hostility");
		BAN_SPACE_SHARD.add("apotheosis");
	}

	public static boolean isSpaceShardBanned() {
		if (LCConfig.COMMON.allowModBanSpaceShard.get()) {
			for (var e : BAN_SPACE_SHARD) {
				if (ModList.get().isLoaded(e)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onCreateSource(CreateSourceEvent event) {
		if (event.getOriginal().equals(DamageTypes.MOB_ATTACK) || event.getOriginal().equals(DamageTypes.PLAYER_ATTACK)) {
			ItemStack stack = event.getAttacker().getMainHandItem();
			for (var e : stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).entrySet()) {
				for (var c : e.getKey().value().getEffects(L2LibReg.LEGACY.get())) {
					if (c instanceof SourceModifierEnchantment mod)
						mod.modify(event, stack, e.getIntValue());
				}
			}
		}
	}

	@Override
	public void onDamageFinalized(DamageData.DefenceMax data) {
		if (data.getTarget() instanceof Player player) {
			float damage = data.getDamageIncoming();
			if (data.getSource().is(DamageTypeTags.IS_EXPLOSION) && damage >= LCConfig.COMMON.explosionDamage.get()) {
				if (data.getDamageFinal() < player.getHealth() + player.getAbsorptionAmount()) {
					player.getInventory().placeItemBackInInventory(LCItems.EXPLOSION_SHARD.asStack());
				}
			}
		}
		if (data.getTarget() instanceof Chicken chicken) {
			if (data.getSource().getMsgId().equals("sonic_boom")) {
				if (data.getDamageFinal() < chicken.getHealth() + chicken.getAbsorptionAmount()) {
					chicken.spawnAtLocation(LCItems.RESONANT_FEATHER.asStack());
				}
			}
		}
		if (data.getSource().is(DamageTypeTags.IS_PROJECTILE) && data.getAttacker() instanceof Player) {
			if (!isSpaceShardBanned() && data.getDamageIncoming() >= LCConfig.COMMON.spaceDamage.get()) {
				data.getTarget().spawnAtLocation(LCItems.SPACE_SHARD.asStack());
			}
		}

	}

	@Override
	public boolean onAttack(DamageData.Attack data) {
		if (!data.getWeapon().isEmpty()) {
			VoidTouchEnchantment.postAttack(data, data.getWeapon());
		}
		return false;
	}

	@Override
	public void onHurt(DamageData.Offence data) {
		if (!data.getWeapon().isEmpty()) {
			VoidTouchEnchantment.initAttack(data, data.getWeapon());
		}
	}

	@Override
	public void onDamage(DamageData.Defence data) {
		if (!data.getWeapon().isEmpty()) {
			VoidTouchEnchantment.initDamage(data, data.getWeapon());
		}
	}

}
