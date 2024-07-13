package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2core.events.SchedulerHandler;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraArmorConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class TotemicArmor extends ExtraArmorConfig {

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		super.onArmorTick(stack, world, player);
		if (player.tickCount % LCConfig.COMMON.totemicHealDuration.get() == 0) {
			player.heal(LCConfig.COMMON.totemicHealAmount.get());
		}
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity) {
		if (entity instanceof Player player) {
			SchedulerHandler.schedule(() -> player.heal(amount));
		}
		return super.damageItem(stack, amount, entity);
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LangData.IDS.TOTEMIC_ARMOR.get().withStyle(ChatFormatting.GRAY));
		super.addTooltip(stack, list);
	}

}
