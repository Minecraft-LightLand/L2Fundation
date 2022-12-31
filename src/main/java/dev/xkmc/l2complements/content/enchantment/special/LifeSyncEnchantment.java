package dev.xkmc.l2complements.content.enchantment.special;

import dev.xkmc.l2complements.content.enchantment.core.SingleLevelEnchantment;
import net.minecraft.ChatFormatting;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class LifeSyncEnchantment extends SingleLevelEnchantment {

	public static final DamageSource SOURCE = new DamageSource("life_sync").bypassArmor().bypassMagic();

	public LifeSyncEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}

	public ChatFormatting getColor(){
		return ChatFormatting.LIGHT_PURPLE;
	}

}
