package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LCLang;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Function;

public class PoseiditeTool extends ExtraToolConfig {

	private static ItemAttributeModifiers modifyDynamicAttributes(Item item) {
		var builder = ItemAttributeModifiers.builder();
		var old = item.getDefaultInstance().get(DataComponents.ATTRIBUTE_MODIFIERS);
		for (var e : old.modifiers())
			builder.add(e.attribute(), e.modifier(), e.slot());
		builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(
				L2Complements.loc("poseidite_attack"), 0.5,
				AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.MAINHAND);
		builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(
				L2Complements.loc("poseidite_atk_speed"), 0.2,
				AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.MAINHAND);
		return builder.build();
	}

	private static ItemAttributeModifiers originalAttributes(Item item) {
		return item.getDefaultInstance().get(DataComponents.ATTRIBUTE_MODIFIERS);
	}

	private static final Function<Item, ItemAttributeModifiers> ATTRS =
			Util.memoize(PoseiditeTool::modifyDynamicAttributes);
	private static final Function<Item, ItemAttributeModifiers> ORIGINAL =
			Util.memoize(PoseiditeTool::originalAttributes);

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity player, int slot, boolean selected) {
		if (player.isInWaterRainOrBubble()) {
			if (!stack.has(LCItems.IN_WATER.get())) {
				stack.set(DataComponents.ATTRIBUTE_MODIFIERS, ATTRS.apply(stack.getItem()));
			}
			stack.set(LCItems.IN_WATER.get(), Unit.INSTANCE);
		} else {
			if (stack.has(LCItems.IN_WATER.get())) {
				stack.set(DataComponents.ATTRIBUTE_MODIFIERS, ORIGINAL.apply(stack.getItem()));
			}
			stack.remove(LCItems.IN_WATER);
		}
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state, float old) {
		if (!stack.has(LCItems.IN_WATER.get())) return old;
		return old * 1.5f;
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LCLang.IDS.POSEIDITE_TOOL.get().withStyle(ChatFormatting.GRAY));
	}

	@Override
	public void onDamage(DamageData.Offence cache, ItemStack stack) {
		if (cache.getTarget().isSensitiveToWater() ||
				cache.getTarget().getType().is(EntityTypeTags.SENSITIVE_TO_IMPALING)) {
			cache.addHurtModifier(DamageModifier.multAttr((float) (1 + LCConfig.SERVER.mobTypeBonus.get()),
					LCMats.POSEIDITE.id().withSuffix("_impaling")));
		}
	}

}
