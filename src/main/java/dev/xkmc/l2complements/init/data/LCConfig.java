package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.events.LCAttackListener;
import dev.xkmc.l2complements.events.MagicEventHandler;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.util.ConfigInit;
import net.neoforged.neoforge.common.ModConfigSpec;

public class LCConfig {

	public static class Client extends ConfigInit {

		public final ModConfigSpec.BooleanValue renderEnchOverlay;

		public Client(Builder builder) {
			markL2();
			renderEnchOverlay = builder
					.text("Render L2 enchantment book overlay")
					.comment("Render a colored char on enchantment book to tell it apart from vanilla ones")
					.define("renderEnchOverlay", true);
		}
	}

	public static class Recipe extends ConfigInit {

		public final ModConfigSpec.BooleanValue useArsNouveauForEnchantmentRecipe;
		public final ModConfigSpec.BooleanValue enableVanillaItemRecipe;
		public final ModConfigSpec.BooleanValue enableToolRecraftRecipe;
		public final ModConfigSpec.BooleanValue enableSpawnEggRecipe;

		public Recipe(Builder builder) {
			markL2();

			builder.push("recipe", "Recipe Toggles");
			{
				useArsNouveauForEnchantmentRecipe = builder
						.text("Ars Nouveau: Apparatus Enchanting")
						.comment("When Ars Nouveau is present, use apparatus recipe for enchantments")
						.define("useArsNouveauForEnchantmentRecipe", true);
				enableVanillaItemRecipe = builder
						.text("Extra recipes for vanilla items")
						.comment("Allow vanilla items such as elytra and ancient debris to be crafted with L2Complements materials")
						.define("enableVanillaItemRecipe", true);
				enableToolRecraftRecipe = builder
						.text("Tool upgrade recipes")
						.comment("Allow tools to be upgraded from tools with same type but different materials")
						.define("enableToolRecraftRecipe", true);
				enableSpawnEggRecipe = builder
						.text("Spawn egg recipes")
						.comment("Allow spawn eggs to be crafted with L2Complements materials")
						.define("enableSpawnEggRecipe", true);
			}
			builder.pop();
		}
	}

	public static class Server extends ConfigInit {

		public final ModConfigSpec.DoubleValue windSpeed;
		public final ModConfigSpec.IntValue belowVoid;
		public final ModConfigSpec.IntValue phantomHeight;
		public final ModConfigSpec.IntValue explosionDamage;
		public final ModConfigSpec.IntValue spaceDamage;
		public final ModConfigSpec.EnumValue<LCAttackListener.SpaceShardCondition> spaceShardDrop;
		public final ModConfigSpec.BooleanValue enableImmunityEnchantments;
		public final ModConfigSpec.EnumValue<MagicEventHandler.CleanseTest> cleansePredicate;

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


