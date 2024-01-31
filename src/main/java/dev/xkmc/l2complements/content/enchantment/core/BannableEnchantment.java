package dev.xkmc.l2complements.content.enchantment.core;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LangData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BannableEnchantment extends ImmuneEnchantment {

	public BannableEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}

	@Override
	public Component getFullname(int lv) {
		if (!LCConfig.COMMON.enableImmunityEnchantments.get()) {
			return LangData.IDS.BANNED_ENCH.get().withStyle(ChatFormatting.DARK_RED).append(" ")
					.append(super.getFullname(lv).copy().withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.STRIKETHROUGH));
		}
		return super.getFullname(lv);
	}
}
