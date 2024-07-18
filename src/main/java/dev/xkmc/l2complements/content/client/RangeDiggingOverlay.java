package dev.xkmc.l2complements.content.client;

import dev.xkmc.l2complements.content.enchantment.digging.DiggerHelper;
import dev.xkmc.l2complements.init.data.LangData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class RangeDiggingOverlay implements LayeredDraw.Layer {

	@Override
	public void render(GuiGraphics g, DeltaTracker delta) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player == null) return;
		ItemStack stack = player.getMainHandItem();
		var e = DiggerHelper.getDigger(stack);
		if (e == null) return;
		var name = Enchantment.getFullname(e.ench(), e.level());
		renderText(Minecraft.getInstance().font, g, g.guiWidth() / 2, g.guiHeight() / 2 + 34,
				LangData.IDS.DIGGER_ACTIVATED.get(name).withStyle(ChatFormatting.RED));
	}

	private static void renderText(Font font, GuiGraphics g, int x, int y, Component text) {
		x -= font.width(text) / 2;
		g.drawString(font, text, x, y, 0xFFFFFFFF);
	}

}
