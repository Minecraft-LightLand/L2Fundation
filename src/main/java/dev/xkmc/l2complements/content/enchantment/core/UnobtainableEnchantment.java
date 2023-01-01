package dev.xkmc.l2complements.content.enchantment.core;

import dev.xkmc.l2complements.init.registrate.LCItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class UnobtainableEnchantment extends Enchantment {

	protected UnobtainableEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
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

	public ChatFormatting getColor(){
		return ChatFormatting.GREEN;
	}

	@Override
	public Component getFullname(int lv) {
		MutableComponent component = Component.translatable(this.getDescriptionId());
		if (lv != 1 || this.getMaxLevel() != 1) {
			component.append(" ").append(Component.translatable("enchantment.level." + lv));
		}
		component.withStyle(getColor());
		return component;
	}

	public boolean allowedInCreativeTab(Item book, CreativeModeTab tab) {
		return tab == CreativeModeTab.TAB_SEARCH || tab == LCItems.TAB_GENERATED;
	}

}
