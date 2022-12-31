package dev.xkmc.l2complements.content.enchantment.special;

import dev.xkmc.l2complements.content.enchantment.core.AttributeEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.SingleLevelEnchantment;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class StableBodyEnchantment extends SingleLevelEnchantment implements AttributeEnchantment {

	public static final UUID ID = MathHelper.getUUIDFromString("stable_body");

	public StableBodyEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}

	@Override
	public void addAttributes(int lv, ItemAttributeModifierEvent event) {
		if (event.getSlotType() == EquipmentSlot.CHEST) {
			event.addModifier(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ID, "stable_body", 1, AttributeModifier.Operation.ADDITION));
		}
	}
	
}
