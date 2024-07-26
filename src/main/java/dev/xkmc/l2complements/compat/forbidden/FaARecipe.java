package dev.xkmc.l2complements.compat.forbidden;

import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import dev.xkmc.l2complements.content.enchantment.core.EnchantmentRecipeBuilder;
import dev.xkmc.l2complements.init.L2Complements;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.*;

public record FaARecipe(EnchantmentRecipeBuilder builder, ResourceLocation id) {

	private static final List<FaARecipe> ALL = new ArrayList<>();

	private static volatile boolean finished = false;

	public static void finish() {
		finished = true;
		L2Complements.LOGGER.info("Finish Recipe Gen");
	}

	public static void saveCompat(EnchantmentRecipeBuilder builder, ResourceLocation loc) {
		ALL.add(new FaARecipe(builder, loc));
	}

	public static void gather(BootstapContext<Ritual> ctx) {
		while (!finished) Thread.onSpinWait();
		for (var rec : ALL) {
			var key = ResourceKey.create(FARegistries.RITUAL, rec.id);
			Map<Character, Integer> map = new LinkedHashMap<>();
			int count = 0;
			Ingredient main = null;
			for (var ss : rec.builder.rows) {
				for (int i = 0; i < ss.length(); i++) {
					char ch = ss.charAt(i);
					var ing = rec.builder.key.get(ch);
					if (ing != null) {
						if (rec.builder.rows.get(1) == ss && i == 1) {
							main = ing;
							continue;
						}
						count++;
						map.compute(ch, (k, v) -> (v == null ? 0 : v) + 1);
					}
				}
			}
			List<RitualInput> list = new ArrayList<>();
			for (var e : map.entrySet()) {
				char ch = e.getKey();
				var ing = rec.builder.key.get(ch);
				if (ing != null) {
					int total = e.getValue();
					while (total > 0) {
						int max = 1;
						if (ing.getItems().length > 0)
							max = ing.getItems()[0].getMaxStackSize();
						int val = Math.min(total, max);
						list.add(new RitualInput(ing, val));
						total -= val;
					}
				}

			}
			list.sort(Comparator.comparing(e -> e.ingredient().toJson().toString()));
			ctx.register(key, new Ritual(list, main,
					new CreateItemResult(EnchantedBookItem.createForEnchantment(
							new EnchantmentInstance(rec.builder.enchantment, rec.builder.level))),
					new EssencesDefinition(1100, 2, 2300, 10), null,
					MagicCircle.Config.DEFAULT
			));
		}
	}


}
