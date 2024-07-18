package dev.xkmc.l2complements.content.item.misc;

import dev.xkmc.l2complements.init.data.LangData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class PoseiditeTotem extends Item implements ILCTotem {

	public PoseiditeTotem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean allow(LivingEntity self, DamageSource pDamageSource) {
		return !pDamageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && self.isInWaterRainOrBubble();
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag flag) {
		list.add(LangData.IDS.TOTEM_SEA.get().withStyle(ChatFormatting.GRAY));
	}

}
