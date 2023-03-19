package dev.xkmc.l2complements.content.enchantment.armors;

import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class DurableArmorEnchantment extends UnobtainableEnchantment {

	public DurableArmorEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}

	@Override
	public int getMinLevel() {
		return 1;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	protected boolean checkCompatibility(Enchantment pOther) {
		return pOther != Enchantments.UNBREAKING && super.checkCompatibility(pOther);
	}

}
