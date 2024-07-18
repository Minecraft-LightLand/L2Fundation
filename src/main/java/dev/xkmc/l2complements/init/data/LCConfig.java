package dev.xkmc.l2complements.init.data;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class LCConfig {

	public static class Client {

		public final ModConfigSpec.BooleanValue renderEnchOverlay;
		public final ModConfigSpec.IntValue enchOverlayZVal;

		Client(ModConfigSpec.Builder builder) {
			renderEnchOverlay = builder.comment("Render enchantment character overlay")
					.define("renderEnchOverlay", true);
			enchOverlayZVal = builder.comment("The height of enchantment character overlay")
					.defineInRange("enchOverlayZVal", 250, -1000000, 1000000);
		}

	}

	public static class Common {

		public final ModConfigSpec.DoubleValue windSpeed;
		public final ModConfigSpec.IntValue belowVoid;
		public final ModConfigSpec.IntValue phantomHeight;
		public final ModConfigSpec.IntValue explosionDamage;
		public final ModConfigSpec.IntValue spaceDamage;
		public final ModConfigSpec.BooleanValue allowModBanSpaceShard;
		public final ModConfigSpec.BooleanValue enableImmunityEnchantments;
		public final ModConfigSpec.IntValue cleansePredicate;

		public final ModConfigSpec.IntValue totemicHealDuration;
		public final ModConfigSpec.IntValue totemicHealAmount;
		public final ModConfigSpec.IntValue eternalTotemCoolDown;
		public final ModConfigSpec.BooleanValue eternalTotemGiveWarpStone;

		public final ModConfigSpec.DoubleValue windSweepIncrement;

		public final ModConfigSpec.IntValue soulFireChargeDuration;
		public final ModConfigSpec.IntValue blackFireChargeDuration;
		public final ModConfigSpec.IntValue strongFireChargePower;
		public final ModConfigSpec.BooleanValue strongFireChargeBreakBlock;

		public final ModConfigSpec.DoubleValue emeraldDamageFactor;
		public final ModConfigSpec.IntValue emeraldBaseRange;
		public final ModConfigSpec.IntValue sonicShooterDamage;
		public final ModConfigSpec.IntValue hellfireWandDamage;

		public final ModConfigSpec.IntValue iceEnchantDuration;
		public final ModConfigSpec.IntValue flameEnchantDuration;
		public final ModConfigSpec.IntValue bleedEnchantDuration;
		public final ModConfigSpec.IntValue curseEnchantDuration;
		public final ModConfigSpec.IntValue bleedEnchantMax;
		public final ModConfigSpec.DoubleValue voidTouchChance;
		public final ModConfigSpec.DoubleValue voidTouchChanceBonus;
		public final ModConfigSpec.DoubleValue lifeSyncFactor;

		public final ModConfigSpec.DoubleValue mobTypeBonus;

		public final ModConfigSpec.IntValue treeChopMaxRadius;
		public final ModConfigSpec.IntValue treeChopMaxHeight;
		public final ModConfigSpec.IntValue treeChopMaxBlock;
		public final ModConfigSpec.IntValue chainDiggingDelayThreshold;
		public final ModConfigSpec.IntValue chainDiggingBlockPerTick;
		public final ModConfigSpec.DoubleValue chainDiggingHardnessRange;
		public final ModConfigSpec.BooleanValue delayDiggingRequireEnder;

		public final ModConfigSpec.BooleanValue useArsNouveauForEnchantmentRecipe;
		public final ModConfigSpec.BooleanValue enableVanillaItemRecipe;
		public final ModConfigSpec.BooleanValue enableToolRecraftRecipe;
		public final ModConfigSpec.BooleanValue enableSpawnEggRecipe;

		Common(ModConfigSpec.Builder builder) {
			useArsNouveauForEnchantmentRecipe = builder.comment("When Ars Nouveau is present, use apparatus recipe for enchantments")
					.define("useArsNouveauForEnchantmentRecipe", true);
			enableVanillaItemRecipe = builder.comment("Allow vanilla items such as elytra and ancient debris to be crafted with L2Complements materials")
					.define("enableVanillaItemRecipe", true);
			enableToolRecraftRecipe = builder.comment("Allow tools to be upgraded from tools with same typ but different materials")
					.define("enableToolRecraftRecipe", true);
			enableSpawnEggRecipe = builder.comment("Allow spawn eggs to be crafted with L2Complements materials")
					.define("enableSpawnEggRecipe", true);
			builder.push("materials");
			{
				windSpeed = builder.comment("Requirement for obtaining Captured Wind. Unit: Block per Tick")
						.defineInRange("windSpeed", 10, 0.1, 100);
				belowVoid = builder.comment("Requirement for void eye drop")
						.defineInRange("belowVoid", 16, 0, 128);
				phantomHeight = builder.comment("Requirement for sun membrane drop")
						.defineInRange("phantomHeight", 200, 0, 10000);
				explosionDamage = builder.comment("Requirement for explosion shard drop")
						.defineInRange("explosionDamage", 80, 1, 10000);
				spaceDamage = builder.comment("Requirement for space shard drop")
						.defineInRange("spaceDamage", 16384, 1, 1000000);
				allowModBanSpaceShard = builder.comment("Allow mods to ban space shard")
						.define("allowModBanSpaceShard", true);
				enableImmunityEnchantments = builder.comment("Enable immunity enchantments")
						.comment("Be sure to inform players when you turn this off")
						.define("enableImmunityEnchantments", true);
			}
			builder.pop();

			builder.push("fire charge");
			{
				soulFireChargeDuration = builder.comment("Soul Fire Charge Duration")
						.defineInRange("soulFireChargeDuration", 60, 1, 10000);
				blackFireChargeDuration = builder.comment("Black Fire Charge Duration")
						.defineInRange("blackFireChargeDuration", 100, 1, 10000);
				strongFireChargePower = builder.comment("Strong Fire Charge Power")
						.defineInRange("strongFireChargePower", 2, 1, 10);
				strongFireChargeBreakBlock = builder.comment("Strong Fire Charge Breaks Block")
						.define("strongFireChargeBreakBlock", true);
			}
			builder.pop();

			builder.push("properties");
			{
				cleansePredicate = builder.comment("Cleanse effect clearing test")
						.comment("0 for clearing everything")
						.comment("1 for clearing neutral and negative only")
						.comment("2 for clearing negative only")
						.defineInRange("cleansePredicate", 0, 0, 2);
				eternalTotemCoolDown = builder.comment("Cool down of Eternal Totem of Dream")
						.defineInRange("eternalTotemCoolDown", 2400, 0, 10000);
				eternalTotemGiveWarpStone = builder.comment("Whether Eternal Totem of Dream gives you warp stone")
						.define("eternalTotemGiveWarpStone", true);
				totemicHealDuration = builder.comment("Totemic Armor healing interval")
						.defineInRange("totemicHealDuration", 100, 1, 1000);
				totemicHealAmount = builder.comment("Totemic Armor healing amount")
						.defineInRange("totemicHealAmount", 1, 1, 1000);
				windSweepIncrement = builder.comment("Wind Sweep enchantment increment to sweep hit box")
						.defineInRange("windSweepIncrement", 1, 0.1, 100);
				emeraldDamageFactor = builder.comment("Damage factor of emerald splash")
						.defineInRange("emeraldDamageFactor", 0.5, 0.001, 1000);
				emeraldBaseRange = builder.comment("Base range for emerald splash")
						.defineInRange("emeraldBaseRange", 10, 1, 100);
				sonicShooterDamage = builder.comment("Sonic Shooter Damage")
						.defineInRange("sonicShooterDamage", 10, 1, 1000);
				hellfireWandDamage = builder.comment("Hellfire Wand Damage")
						.defineInRange("hellfireWandDamage", 10, 1, 1000);
				iceEnchantDuration = builder.comment("Base duration for iceBlade")
						.defineInRange("iceEnchantDuration", 100, 1, 10000);
				flameEnchantDuration = builder.comment("Duration for flameBlade")
						.defineInRange("flameEnchantDuration", 60, 1, 10000);
				bleedEnchantDuration = builder.comment("Base duration for sharpBlade")
						.defineInRange("bleedEnchantDuration", 80, 1, 10000);
				curseEnchantDuration = builder.comment("Base duration for cursedBlade")
						.defineInRange("curseEnchantDuration", 100, 1, 10000);
				bleedEnchantMax = builder.comment("Max effect level for sharpBlade")
						.defineInRange("bleedEnchantMax", 3, 1, 10000);
				voidTouchChance = builder.comment("Void Touch chance for true damage")
						.defineInRange("voidTouchChance", 0.05, 0, 1);
				voidTouchChanceBonus = builder.comment("Void Touch chance for true damage if bypass armor or magic")
						.defineInRange("voidTouchChanceBonus", 0.5, 0, 1);
				mobTypeBonus = builder.comment("Bonus damage factor for specific materials against specific mob types")
						.defineInRange("mobTypeBonus", 1d, 0, 1000);
				lifeSyncFactor = builder.comment("Damage factor for lifeSync (damage to user per durability cost)")
						.defineInRange("lifeSyncFactor", 1d, 0, 1000);


				treeChopMaxRadius = builder.comment("Max radius for blocks to be considered for tree chopping, except upward direction")
						.defineInRange("treeChopMaxRadius", 16, 0, 32);
				treeChopMaxHeight = builder.comment("Max height for blocks to be considered for tree chopping.")
						.defineInRange("treeChopMaxHeight", 256, 0, 512);
				treeChopMaxBlock = builder.comment("Max number of blocks to be considered for tree chopping.")
						.defineInRange("treeChopMaxBlock", 1024, 0, 16384);

				chainDiggingDelayThreshold = builder.comment("Max number of blocks to break before resort to delayed breaking")
						.defineInRange("chainDiggingDelayThreshold", 64, 1, 1024);
				chainDiggingBlockPerTick = builder.comment("Max number of blocks to break per tick in delayed breaking")
						.defineInRange("chainDiggingBlockPerTick", 16, 1, 1024);
				chainDiggingHardnessRange = builder.comment("Max hardness blocks to break may have, as a factor of the hardness of the block broken.")
						.comment("Apotheosis implementation of chain digging use 3 has their hardness factor.")
						.defineInRange("chainDiggingHardnessRange", 3d, 1, 100);
				delayDiggingRequireEnder = builder.comment("Delayed breaking requires Ender Transport to take effect to reduce lag")
						.define("delayDiggingRequireEnder", true);
			}
			builder.pop();

		}

	}

	public static final ModConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	public static final ModConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	public static String COMMON_PATH;

	static {
		final Pair<Client, ModConfigSpec> client = new ModConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();

		final Pair<Common, ModConfigSpec> common = new ModConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}

	public static void init() {
		register(ModConfig.Type.CLIENT, CLIENT_SPEC);
		COMMON_PATH = register(ModConfig.Type.SERVER, COMMON_SPEC);
	}

	private static String register(ModConfig.Type type, IConfigSpec spec) {
		var mod = ModLoadingContext.get().getActiveContainer();
		String path = "l2_configs/" + mod.getModId() + "-" + type.extension() + ".toml";
		mod.registerConfig(type, spec, path);
		return path;
	}

}
