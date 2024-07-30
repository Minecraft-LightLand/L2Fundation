package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.init.data.LCLang;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraArmorConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShulkerateArmor extends ExtraArmorConfig {

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity) {
		return 1;
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LCLang.IDS.SHULKERATE_ARMOR.get().withStyle(ChatFormatting.GRAY));
		super.addTooltip(stack, list);
	}

}
