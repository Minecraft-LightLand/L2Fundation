package dev.xkmc.l2complements.content.feature;

import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public enum EntityFeature {
	STABLE_BODY(List.of(new SlotEnchantmentFeaturePredicate(EquipmentSlot.CHEST, LCEnchantments.STABLE_BODY::holder))),
	PROJECTILE_REJECT(List.of(new EnchantmentFeaturePredicate(LCEnchantments.IMM_PROJECTILE::holder))),
	EXPLOSION_REJECT(List.of(new EnchantmentFeaturePredicate(LCEnchantments.IMM_EXPLOSION::holder))),
	FIRE_REJECT(List.of(new EnchantmentFeaturePredicate(LCEnchantments.IMM_FIRE::holder))),
	ENVIRONMENTAL_REJECT(List.of(new EnchantmentFeaturePredicate(LCEnchantments.IMM_ENVIRONMENT::holder))),
	MAGIC_REJECT(List.of(new EnchantmentFeaturePredicate(LCEnchantments.IMM_MAGIC::holder))),
	OWNER_PROTECTION(List.of(new EnchantmentFeaturePredicate(LCEnchantments.IMM_MATES::holder))),
	INVINCIBLE(List.of(new EnchantmentFeaturePredicate(LCEnchantments.INVINCIBLE::holder))),
	SNOW_WALKER(List.of(new EnchantmentFeaturePredicate(LCEnchantments.SNOW_WALKER::holder))),
	LAVA_WALKER(List.of()),
	;

	private final ArrayList<FeaturePredicate> list;

	EntityFeature(List<FeaturePredicate> list) {
		this.list = new ArrayList<>(list);
	}

	public void add(FeaturePredicate pred) {
		list.add(pred);
	}

	public boolean test(LivingEntity e) {
		for (var x : list) {
			if (x.test(e)) return true;
		}
		return false;
	}

}
