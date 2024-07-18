package dev.xkmc.l2complements.content.enchantment.digging;

import com.google.common.collect.Lists;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.init.L2LibReg;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DiggerHelper {

	private static final String KEY = "l2complements:selected_digger";

	@Nullable
	private static RangeDiggingEnchantment of(Enchantment ench) {
		var list = ench.getEffects(L2LibReg.LEGACY.get());
		for (var e : list) {
			if (e instanceof RangeDiggingEnchantment digger) {
				return digger;
			}
		}
		return null;
	}

	@Nullable
	public static Digger getDigger(ItemStack stack) {
		if (stack.isEmpty() || !stack.isEnchanted()) return null;
		var reg = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
		if (reg == null) return null;
		var ench = Optional.ofNullable(stack.get(LCItems.DIGGER_SEL))
				.map(ResourceLocation::tryParse)
				.map(e -> reg.getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, e)));
		var list = ench.map(e -> e.value().getEffects(L2LibReg.LEGACY.get()));
		if (ench.isEmpty() || list.get().isEmpty()) return null;
		var legacy = list.get().getFirst();
		if (!(legacy instanceof RangeDiggingEnchantment digger)) return null;
		int lv = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).getLevel(ench.get());
		if (lv <= 0) return null;
		return new Digger(ench.get(), digger, lv);
	}

	public static void rotateDigger(ItemStack stack, boolean reverse) {
		var list = LegacyEnchantment.findAll(stack, RangeDiggingEnchantment.class, true);
		if (reverse) list = Lists.reverse(list);
		var current = getDigger(stack);
		for (var ent : list) {
			if (current == null) {
				LCItems.DIGGER_SEL.set(stack, ent.holder().unwrapKey().orElseThrow().location().toString());
				return;
			}
			if (ent.holder().value() == current.ench().value()) {
				current = null;
			}
		}
		stack.remove(LCItems.DIGGER_SEL.get());
	}

	public record Digger(Holder<Enchantment> ench, RangeDiggingEnchantment digger, int level) {
	}

}
