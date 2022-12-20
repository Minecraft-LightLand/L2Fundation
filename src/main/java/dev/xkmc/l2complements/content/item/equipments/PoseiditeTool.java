package dev.xkmc.l2complements.content.item.equipments;

import com.google.common.collect.Multimap;
import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class PoseiditeTool extends ExtraToolConfig {

	private static final String KEY = "UserInWater", NAME_ATK = "neptonium_attack", NAME_SPEED = "neptonium_attack_speed";

	private static final AttributeModifier ATK = new AttributeModifier(MathHelper.getUUIDFromString(NAME_ATK), NAME_ATK, 0.5d, AttributeModifier.Operation.MULTIPLY_BASE);
	private static final AttributeModifier SPEED = new AttributeModifier(MathHelper.getUUIDFromString(NAME_SPEED), NAME_SPEED, 0.2d, AttributeModifier.Operation.MULTIPLY_BASE);

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		stack.getOrCreateTag().putBoolean(KEY, entity.isInWaterRainOrBubble());
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state, float old) {
		if (stack.getOrCreateTag().getBoolean(KEY)) {
			return old * 1.5f;
		}
		return old;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> modify(Multimap<Attribute, AttributeModifier> map, EquipmentSlot slot, ItemStack stack) {
		if (stack.getOrCreateTag().getBoolean(KEY)) {
			map.put(Attributes.ATTACK_DAMAGE, ATK);
			map.put(Attributes.ATTACK_SPEED, SPEED);
		}
		return map;
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LangData.IDS.POSEIDITE_TOOL.get());
	}

}
