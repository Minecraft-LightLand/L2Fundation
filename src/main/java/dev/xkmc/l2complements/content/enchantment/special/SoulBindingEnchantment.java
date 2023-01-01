package dev.xkmc.l2complements.content.enchantment.special;

import dev.xkmc.l2complements.content.enchantment.core.SingleLevelEnchantment;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class SoulBindingEnchantment extends SingleLevelEnchantment {

	public SoulBindingEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}

	public ChatFormatting getColor() {
		return ChatFormatting.GOLD;
	}

	@Override
	protected boolean checkCompatibility(Enchantment pOther) {
		return pOther != Enchantments.VANISHING_CURSE;
	}

}
