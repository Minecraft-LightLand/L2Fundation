package dev.xkmc.l2complements.content.item.equipments;

import com.google.common.collect.Multimap;
import dev.xkmc.l2complements.content.item.generic.ExtraArmorConfig;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import java.util.List;
import java.util.Locale;

public class PoseiditeArmor extends ExtraArmorConfig {

	private static final String KEY = "UserInWater", NAME_ARMOR = "neptunium_armor", NAME_TOUGH = "neptunium_toughness", NAME_SPEED = "neptunium_speed", NAME_SWIM = "neptunium_swim";

	private static final AttributeModifier[] ARMOR = makeModifiers(NAME_ARMOR, 4d, 6d, AttributeModifier.Operation.ADDITION);
	private static final AttributeModifier[] TOUGH = makeModifiers(NAME_TOUGH, 2d, 3d, AttributeModifier.Operation.ADDITION);
	private static final AttributeModifier[] SPEED = makeModifiers(NAME_SPEED, 0.1d, 0.15d, AttributeModifier.Operation.MULTIPLY_BASE);
	private static final AttributeModifier[] SWIM = makeModifiers(NAME_SWIM, 0.1d, 0.15d, AttributeModifier.Operation.MULTIPLY_BASE);

	private static AttributeModifier[] makeModifiers(String name, double val, double val2, AttributeModifier.Operation op) {
		AttributeModifier[] ans = new AttributeModifier[4];
		for (int i = 0; i < 4; i++) {
			EquipmentSlot slot = EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, i);
			double v = slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS ? val2 : val;
			String str = name + "/" + slot.getName().toLowerCase(Locale.ROOT);
			ans[i] = new AttributeModifier(MathHelper.getUUIDFromString(str), str, v, op);
		}
		return ans;
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		super.onArmorTick(stack, world, player);
		stack.getOrCreateTag().putBoolean(KEY, player.isInWaterRainOrBubble());
		if (!player.isInWaterRainOrBubble()) return;
		EquipmentSlot slot = LivingEntity.getEquipmentSlotForItem(stack);
		if (slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST) {
			EffectUtil.refreshEffect(player, new MobEffectInstance(MobEffects.CONDUIT_POWER, 200), EffectUtil.AddReason.SELF, player);
		}
		if (slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET) {
			EffectUtil.refreshEffect(player, new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 200), EffectUtil.AddReason.SELF, player);
		}
	}

	@Override
	public Multimap<Attribute, AttributeModifier> modify(Multimap<Attribute, AttributeModifier> map, EquipmentSlot slot, ItemStack stack) {
		if (stack.getOrCreateTag().getBoolean(KEY)) {
			map.put(Attributes.ARMOR, ARMOR[slot.getIndex()]);
			map.put(Attributes.ARMOR_TOUGHNESS, TOUGH[slot.getIndex()]);
			map.put(Attributes.MOVEMENT_SPEED, SPEED[slot.getIndex()]);
		}
		map.put(ForgeMod.SWIM_SPEED.get(), SWIM[slot.getIndex()]);
		return map;
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LangData.IDS.POSEIDITE_ARMOR.get().withStyle(ChatFormatting.GRAY));
		super.addTooltip(stack, list);
	}

}
