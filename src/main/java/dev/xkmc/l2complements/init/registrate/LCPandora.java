package dev.xkmc.l2complements.init.registrate;

import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.xkmc.l2complements.content.item.curios.DescCurioItem;
import dev.xkmc.l2complements.content.item.pandora.*;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.pandora.init.data.PandoraTagGen;
import dev.xkmc.pandora.init.registrate.PandoraItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;

public class LCPandora {

	public static final ItemEntry<Item> CHARM, MINI_BEACON, MINI_BEACON_BASE;
	public static final ItemEntry<EnchDescItem> FIRE_REJECT;
	public static final ItemEntry<EnchDescItem> EXPLOSION_REJECT;
	public static final ItemEntry<EnchDescItem> MAGIC_REJECT;
	public static final ItemEntry<EnchDescItem> ENVIRONMENTAL_REJECT;
	public static final ItemEntry<EnchDescItem> PROJECTILE_REJECT;
	public static final ItemEntry<EnchDescItem> OWNER_PROTECTION;
	public static final ItemEntry<EnchDescItem> STABLE_BODY;
	public static final ItemEntry<PiglinShinnyCharm> GOLDEN_HEART;
	public static final ItemEntry<EnderMaskCharm> ENDER_CHARM;
	public static final ItemEntry<SnowWalkerCharm> BLESS_SNOW_WALKER;
	public static final ItemEntry<EffectRefreshItem> MINI_BEACON_SPEED;
	public static final ItemEntry<EffectRefreshItem> MINI_BEACON_HASTE;
	public static final ItemEntry<EffectRefreshItem> MINI_BEACON_JUMP;
	public static final ItemEntry<EffectRefreshItem> MINI_BEACON_ATTACK;
	public static final ItemEntry<EffectRefreshItem> MINI_BEACON_RESISTANCE;
	public static final ItemEntry<EffectRefreshItem> MINI_BEACON_REGEN;
	public static final ItemEntry<EffectRefreshItem> NIGHT_VISION_CHARM;
	public static final ItemEntry<DescCurioItem> BLESS_LAVA_WALKER;

	public static final ItemEntry<AttributeItem> CHARM_HEALTH;
	public static final ItemEntry<AttributeItem> CHARM_ARMOR;
	public static final ItemEntry<AttributeItem> CHARM_SPEED;
	public static final ItemEntry<AttributeItem> CHARM_DAMAGE;
	public static final ItemEntry<AttributeItem> CHARM_HEAVY;
	public static final ItemEntry<CurseOfInertiaItem> CURSE_OF_INERTIA;
	public static final ItemEntry<CurseOfProximityItem> CURSE_OF_PROXIMITY;


