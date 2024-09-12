package dev.xkmc.l2complements.init.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2complements.events.LCAttackListener;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2complements.init.registrate.LCItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;

import javax.annotation.Nullable;
import java.util.Locale;

public class LCLang {

	public static MutableComponent eff(MobEffect eff) {
		return Component.translatable(eff.getDescriptionId()).withStyle(eff.getCategory().getTooltipFormatting());
	}


	public enum Items {
		PRECURSOR("tooltip.misc.precursor", "Precursor to [%s] ans [%s].", 2),
		EXPLOSION_KILL("tooltip.misc.explosion_kill", "Obtained by killing a %s with explosion.", 1),
		ARROW_KILL("tooltip.misc.arrow_kill", "Obtained by killing a %s with arrow.", 1),
		STRIKE_KILL("tooltip.misc.strike_kill", "Dropped when %s is killed by lightning strike.", 1),
		PLAYER_KILL("tooltip.misc.player_kill", "Dropped when %s is killed by player.", 1),
		EFFECT_KILL("tooltip.misc.effect_kill", "Obtained by killing a %s with %s effect.", 2),
		CLICK("tooltip.misc.click", "Obtained by right clicking %s with [%s].", 2),
		TRANSFORM_RUNE("tooltip.misc.transform_rune", "Right click %s to turn it into %s.", 2),

		VOID_EYE("tooltip.misc.void_eye", "Obtained by killing an angry %s that is %s blocks into the void. This item kill holder in void, collect it with care.", 2),
		SUN_MEMBRANE("tooltip.misc.sun_membrane", "Obtained by killing a sun-burning %s that is %s blocks above max build height.", 2),
		CAPTURED_WIND("tooltip.misc.captured_wind", "Obtained by flying faster than %s blocks per second while having [%s] in hand or inventory.", 2),
		EXPLOSION_SHARD("tooltip.misc.explosion_shard", "Obtained by surviving an explosion damage of at least %s.", 1),
		HARD_ICE("tooltip.misc.hard_ice", "Obtained by killing a %s with Powdered Snow.", 1),
		RESONANT_FEATHER("tooltip.misc.resonant_feather", "Obtained when a %s survives a sonic boom attack.", 1),

		SPACE_SHARD("tooltip.misc.space_shard", "Obtained by causing a projectile damage of at least %s.", 1),

		;

		final String id, def;
		final int count;

		Items(String id, String def, int count) {
			this.id = id;
			this.def = def;
			this.count = count;
		}

		public MutableComponent get(Object... objs) {
			if (objs.length != count)
				throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
			return translate(L2Complements.MODID + "." + id, objs);
		}

		public static MutableComponent windBottle() {
			return PRECURSOR.get(LCItems.CAPTURED_WIND.asStack().getHoverName(), LCItems.CAPTURED_BULLET.asStack().getHoverName());
		}

		public static MutableComponent voidEye() {
			return VOID_EYE.get(EntityType.ENDERMAN.getDescription(), LCConfig.SERVER.belowVoid.get());
		}

		public static MutableComponent sunMembrane() {
			return SUN_MEMBRANE.get(EntityType.PHANTOM.getDescription(), LCConfig.SERVER.phantomHeight.get());
		}

		public static MutableComponent soulFlame() {
			return EFFECT_KILL.get(EntityType.GHAST.getDescription(), eff(LCEffects.FLAME.get()));
		}

		public static MutableComponent capturedWind() {
			return CAPTURED_WIND.get(LCConfig.SERVER.windSpeed.get() * 20, LCItems.WIND_BOTTLE.asStack().getHoverName());
		}

		public static MutableComponent capturedBullet() {
			return CLICK.get(EntityType.SHULKER_BULLET.getDescription(), LCItems.WIND_BOTTLE.asStack().getHoverName());
		}

		public static MutableComponent explosionShard() {
			return EXPLOSION_SHARD.get(LCConfig.SERVER.explosionDamage.get());
		}

		public static MutableComponent hardIce() {
			return HARD_ICE.get(EntityType.DROWNED.getDescription());
		}

		public static MutableComponent stormCore() {
			return EXPLOSION_KILL.get(EntityType.PHANTOM.getDescription());
		}

		public static MutableComponent blackstoneCore() {
			return EFFECT_KILL.get(EntityType.PIGLIN_BRUTE.getDescription(), eff(LCEffects.INCARCERATE.get()));
		}

		public static MutableComponent resonantFeather() {
			return RESONANT_FEATHER.get(EntityType.CHICKEN.getDescription());
		}

		public static MutableComponent forceField() {
			return ARROW_KILL.get(EntityType.WITHER.getDescription());
		}

		public static MutableComponent wardenBoneShard() {
			return PLAYER_KILL.get(EntityType.WARDEN.getDescription());
		}

		public static MutableComponent guardianEye() {
			return STRIKE_KILL.get(EntityType.ELDER_GUARDIAN.getDescription());
		}

		@Nullable
		public static MutableComponent spaceShard() {
			if (LCAttackListener.isSpaceShardBanned()) return null;
			return SPACE_SHARD.get(LCConfig.SERVER.spaceDamage.get());
		}

	}

	public enum IDS {
		BURNT_TITLE("jei.burnt.title", "Burning", 0),
		BURNT_COUNT("jei.burnt.count", "One in %s chance of conversion", 1),
		DIFFUSE_TITLE("jei.diffuse.title", "Diffusion", 0),

