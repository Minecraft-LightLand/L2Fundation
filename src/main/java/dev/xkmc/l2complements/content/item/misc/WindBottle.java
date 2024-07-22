package dev.xkmc.l2complements.content.item.misc;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCItems;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class WindBottle extends TooltipItem {

	public WindBottle(Properties properties, Supplier<MutableComponent> sup) {
		super(properties, sup);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean select) {
		if (!level.isClientSide() && entity instanceof Player player) {
			if (entity.getDeltaMovement().length() >= LCConfig.SERVER.windSpeed.get()) {
				stack.shrink(1);
				player.getInventory().placeItemBackInInventory(LCItems.CAPTURED_WIND.asStack());
			}
		}
	}
}
