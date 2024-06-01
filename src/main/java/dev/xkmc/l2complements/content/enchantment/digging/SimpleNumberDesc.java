package dev.xkmc.l2complements.content.enchantment.digging;

import dev.xkmc.l2complements.init.data.LangData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface SimpleNumberDesc extends BlockBreaker {

	@Override
	default List<Component> descFull(int lv, String key, boolean alt, boolean book) {
		return List.of(Component.translatable(key,
								range(Math.min(getMaxLevel(), lv)) + "")
						.withStyle(ChatFormatting.GRAY),
				LangData.diggerRotate().withStyle(ChatFormatting.DARK_GRAY)
		);
	}

	int range(int lv);

}
