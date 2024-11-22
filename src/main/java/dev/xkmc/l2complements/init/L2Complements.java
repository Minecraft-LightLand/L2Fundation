package dev.xkmc.l2complements.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.l2complements.compat.forbidden.FaARecipe;
import dev.xkmc.l2complements.content.enchantment.special.SoulBoundPlayerData;
import dev.xkmc.l2complements.events.L2ComplementsClick;
import dev.xkmc.l2complements.events.MaterialDamageListener;
import dev.xkmc.l2complements.init.data.*;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2complements.init.registrate.*;
import dev.xkmc.l2complements.network.EmptyRightClickToServer;
import dev.xkmc.l2complements.network.RotateDiggerToServer;
import dev.xkmc.l2damagetracker.contents.attack.AttackEventHandler;
import dev.xkmc.l2damagetracker.contents.materials.vanilla.GenItemVanillaType;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.serial.config.PacketHandlerWithConfig;
import dev.xkmc.l2screentracker.click.quickaccess.DefaultQuickAccessActions;
import dev.xkmc.l2screentracker.compat.arclight.AnvilMenuArclight;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Map;

import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(L2Complements.MODID)
@Mod.EventBusSubscriber(modid = L2Complements.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class L2Complements {

	public static final String MODID = "l2complements";
	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(MODID, "main"), 3,
			e -> e.create(EmptyRightClickToServer.class, PLAY_TO_SERVER),
			e -> e.create(RotateDiggerToServer.class, PLAY_TO_SERVER)
	);
	public static final Logger LOGGER = LogManager.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);
	public static final GenItemVanillaType MATS = new GenItemVanillaType(MODID, REGISTRATE);

	public L2Complements() {
		ForgeMod.enableMilkFluid();
		LCItems.register();
		LCBlocks.register();
		LCEffects.register();
		LCParticle.register();
		LCEnchantments.register();
		LCEntities.register();
		LCRecipes.register();
		LCConfig.init();
		SoulBoundPlayerData.register();
		DamageTypeGen.register();
		new L2ComplementsClick(new ResourceLocation(MODID, "main"));
		AttackEventHandler.register(5000, new MaterialDamageListener());
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::addTranslations);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, TagGen::onBlockTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagGen::onItemTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, TagGen::onEntityTagGen);
		REGISTRATE.addDataGenerator(TagGen.EFF_TAGS, TagGen::onEffectTagGen);
		REGISTRATE.addDataGenerator(TagGen.ENCH_TAGS, TagGen::onEnchTagGen);
	}

	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			LCEffects.registerBrewingRecipe();

			DispenserBlock.registerBehavior(LCItems.SOUL_CHARGE.get(), LCItems.SOUL_CHARGE.get().new FireChargeBehavior());
			DispenserBlock.registerBehavior(LCItems.STRONG_CHARGE.get(), LCItems.STRONG_CHARGE.get().new FireChargeBehavior());
			DispenserBlock.registerBehavior(LCItems.BLACK_CHARGE.get(), LCItems.BLACK_CHARGE.get().new FireChargeBehavior());

			DefaultQuickAccessActions.quickAccess(MenuType.ANVIL, LCBlocks.ETERNAL_ANVIL.asItem(), AnvilMenuArclight::new, "container.repair");
		});
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void gatherData(GatherDataEvent event) {
		boolean run = event.includeServer();
		var gen = event.getGenerator();
		PackOutput output = gen.getPackOutput();
		var pvd = event.getLookupProvider();
		var helper = event.getExistingFileHelper();

		gen.addProvider(run, new LCConfigGen(gen));
		gen.addProvider(run, new LCSpriteSourceProvider(output, helper));

	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void gatherDataAfter(GatherDataEvent event){
		boolean run = event.includeServer();
		var gen = event.getGenerator();
		PackOutput output = gen.getPackOutput();
		var pvd = event.getLookupProvider();
		var helper = event.getExistingFileHelper();

		new DamageTypeGen(output, pvd, helper).generate(run, gen);
		var data = new RegistrySetBuilder()
				.add(Registries.TRIM_MATERIAL, ctx -> Arrays.stream(LCMats.values())
						.forEach(e -> ctx.register(ResourceKey.create(Registries.TRIM_MATERIAL,
										new ResourceLocation(L2Complements.MODID, e.getID())),
								TrimMaterial.create(e.getID(), e.getIngot(), 0,
										e.getIngot().getDescription().copy().withStyle(e.trim_text_color), Map.of()))));
		gen.addProvider(run, new LCDatapackRegistriesGen(output, pvd, data, "L2Complements Data"));

		if (ModList.get().isLoaded(ForbiddenArcanus.MOD_ID))
			gen.addProvider(run, new LCDatapackRegistriesGen(output, pvd, new RegistrySetBuilder()
					.add(FARegistries.RITUAL, FaARecipe::gather), "Forbidden and Arcanus Data"));
	}

}
