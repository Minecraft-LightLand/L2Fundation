package dev.xkmc.l2complements.init.registrate;

import dev.xkmc.l2complements.content.enchantment.core.CraftableEnch;
import dev.xkmc.l2complements.content.enchantment.core.LimitValue;
import dev.xkmc.l2complements.content.enchantment.core.SoulBoundPlayerData;
import dev.xkmc.l2complements.content.enchantment.digging.*;
import dev.xkmc.l2complements.content.enchantment.legacy.*;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCTagGen;
import dev.xkmc.l2core.capability.player.PlayerCapabilityNetworkHandler;
import dev.xkmc.l2core.init.reg.ench.*;
import dev.xkmc.l2core.init.reg.simple.AttReg;
import dev.xkmc.l2core.init.reg.simple.AttVal;
import dev.xkmc.l2core.init.reg.simple.CdcReg;
import dev.xkmc.l2core.init.reg.simple.CdcVal;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;

import java.util.List;

import static dev.xkmc.l2complements.init.L2Complements.REGISTRATE;

public class LCEnchantments {

	public static final EnchReg REG = EnchReg.of(L2Complements.REG, REGISTRATE);
	private static final CdcReg<EnchantmentValueEffect> EVE = CdcReg.of(L2Complements.REG, BuiltInRegistries.ENCHANTMENT_VALUE_EFFECT_TYPE);
	public static final CdcVal<LimitValue> EVE_LIMIT = EVE.reg("limit", LimitValue.MAP_CODEC);
	public static final EECVal.Special<CraftableEnch> CRAFT = REG.special("craftable", CraftableEnch.CODEC);

	public static final EnchVal ENDER_MASK;
	public static final EnchVal SHINNY;
	public static final EnchVal SNOW_WALKER;
	public static final EnchVal DAMPENED;
	public static final EnchVal ENDER_TRANSPORT;
	public static final EnchVal SMELT;
	public static final EnchVal STABLE_BODY;
	public static final EnchVal TRANSPARENT;
	public static final EnchVal LIFE_SYNC;
	public static final EnchVal SAFEGUARD;
	public static final EnchVal ETERNAL;

	public static final EnchVal HARDENED;

	public static final EnchVal IMM_PROJECTILE;
	public static final EnchVal IMM_FIRE;
	public static final EnchVal IMM_ENVIRONMENT;
	public static final EnchVal IMM_EXPLOSION;
	public static final EnchVal IMM_MAGIC;
	public static final EnchVal IMM_MATES;
	public static final EnchVal LIFE_MENDING;
	public static final EnchVal WIND_SWEEP;
	public static final EnchVal SOUL_BOUND;
	public static final EnchVal ICE_BLADE;
	public static final EnchVal HELLFIRE_BLADE;
	public static final EnchVal CURSE_BLADE;
	public static final EnchVal SHARP_BLADE;
	public static final EnchVal ICE_THORN;
	public static final EnchVal HELLFIRE_THORN;
	public static final EnchVal DURABLE_ARMOR;
	public static final EnchVal VOID_TOUCH;
	public static final EnchVal CUBIC;
	public static final EnchVal PLANE;
	public static final EnchVal DRILL;
	public static final EnchVal VIEN;
	public static final EnchVal TREE;
	public static final EnchVal CHUNK_CUBIC;
	public static final EnchVal CHUNK_PLANE;
	public static final EnchVal INVINCIBLE;


	public static final AttVal.PlayerVal<SoulBoundPlayerData> ATT_SOULBOUND = AttReg.of(L2Complements.REG).player("soulbound",
			SoulBoundPlayerData.class, SoulBoundPlayerData::new, PlayerCapabilityNetworkHandler::new);

