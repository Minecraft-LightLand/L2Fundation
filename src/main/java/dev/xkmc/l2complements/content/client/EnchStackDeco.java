package dev.xkmc.l2complements.content.client;

import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2complements.init.data.LCConfig;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.client.IItemDecorator;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Locale;

public class EnchStackDeco implements IItemDecorator {

	@Override
	public boolean render(GuiGraphics g, Font font, ItemStack stack, int x, int y) {
		boolean render = LCConfig.CLIENT.renderEnchOverlay.get();
		if (!render) return false;
		if (!(stack.getItem() instanceof EnchantedBookItem)) return false;
		if (stack.getCount() > 1) return false;
		var map = EnchantmentHelper.getEnchantments(stack);
		if (map.size() != 1) return false;
		Enchantment ench = new ArrayList<>(map.keySet()).get(0);
		if (!(ench instanceof UnobtainableEnchantment un)) return false;
		var id = ForgeRegistries.ENCHANTMENTS.getKey(ench);
		if (id == null) return false;

		String s = ("" + id.getNamespace().charAt(2)).toUpperCase(Locale.ROOT);
		g.pose().pushPose();
		int height = LCConfig.CLIENT.enchOverlayZVal.get();
		g.pose().translate(0, 0, height);
		int col = un.getDecoColor(s);
		g.drawString(font, s, x + 17 - font.width(s), y + 9, col);
		g.pose().popPose();
		return true;
	}
}
