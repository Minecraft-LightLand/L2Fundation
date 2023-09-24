package dev.xkmc.l2complements.init.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class LangData {

	public enum IDS {
		WIND_BOTTLE("tooltip.misc.wind_bottle", "Used to obtain Captured Wind or Captured Bullet.", 0),
		VOID_EYE("tooltip.misc.void_eye", "Obtained by killing an angry Enderman %s block below the void. This item kill holder in void, collect it with care.", 1),
		SUN_MEMBRANE("tooltip.misc.sun_membrane", "Obtained by killing a sun-burning Phantom %s blocks above max build height.", 1),
		SOUL_FLAME("tooltip.misc.soul_flame", "Obtained by killing a ghast with soul flame.", 0),
		CAPTURED_WIND("tooltip.misc.captured_wind", "Obtained by moving faster than %s blocks per second while having Wind Bottle in hand or inventory.", 1),
		CAPTURED_BULLET("tooltip.misc.captured_shulker_bullet", "Obtained by right clicking shulker bullet with Wind Bottle.", 0),
		EXPLOSION_SHARD("tooltip.misc.explosion_shard", "Obtained by surviving an explosion damage of at least %s.", 1),
		HARD_ICE("tooltip.misc.hard_ice", "Obtained by killing a Drowned with Powdered Snow.", 0),
		STORM_CORE("tooltip.misc.storm_core", "Obtained by killing a Phantom with explosion.", 0),
		BLACKSTONE_CORE("tooltip.misc.blackstone_core", "Obtained by killing a Piglin Brute that has Incarceration effect.", 0),
		RESONANT_FEATHER("tooltip.misc.resonant_feather", "Obtained when a chicken survives a sonic boom attack.", 0),
		SPACE_SHARD("tooltip.misc.space_shard", "Obtained by causing a projectile damage of at least %s.", 1),
		FORCE_FIELD("tooltip.misc.force_field", "Obtained by killing a wither with arrow.", 0),
		WARDEN_BONE_SHARD("tooltip.misc.warden_bone_shard", "Dropped when Warden is killed by player.", 0),
		GUARDIAN_EYE("tooltip.misc.guardian_eye", "Dropped when Elder Guardian is killed by lightning strike.", 0),
		GUARDIAN_RUNE("tooltip.misc.guardian_rune", "Right click guardian to turn it into an elder guardian.", 0),
		PIGLIN_RUNE("tooltip.misc.piglin_rune", "Right click piglin to turn it into a piglin brute.", 0),

		BURNT_TITLE("jei.burnt.title", "Burning", 0),
		BURNT_COUNT("jei.burnt.count", "One in %s chance of conversion", 1),

		FLOAT("tooltip.misc.float", "This item will float in the air.", 0),

		WARP_RECORD("tooltip.misc.warp_record", "Right click to record position. After that, right click to teleport. Durability: %s", 1),
		WARP_TELEPORT("tooltip.misc.warp_teleport", "Right click to teleport. Durability: %s", 1),
		WARP_POS("tooltip.misc.warp_pos", "Target: %s, (%s,%s,%s)", 4),
		TOTEM_DREAM("tooltip.misc.totem_dream", "Return players back to home when triggers, and becomes a fragile warp stone to go back. Valid against void damage. Also heal player to full health.", 0),
		TOTEM_SEA("tooltip.misc.totem_sea", "It's stackable, but can only be triggered when in water or rain.", 0),

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
		DELAY_WARNING("msg.delay_warning", "Your tool needs %s to break more than %s blocks", 2);

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

	public interface LangEnum<T extends Enum<T> & LangEnum<T>> {

		int getCount();

		@Nullable
		default Class<? extends Enum<?>> mux() {
			return null;
		}

		@SuppressWarnings({"unchecked"})
		default T getThis() {
			return (T) this;
		}

	}

	public static void addTranslations(RegistrateLangProvider pvd) {
		for (IDS id : IDS.values()) {
			String[] strs = id.id.split("\\.");
			String str = strs[strs.length - 1];
			pvd.add(L2Complements.MODID + "." + id.id, id.def);
		}

		pvd.add("death.attack.soul_flame", "%s has its soul burnt out");
		pvd.add("death.attack.soul_flame.player", "%s has its soul burnt out by %s");
		pvd.add("death.attack.life_sync", "%s was drained");
		pvd.add("death.attack.void_eye", "%s was cursed by void eye");

		List<Item> list = List.of(Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION, Items.TIPPED_ARROW);
		for (RegistryEntry<? extends Potion> ent : LCEffects.POTION_LIST) {
			for (Item item : list) {
				String pref = item.getDescriptionId();
				String[] prefs = pref.split("\\.");
				String str = ent.get().getName(item.getDescriptionId() + ".effect.");
				String[] ids = ent.get().getEffects().get(0).getDescriptionId().split("\\.");
				String id = ids[ids.length - 1];
				String name = LCEffects.NAME_CACHE.getOrDefault(id, RegistrateLangProvider.toEnglishName(id));
				String pref_name = RegistrateLangProvider.toEnglishName(prefs[prefs.length - 1]);
				if (item == Items.TIPPED_ARROW) pref_name = "Arrow";
				pvd.add(str, pref_name + " of " + name);
			}
		}
	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public static MutableComponent translate(String key, Object... objs) {
		return Component.translatable(key, objs);
	}

}
