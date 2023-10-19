package dev.xkmc.l2complements.compat;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import javax.annotation.Nullable;

public class ApotheosisLoopCompat {

	public static int loop = 0;

	public static int getTagEnchantmentLevel(Enchantment pEnchantment, ItemStack pStack) {
		if (!pStack.isEmpty()) {
			ResourceLocation id = getEnchantmentId(pEnchantment);
			ListTag list = pStack.getEnchantmentTags();

			for (int i = 0; i < list.size(); ++i) {
				CompoundTag tag = list.getCompound(i);
				ResourceLocation ench = getEnchantmentId(tag);
				if (ench != null && ench.equals(id)) {
					return getEnchantmentLevel(tag);
				}
			}
		}
		return 0;
	}

	@Nullable
	public static ResourceLocation getEnchantmentId(CompoundTag pCompoundTag) {
		return ResourceLocation.tryParse(pCompoundTag.getString("id"));
	}

	@Nullable
	public static ResourceLocation getEnchantmentId(Enchantment pEnchantment) {
		return Registry.ENCHANTMENT.getKey(pEnchantment);
	}

	public static int getEnchantmentLevel(CompoundTag pCompoundTag) {
		return Mth.clamp(pCompoundTag.getInt("lvl"), 0, 255);
	}

}
