
package dev.xkmc.l2complements.content.client;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2core.init.L2CoreConfig;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.client.IItemDecorator;

import java.util.ArrayList;
import java.util.Locale;

public class EnchStackDeco implements IItemDecorator {

	@Override
	public boolean render(GuiGraphics g, Font font, ItemStack stack, int x, int y) {
		boolean render = LCConfig.CLIENT.renderEnchOverlay.getAsBoolean();
		if (!render) return false;
		if (!(stack.getItem() instanceof EnchantedBookItem)) return false;
		if (stack.getCount() > 1) return false;
		var map = EnchantmentHelper.getEnchantmentsForCrafting(stack);
		if (map.size() != 1) return false;
		var ench = new ArrayList<>(map.keySet()).getFirst();
		var craft = LegacyEnchantment.firstOf(ench, LCEnchantments.CRAFT);
		if (craft == null) return false;
		String s = ("" + ench.unwrapKey().orElseThrow().location().getNamespace().charAt(2)).toUpperCase(Locale.ROOT);
		g.pose().pushPose();
		int height = L2CoreConfig.CLIENT.overlayZVal.get();
		g.pose().translate(0, 0, height);
		int col = craft.color();
		g.drawString(font, s, x + 17 - font.width(s), y + 9, col);
		g.pose().popPose();
		return true;
	}
}
