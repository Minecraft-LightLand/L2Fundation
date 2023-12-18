package dev.xkmc.l2complements.init;

import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.curseofpandora.event.PandoraAttackListener;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.registrate.CoPFakeEffects;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2complements.content.enchantment.special.SoulBoundPlayerData;
import dev.xkmc.l2complements.events.L2ComplementsClick;
import dev.xkmc.l2complements.events.MaterialDamageListener;
import dev.xkmc.l2complements.init.data.*;
import dev.xkmc.l2complements.init.registrate.*;
import dev.xkmc.l2complements.network.EmptyRightClickToServer;
import dev.xkmc.l2complements.network.TotemUseToClient;
import dev.xkmc.l2damagetracker.contents.attack.AttackEventHandler;
import dev.xkmc.l2damagetracker.contents.materials.vanilla.GenItemVanillaType;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.init.events.EffectSyncEvents;
import dev.xkmc.l2library.serial.config.PacketHandlerWithConfig;
import dev.xkmc.l2screentracker.click.quickaccess.DefaultQuickAccessActions;
import dev.xkmc.l2screentracker.compat.arclight.AnvilMenuArclight;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(L2Complements.MODID)
@Mod.EventBusSubscriber(modid = L2Complements.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class L2Complements {

	public static final String MODID = "l2complements";
	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(MODID, "main"), 2,
			e -> e.create(EmptyRightClickToServer.class, PLAY_TO_SERVER),
			e -> e.create(TotemUseToClient.class, PLAY_TO_CLIENT)
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
		CoPItems.register();
		CoPFakeEffects.register();
		LCRecipes.register();
		LCConfig.init();
		SoulBoundPlayerData.register();
		new L2ComplementsClick(new ResourceLocation(MODID, "main"));
		AttackEventHandler.register(5000, new MaterialDamageListener());
		AttackEventHandler.register(5200, new PandoraAttackListener());
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::addTranslations);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, TagGen::onBlockTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagGen::onItemTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, TagGen::onEntityTagGen);
		REGISTRATE.addDataGenerator(TagGen.EFF_TAGS, TagGen::onEffectTagGen);
	}

	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			EffectSyncEvents.TRACKED.add(LCEffects.FLAME.get());
			EffectSyncEvents.TRACKED.add(LCEffects.EMERALD.get());
			EffectSyncEvents.TRACKED.add(LCEffects.ICE.get());
			EffectSyncEvents.TRACKED.add(LCEffects.STONE_CAGE.get());
			EffectSyncEvents.TRACKED.add(LCEffects.BLEED.get());
			EffectSyncEvents.TRACKED.add(LCEffects.CLEANSE.get());
			EffectSyncEvents.TRACKED.add(LCEffects.CURSE.get());
			LCEffects.registerBrewingRecipe();

			DispenserBlock.registerBehavior(LCItems.SOUL_CHARGE.get(), LCItems.SOUL_CHARGE.get().new FireChargeBehavior());
			DispenserBlock.registerBehavior(LCItems.STRONG_CHARGE.get(), LCItems.STRONG_CHARGE.get().new FireChargeBehavior());
			DispenserBlock.registerBehavior(LCItems.BLACK_CHARGE.get(), LCItems.BLACK_CHARGE.get().new FireChargeBehavior());

			DefaultQuickAccessActions.quickAccess(MenuType.ANVIL, LCBlocks.ETERNAL_ANVIL.asItem(), AnvilMenuArclight::new, "container.repair");
		});
	}

	@SubscribeEvent
	public static void modifyAttributes(EntityAttributeModificationEvent event) {
		CurseOfPandora.modifyAttributes(event);
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		boolean gen = event.includeServer();
		PackOutput output = event.getGenerator().getPackOutput();
		var pvd = event.getLookupProvider();
		var helper = event.getExistingFileHelper();
		new DamageTypeGen(output, pvd, helper).generate(gen, event.getGenerator());
		event.getGenerator().addProvider(gen, new LCConfigGen(event.getGenerator()));
	}

}
