package dev.xkmc.l2complements.content.item.misc;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class TooltipItem extends Item {

	private final Supplier<MutableComponent> sup;

	public TooltipItem(Properties properties, Supplier<MutableComponent> sup) {
		super(properties);
		this.sup = sup;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(sup.get().withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, level, list, flag);
	}
}