	static {

		EnchColor green = new EnchColor(ChatFormatting.GREEN, ChatFormatting.GRAY);
		EnchColor gold = new EnchColor(ChatFormatting.GOLD, ChatFormatting.GRAY);
		EnchColor purple = new EnchColor(ChatFormatting.LIGHT_PURPLE, ChatFormatting.GRAY);

		Order order = new Order();
		int all = 0xffff9f4f;
		int armor = 0xff4fbfff;
		int weapon = 0xffff4f4f;
		int durable = 0xff7fff7f;
		int util = 0xffffff7f;
		int digging = 0xffafafaf;

		{
			SHINNY = REG.ench("shinny", "Shinny", "Piglins loves it.",
					e -> e.items(ItemTags.EQUIPPABLE_ENCHANTABLE)
							.color(green).special(CRAFT, order.of(util)));

			DAMPENED = REG.ench("dampened", "Dampened", "Cancel all vibrations emitted by wearer",
					e -> e.items(ItemTags.EQUIPPABLE_ENCHANTABLE)
							.color(green).special(CRAFT, order.of(util)));

			TRANSPARENT = REG.ench("transparent", "Transparent", "Items become invisible when player has invisibility effect",
					e -> e.items(HolderSetBuilder.any())
							.color(green).special(CRAFT, order.of(all)));

			SOUL_BOUND = REG.ench("soul_bound", "Soul Bound", "Remain in inventory after death.",
					e -> e.items(HolderSetBuilder.any()).exclusive(Enchantments.VANISHING_CURSE)
							.color(gold).special(CRAFT, order.of(all)));

			ENDER_MASK = REG.ench("ender_mask", "Ender Mask", "Enderman won't be mad at you for direct eye contact",
					e -> e.items(ItemTags.HEAD_ARMOR_ENCHANTABLE)
							.color(green).special(CRAFT, order.of(util)));

			SNOW_WALKER = REG.ench("snow_walker", "Snow Walker", "Allow Wearer to walk on powdered snow.",
					e -> e.items(ItemTags.FOOT_ARMOR_ENCHANTABLE)
							.color(green).special(CRAFT, order.of(util)));

			ENDER_TRANSPORT = REG.ench("ender_transport", "Ender Transport", "Teleport mined items and mob drops to inventory if possible.",
					e -> e.items(LCTagGen.WEAPON_MINING_ENCHANTABLE)
							.color(green).special(CRAFT, order.of(util)));

			SMELT = REG.ench("smelt_touch", "Smelt Touch", "Smelt mined items and mob drops if possible, including items in chests!",
					e -> e.items(LCTagGen.WEAPON_MINING_ENCHANTABLE)
							.color(green).special(CRAFT, order.of(util)));

			LIFE_SYNC = REG.ench("life_sync", "Life Sync", "Cost health instead of durability when possible. May kill the user",
					e -> e.items(ItemTags.DURABILITY_ENCHANTABLE)
							.color(purple).special(CRAFT, order.of(durable)));

			ETERNAL = REG.ench("eternal", "Eternal (Creative Only)", "Item will ignore all durability damage",
					e -> e.items(ItemTags.DURABILITY_ENCHANTABLE)
							.color(gold).special(CRAFT, order.of(durable)));

			SAFEGUARD = REG.ench("safeguard", "Safeguard", "when item has more than 1 durability, it will keep at least 1 durability when damaged",
					e -> e.items(ItemTags.DURABILITY_ENCHANTABLE)
							.color(green).special(CRAFT, order.of(durable)));

			DURABLE_ARMOR = REG.ench("durable_armor", "Durable Armor", "Armor will have higher durability.",
					e -> e.items(HolderSetBuilder.and(List.of(
							HolderSetBuilder.tag(ItemTags.ARMOR_ENCHANTABLE),
							HolderSetBuilder.tag(ItemTags.DURABILITY_ENCHANTABLE)
					))).maxLevel(3).color(green).special(CRAFT, order.of(armor, 3)));

			LIFE_MENDING = REG.ench("life_mending", "Life Mending", "When healing, cost heal amount to repair item durability.",
					e -> e.items(ItemTags.DURABILITY_ENCHANTABLE).maxLevel(3)
							.color(green).special(CRAFT, order.of(durable)));

			HARDENED = REG.ench("hardened", "Hardened", "Durability loss will be capped to 1.",
					e -> e.items(ItemTags.DURABILITY_ENCHANTABLE).effect(b -> b.withEffect(
							EnchantmentEffectComponents.ITEM_DAMAGE, new LimitValue(() -> 1)
					)).color(gold).special(CRAFT, order.of(durable)));

			STABLE_BODY = REG.ench("stable_body", "Stable Body", "Player won't be knocked back when wearing chestplate with this enchantment.",
					e -> e.items(ItemTags.CHEST_ARMOR_ENCHANTABLE).group(EquipmentSlotGroup.CHEST).effect(b -> b.withEffect(
							EnchantmentEffectComponents.ATTRIBUTES,
							new EnchantmentAttributeEffect(e.id, Attributes.KNOCKBACK_RESISTANCE,
									LevelBasedValue.constant(1), AttributeModifier.Operation.ADD_VALUE)
					)).color(green).special(CRAFT, order.of(util)));

		}

		{

			WIND_SWEEP = REG.enchLegacy("wind_sweep", "Wind Sweep", "Increase sweeping hit box by %s",
					e -> e.items(ItemTags.WEAPON_ENCHANTABLE).maxLevel(5)
							.color(green).special(CRAFT, order.of(weapon)),
					WindSweepDesc::new);

			VOID_TOUCH = REG.enchLegacy("void_touch", "Void Touch", "Have %s chance to deal true damage. +%s chance if the damage bypasses armor or magic already.",
					e -> e.items(ItemTags.WEAPON_ENCHANTABLE).maxLevel(3)
							.color(gold).special(CRAFT, order.of(weapon)),
					VoidTouchEnchantment::new);

			ICE_BLADE = REG.enchLegacy("freezing_blade", "Freezing Blade", "Apply %s on target",
					e -> e.items(ItemTags.WEAPON_ENCHANTABLE).maxLevel(3)
							.color(green).special(CRAFT, order.of(weapon)),
					IceBladeEnchantment::new);

			HELLFIRE_BLADE = REG.enchLegacy("hellfire_blade", "Hellfire Blade", "Apply %s on target",
					e -> e.items(ItemTags.WEAPON_ENCHANTABLE).maxLevel(3)
							.color(green).special(CRAFT, order.of(weapon)),
					SoulFlameBladeEnchantment::new);

			CURSE_BLADE = REG.enchLegacy("cursed_blade", "Cursed Blade", "Apply %s on target",
					e -> e.items(ItemTags.WEAPON_ENCHANTABLE).maxLevel(3)
							.color(green).special(CRAFT, order.of(weapon)),
					CurseBladeEnchantment::new);

			SHARP_BLADE = REG.enchLegacy("sharp_blade", "Sharp Blade", "Stack %s on target, up to %s level.",
					e -> e.items(ItemTags.WEAPON_ENCHANTABLE).maxLevel(3)
							.color(green).special(CRAFT, order.of(weapon)),
					SharpBladeEnchantment::new);

			ICE_THORN = REG.enchLegacy("freezing_thorn", "Freezing Thorn", "When attacked, apply %s to attacker.",
					e -> e.items(ItemTags.ARMOR_ENCHANTABLE).group(EquipmentSlotGroup.ARMOR)
							.exclusive(LCTagGen.THORN).tags(LCTagGen.THORN).maxLevel(3)
							.color(green).special(CRAFT, order.of(armor)),
					IceThornEnchantment::new);

			HELLFIRE_THORN = REG.enchLegacy("hellfire_thorn", "Hellfire Thorn", "When attacked, apply %s to attacker.",
					e -> e.items(ItemTags.ARMOR_ENCHANTABLE).group(EquipmentSlotGroup.ARMOR)
							.exclusive(LCTagGen.THORN).tags(LCTagGen.THORN)
							.color(green).special(CRAFT, order.of(armor)),
					FlameThornEnchantment::new);

		}

		{
			IMM_PROJECTILE = REG.ench("projectile_immunity", "Projectile Immunity", "Deflects all projectiles. Make wearer immune to projectile damage.",
					e -> e.items(ItemTags.ARMOR_ENCHANTABLE).exclusive(LCTagGen.IMMUNITY).group(EquipmentSlotGroup.ARMOR)
							.color(gold).special(CRAFT, order.of(armor)));

			IMM_FIRE = REG.ench("fire_immunity", "Fire Immunity", "Puts down fire. Makes wearer immune to fire damage.",
					e -> e.items(ItemTags.ARMOR_ENCHANTABLE).exclusive(LCTagGen.IMMUNITY).group(EquipmentSlotGroup.ARMOR)
							.color(gold).special(CRAFT, order.of(armor)));

			IMM_ENVIRONMENT = REG.ench("environment_immunity", "Environment Immunity", "Makes wearer immune to damage without attacker.",
					e -> e.items(ItemTags.ARMOR_ENCHANTABLE).exclusive(LCTagGen.IMMUNITY).group(EquipmentSlotGroup.ARMOR)
							.color(gold).special(CRAFT, order.of(armor)));

			IMM_EXPLOSION = REG.ench("explosion_immunity", "Explosion Immunity", "Makes wearer immune to explosion damage.",
					e -> e.items(ItemTags.ARMOR_ENCHANTABLE).exclusive(LCTagGen.IMMUNITY).group(EquipmentSlotGroup.ARMOR)
							.color(gold).special(CRAFT, order.of(armor)));

			IMM_MAGIC = REG.ench("magic_immunity", "Magic Immunity", "Makes wearer immune to magic damage.",
					e -> e.items(ItemTags.ARMOR_ENCHANTABLE).exclusive(LCTagGen.IMMUNITY).group(EquipmentSlotGroup.ARMOR)
							.color(gold).special(CRAFT, order.of(armor)));

			INVINCIBLE = REG.ench("invincible", "Invincible (Creative Only)", "Makes wearer immune to all damage.",
					e -> e.items(ItemTags.ARMOR_ENCHANTABLE).exclusive(LCTagGen.IMMUNITY).group(EquipmentSlotGroup.ARMOR)
							.color(gold).special(CRAFT, order.of(armor)));

			IMM_MATES = REG.ench("friendly_fire_protection", "Friendly Fire Protection", "Makes wearer immune to damage from its owner and followers.",
					e -> e.items(ItemTags.ARMOR_ENCHANTABLE).exclusive(LCTagGen.IMMUNITY).group(EquipmentSlotGroup.ARMOR)
							.color(gold).special(CRAFT, order.of(armor)));
		}

		{

			CUBIC = REG.enchLegacy("cubic_mining", "Cubic Mining", "Dig %1$sx%1$sx%1$s blocks at once",
					e -> e.items(ItemTags.MINING_ENCHANTABLE).maxLevel(2)
							.color(green).special(CRAFT, order.of(digging)),
					() -> new RangeDiggingEnchantment(new CubicBlockBreaker(1)));

			PLANE = REG.enchLegacy("planar_mining", "Planar Mining", "Dig %1$sx%1$s blocks at once",
					e -> e.items(ItemTags.MINING_ENCHANTABLE).maxLevel(3)
							.color(green).special(CRAFT, order.of(digging)),
					() -> new RangeDiggingEnchantment(new PlaneBlockBreaker(2)));

			DRILL = REG.enchLegacy("drilling", "Drilling", "Dig %s blocks at once",
					e -> e.items(ItemTags.MINING_ENCHANTABLE).maxLevel(3)
							.color(green).special(CRAFT, order.of(digging)),
					() -> new RangeDiggingEnchantment(new DrillBlockBreaker(7)));

			VIEN = REG.enchLegacy("vien_mining", "Vien Mining", "Dig connected blocks of the same type, up to %s blocks",
					e -> e.items(ItemTags.MINING_ENCHANTABLE).maxLevel(3)
							.color(green).special(CRAFT, order.of(digging)),
					() -> new RangeDiggingEnchantment(new OreDigger(7, 8)));

			TREE = REG.enchLegacy("tree_chopper", "Tree Tropper", "Chop logs and adjacent leaves",
					e -> e.items(ItemTags.MINING_ENCHANTABLE).maxLevel(2)
							.color(green).special(CRAFT, order.of(digging)),
					() -> new RangeDiggingEnchantment(new TreeDigger()));

			CHUNK_CUBIC = REG.enchLegacy("cubic_chunk_eater", "Cubic Chunk Eater", "Dig %1$sx%1$sx%1$s chunk-aligned blocks at once",
					e -> e.items(ItemTags.MINING_ENCHANTABLE).maxLevel(2)
							.color(gold).special(CRAFT, order.of(digging)),
					() -> new RangeDiggingEnchantment(new CubicChunkBreaker(2)));

			CHUNK_PLANE = REG.enchLegacy("planar_chunk_eater", "Planar Chunk Eater", "Dig 16x16 chunk-aligned blocks at once for %s layers",
					e -> e.items(ItemTags.MINING_ENCHANTABLE).maxLevel(3)
							.color(gold).special(CRAFT, order.of(digging)),
					() -> new RangeDiggingEnchantment(new PlaneChunkBreaker(1)));

		}
	}

	public static void register() {
	}

	public static class Order {

		private int order = 0;

		public CraftableEnch of(int color) {
			return new CraftableEnch(order++, color);
		}

		public CraftableEnch of(int color, int max) {
			return new CraftableEnch(order++, color, max);
		}


	}

}
