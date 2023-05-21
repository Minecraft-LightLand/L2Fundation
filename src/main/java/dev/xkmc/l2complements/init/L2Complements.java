package dev.xkmc.l2complements.init;

import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.l2complements.events.*;
import dev.xkmc.l2complements.init.data.*;
import dev.xkmc.l2complements.init.registrate.*;
import dev.xkmc.l2complements.network.NetworkManager;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.init.events.attack.AttackEventHandler;
import dev.xkmc.l2library.init.events.click.quickaccess.DefaultQuickAccessActions;
import dev.xkmc.l2library.init.events.listeners.EffectSyncEvents;
import dev.xkmc.l2library.init.materials.vanilla.GenItemVanillaType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(L2Complements.MODID)
public class L2Complements {

	public static final String MODID = "l2complements";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);
	public static final GenItemVanillaType MATS = new GenItemVanillaType(MODID, REGISTRATE);

	public static final L2ComplementsClick CLICK = new L2ComplementsClick(new ResourceLocation(MODID, "main"));

	private static void registerRegistrates(IEventBus bus) {
		ForgeMod.enableMilkFluid();
		LCItems.register();
		LCBlocks.register();
		LCEffects.register();
		LCParticle.register();
		LCEnchantments.register();
		LCEntities.register();
		LCRecipes.register(bus);
		NetworkManager.register();
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::addTranslations);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, TagGen::onBlockTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagGen::onItemTagGen);
	}

	private static void registerForgeEvents() {
		LCConfig.init();
		MinecraftForge.EVENT_BUS.register(ItemUseEventHandler.class);
		MinecraftForge.EVENT_BUS.register(MaterialEventHandler.class);
		MinecraftForge.EVENT_BUS.register(MagicEventHandler.class);
		MinecraftForge.EVENT_BUS.register(SpecialEquipmentEvents.class);
		AttackEventHandler.register(5000, new MaterialDamageListener());
	}

	private static void registerModBusEvents(IEventBus bus) {
		bus.addListener(L2Complements::setup);
		bus.addListener(EventPriority.LOWEST, L2Complements::gatherData);
	}

	public L2Complements() {
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		IEventBus bus = ctx.getModEventBus();
		registerModBusEvents(bus);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> L2ComplementsClient.onCtorClient(bus, MinecraftForge.EVENT_BUS));
		registerRegistrates(bus);
		registerForgeEvents();
	}

	private static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			EffectSyncEvents.TRACKED.add(LCEffects.FLAME.get());
			EffectSyncEvents.TRACKED.add(LCEffects.EMERALD.get());
			EffectSyncEvents.TRACKED.add(LCEffects.ICE.get());
			EffectSyncEvents.TRACKED.add(LCEffects.STONE_CAGE.get());
			LCEffects.registerBrewingRecipe();

			DispenserBlock.registerBehavior(LCItems.SOUL_CHARGE.get(), LCItems.SOUL_CHARGE.get().new FireChargeBehavior());
			DispenserBlock.registerBehavior(LCItems.STRONG_CHARGE.get(), LCItems.STRONG_CHARGE.get().new FireChargeBehavior());
			DispenserBlock.registerBehavior(LCItems.BLACK_CHARGE.get(), LCItems.BLACK_CHARGE.get().new FireChargeBehavior());

			DefaultQuickAccessActions.quickAccess(MenuType.ANVIL, LCBlocks.ETERNAL_ANVIL.asItem(), AnvilMenu::new, "container.repair");
		});
	}

	public static void gatherData(GatherDataEvent event) {
		boolean gen = event.includeServer();
		PackOutput output = event.getGenerator().getPackOutput();
		var pvd = event.getLookupProvider();
		var helper = event.getExistingFileHelper();
		new DamageTypeGen(output, pvd, helper).generate(gen, event.getGenerator());
		event.getGenerator().addProvider(gen, new LCConfigGen(event.getGenerator()));
	}

}
