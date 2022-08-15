package dev.xkmc.l2foundation.init.registrate;

import dev.xkmc.l2foundation.content.item.equipments.ArmorMat;
import dev.xkmc.l2foundation.content.item.equipments.MedicineArmor;
import dev.xkmc.l2foundation.content.item.equipments.MedicineLeather;
import dev.xkmc.l2foundation.content.questline.item.DispellWaterBottle;
import dev.xkmc.l2foundation.content.questline.item.SlimeTentacleItem;
import dev.xkmc.l2foundation.init.L2Foundation;
import dev.xkmc.l2foundation.init.data.GenItem;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.repack.registrate.builders.ItemBuilder;
import dev.xkmc.l2library.repack.registrate.providers.DataGenContext;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateItemModelProvider;
import dev.xkmc.l2library.repack.registrate.util.entry.EntityEntry;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static dev.xkmc.l2foundation.init.L2Foundation.REGISTRATE;

@SuppressWarnings({"rawtypes", "unchecked", "unsafe"})
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

	public static final Tab TAB_MAIN = new Tab("material", () -> LFItems.DISPELL_DUST);
	public static final Tab TAB_GENERATED = new Tab("generated", () -> LFItems.GEN_ITEM[0][0]);

	static {
		REGISTRATE.creativeModeTab(() -> TAB_MAIN);
	}

	// -------- common --------
	public static final ItemEntry<Item> STRONG_LEATHER, KING_LEATHER, LEAD_INGOT, LEAD_NUGGET;
	public static final ItemEntry<MedicineLeather> MEDICINE_LEATHER, KING_MED_LEATHER;

	public static final ArmorItems<MedicineArmor> MEDICINE_ARMOR, KING_MED_ARMOR;

	public static final ItemEntry<Item> LAYLINE_ORB, CURSED_DROPLET, KNIGHT_SCRAP,
			DISPELL_DUST, OLDROOT, LAYLINE_HEART, ACID_SLIME, DIRTY_SLIME, UNSTABLE_SLIME, BOSS_SLIME,
			GOOD_SOUL, BAD_SOUL, HOLY_POWDER;

	public static final ItemEntry<Item>[] MAT_INGOTS, MAT_NUGGETS;
	public static final ItemEntry<Item>[][] GEN_ITEM;

	public static final ItemEntry<DispellWaterBottle> CLEANSE_WATER_BOTTLE, HOLY_WATER_BOTTLE;
	public static final ItemEntry<SlimeTentacleItem> SLIME_TENTACLE;

	//TODO public static final FluidEntry<VirtualFluid> CLEANSE_WATER, HOLY_WATER;

	static {

		MAT_INGOTS = GenItem.genMats("ingot");
		MAT_NUGGETS = GenItem.genMats("nugget");

		// materials
		{

			LEAD_INGOT = simpleItem("lead_ingot");
			LEAD_NUGGET = simpleItem("lead_nugget");
			STRONG_LEATHER = simpleItem("strong_leather");
			KING_LEATHER = simpleItem("king_leather");
		}
		{

			MEDICINE_LEATHER = REGISTRATE.item("medicine_leather", p -> new MedicineLeather(14, p))
					.color(() -> () -> (stack, val) -> val > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack))
					.defaultModel().defaultLang().register();
			KING_MED_LEATHER = REGISTRATE.item("king_med_leather", p -> new MedicineLeather(100, p))
					.color(() -> () -> (stack, val) -> val > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack))
					.defaultModel().defaultLang().register();
			MEDICINE_ARMOR = genArmor("medicine_leather",
					(slot, p) -> new MedicineArmor(ArmorMat.MEDICINE_LEATHER, slot, p), e -> e.model(LFItems::createDoubleLayerModel)
							.color(() -> () -> (stack, val) -> val > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack)));
			KING_MED_ARMOR = genArmor("king_leather",
					(slot, p) -> new MedicineArmor(ArmorMat.KING_LEATHER, slot, p), e -> e.model(LFItems::createDoubleLayerModel)
							.color(() -> () -> (stack, val) -> val > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack)));
		}
		{
			LAYLINE_ORB = simpleItem("layline_orb", "questline");
			CURSED_DROPLET = simpleItem("cursed_droplet", "questline");
			KNIGHT_SCRAP = simpleItem("knight_scrap", "questline");
			DISPELL_DUST = simpleItem("dispell_dust", "questline");
			OLDROOT = simpleItem("oldroot", "questline");
			LAYLINE_HEART = simpleItem("layline_heart", "questline");
			ACID_SLIME = simpleItem("acid_slime", "questline");
			DIRTY_SLIME = simpleItem("dirty_slime", "questline");
			UNSTABLE_SLIME = simpleItem("unstable_slime", "questline");
			BOSS_SLIME = simpleItem("boss_slime", "questline");
			GOOD_SOUL = simpleItem("good_soul", "questline");
			BAD_SOUL = simpleItem("bad_soul", "questline");
			HOLY_POWDER = simpleItem("holy_powder", "questline");

			CLEANSE_WATER_BOTTLE = REGISTRATE.item("cleanse_water_bottle", p -> new DispellWaterBottle(
							p.craftRemainder(Items.GLASS_BOTTLE).food(new FoodProperties.Builder().nutrition(1).saturationMod(2).alwaysEat().build()).stacksTo(16)))
					.model((ctx, pvd) -> simpleModel(ctx, pvd, "questline")).defaultLang().register();
			HOLY_WATER_BOTTLE = REGISTRATE.item("holy_water_bottle", p -> new DispellWaterBottle(
							p.craftRemainder(Items.GLASS_BOTTLE).food(new FoodProperties.Builder().nutrition(1).saturationMod(2).alwaysEat().build()).stacksTo(16)))
					.model((ctx, pvd) -> simpleModel(ctx, pvd, "questline")).defaultLang().register();
			SLIME_TENTACLE = REGISTRATE.item("slime_tentacle", SlimeTentacleItem::new)
					.model((ctx, pvd) -> simpleModel(ctx, pvd, "questline")).defaultLang().register();

			//TODO CLEANSE_WATER = REGISTRATE.virtualFluid("cleanse_water").defaultLang().register();
			//TODO HOLY_WATER = REGISTRATE.virtualFluid("holy_water").defaultLang().register();
		}
		{
			registerEgg("layline_zombie_spawn_egg", 0, 0, () -> LFEntities.ET_LAYLINE_ZOMBIE);
			registerEgg("layline_skeleton_spawn_egg", 0, 0, () -> LFEntities.ET_LAYLINE_SKELETON);
			registerEgg("cursed_knight_spawn_egg", 0, 0, () -> LFEntities.ET_CURSED_KNIGHT);
			registerEgg("cursed_archer_spawn_egg", 0, 0, () -> LFEntities.ET_CURSED_ARCHER);
			registerEgg("cursed_shield_spawn_egg", 0, 0, () -> LFEntities.ET_CURSED_SHIELD);
			registerEgg("potion_slime_spawn_egg", 0, 0, () -> LFEntities.ET_POTION_SLIME);
			registerEgg("stone_slime_spawn_egg", 0, 0, () -> LFEntities.ET_STONE_SLIME);
			registerEgg("vine_slime_spawn_egg", 0, 0, () -> LFEntities.ET_VINE_SLIME);
			registerEgg("carpet_slime_spawn_egg", 0, 0, () -> LFEntities.ET_CARPET_SLIME);
			registerEgg("boss_slime_spawn_egg", 0, 0, () -> LFEntities.ET_BOSS_SLIME);
		}

		REGISTRATE.creativeModeTab(() -> TAB_GENERATED);
		GEN_ITEM = GenItem.genItem();
	}

	private static void registerEgg(String id, int col_0, int col_1, Supplier<EntityEntry<? extends Mob>> sup) {
		REGISTRATE.item(id,
						p -> new ForgeSpawnEggItem(() -> sup.get().get(), col_0, col_1, p))
				.model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation("item/template_spawn_egg")))
				.defaultLang().register();
	}

	public static void register() {
	}

	public static <T extends ArmorItem> ArmorItems<T> genArmor(String id, BiFunction<EquipmentSlot, Item.Properties, T> sup, Function<ItemBuilder<T, L2Registrate>, ItemBuilder<T, L2Registrate>> func) {
		return new ArmorItems<>(REGISTRATE, id, sup, func);
	}

	public static <T extends Item> void createDoubleLayerModel(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd) {
		ItemModelBuilder builder = pvd.withExistingParent(ctx.getName(), "minecraft:generated");
		String name = ctx.getName();
		var arr = name.toCharArray();
		arr[name.lastIndexOf('_')] = '/';
		name = new String(arr);
		builder.texture("layer0", "item/equipments/" + name);
		builder.texture("layer1", "item/equipments/" + name + "_overlay");
	}

	public static ItemEntry<Item> simpleItem(String id, String path) {
		return REGISTRATE.item(id, Item::new).model((ctx, pvd) -> simpleModel(ctx, pvd, path))
				.defaultLang().register();
	}

	public static ItemEntry<Item> simpleItem(String id) {
		return REGISTRATE.item(id, Item::new).defaultModel().defaultLang().register();
	}

	public static <T extends Item> void simpleModel(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd, String path) {
		pvd.generated(ctx, new ResourceLocation(L2Foundation.MODID, "item/" + path + "/" + ctx.getName()));
	}

	public static class ArmorItems<T extends ArmorItem> {

		public final String prefix;
		public final ItemEntry<T>[] armors = new ItemEntry[4];

		public ArmorItems(L2Registrate reg, String id, BiFunction<EquipmentSlot, Item.Properties, T> sup, Function<ItemBuilder<T, L2Registrate>, ItemBuilder<T, L2Registrate>> func) {
			this.prefix = reg.getModid() + ":" + id;
			armors[0] = func.apply(reg.item(id + "_helmet", p -> sup.apply(EquipmentSlot.HEAD, p))).defaultLang().register();
			armors[1] = func.apply(reg.item(id + "_chestplate", p -> sup.apply(EquipmentSlot.CHEST, p))).defaultLang().register();
			armors[2] = func.apply(reg.item(id + "_leggings", p -> sup.apply(EquipmentSlot.LEGS, p))).defaultLang().register();
			armors[3] = func.apply(reg.item(id + "_boots", p -> sup.apply(EquipmentSlot.FEET, p))).defaultLang().register();
		}

	}

}
