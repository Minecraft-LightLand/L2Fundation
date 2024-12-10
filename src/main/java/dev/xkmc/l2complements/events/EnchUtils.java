package dev.xkmc.l2complements.events;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import javax.annotation.Nullable;

public class EnchUtils {

	@Nullable
	public static ResourceLocation getEnchantmentId(CompoundTag p_182447_) {
		return ResourceLocation.tryParse(p_182447_.getString("id"));
	}

	@Nullable
	public static ResourceLocation getEnchantmentId(Enchantment p_182433_) {
		return BuiltInRegistries.ENCHANTMENT.getKey(p_182433_);
	}

	public static int getEnchantmentLevel(CompoundTag p_182439_) {
		return Mth.clamp(p_182439_.getInt("lvl"), 0, 255);
	}

	public static int getTagEnchantmentLevel(Enchantment p_44844_, ItemStack p_44845_) {
		if (p_44845_.isEmpty()) {
			return 0;
		} else {
			ResourceLocation resourcelocation = getEnchantmentId(p_44844_);
			ListTag listtag = p_44845_.getEnchantmentTags();

			for(int i = 0; i < listtag.size(); ++i) {
				CompoundTag compoundtag = listtag.getCompound(i);
				ResourceLocation resourcelocation1 = getEnchantmentId(compoundtag);
				if (resourcelocation1 != null && resourcelocation1.equals(resourcelocation)) {
					return getEnchantmentLevel(compoundtag);
				}
			}

			return 0;
		}
	}

}
