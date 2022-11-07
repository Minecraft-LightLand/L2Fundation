package dev.xkmc.l2foundation.content.item.equipments;

import com.google.common.collect.Multimap;
import dev.xkmc.l2foundation.content.item.generic.ExtraArmorConfig;
import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Locale;

public class NeptuniumArmor extends ExtraArmorConfig {

	private static final String KEY = "UserInWater", NAME_ARMOR = "neptonium_armor", NAME_TOUGH = "neptonium_toughness", NAME_SPEED = "neptonium_speed";

	private static final AttributeModifier[] ARMOR = makeModifiers(NAME_ARMOR, 4d, 6d, AttributeModifier.Operation.ADDITION);
	private static final AttributeModifier[] TOUGH = makeModifiers(NAME_TOUGH, 2d, 3d, AttributeModifier.Operation.ADDITION);
	private static final AttributeModifier[] SPEED = makeModifiers(NAME_SPEED, 1.1d, 1.15d, AttributeModifier.Operation.MULTIPLY_BASE);

	private static AttributeModifier[] makeModifiers(String name, double val, double val2, AttributeModifier.Operation op) {
		AttributeModifier[] ans = new AttributeModifier[4];
		for (int i = 0; i < 4; i++) {
			EquipmentSlot slot = EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, i);
			double v = slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS ? val : val2;
			String str = name + "/" + slot.getName().toLowerCase(Locale.ROOT);
			ans[i] = new AttributeModifier(MathHelper.getUUIDFromString(str), str, v, op);
		}
		return ans;
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		stack.getOrCreateTag().putBoolean(KEY, player.isInWaterRainOrBubble());
		if (!player.isInWaterRainOrBubble()) return;
		EquipmentSlot slot = stack.getEquipmentSlot();
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
		return map;
	}
}
