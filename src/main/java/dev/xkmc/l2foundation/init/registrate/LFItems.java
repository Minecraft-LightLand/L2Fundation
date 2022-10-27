package dev.xkmc.l2foundation.init.registrate;

import dev.xkmc.l2foundation.content.item.create.RefinedRadianceItem;
import dev.xkmc.l2foundation.content.item.create.ShadowSteelItem;
import dev.xkmc.l2foundation.content.item.misc.WindBottle;
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

	public static final ItemEntry<WindBottle> WIND_BOTTLE;
	public static final ItemEntry<ShadowSteelItem> VOID_EYE;
	public static final ItemEntry<RefinedRadianceItem> SUN_MEMBRANE, SOUL_FLAME;
	public static final ItemEntry<Item> CAPTURED_WIND, CAPTURED_BULLET, EXPLOSION_SHARD, HARD_ICE,
			STORM_CORE, BLACKSTONE_CORE, RESONANT_FEATHER, SPACE_SHARD, FORCE_FIELD;

	public static final ItemEntry<Item>[] MAT_INGOTS, MAT_NUGGETS;
	public static final ItemEntry<Item>[][] GEN_ITEM;

	static {

		MAT_INGOTS = L2Foundation.MATS.genMats("ingot");
		MAT_NUGGETS = L2Foundation.MATS.genMats("nugget");
		{
			WIND_BOTTLE = REGISTRATE.item("wind_capture_bottle", WindBottle::new).register(); // tested
			VOID_EYE = REGISTRATE.item("void_eye", ShadowSteelItem::new).register(); // kill aggroed enderman 16 blocks in void
			CAPTURED_WIND = simpleItem("captured_wind"); // player reach 200m/s
			CAPTURED_BULLET = simpleItem("captured_shulker_bullet"); //  capture bullet
			SUN_MEMBRANE = REGISTRATE.item("sun_membrane", RefinedRadianceItem::new).register(); // kill phantom 200 blocks above maximum build height with arrow
			EXPLOSION_SHARD = simpleItem("explosion_shard"); // endure > 80 explosion damage
			HARD_ICE = simpleItem("hard_ice"); // kill drowned with powder snow damage
			SOUL_FLAME = REGISTRATE.item("soul_flame", RefinedRadianceItem::new).register(); // kill hast with soul flame
			STORM_CORE = simpleItem("storm_core"); // kill phantom with explosion
			BLACKSTONE_CORE = simpleItem("blackstone_core"); // kill guardian with stone cage effect
			RESONANT_FEATHER = simpleItem("resonant_feather"); // let chicken survive sonic boom
			SPACE_SHARD = simpleItem("space_shard"); // deal 500 arrow damage
			FORCE_FIELD = simpleItem("force_field"); //kill wither with arrow
		}
		GEN_ITEM = L2Foundation.MATS.genItem();
	}

	public static ItemEntry<Item> simpleItem(String id) {
		return REGISTRATE.item(id, Item::new).defaultModel().defaultLang().register();
	}

	public static void register() {
	}

}
