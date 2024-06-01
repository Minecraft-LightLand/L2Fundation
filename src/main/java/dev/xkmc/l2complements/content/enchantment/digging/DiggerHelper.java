package dev.xkmc.l2complements.content.enchantment.digging;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DiggerHelper {

	private static final String KEY = "l2complements:selected_digger";

	@Nullable
	public static Pair<RangeDiggingEnchantment, Integer> getDigger(ItemStack stack) {
		if (stack.isEmpty() || !stack.isEnchanted()) return null;
		var root = stack.getTag();
		if (root == null) return null;
		String str = root.getString(KEY);
		if (!ResourceLocation.isValidResourceLocation(str)) return null;
		var e = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(str));
		if (!(e instanceof RangeDiggingEnchantment ench)) return null;
		int lv = stack.getEnchantmentLevel(e);
		if (lv <= 0) return null;
		return Pair.of(ench, lv);
	}

	public static void rotateDigger(ItemStack stack, boolean reverse) {
		var current = getDigger(stack);
		List<Enchantment> list = new ArrayList<>(stack.getAllEnchantments().keySet());
		if (reverse) list = Lists.reverse(list);
		for (var ent : list) {
			if (ent instanceof RangeDiggingEnchantment ench) {
				if (current == null) {
					var rl = ForgeRegistries.ENCHANTMENTS.getKey(ench);
					assert rl == null;
					stack.getOrCreateTag().putString(KEY, rl.toString());
					return;
				}
				if (ench == current.getFirst()) {
					current = null;
				}
			}
		}
		stack.getOrCreateTag().remove(KEY);
	}

}
