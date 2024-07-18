package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.base.effects.EffectUtil;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraArmorConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.List;

public class PoseiditeArmor extends ExtraArmorConfig {

	@Override
	public void configureAttributes(ItemAttributeModifiers.Builder builder, EquipmentSlot slot) {
		double factor = slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS ? 1.5 : 1;
		var group = EquipmentSlotGroup.bySlot(slot);
		builder.add(NeoForgeMod.SWIM_SPEED, new AttributeModifier(
				L2Complements.loc("poseidite_swim" + slot.getName()),
				0.1 * factor,
				AttributeModifier.Operation.ADD_MULTIPLIED_BASE), group);
	}

	@Override
	public void modifyDynamicAttributes(ItemAttributeModifiers.Builder builder, EquipmentSlot slot, ItemStack stack) {
		if (!stack.has(LCItems.IN_WATER.get())) return;
		double factor = slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS ? 1.5 : 1;
		var group = EquipmentSlotGroup.bySlot(slot);

		builder.add(Attributes.ARMOR, new AttributeModifier(
				L2Complements.loc("poseidite_armor" + slot.getName()),
				4 * factor,
				AttributeModifier.Operation.ADD_VALUE), group);

		builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(
				L2Complements.loc("poseidite_toughness" + slot.getName()),
				2 * factor,
				AttributeModifier.Operation.ADD_VALUE), group);

		builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(
				L2Complements.loc("poseidite_walk" + slot.getName()),
				0.1 * factor,
				AttributeModifier.Operation.ADD_MULTIPLIED_BASE), group);

	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		if (player.isInWaterRainOrBubble()) {
			stack.set(LCItems.IN_WATER.get(), Unit.INSTANCE);
			EquipmentSlot slot = ((ArmorItem) stack.getItem()).getEquipmentSlot();
			if (slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST) {
				EffectUtil.refreshEffect(player, new MobEffectInstance(MobEffects.CONDUIT_POWER, 200), player);
			}
			if (slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET) {
				EffectUtil.refreshEffect(player, new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 200), player);
			}
		} else stack.remove(LCItems.IN_WATER);
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LangData.IDS.POSEIDITE_ARMOR.get().withStyle(ChatFormatting.GRAY));
		super.addTooltip(stack, list);
	}

}
