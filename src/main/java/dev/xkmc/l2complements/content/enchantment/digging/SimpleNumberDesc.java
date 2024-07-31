package dev.xkmc.l2complements.content.enchantment.digging;

import dev.xkmc.l2complements.init.data.LCLang;
import dev.xkmc.l2core.init.reg.ench.CustomDescEnchantment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface SimpleNumberDesc extends BlockBreaker {

	@Override
	default List<Component> descFull(int lv, String key, boolean alt, boolean book) {
		return List.of(Component.translatable(key,
								CustomDescEnchantment.num(range(Math.min(getMaxLevel(), lv))))
						.withStyle(ChatFormatting.GRAY),
				LCLang.diggerRotate().withStyle(ChatFormatting.DARK_GRAY)
		);
	}

	int range(int lv);

}
