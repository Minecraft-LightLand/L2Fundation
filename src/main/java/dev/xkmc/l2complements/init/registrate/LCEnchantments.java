package dev.xkmc.l2complements.init.registrate;

import com.tterrag.registrate.builders.EnchantmentBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2complements.content.enchantment.armors.DurableArmorEnchantment;
import dev.xkmc.l2complements.content.enchantment.armors.FlameThornEnchantment;
import dev.xkmc.l2complements.content.enchantment.armors.IceThornEnchantment;
import dev.xkmc.l2complements.content.enchantment.armors.StableBodyEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.ImmuneEnchantment;
import dev.xkmc.l2complements.content.enchantment.core.SingleLevelEnchantment;
import dev.xkmc.l2complements.content.enchantment.digging.*;
import dev.xkmc.l2complements.content.enchantment.special.LegendaryEnchantment;
import dev.xkmc.l2complements.content.enchantment.special.LifeMendingEnchantment;
import dev.xkmc.l2complements.content.enchantment.special.LifeSyncEnchantment;
import dev.xkmc.l2complements.content.enchantment.special.SoulBindingEnchantment;
import dev.xkmc.l2complements.content.enchantment.weapon.*;
import dev.xkmc.l2library.base.L2Registrate;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import static dev.xkmc.l2complements.init.L2Complements.REGISTRATE;

public class LCEnchantments {

	public static final EnchantmentCategory ALL = EnchantmentCategory.create("ALL", e -> e.getMaxStackSize() == 1);

	public static final RegistryEntry<ImmuneEnchantment> ENCH_PROJECTILE, ENCH_FIRE, ENCH_ENVIRONMENT,
			ENCH_EXPLOSION, ENCH_MAGIC, ENCH_INVINCIBLE, ENCH_MATES;

	public static final RegistryEntry<SingleLevelEnchantment> SHULKER_ARMOR, ENDER_MASK, SHINNY,
			SNOW_WALKER, DAMPENED, ENDER, SMELT, SAFEGUARD;

	public static final RegistryEntry<StableBodyEnchantment> STABLE_BODY;
	public static final RegistryEntry<LifeSyncEnchantment> LIFE_SYNC;
	public static final RegistryEntry<LifeMendingEnchantment> LIFE_MENDING;
	public static final RegistryEntry<LegendaryEnchantment> ETERNAL;
	public static final RegistryEntry<LegendaryEnchantment> HARDENED;
	public static final RegistryEntry<WindSweepEnchantment> WIND_SWEEP;
	public static final RegistryEntry<SoulBindingEnchantment> SOUL_BOUND;
	public static final RegistryEntry<IceBladeEnchantment> ICE_BLADE;
	public static final RegistryEntry<SoulFlameBladeEnchantment> FLAME_BLADE;
	public static final RegistryEntry<CurseBladeEnchantment> CURSE_BLADE;
	public static final RegistryEntry<SharpBladeEnchantment> SHARP_BLADE;
	public static final RegistryEntry<IceThornEnchantment> ICE_THORN;
	public static final RegistryEntry<FlameThornEnchantment> FLAME_THORN;
	public static final RegistryEntry<DurableArmorEnchantment> DURABLE_ARMOR;
	public static final RegistryEntry<VoidTouchEnchantment> VOID_TOUCH;

	public static final RegistryEntry<RangeDiggingEnchantment> CUBIC, PLANE, DRILL, VIEN, TREE,
			CHUNK_CUBIC, CHUNK_PLANE;

	static {

		{

			SHULKER_ARMOR = reg("shulker_armor", ALL, SingleLevelEnchantment::new,
					"Armor invisible to mobs and players when wearer has invisibility effect.")
					.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).lang("Transparent")
					.register();

			ENDER_MASK = reg("ender_mask", EnchantmentCategory.ARMOR_HEAD, SingleLevelEnchantment::new,
					"Endermen won't be mad at you for direct eye contact")
					.addSlots(EquipmentSlot.HEAD).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

			SHINNY = reg("shinny", EnchantmentCategory.WEARABLE, SingleLevelEnchantment::new,
					"Piglins loves it.")
					.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

			SNOW_WALKER = reg("snow_walker", EnchantmentCategory.ARMOR_FEET, SingleLevelEnchantment::new,
					"Allow Wearer to walk on powdered snow.")
					.addSlots(EquipmentSlot.FEET).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

			STABLE_BODY = reg("stable_body", EnchantmentCategory.ARMOR_CHEST, StableBodyEnchantment::new,
					"Player won't be knocked back when wearing chestplate with this enchantment.")
					.addSlots(EquipmentSlot.CHEST).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

			ENDER = reg("ender_reach", EnchantmentCategory.DIGGER, SingleLevelEnchantment::new,
					"Teleport mined items to inventory if possible.")
					.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).register();

