package dev.xkmc.l2complements.content.enchantment.legacy;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2core.init.reg.ench.CustomDescEnchantment;
import dev.xkmc.l2core.init.reg.ench.EnchColor;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BannableEnchantment extends LegacyEnchantment implements CustomDescEnchantment {

	@Override
	public Component title(ItemStack stack, Component comp, boolean alt, boolean book, EnchColor color) {
		if (!LCConfig.SERVER.enableImmunityEnchantments.get())
			return comp.copy().withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.STRIKETHROUGH);
		return CustomDescEnchantment.super.title(stack, comp, alt, book, color);
	}


	@Override
	public List<Component> descFull(ItemStack stack, int lv, String key, boolean alt, boolean book, EnchColor color) {
		if (!LCConfig.SERVER.enableImmunityEnchantments.get())
			return List.of();
		return List.of(Component.translatable(key).withStyle(color.desc()));
	}

}
