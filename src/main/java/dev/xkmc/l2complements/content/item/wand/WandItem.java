package dev.xkmc.l2complements.content.item.wand;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class WandItem extends Item {

	public WandItem(Properties prop) {
		super(prop);
	}

	@Override
	public int getEnchantmentLevel(ItemStack stack, Holder<Enchantment> enchantment) {
		return super.getEnchantmentLevel(stack, enchantment);
	}

	@Override
	public ItemEnchantments getAllEnchantments(ItemStack stack, HolderLookup.RegistryLookup<Enchantment> lookup) {
		return super.getAllEnchantments(stack, lookup);
	}

}