			DURABLE_ARMOR = reg("durable_armor", EnchantmentCategory.ARMOR, DurableArmorEnchantment::new,
					"Armor will have higher durability. Conflict with Unbreaking.")
					.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).lang("Durable Armor").register();

			SMELT = reg("smelt", EnchantmentCategory.DIGGER, SingleLevelEnchantment::new,
					"Smelt mined items if possible, including items in chests!")
					.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).register();

			LIFE_MENDING = reg("life_mending", EnchantmentCategory.BREAKABLE, LifeMendingEnchantment::new,
					"When healing, cost heal amount to repair item first.")
					.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

			LIFE_SYNC = reg("life_sync", EnchantmentCategory.BREAKABLE, LifeSyncEnchantment::new,
					"Cost health instead of durability when possible. May kill the user")
					.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

			SAFEGUARD = reg("safeguard", EnchantmentCategory.BREAKABLE, SingleLevelEnchantment::new,
					"when item has more than 1 durability, it will keep at least 1 durability if damaged")
					.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

			HARDENED = reg("hardened", EnchantmentCategory.BREAKABLE, LegendaryEnchantment::new,
					"Durability loss will be capped to 1.")
					.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

			DAMPENED = reg("dampened", EnchantmentCategory.WEARABLE, SingleLevelEnchantment::new,
					"When wearing 4 pieces of armors with dampened effect, cancel all vibrations emitted by wearer.")
					.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).register();

			SOUL_BOUND = reg("soul_bound", ALL, SoulBindingEnchantment::new,
					"Remain in inventory after death.")
					.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

			ETERNAL = reg("eternal", EnchantmentCategory.BREAKABLE, LegendaryEnchantment::new,
					"Item will ignore all durability damage.")
					.addSlots(EquipmentSlot.values()).rarity(Enchantment.Rarity.VERY_RARE).lang("Eternal (Creative)").register();

		}

		{
			WIND_SWEEP = reg("wind_sweep", EnchantmentCategory.WEAPON, WindSweepEnchantment::new,
					"Increase sweeping hit box")
					.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();

			ICE_BLADE = reg("ice_blade", EnchantmentCategory.WEAPON, IceBladeEnchantment::new,
					"Apply freezing effect to target. Higher levels have longer duration.")
					.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).register();

			FLAME_BLADE = reg("soul_flame_blade", EnchantmentCategory.WEAPON, SoulFlameBladeEnchantment::new,
					"Apply flame effect to target. Higher levels have higher damage.")
					.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).register();

			CURSE_BLADE = reg("cursed_blade", EnchantmentCategory.WEAPON, CurseBladeEnchantment::new,
					"Apply cursed effect to target. Higher levels have longer duration.")
					.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).register();

			SHARP_BLADE = reg("sharp_blade", EnchantmentCategory.WEAPON, SharpBladeEnchantment::new,
					"Stack bleeding effect to target. Higher levels have higher stack cap.")
					.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).register();

			ICE_THORN = reg("ice_thorn", EnchantmentCategory.ARMOR, IceThornEnchantment::new,
					"Apply freezing effect to attacker. Higher levels have longer duration.")
					.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).register();

			FLAME_THORN = reg("soul_flame_thorn", EnchantmentCategory.ARMOR, FlameThornEnchantment::new,
					"Apply flame effect to attacker. Higher levels have higher damage.")
					.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).register();

			VOID_TOUCH = reg("void_touch", EnchantmentCategory.WEAPON, VoidTouchEnchantment::new,
					"Have a small chance to deal true damage. Chance increase significantly if the damage bypasses armor or magic already.")
					.addSlots(EquipmentSlot.MAINHAND).rarity(Enchantment.Rarity.VERY_RARE).register();
		}

		{
			ENCH_PROJECTILE =
					regImmune("projectile_reject", "Projectile Reject",
							"Deflects all projectiles. Make wearer immune to projectile damage.");
			ENCH_FIRE =
					regImmune("fire_reject", "Fire Immune",
							"Make wearer immune to fire damage.");
			ENCH_ENVIRONMENT =
					regImmune("environment_reject", "Environmental Damage Immune",
							"Make wearer immune to damage without attacker.");
			ENCH_EXPLOSION =
					regImmune("explosion_reject", "Explosion Immune",
							"Make wearer immune to explosion damage.");
			ENCH_MAGIC =
					regImmune("magic_reject", "Magic Immune",
							"Make wearer immune to magic damage.");
			ENCH_INVINCIBLE =
					regImmune("invincible", "Invincible (Creative)",
							"Player is invincible to all damage.");
			ENCH_MATES =
					regImmune("owner_protection", "Owner Protection",
							"Negate all damages from entities owned by you.");
		}

		{
			CUBIC = reg("cubic_digging", EnchantmentCategory.DIGGER, (r, c, s) ->
							new RangeDiggingEnchantment(new CubicBlockBreaker(1), r, c, s),
					"Dig 3x3x3 blocks at once. Higher level increase radius. Sneak to bypass")
					.addSlots(EquipmentSlot.MAINHAND)
					.rarity(Enchantment.Rarity.VERY_RARE).lang("Cubic Digging")
					.register();

			PLANE = reg("plane_digging", EnchantmentCategory.DIGGER, (r, c, s) ->
							new RangeDiggingEnchantment(new PlaneBlockBreaker(2), r, c, s),
					"Dig 5x5 blocks at once. Higher level increase radius. Sneak to bypass")
					.addSlots(EquipmentSlot.MAINHAND)
					.rarity(Enchantment.Rarity.VERY_RARE).lang("Plane Digging")
					.register();

			DRILL = reg("drill_digging", EnchantmentCategory.DIGGER, (r, c, s) ->
							new RangeDiggingEnchantment(new DrillBlockBreaker(7), r, c, s),
					"Dig 7 blocks at once. Higher level increase range. Sneak to bypass")
					.addSlots(EquipmentSlot.MAINHAND)
					.rarity(Enchantment.Rarity.VERY_RARE).lang("Drill Digging")
					.register();


			VIEN = reg("vien_mining", EnchantmentCategory.DIGGER, (r, c, s) ->
							new RangeDiggingEnchantment(new OreDigger(7, 8), r, c, s),
					"Dig connected blocks of the same type. Higher level increase max count. Sneak to bypass")
					.addSlots(EquipmentSlot.MAINHAND)
					.rarity(Enchantment.Rarity.VERY_RARE).lang("Vien Mining")
					.register();


			TREE = reg("tree_chopping", EnchantmentCategory.DIGGER, (r, c, s) ->
							new RangeDiggingEnchantment(new TreeDigger(), r, c, s),
					"Chop logs and adjacent leaves. Level 2 cleans leaves as well, and doesn't cost durability when breaking leaves. Sneak to bypass")
					.addSlots(EquipmentSlot.MAINHAND)
					.rarity(Enchantment.Rarity.VERY_RARE).lang("Tree Chopper")
					.register();

			CHUNK_CUBIC = reg("cubic_eater", EnchantmentCategory.DIGGER, (r, c, s) ->
							new RangeDiggingEnchantment(new CubicChunkBreaker(2), r, c, s),
					"Dig 4x4x4 chunk-aligned blocks at once. Level 2 makes it 8x8x8. Sneak to bypass")
					.addSlots(EquipmentSlot.MAINHAND)
					.rarity(Enchantment.Rarity.VERY_RARE).lang("Cubic Chunk Eater")
					.register();

			CHUNK_PLANE = reg("plane_eater", EnchantmentCategory.DIGGER, (r, c, s) ->
							new RangeDiggingEnchantment(new PlaneChunkBreaker(1), r, c, s),
					"Dig 16x16 chunk-aligned blocks at once. Higher level increase depth. Sneak to bypass")
					.addSlots(EquipmentSlot.MAINHAND)
					.rarity(Enchantment.Rarity.VERY_RARE).lang("Planar Chunk Eater")
					.register();

		}
	}

	private static RegistryEntry<ImmuneEnchantment> regImmune(String id, String name, String desc) {
		return reg(id, EnchantmentCategory.ARMOR, ImmuneEnchantment::new, desc)
				.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).lang(name).register();
	}

	private static <T extends Enchantment> EnchantmentBuilder<T, L2Registrate> reg(
			String id, EnchantmentCategory category,
			EnchantmentBuilder.EnchantmentFactory<T> fac, String desc
	) {
		return REGISTRATE.enchantment(id, category, fac, desc);
	}

	public static void register() {
	}

}
