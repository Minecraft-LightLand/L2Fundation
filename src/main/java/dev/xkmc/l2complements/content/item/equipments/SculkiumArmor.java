package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraArmorConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;

public class SculkiumArmor extends ExtraArmorConfig {

	@Override
	public void configureAttributes(ItemAttributeModifiers.Builder builder, EquipmentSlot slot) {
		double factor = slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS ? 1.5 : 1;
		var group = EquipmentSlotGroup.bySlot(slot);
		if (slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST) {
			builder.add(Attributes.MAX_HEALTH, new AttributeModifier(
					L2Complements.loc("sculkium_attack_" + slot.getName()),
					0.06 * factor,
					AttributeModifier.Operation.ADD_MULTIPLIED_BASE), group);
		} else {
			builder.add(Attributes.MAX_HEALTH, new AttributeModifier(
					L2Complements.loc("sculkium_walk_" + slot.getName()),
					0.1 * factor,
					AttributeModifier.Operation.ADD_MULTIPLIED_BASE), group);
		}
		builder.add(Attributes.MAX_HEALTH, new AttributeModifier(
				L2Complements.loc("sculkium_health_" + slot.getName()),
				4 * factor,
				AttributeModifier.Operation.ADD_VALUE), group);
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LangData.IDS.SCULKIUM_ARMOR.get().withStyle(ChatFormatting.GRAY));
		super.addTooltip(stack, list);
	}

}