		FLOAT("tooltip.misc.float", "This item will float in the air.", 0),

		WARP_RECORD("tooltip.misc.warp_record", "Right click to record position. After that, right click to teleport. Durability: %s", 1),
		WARP_TELEPORT("tooltip.misc.warp_teleport", "Right click in inventory or UI to teleport. Durability: %s", 1),
		WARP_POS("tooltip.misc.warp_pos", "Target: %s, (%s,%s,%s)", 4),
		WARP_GRIND("tooltip.misc.warp_grind", "Use grindstone to remove record", 0),
		TOTEM_DREAM("tooltip.misc.totem_dream", "Return players back to home when triggers, and becomes a fragile warp stone to go back. Valid against void damage. Also heal player to full health.", 0),
		TOTEM_SEA("tooltip.misc.totem_sea", "It's stackable, but can only be triggered when in water or rain.", 0),
		TOTEM_ETERNAL("tooltip.misc.totem_eternal", "Reusable Totem of Dream with cool down of %s seconds", 1),

		CHARGE_THROW("tooltip.misc.charge_throw", "Right click to throw at target", 0),
		EFFECT_CHARGE("tooltip.misc.effect_charge", "Apply on Hit: %s", 1),
		EXPLOSION_CHARGE("tooltip.misc.explosion_charge", "Create explosion of level %s on Hit", 1),
		ARMOR_IMMUNE("tooltip.tool.immune", "Immune to: ", 0),

		POSEIDITE_TOOL("tooltip.tool.poseidite_tool", "Sharper and faster when user is in rain or water. Effective against water based mobs and mobs sensitive to water.", 0),
		POSEIDITE_ARMOR("tooltip.tool.poseidite_armor", "When user is in rain or water: provides extra protection, walk/swim speed boost, and conduit/dolphin grace effect.", 0),
		SCULKIUM_TOOL("tooltip.tool.sculkium_tool", "Breaks all breakable blocks for the same speed. Be aware of the breaking level.", 0),
		SCULKIUM_ARMOR("tooltip.tool.sculkium_armor", "Dampened: When wearing 4 pieces of armors with dampened effect, cancel all vibrations emitted by wearer.", 0),
		SHULKERATE_TOOL("tooltip.tool.shulkerate_tool", "Really durable. Not easily damaged. Increase Reach and Attack distance", 0),
		SHULKERATE_ARMOR("tooltip.tool.shulkerate_armor", "Really durable. Not easily damaged.", 0),
		TOTEMIC_TOOL("tooltip.tool.totemic_tool", "Heal user when used. Effective against undead mobs.", 0),
		TOTEMIC_ARMOR("tooltip.tool.totemic_armor", "Heal user when damaged. Regenerate health over time.", 0),
		DELAY_WARNING("msg.delay_warning", "Your tool needs %s to break more than %s blocks", 2),

		SONIC_SHOOTER("tooltip.misc.sonic_shooter", "Hold use to charge. Shoot sonic boom automatically when charged to full. Damage all mobs in front of you.", 0),
		HELLFIRE_WAND("tooltip.misc.hellfire_wand", "Hold use to start. Gradually grows a ring of fire on target position. On release, damage all mobs inside the ring.", 0),
		WINTERSTORM_WAND("tooltip.misc.winterstorm_wand", "Hold use to create a ring of storm. Push mobs away and freeze them.", 0),
		DIFFUSION_WAND("tooltip.misc.diffusion_wand", "Right click a block of gem/dust to diffuse it into nearby stones to create ore. Check JEI for recipe", 0),
		BANNED("tooltip.misc.banned", "This item is disabled.", 0),
		BANNED_ENCH("tooltip.misc.banned_ench", "Disabled", 0),
		DIGGER_ACTIVATED("msg.digger_activated", "Activated: %s", 1),
		TREE_CHOP("tooltip.ench.tree", "Breaks leaves as well, and doesn't cost durability when breaking leaves", 0),
		DIGGER_ROTATE("tooltip.ench.rotate", "Press keybind [%s] to toggle", 1);

		final String id, def;
		final int count;

		IDS(String id, String def, int count) {
			this.id = id;
			this.def = def;
			this.count = count;
		}

		public MutableComponent get(Object... objs) {
			if (objs.length != count)
				throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
			return translate(L2Complements.MODID + "." + id, objs);
		}

	}

	public static void addTranslations(RegistrateLangProvider pvd) {
		for (IDS id : IDS.values()) {
			pvd.add(L2Complements.MODID + "." + id.id, id.def);
		}
		for (var lang : LCKeys.values()) {
			pvd.add(lang.id, lang.def);
		}

		pvd.add("death.attack.soul_flame", "%s has its soul burnt out");
		pvd.add("death.attack.soul_flame.player", "%s has its soul burnt out by %s");
		pvd.add("death.attack.life_sync", "%s was drained");
		pvd.add("death.attack.void_eye", "%s was cursed by void eye");
		pvd.add("death.attack.emerald", "%s was killed by emerald splash");
		pvd.add("death.attack.emerald.player", "%s was killed by emerald splash by %s");
		pvd.add("death.attack.bleed", "%s bleed to death");

	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public static MutableComponent translate(String key, Object... objs) {
		return Component.translatable(key, objs);
	}

	public static MutableComponent diggerRotate() {
		return IDS.DIGGER_ROTATE.get(LCKeys.DIG.map.getKey().getDisplayName());
	}

}
