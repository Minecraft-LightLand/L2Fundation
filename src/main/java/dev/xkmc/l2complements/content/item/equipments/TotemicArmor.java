package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LCLang;
import dev.xkmc.l2core.events.SchedulerHandler;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraArmorConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class TotemicArmor extends ExtraArmorConfig {

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int index, boolean selected) {
		if (!(entity instanceof LivingEntity le)) return;
		var slot = ((ArmorItem) stack.getItem()).getEquipmentSlot();
		if (le.getItemBySlot(slot) != stack) return;
		if (le.tickCount % LCConfig.SERVER.totemicHealDuration.get() == 0) {
			le.heal(LCConfig.SERVER.totemicHealAmount.get());
		}
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity) {
		if (entity instanceof LivingEntity player) {
			SchedulerHandler.schedule(() -> player.heal(amount));
		}
		return super.damageItem(stack, amount, entity);
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LCLang.IDS.TOTEMIC_ARMOR.get().withStyle(ChatFormatting.GRAY));
		super.addTooltip(stack, list);
	}

}
