package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCLang;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShulkerateTool extends ExtraToolConfig {

	@Override
	public void configureAttributes(ItemAttributeModifiers.Builder builder) {
		builder.add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(
				L2Complements.loc("shulkerate_block"), 1,
				AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
		builder.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(
				L2Complements.loc("shulkerate_entity"), 1,
				AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity) {
		return 1;
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LCLang.IDS.SHULKERATE_TOOL.get().withStyle(ChatFormatting.GRAY));
	}

}
