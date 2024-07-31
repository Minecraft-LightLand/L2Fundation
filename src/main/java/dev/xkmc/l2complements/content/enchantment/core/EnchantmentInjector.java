package dev.xkmc.l2complements.content.enchantment.core;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.Comparator;
import java.util.function.Function;

public class EnchantmentInjector {

	public static void injectTab(L2Registrate reg) {
		reg.modifyCreativeModeTab(LCItems.TAB_ENCHMIN.key(), m ->
				genEnch(m, m.getParameters().holders().lookupOrThrow(Registries.ENCHANTMENT), e -> 1,
						CreativeModeTab.TabVisibility.PARENT_TAB_ONLY));

		reg.modifyCreativeModeTab(LCItems.TAB_ENCHMAX.key(), m ->
				genEnch(m, m.getParameters().holders().lookupOrThrow(Registries.ENCHANTMENT), Enchantment::getMaxLevel,
						CreativeModeTab.TabVisibility.PARENT_TAB_ONLY));

	}

	private static void genEnch(
			CreativeModeTab.Output output, HolderLookup<Enchantment> reg,
			Function<Enchantment, Integer> func, CreativeModeTab.TabVisibility vis
	) {
		reg.listElements()
				.map(EnchantmentInjector::sorter)
				.filter(e -> e.priority() >= 0)
				.sorted(Comp.COMP)
				.map(e -> EnchantedBookItem.createForEnchantment(new EnchantmentInstance(e.e(), func.apply(e.e().value()))))
				.forEach(e -> output.accept(e, vis));
	}

	private static Comp sorter(Holder<Enchantment> e) {
		var id = e.unwrapKey().orElseThrow().location();
		var craft = LegacyEnchantment.firstOf(e, LCEnchantments.CRAFT);
		if (craft == null) return new Comp(-1, "", 0, e);
		if (id.getNamespace().equals(L2Complements.MODID))
			return new Comp(0, id.getNamespace(), craft.order(), e);
		return new Comp(1, id.getNamespace(), craft.order(), e);
	}

	private record Comp(int priority, String modid, int order, Holder<Enchantment> e) {

		private static final Comparator<Comp> COMP = Comparator
				.<Comp>comparingInt(e -> e.priority)
				.thenComparing(e -> e.modid)
				.thenComparingInt(e -> e.order);


	}

}