	static {
		L2Complements.REGISTRATE.defaultCreativeTab(PandoraItems.TAB.getKey());

		{

			CHARM = item("plain_charm", Item::new)
					.register();

			MINI_BEACON_BASE = item("mini_beacon_base", p -> new Item(p.fireResistant()))
					.register();

			MINI_BEACON = item("mini_beacon", Item::new)
					.register();

			FIRE_REJECT = item("orb_of_fire_rejection",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_FIRE::get))
					.lang("Orb of Fire Rejection")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			EXPLOSION_REJECT = item("orb_of_explosion_rejection",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_EXPLOSION::get))
					.lang("Orb of Explosion Rejection")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			MAGIC_REJECT = item("orb_of_magic_rejection",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_MAGIC::get))
					.lang("Orb of Magic Rejection")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			PROJECTILE_REJECT = item("orb_of_projectile_rejection",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_PROJECTILE::get))
					.lang("Orb of Projectile Rejection")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			ENVIRONMENTAL_REJECT = item("orb_of_nature",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_ENVIRONMENT::get))
					.lang("Orb of Nature")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			OWNER_PROTECTION = item("orb_of_master",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_MATES::get))
					.lang("Orb of Master")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			STABLE_BODY = item("orb_of_stability",
					p -> new EnchDescItem(p, LCEnchantments.STABLE_BODY::get))
					.lang("Orb of Stability")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			GOLDEN_HEART = item("golden_heart",
					p -> new PiglinShinnyCharm(p, LCEnchantments.SHINNY::get))
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			ENDER_CHARM = item("charm_of_calmness",
					p -> new EnderMaskCharm(p, LCEnchantments.ENDER_MASK::get))
					.lang("Charm of Calmness")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			BLESS_SNOW_WALKER = item("bless_of_snow_walker",
					p -> new SnowWalkerCharm(p, LCEnchantments.SNOW_WALKER::get))
					.lang("Bless of Snow Walker")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			BLESS_LAVA_WALKER = descItem("bless_of_lava_walker", "Bless of Lava Walker",
					"Allows you to walk on lava");

			MINI_BEACON_SPEED = item("mini_beacon_speed",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.MOVEMENT_SPEED, 40, 1, true, true)))
					.lang("Mini Beacon: Speed")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			MINI_BEACON_HASTE = item("mini_beacon_haste",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.DIG_SPEED, 40, 1, true, true)))
					.lang("Mini Beacon: Haste")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			MINI_BEACON_ATTACK = item("mini_beacon_strength",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.DAMAGE_BOOST, 40, 1, true, true)))
					.lang("Mini Beacon: Strength")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			MINI_BEACON_JUMP = item("mini_beacon_jump",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.JUMP, 40, 1, true, true)))
					.lang("Mini Beacon: Jump Boost")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			MINI_BEACON_RESISTANCE = item("mini_beacon_resistance",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.DAMAGE_RESISTANCE, 40, 1, true, true)))
					.lang("Mini Beacon: Resistance")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			MINI_BEACON_REGEN = item("mini_beacon_regen",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.REGENERATION, 60, 0, true, true)))
					.lang("Mini Beacon: Regeneration")
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			NIGHT_VISION_CHARM = item("charm_of_night_vision",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.NIGHT_VISION, 440, 0, true, true)))
					.tag(PandoraTagGen.PANDORA_SLOT).register();

		}

		// attributes

		{
			CHARM_HEALTH = item("charm_of_health", p -> new AttributeItem(p,
					(uuid, map) -> map.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "charm_of_health",
							2, AttributeModifier.Operation.ADDITION))))
					.tag(PandoraTagGen.PANDORA_SLOT, PandoraTagGen.ALLOW_DUPLICATE).register();

			CHARM_ARMOR = item("charm_of_armor", p -> new AttributeItem(p,
					(uuid, map) -> map.put(Attributes.ARMOR, new AttributeModifier(uuid, "charm_of_armor",
							2, AttributeModifier.Operation.ADDITION)),
					(uuid, map) -> map.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "charm_of_armor",
							1, AttributeModifier.Operation.ADDITION))
			)).tag(PandoraTagGen.PANDORA_SLOT, PandoraTagGen.ALLOW_DUPLICATE).register();

			CHARM_SPEED = item("charm_of_speed", p -> new AttributeItem(p,
					(uuid, map) -> map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "charm_of_speed",
							0.05, AttributeModifier.Operation.MULTIPLY_BASE))))
					.tag(PandoraTagGen.PANDORA_SLOT, PandoraTagGen.ALLOW_DUPLICATE).register();

			CHARM_DAMAGE = item("charm_of_damage", p -> new AttributeItem(p,
					(uuid, map) -> map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "charm_of_damage",
							0.05, AttributeModifier.Operation.MULTIPLY_BASE))))
					.tag(PandoraTagGen.PANDORA_SLOT, PandoraTagGen.ALLOW_DUPLICATE).register();

			CHARM_HEAVY = item("charm_of_heavy_weapon", p -> new AttributeItem(p,
					(uuid, map) -> map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "charm_of_heavy_weapon",
							1, AttributeModifier.Operation.MULTIPLY_BASE)),
					(uuid, map) -> map.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "charm_of_heavy_weapon",
							-2, AttributeModifier.Operation.ADDITION))
			)).tag(PandoraTagGen.PANDORA_SLOT, PandoraTagGen.ALLOW_DUPLICATE).register();

			CURSE_OF_INERTIA = item("curse_of_inertia", CurseOfInertiaItem::new)
					.tag(PandoraTagGen.PANDORA_SLOT).register();

			CURSE_OF_PROXIMITY = item("curse_of_proximity", CurseOfProximityItem::new)
					.tag(PandoraTagGen.PANDORA_SLOT).register();

		}

	}

	public static <T extends Item> ItemBuilder<T, L2Registrate> item(String id, NonNullFunction<Item.Properties, T> factory) {
		return L2Complements.REGISTRATE.item(id, factory)
				.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("item/pandora/" + id)));
	}

	public static ItemEntry<DescCurioItem> descItem(String id, String name, String desc) {
		L2Complements.REGISTRATE.addRawLang("item." + L2Complements.MODID + "." + id + ".desc", desc);
		return item(id, DescCurioItem::new)
				.lang(name).tag(PandoraTagGen.PANDORA_SLOT).register();
	}

	public static void register() {

	}

}
