package dev.xkmc.l2complements.content.enchantment.core;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class EnchantmentInjector {

	private static final ConcurrentHashMap<String, Unit> MODS = new ConcurrentHashMap<>();

	public static void register(String modid) {
		MODS.put(modid, Unit.INSTANCE);
	}

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
				.map(e -> Pair.of(sorter(e.key().location()), e))
				.filter(e -> !e.first().isEmpty())
				.sorted(Comparator.comparing(Pair::first))
				.map(e -> EnchantedBookItem.createForEnchantment(new EnchantmentInstance(e.second(), func.apply(e.second().value()))))
				.forEach(e -> output.accept(e, vis));
	}

	private static String sorter(ResourceLocation id) {
		if (!MODS.containsKey(id.getNamespace())) return "";
		if (id.getNamespace().equals(L2Complements.MODID))
			return "0_" + id;
		return "1_" + id;
	}

}
