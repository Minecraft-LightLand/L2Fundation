package dev.xkmc.l2complements.init.registrate;

import dev.xkmc.l2complements.content.entity.fireball.BlackFireball;
import dev.xkmc.l2complements.content.entity.fireball.SoulFireball;
import dev.xkmc.l2complements.content.entity.fireball.StrongFireball;
import dev.xkmc.l2complements.content.item.create.RefinedRadianceItem;
import dev.xkmc.l2complements.content.item.create.ShadowSteelItem;
import dev.xkmc.l2complements.content.item.misc.*;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LCMats;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.model.generators.ModelFile;
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

	public static final ItemEntry<TooltipItem> WIND_BOTTLE;
	public static final ItemEntry<TooltipItem> VOID_EYE;
	public static final ItemEntry<TooltipItem> SUN_MEMBRANE;
	public static final ItemEntry<TooltipItem> SOUL_FLAME;
	public static final ItemEntry<TooltipItem> CAPTURED_WIND;
	public static final ItemEntry<TooltipItem> CAPTURED_BULLET;
	public static final ItemEntry<TooltipItem> EXPLOSION_SHARD;
	public static final ItemEntry<TooltipItem> HARD_ICE;
	public static final ItemEntry<TooltipItem> STORM_CORE;
	public static final ItemEntry<TooltipItem> BLACKSTONE_CORE;
	public static final ItemEntry<TooltipItem> RESONANT_FEATHER;
	public static final ItemEntry<TooltipItem> SPACE_SHARD;
	public static final ItemEntry<TooltipItem> WARDEN_BONE_SHARD;
	public static final ItemEntry<BurntItem> EMERALD;
	public static final ItemEntry<BurntItem> CURSED_DROPLET;
	public static final ItemEntry<SpecialRenderItem> FORCE_FIELD;


	public static final ItemEntry<WarpStone> FRAGILE_WARP_STONE;
	public static final ItemEntry<WarpStone> REINFORCED_WARP_STONE;
	public static final ItemEntry<HomeTotem> TOTEM_OF_DREAM;
	public static final ItemEntry<PoseiditeTotem> TOTEM_OF_THE_SEA;

	public static final ItemEntry<FireChargeItem<SoulFireball>> SOUL_CHARGE;
	public static final ItemEntry<FireChargeItem<StrongFireball>> STRONG_CHARGE;
	public static final ItemEntry<FireChargeItem<BlackFireball>> BLACK_CHARGE;

	public static final ItemEntry<Item>[] MAT_INGOTS, MAT_NUGGETS;
	public static final ItemEntry<Item>[][] GEN_ITEM;

	static {

		MAT_INGOTS = L2Complements.MATS.genMats(LCMats.values(), "ingot", Tags.Items.INGOTS);
		MAT_NUGGETS = L2Complements.MATS.genMats(LCMats.values(), "nugget", Tags.Items.NUGGETS);
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
			WARDEN_BONE_SHARD = simpleItem("warden_bone_shard", TooltipItem::new, Rarity.RARE, LangData.IDS.WARDEN_BONE_SHARD::get);
			EMERALD = REGISTRATE.item("heirophant_green", p -> new BurntItem(p.fireResistant().rarity(Rarity.EPIC))).defaultModel().defaultLang().register();
			CURSED_DROPLET = REGISTRATE.item("cursed_droplet", p -> new BurntItem(p.fireResistant().rarity(Rarity.RARE))).defaultModel().defaultLang().register();
			FORCE_FIELD = REGISTRATE.item("force_field", p -> new SpecialRenderItem(p.fireResistant().rarity(Rarity.EPIC), LangData.IDS.FORCE_FIELD::get))
					.model((ctx, pvd) -> pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("builtin/entity")))
					.defaultLang().register();
		}
		{
			FRAGILE_WARP_STONE = REGISTRATE.item("fragile_warp_stone", p ->
							new WarpStone(p.fireResistant().stacksTo(1).rarity(Rarity.RARE), true))
					.defaultModel().defaultLang().register();
			REINFORCED_WARP_STONE = REGISTRATE.item("reinforced_warp_stone", p ->
							new WarpStone(p.fireResistant().stacksTo(1).durability(64).rarity(Rarity.RARE), false))
					.defaultModel().defaultLang().register();
			TOTEM_OF_DREAM = REGISTRATE.item("totem_of_dream", p ->
							new HomeTotem(p.fireResistant().stacksTo(1).rarity(Rarity.EPIC)))
					.defaultModel().defaultLang().register();
			TOTEM_OF_THE_SEA = REGISTRATE.item("totem_of_the_sea", p ->
							new PoseiditeTotem(p.fireResistant().stacksTo(16).rarity(Rarity.EPIC)))
					.defaultModel().defaultLang().register();
		}
		{
			SOUL_CHARGE = REGISTRATE.item("soul_fire_charge", p ->
							new FireChargeItem<>(p, SoulFireball::new, SoulFireball::new,
									() -> LangData.IDS.EFFECT_CHARGE.get(getTooltip(
											new MobEffectInstance(LCEffects.FLAME.get(),
													LCConfig.COMMON.soulFireChargeDuration.get())))))
					.defaultModel().defaultLang().register();

			STRONG_CHARGE = REGISTRATE.item("strong_fire_charge", p ->
							new FireChargeItem<>(p, StrongFireball::new, StrongFireball::new,
									() -> LangData.IDS.EXPLOSION_CHARGE.get(LCConfig.COMMON.strongFireChargePower.get())))
					.defaultModel().defaultLang().register();

			BLACK_CHARGE = REGISTRATE.item("black_fire_charge", p ->
							new FireChargeItem<>(p, BlackFireball::new, BlackFireball::new,
									() -> LangData.IDS.EFFECT_CHARGE.get(getTooltip(
											new MobEffectInstance(LCEffects.STONE_CAGE.get(),
													LCConfig.COMMON.blackFireChargeDuration.get())))))
					.defaultModel().defaultLang().register();
		}
		GEN_ITEM = L2Complements.MATS.genItem(LCMats.values());
	}

	public static MutableComponent getTooltip(MobEffectInstance eff) {
		MutableComponent comp = Component.translatable(eff.getDescriptionId());
		MobEffect mobeffect = eff.getEffect();
		if (eff.getAmplifier() > 0) {
			comp = Component.translatable("potion.withAmplifier", comp,
					Component.translatable("potion.potency." + eff.getAmplifier()));
		}
		if (eff.getDuration() > 20) {
			comp = Component.translatable("potion.withDuration", comp,
					MobEffectUtil.formatDuration(eff, 1));
		}
		return comp.withStyle(mobeffect.getCategory().getTooltipFormatting());
	}

	public static <T extends Item> ItemEntry<T> simpleItem(String id, BiFunction<Item.Properties, Supplier<MutableComponent>, T> func, Rarity r, Supplier<MutableComponent> sup) {
		return REGISTRATE.item(id, p -> func.apply(p.fireResistant().rarity(r), sup)).defaultModel().defaultLang().register();
	}

	public static void register() {
	}

}
