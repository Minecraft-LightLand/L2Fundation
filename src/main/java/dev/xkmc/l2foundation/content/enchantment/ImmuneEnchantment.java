package dev.xkmc.l2foundation.content.enchantment;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ImmuneEnchantment extends Enchantment {

	public ImmuneEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}

	@Override
	public int getMinLevel() {
		return 1;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public int getMinCost(int lv) {
		return 200;
	}

	@Override
	public int getMaxCost(int lv) {
		return 150;
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}

	@Override
	public boolean isTradeable() {
		return false;
	}

	@Override
	public boolean isDiscoverable() {
		return false;
	}

	@Override
	public boolean isAllowedOnBooks() {
		return true;
	}

	@Override
	protected boolean checkCompatibility(Enchantment enchantment) {
		return !(enchantment instanceof ImmuneEnchantment);
	}

	@Override
	public Component getFullname(int lv) {
		MutableComponent component = Component.translatable(this.getDescriptionId());

		if (lv != 1 || this.getMaxLevel() != 1) {
			component.append(" ").append(Component.translatable("enchantment.level." + lv));
		}
		component.withStyle(ChatFormatting.GOLD);
		return component;
	}

}
