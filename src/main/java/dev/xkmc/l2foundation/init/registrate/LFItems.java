package dev.xkmc.l2foundation.init.registrate;

import dev.xkmc.l2foundation.init.L2Foundation;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

import static dev.xkmc.l2foundation.init.L2Foundation.REGISTRATE;

@SuppressWarnings({"rawtypes", "unsafe"})
@MethodsReturnNonnullByDefault
public class LFItems {

	public static class Tab extends CreativeModeTab {

		private final Supplier<ItemEntry> icon;

		public Tab(String id, Supplier<ItemEntry> icon) {
			super(L2Foundation.MODID + "." + id);
			this.icon = icon;
		}

		@Override
		public ItemStack makeIcon() {
			return icon.get().asStack();
		}
	}

	public static final Tab TAB_GENERATED = new Tab("generated", () -> LFItems.GEN_ITEM[0][0]);

	static {
		REGISTRATE.creativeModeTab(() -> TAB_GENERATED);
	}

	// -------- common --------
	public static final ItemEntry<Item>[] MAT_INGOTS, MAT_NUGGETS;
	public static final ItemEntry<Item>[][] GEN_ITEM;

	static {

		MAT_INGOTS = L2Foundation.MATS.genMats("ingot");
		MAT_NUGGETS = L2Foundation.MATS.genMats("nugget");
		GEN_ITEM = L2Foundation.MATS.genItem();
	}

	public static void register() {
	}

}
