package dev.xkmc.l2complements.content.item.misc;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class BurntItem extends TooltipItem {

	public BurntItem(Properties properties, Supplier<MutableComponent> sup) {
		super(properties, sup);
	}

	@Override
	public boolean isFoil(ItemStack pStack) {
		return true;
	}

}
