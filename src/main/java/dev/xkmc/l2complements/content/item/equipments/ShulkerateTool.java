package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2complements.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ShulkerateTool extends ExtraToolConfig {

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity) {
		return super.damageItem(stack, 1, entity);
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LangData.IDS.SHULKERATE_TOOL.get());
	}

	@Override
	public boolean hideWithEffect() {
		return true;
	}

}
