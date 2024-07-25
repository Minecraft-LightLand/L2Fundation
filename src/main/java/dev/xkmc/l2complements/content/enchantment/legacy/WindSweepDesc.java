package dev.xkmc.l2complements.content.enchantment.legacy;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2core.init.reg.ench.CustomDescEnchantment;
import dev.xkmc.l2core.init.reg.ench.EnchColor;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import net.minecraft.network.chat.Component;

import java.util.List;

public class WindSweepDesc extends LegacyEnchantment implements CustomDescEnchantment {

	@Override
	public List<Component> descFull(int lv, String key, boolean alt, boolean book, EnchColor color) {
		return List.of(Component.translatable(key,
				CustomDescEnchantment.numSmall(lv * LCConfig.SERVER.windSweepIncrement.get())
		).withStyle(color.desc()));
	}

}
