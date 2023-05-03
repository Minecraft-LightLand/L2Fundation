package dev.xkmc.l2complements.content.enchantment.special;

import dev.xkmc.l2complements.content.enchantment.core.SingleLevelEnchantment;
import dev.xkmc.l2complements.init.data.DamageTypeGen;
import net.minecraft.ChatFormatting;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;

public class LifeSyncEnchantment extends SingleLevelEnchantment {

	public LifeSyncEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}

	public static DamageSource getSource(Level level) {
		return new DamageSource(DamageTypeGen.forKey(level, DamageTypeGen.LIFE_SYNC));
	}

	public ChatFormatting getColor() {
		return ChatFormatting.LIGHT_PURPLE;
	}

}
