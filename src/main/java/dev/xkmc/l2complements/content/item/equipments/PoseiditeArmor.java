package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCLang;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.base.effects.EffectUtil;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraArmorConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.List;
import java.util.function.Function;

public class PoseiditeArmor extends ExtraArmorConfig {

	private static ItemAttributeModifiers modifyDynamicAttributes(ArmorItem.Type type) {
		var builder = ItemAttributeModifiers.builder();
		LCMats.POSEIDITE.defaultAttributes(builder, type);
		double factor = type.getSlot() == EquipmentSlot.CHEST || type.getSlot() == EquipmentSlot.LEGS ? 1.5 : 1;
		var group = EquipmentSlotGroup.bySlot(type.getSlot());

		builder.add(Attributes.ARMOR, new AttributeModifier(
				L2Complements.loc("poseidite_armor." + type.getName()),
				4 * factor,
				AttributeModifier.Operation.ADD_VALUE), group);

		builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(
				L2Complements.loc("poseidite_toughness." + type.getName()),
				2 * factor,
				AttributeModifier.Operation.ADD_VALUE), group);

		builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(
				L2Complements.loc("poseidite_walk." + type.getName()),
				0.1 * factor,
				AttributeModifier.Operation.ADD_MULTIPLIED_BASE), group);
		return builder.build();
	}

	private static ItemAttributeModifiers originalAttributes(ArmorItem.Type type) {
		return LCMats.POSEIDITE.getArmor(type.getSlot()).getDefaultInstance().get(DataComponents.ATTRIBUTE_MODIFIERS);
	}

	private static final Function<ArmorItem.Type, ItemAttributeModifiers> ATTRS =
			Util.memoize(PoseiditeArmor::modifyDynamicAttributes);
	private static final Function<ArmorItem.Type, ItemAttributeModifiers> ORIGINAL =
			Util.memoize(PoseiditeArmor::originalAttributes);

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
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int index, boolean selected) {
		if (!(entity instanceof LivingEntity le)) return;
		var slot = ((ArmorItem) stack.getItem()).getEquipmentSlot();
		if (le.getItemBySlot(slot) != stack) return;
		if (le.isInWaterRainOrBubble()) {
			if (!stack.has(LCItems.IN_WATER.get())) {
				stack.set(DataComponents.ATTRIBUTE_MODIFIERS, ATTRS.apply(((ArmorItem) stack.getItem()).getType()));
			}
			stack.set(LCItems.IN_WATER.get(), Unit.INSTANCE);
			if (slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST) {
				EffectUtil.refreshEffect(le, new MobEffectInstance(MobEffects.CONDUIT_POWER, 200), le);
			}
			if (slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET) {
				EffectUtil.refreshEffect(le, new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 200), le);
			}
		} else {
			if (stack.has(LCItems.IN_WATER.get())) {
				stack.set(DataComponents.ATTRIBUTE_MODIFIERS, ORIGINAL.apply(((ArmorItem) stack.getItem()).getType()));
			}
			stack.remove(LCItems.IN_WATER);
		}
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LCLang.IDS.POSEIDITE_ARMOR.get().withStyle(ChatFormatting.GRAY));
		super.addTooltip(stack, list);
	}

}
