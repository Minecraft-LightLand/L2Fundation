package dev.xkmc.l2complements.init;

import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.l2complements.events.L2ComplementsClick;
import dev.xkmc.l2complements.events.LCAttackListener;
import dev.xkmc.l2complements.init.data.*;
import dev.xkmc.l2complements.init.registrate.*;
import dev.xkmc.l2complements.network.EmptyRightClickToServer;
import dev.xkmc.l2complements.network.RotateDiggerToServer;
import dev.xkmc.l2core.init.L2TagGen;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2core.init.reg.simple.Reg;
import dev.xkmc.l2core.serial.config.PacketHandlerWithConfig;
import dev.xkmc.l2damagetracker.contents.attack.AttackEventHandler;
import dev.xkmc.l2damagetracker.contents.materials.vanilla.GenItemVanillaType;
import dev.xkmc.l2menustacker.click.quickaccess.DefaultQuickAccessActions;
import dev.xkmc.l2menustacker.compat.arclight.AnvilMenuArclight;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static dev.xkmc.l2serial.network.PacketHandler.NetDir.PLAY_TO_SERVER;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(L2Complements.MODID)
@EventBusSubscriber(modid = L2Complements.MODID, bus = EventBusSubscriber.Bus.MOD)
public class L2Complements {

	public static final String MODID = "l2complements";
	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			MODID, 3,
			e -> e.create(EmptyRightClickToServer.class, PLAY_TO_SERVER),
			e -> e.create(RotateDiggerToServer.class, PLAY_TO_SERVER)
	);
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Reg REG = new Reg(MODID);
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);
	public static final GenItemVanillaType MATS = new GenItemVanillaType(MODID, REGISTRATE);

	public L2Complements() {
		LCItems.register();
		LCBlocks.register();
		LCEffects.register();
		LCParticle.register();
		LCEnchantments.register();
		LCEntities.register();
		LCRecipes.register();
		LCConfig.init();
		new L2ComplementsClick(loc("main"));
		AttackEventHandler.register(5000, new LCAttackListener());
	}

	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			DispenserBlock.registerBehavior(LCItems.SOUL_CHARGE.get(), LCItems.SOUL_CHARGE.get().new FireChargeBehavior());
			DispenserBlock.registerBehavior(LCItems.STRONG_CHARGE.get(), LCItems.STRONG_CHARGE.get().new FireChargeBehavior());
			DispenserBlock.registerBehavior(LCItems.BLACK_CHARGE.get(), LCItems.BLACK_CHARGE.get().new FireChargeBehavior());

			DefaultQuickAccessActions.quickAccess(MenuType.ANVIL, LCBlocks.ETERNAL_ANVIL.asItem(), AnvilMenuArclight::new, "container.repair");
		});
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void gatherData(GatherDataEvent event) {
		REGISTRATE.addDataGenerator(ProviderType.LANG, LCLang::addTranslations);
		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, LCTagGen::onBlockTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, LCTagGen::onItemTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, LCTagGen::onEntityTagGen);
		REGISTRATE.addDataGenerator(L2TagGen.EFF_TAGS, LCTagGen::onEffectTagGen);
		REGISTRATE.addDataGenerator(L2TagGen.ENCH_TAGS, LCTagGen::onEnchTagGen);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, LCRecipeGen::genRecipe);
		REGISTRATE.addDataGenerator(ProviderType.DATA_MAP, LCDataMapGen::onGather);
		var init = REGISTRATE.getDataGenInitializer();
		init.addDependency(ProviderType.DYNAMIC, ProviderType.ITEM_TAGS);
		init.addDependency(ProviderType.RECIPE, L2TagGen.ENCH_TAGS);
		init.add(Registries.TRIM_MATERIAL, LCTrimsGen::build);
		new LCDamageTypes(REGISTRATE).generate();

		boolean run = event.includeServer();
		var gen = event.getGenerator();
		PackOutput output = gen.getPackOutput();
		var pvd = event.getLookupProvider();
		var helper = event.getExistingFileHelper();
		gen.addProvider(run, new LCSpriteSourceProvider(output, pvd, helper));
	}

	public static ResourceLocation loc(String id) {
		return ResourceLocation.fromNamespaceAndPath(MODID, id);
	}

}
