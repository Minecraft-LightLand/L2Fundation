package dev.xkmc.l2complements.content.item.equipments;

import com.google.common.collect.Multimap;
import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;

import java.util.List;

public class ShulkerateTool extends ExtraToolConfig {

	private static final String NAME_R = "shulkerate_reach", NAME_D = "shulkerate_dist";

	@Override
	public Multimap<Attribute, AttributeModifier> modify(Multimap<Attribute, AttributeModifier> map, EquipmentSlot slot, ItemStack stack) {
		double val = LCConfig.COMMON.shulkerateReach.get();
		map.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(MathHelper.getUUIDFromString(NAME_R), NAME_R, val, AttributeModifier.Operation.ADDITION));
		map.put(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(MathHelper.getUUIDFromString(NAME_D), NAME_D, val, AttributeModifier.Operation.ADDITION));
		return map;
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity) {
		return super.damageItem(stack, 1, entity);
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LangData.IDS.SHULKERATE_TOOL.get().withStyle(ChatFormatting.GRAY));
	}

	@Override
	public boolean hideWithEffect() {
		return true;
	}

}
