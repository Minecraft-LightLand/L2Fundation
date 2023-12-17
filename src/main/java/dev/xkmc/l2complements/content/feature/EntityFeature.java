package dev.xkmc.l2complements.content.feature;

import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public enum EntityFeature {
	STABLE_BODY(List.of(
			new SlotEnchantmentFeaturePredicate(EquipmentSlot.CHEST, LCEnchantments.STABLE_BODY::get),
			new CurioFeaturePredicate(CoPItems.STABLE_BODY::get)
	)),
	PROJECTILE_REJECT(List.of(new EnchantmentFeaturePredicate(LCEnchantments.ENCH_PROJECTILE::get),
			new CurioFeaturePredicate(CoPItems.PROJECTILE_REJECT::get)
	)),
	EXPLOSION_REJECT(List.of(new EnchantmentFeaturePredicate(LCEnchantments.ENCH_EXPLOSION::get),
			new CurioFeaturePredicate(CoPItems.EXPLOSION_REJECT::get)
	)),
	FIRE_REJECT(List.of(new EnchantmentFeaturePredicate(LCEnchantments.ENCH_FIRE::get),
			new CurioFeaturePredicate(CoPItems.FIRE_REJECT::get)
	)),
	ENVIRONMENTAL_REJECT(List.of(new EnchantmentFeaturePredicate(LCEnchantments.ENCH_ENVIRONMENT::get),
			new CurioFeaturePredicate(CoPItems.ENVIRONMENTAL_REJECT::get)
	)),
	MAGIC_REJECT(List.of(new EnchantmentFeaturePredicate(LCEnchantments.ENCH_MAGIC::get),
			new CurioFeaturePredicate(CoPItems.MAGIC_REJECT::get)
	)),
	OWNER_PROTECTION(List.of(new EnchantmentFeaturePredicate(LCEnchantments.ENCH_MATES::get),
			new CurioFeaturePredicate(CoPItems.OWNER_PROTECTION::get)
	)),
	INVINCIBLE(List.of(new EnchantmentFeaturePredicate(LCEnchantments.ENCH_INVINCIBLE::get))),
	LAVA_WALKER(List.of(new CurioFeaturePredicate(CoPItems.BLESS_LAVA_WALKER::get))),
	;

	private final ArrayList<FeaturePredicate> list;

	EntityFeature(List<FeaturePredicate> list) {
		this.list = new ArrayList<>(list);
	}

	public boolean test(LivingEntity e) {
		for (var x : list) {
			if (x.test(e)) return true;
		}
		return false;
	}

}