		Server(Builder builder) {
			markL2();

			builder.push("materials", "Material Drop Conditions");
			{
				windSpeed = builder
						.text("Captured Wind speed requirement")
						.comment("Requirement for obtaining Captured Wind. Unit: Block per Tick")
						.defineInRange("windSpeed", 10, 0.1, 100);
				belowVoid = builder
						.text("Void Eye depth requirement")
						.comment("Blocks below min build height for void eye drop")
						.defineInRange("belowVoid", 16, 0, 128);
				phantomHeight = builder
						.text("Sun Membrane height requirement")
						.comment("Blocks above max build height for sun membrane drop")
						.defineInRange("phantomHeight", 200, 0, 10000);
				explosionDamage = builder
						.text("Explosion Shard damage requirement")
						.defineInRange("explosionDamage", 80, 1, 10000);
				spaceDamage = builder
						.text("Space Shard damage requirement")
						.defineInRange("spaceDamage", 16384, 1, Integer.MAX_VALUE);
				spaceShardDrop = builder.text("Space Shard Toggle")
						.comment("- Allow: Space Shard is enabled")
						.comment("- Default: When certain mods are installed, Space Shard will be banned")
						.comment("- Deny: Space Shard is banned")
						.defineEnum("spaceShardDrop", LCAttackListener.SpaceShardCondition.DEFAULT);
			}
			builder.pop();

			builder.push("fire_charge", "Fire Charge");
			{
				soulFireChargeDuration = builder.text("Soul Fire Charge effect duration in ticks")
						.defineInRange("soulFireChargeDuration", 60, 1, 10000);
				blackFireChargeDuration = builder.text("Black Fire Charge effect duration in ticks")
						.defineInRange("blackFireChargeDuration", 100, 1, 10000);
				strongFireChargePower = builder.text("Strong Fire Charge Power")
						.comment("Power: 2 for Ghast, 3 for Creeper, 4 for TNT, 5 for beds, 6 for End Crystal")
						.defineInRange("strongFireChargePower", 2, 1, 10);
				strongFireChargeBreakBlock = builder.text("Strong Fire Charge Breaks Block")
						.define("strongFireChargeBreakBlock", true);
			}
			builder.pop();

			builder.push("properties", "Item / Effect Properties");
			{

				cleansePredicate = builder.text("Cleanse effect clearing test")
						.comment("- ALL: for clearing everything")
						.comment("- EXCEPT_BENEFICIAL: for clearing neutral and negative only")
						.comment("- HARMFUL_ONLY: for clearing negative only")
						.defineEnum("cleansePredicate", MagicEventHandler.CleanseTest.ALL);

				emeraldDamageFactor = builder.text("Damage factor of emerald splash")
						.defineInRange("emeraldDamageFactor", 0.5, 0.001, 1000);
				emeraldBaseRange = builder.text("Base range for emerald splash")
						.defineInRange("emeraldBaseRange", 10, 1, 100);


				eternalTotemCoolDown = builder.text("Cool down of Eternal Totem of Dream")
						.defineInRange("eternalTotemCoolDown", 2400, 0, 10000);
				eternalTotemGiveWarpStone = builder.text("Eternal Totem of Dream giving you warp stone")
						.define("eternalTotemGiveWarpStone", true);

				totemicHealDuration = builder.text("Totemic Armor healing interval in ticks")
						.defineInRange("totemicHealDuration", 100, 1, 1000);
				totemicHealAmount = builder.text("Totemic Armor healing amount")
						.defineInRange("totemicHealAmount", 1, 1, 1000);

				mobTypeBonus = builder.text("Bonus damage factor for effective targets")
						.comment("This is for weapons made with totemic gold and poseidite.",
								"When weapons with above materials is used against mob types they are effective for",
								"This number determines the bonus in percentage of original damage"
						).defineInRange("mobTypeBonus", 1d, 0, 1000);

				sonicShooterDamage = builder.text("Sonic Shooter Damage")
						.defineInRange("sonicShooterDamage", 10, 1, 1000);
				hellfireWandDamage = builder.text("Hellfire Wand Damage")
						.defineInRange("hellfireWandDamage", 10, 1, 1000);

			}
			builder.pop();

			builder.push("enchantments", "Enchantment Properties");
			{
				enableImmunityEnchantments = builder.text("Enable immunity enchantments")
						.comment("Be sure to inform players when you turn this off")
						.define("enableImmunityEnchantments", true);
				windSweepIncrement = builder.text("Wind Sweep: Hit box increment per level")
						.defineInRange("windSweepIncrement", 1, 0.1, 100);
				lifeSyncFactor = builder.text("Life Sync: Damage to user per durability cost")
						.defineInRange("lifeSyncFactor", 1d, 0, 1000);
				iceEnchantDuration = builder.text("Freezing Blade: Base effect duration in ticks")
						.defineInRange("iceEnchantDuration", 100, 1, 10000);
				flameEnchantDuration = builder.text("Hell Fire Blade: effect duration in ticks")
						.defineInRange("flameEnchantDuration", 60, 1, 10000);
				bleedEnchantDuration = builder.text("Sharp Blade: Base effect duration in ticks")
						.defineInRange("bleedEnchantDuration", 80, 1, 10000);
				curseEnchantDuration = builder.text("Cursed Blade: Base effect duration in ticks")
						.defineInRange("curseEnchantDuration", 100, 1, 10000);
				bleedEnchantMax = builder.text("Sharp Blade: Max effect level per enchantment level")
						.defineInRange("bleedEnchantMax", 3, 1, 10000);
				voidTouchChance = builder.text("Void Touch: Chance for true damage")
						.defineInRange("voidTouchChance", 0.05, 0, 1);
				voidTouchChanceBonus = builder.text("Void Touch: extra chance when damage already bypass armor or magic")
						.defineInRange("voidTouchChanceBonus", 0.5, 0, 1);
			}
			builder.pop();

			builder.push("enchantment_digging", "Digging Enchantment Properties");
			{

				treeChopMaxRadius = builder.text("Tree Chopping max radius")
						.comment("Max radius for blocks to be considered for tree chopping, except upward direction")
						.defineInRange("treeChopMaxRadius", 16, 0, 32);
				treeChopMaxHeight = builder.text("Tree Chopping max height")
						.comment("Max height for blocks to be considered for tree chopping.")
						.defineInRange("treeChopMaxHeight", 256, 0, 512);
				treeChopMaxBlock = builder.text("Tree Chopping max block")
						.comment("Max number of blocks to be considered for tree chopping.")
						.defineInRange("treeChopMaxBlock", 1024, 0, 16384);

				chainDiggingDelayThreshold = builder.text("One-shot breaking max block count")
						.comment("Max number of blocks to break before resort to delayed breaking")
						.defineInRange("chainDiggingDelayThreshold", 64, 1, 1024);
				chainDiggingBlockPerTick = builder.text("Delayed breaking: blocks per tick")
						.comment("Max number of blocks to break per tick in delayed breaking")
						.defineInRange("chainDiggingBlockPerTick", 16, 1, 1024);
				chainDiggingHardnessRange = builder.text("Allowed hardness multiplier")
						.comment("Max hardness blocks to break may have, as a factor of the hardness of the block broken.")
						.comment("Apotheosis implementation of chain digging use 3 has their hardness factor.")
						.defineInRange("chainDiggingHardnessRange", 3d, 1, 100);
				delayDiggingRequireEnder = builder.text("Delayed Breaking: requires Ender Transport")
						.comment("Delayed breaking requires Ender Transport to take effect to reduce lag")
						.define("delayDiggingRequireEnder", true);
			}
			builder.pop();

		}

	}

	public static final Client CLIENT = L2Complements.REGISTRATE.registerClient(Client::new);
	public static final Recipe RECIPE = L2Complements.REGISTRATE.registerUnsynced(Recipe::new);
	public static final Server SERVER = L2Complements.REGISTRATE.registerSynced(Server::new);

	public static void init() {
	}

}
