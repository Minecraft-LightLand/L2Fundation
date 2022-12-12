package dev.xkmc.l2complements.content.item.misc;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class PoseiditeTotem extends Item implements ILCTotem {

	public PoseiditeTotem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean allow(LivingEntity self, DamageSource pDamageSource) {
		return !pDamageSource.isBypassInvul() && self.isInWaterRainOrBubble();
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
	}

}
