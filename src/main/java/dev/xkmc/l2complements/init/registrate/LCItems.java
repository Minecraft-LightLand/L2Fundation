package dev.xkmc.l2complements.init.registrate;

import dev.xkmc.l2complements.content.item.create.RefinedRadianceItem;
import dev.xkmc.l2complements.content.item.create.ShadowSteelItem;
import dev.xkmc.l2complements.content.item.misc.TooltipItem;
import dev.xkmc.l2complements.content.item.misc.WindBottle;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.Tags;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import static dev.xkmc.l2complements.init.L2Complements.REGISTRATE;

@SuppressWarnings({"rawtypes", "unsafe"})
@MethodsReturnNonnullByDefault
public class LCItems {

	public static class Tab extends CreativeModeTab {

		private final Supplier<ItemEntry> icon;

		public Tab(String id, Supplier<ItemEntry> icon) {
			super(L2Complements.MODID + "." + id);
			this.icon = icon;
		}

		@Override
		public ItemStack makeIcon() {
			return icon.get().asStack();
		}
	}

	public static final Tab TAB_GENERATED = new Tab("generated", () -> LCItems.GEN_ITEM[0][0]);

	static {
		REGISTRATE.creativeModeTab(() -> TAB_GENERATED);
	}

	public static final ItemEntry<TooltipItem> WIND_BOTTLE, VOID_EYE, SUN_MEMBRANE, SOUL_FLAME,
			CAPTURED_WIND, CAPTURED_BULLET, EXPLOSION_SHARD, HARD_ICE,
			STORM_CORE, BLACKSTONE_CORE, RESONANT_FEATHER, SPACE_SHARD, FORCE_FIELD, WARDEN_BONE_SHARD;

	public static final ItemEntry<Item>[] MAT_INGOTS, MAT_NUGGETS;
	public static final ItemEntry<Item>[][] GEN_ITEM;

	static {

		MAT_INGOTS = L2Complements.MATS.genMats("ingot", Tags.Items.INGOTS);
		MAT_NUGGETS = L2Complements.MATS.genMats("nugget", Tags.Items.NUGGETS);
		{
			WIND_BOTTLE = simpleItem("wind_capture_bottle", WindBottle::new, Rarity.COMMON, LangData.IDS.WIND_BOTTLE::get); // tested
			VOID_EYE = simpleItem("void_eye", ShadowSteelItem::new, Rarity.EPIC, () -> LangData.IDS.VOID_EYE.get(LCConfig.COMMON.belowVoid.get())); // kill aggroed enderman 16 blocks in void
			CAPTURED_WIND = simpleItem("captured_wind", TooltipItem::new, Rarity.RARE, () -> LangData.IDS.CAPTURED_WIND.get(LCConfig.COMMON.windSpeed.get() * 20)); // player reach 200m/s
			CAPTURED_BULLET = simpleItem("captured_shulker_bullet", TooltipItem::new, Rarity.UNCOMMON, LangData.IDS.CAPTURED_BULLET::get); //  capture bullet
			SUN_MEMBRANE = simpleItem("sun_membrane", RefinedRadianceItem::new, Rarity.EPIC, () -> LangData.IDS.SUN_MEMBRANE.get(LCConfig.COMMON.phantomHeight.get())); // kill phantom 200 blocks above maximum build height with arrow
			EXPLOSION_SHARD = simpleItem("explosion_shard", TooltipItem::new, Rarity.UNCOMMON, () -> LangData.IDS.EXPLOSION_SHARD.get(LCConfig.COMMON.explosionDamage.get())); // endure > 80 explosion damage
			HARD_ICE = simpleItem("hard_ice", TooltipItem::new, Rarity.UNCOMMON, LangData.IDS.HARD_ICE::get); // kill drowned with powder snow damage
			SOUL_FLAME = simpleItem("soul_flame", RefinedRadianceItem::new, Rarity.RARE, LangData.IDS.SOUL_FLAME::get); // kill ghast with soul flame
			STORM_CORE = simpleItem("storm_core", TooltipItem::new, Rarity.UNCOMMON, LangData.IDS.STORM_CORE::get); // kill phantom with explosion
			BLACKSTONE_CORE = simpleItem("blackstone_core", TooltipItem::new, Rarity.RARE, LangData.IDS.BLACKSTONE_CORE::get); // kill guardian with stone cage effect
			RESONANT_FEATHER = simpleItem("resonant_feather", TooltipItem::new, Rarity.EPIC, LangData.IDS.RESONANT_FEATHER::get); // let chicken survive sonic boom
			SPACE_SHARD = simpleItem("space_shard", TooltipItem::new, Rarity.EPIC, () -> LangData.IDS.SPACE_SHARD.get(LCConfig.COMMON.spaceDamage.get())); // deal 500 arrow damage
			FORCE_FIELD = simpleItem("force_field", TooltipItem::new, Rarity.EPIC, LangData.IDS.FORCE_FIELD::get); //kill wither with arrow
			WARDEN_BONE_SHARD = simpleItem("warden_bone_shard", TooltipItem::new, Rarity.RARE, LangData.IDS.WARDEN_BONE_SHARD::get);
		}
		GEN_ITEM = L2Complements.MATS.genItem();
	}

	public static ItemEntry<TooltipItem> simpleItem(String id, BiFunction<Item.Properties, Supplier<MutableComponent>, TooltipItem> func, Rarity r, Supplier<MutableComponent> sup) {
		return REGISTRATE.item(id, p -> func.apply(p.fireResistant().rarity(r), sup)).defaultModel().defaultLang().register();
	}

	public static void register() {
	}

}
