package dev.xkmc.l2foundation.init;

import dev.xkmc.l2foundation.events.*;
import dev.xkmc.l2foundation.init.data.GenItem;
import dev.xkmc.l2foundation.init.data.LFConfig;
import dev.xkmc.l2foundation.init.data.LangData;
import dev.xkmc.l2foundation.init.data.RecipeGen;
import dev.xkmc.l2foundation.init.registrate.*;
import dev.xkmc.l2foundation.network.NetworkManager;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.base.effects.EffectSyncEvents;
import dev.xkmc.l2library.init.events.attack.AttackEventHandler;
import dev.xkmc.l2library.repack.registrate.providers.ProviderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
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
@Mod(L2Foundation.MODID)
public class L2Foundation {

	public static final String MODID = "l2foundation";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);
	public static final GenItem MATS = new GenItem(MODID, REGISTRATE);

	private static void registerRegistrates() {
		ForgeMod.enableMilkFluid();
		LFBlocks.register();
		LFItems.register();
		LFEffects.register();
		LFParticle.register();
		LFEnchantments.register();
		NetworkManager.register();
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::addTranslations);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
	}

	private static void registerForgeEvents() {
		LFConfig.init();
		MinecraftForge.EVENT_BUS.register(ItemUseEventHandler.class);
		MinecraftForge.EVENT_BUS.register(MaterialEventHandler.class);
		MinecraftForge.EVENT_BUS.register(EnchantmentEventHandler.class);
		AttackEventHandler.LISTENERS.add(new ToolDamageListener());
		AttackEventHandler.LISTENERS.add(new MaterialDamageListener());
	}

	private static void registerModBusEvents(IEventBus bus) {
		bus.addListener(L2Foundation::setup);
		bus.addListener(EventPriority.LOWEST, L2Foundation::gatherData);
		bus.addListener(L2Foundation::onParticleRegistryEvent);
	}

	public L2Foundation() {
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		IEventBus bus = ctx.getModEventBus();
		registerModBusEvents(bus);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> L2FoundationClient.onCtorClient(bus, MinecraftForge.EVENT_BUS));
		registerRegistrates();
		registerForgeEvents();
	}

	private static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			EffectSyncEvents.TRACKED.add(LFEffects.FLAME.get());
			EffectSyncEvents.TRACKED.add(LFEffects.EMERALD.get());
			EffectSyncEvents.TRACKED.add(LFEffects.ICE.get());
			LFEffects.registerBrewingRecipe();
		});
	}

	public static void gatherData(GatherDataEvent event) {
	}

	public static void onParticleRegistryEvent(RegisterParticleProvidersEvent event) {
		LFParticle.registerClient();
	}

}
