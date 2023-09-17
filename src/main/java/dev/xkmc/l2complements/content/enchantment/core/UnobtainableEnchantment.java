package dev.xkmc.l2complements.content.enchantment.core;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2library.base.L2Registrate;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class UnobtainableEnchantment extends Enchantment implements CraftableEnchantment {

	public static ItemStack makeBook(Enchantment ench, int level) {
		return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(ench, level));
	}

	public static void injectTab(L2Registrate reg, EnchantmentCategory... cats) {
		reg.modifyCreativeModeTab(LCItems.TAB_ENCHMIN.getKey(), m -> {
			Set<EnchantmentCategory> set = Set.of(cats);
			getSorted().stream()
					.filter(e -> e.allowedInCreativeTab(Items.ENCHANTED_BOOK, set))
					.forEach((e) -> e.getCraftableLevels().forEach(i -> m.accept(makeBook(e, i),
							CreativeModeTab.TabVisibility.PARENT_TAB_ONLY)));
		});

		reg.modifyCreativeModeTab(LCItems.TAB_ENCHMAX.getKey(), m -> {
			Set<EnchantmentCategory> set = Set.of(cats);
			getSorted().stream()
					.filter(e -> e.allowedInCreativeTab(Items.ENCHANTED_BOOK, set))
					.forEach((e) -> m.accept(makeBook(e, e.getMaxLevel()), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY));
		});
	}

	private static List<UnobtainableEnchantment> getSorted() {
		Map<String, List<UnobtainableEnchantment>> map = new TreeMap<>();
		Set<String> ids = new LinkedHashSet<>();
		for (var e : CACHE) {
			ResourceLocation id = ForgeRegistries.ENCHANTMENTS.getKey(e);
			assert id != null;
			map.computeIfAbsent(id.getNamespace(), k -> new ArrayList<>()).add(e);
		}
		ids.add(L2Complements.MODID);
		ids.addAll(map.keySet());
		List<UnobtainableEnchantment> ans = new ArrayList<>();
		for (String s : ids) {
			ans.addAll(map.get(s));
		}
		return ans;
	}

	private final static List<UnobtainableEnchantment> CACHE = new ArrayList<>();

	protected UnobtainableEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
		synchronized (CACHE) {
			CACHE.add(this);
		}
	}

	@Override
	public int getMinCost(int lv) {
		return 200;
	}

	@Override
	public int getMaxCost(int lv) {
		return 150;
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}

	@Override
	public boolean isTradeable() {
		return false;
	}

	@Override
	public boolean isDiscoverable() {
		return false;
	}

	@Override
	public boolean isAllowedOnBooks() {
		return true;
	}

	public ChatFormatting getColor() {
		return ChatFormatting.GREEN;
	}

	@Override
	public Component getFullname(int lv) {
		MutableComponent component = Component.translatable(this.getDescriptionId());
		if (lv != 1 || this.getMaxLevel() != 1) {
			component.append(" ").append(Component.translatable("enchantment.level." + lv));
		}
		component.withStyle(getColor());
		return component;
	}

	public boolean allowedInCreativeTab(Item book, Set<EnchantmentCategory> tab) {
		return tab.contains(LCEnchantments.ALL);
	}

}
