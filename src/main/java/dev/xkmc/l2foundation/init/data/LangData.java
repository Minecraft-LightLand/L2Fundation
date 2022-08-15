package dev.xkmc.l2foundation.init.data;

import dev.xkmc.l2library.repack.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2foundation.init.L2Foundation;
import dev.xkmc.l2foundation.init.registrate.LFEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;

public class LangData {

	public enum IDS {
		;

		final String id;
		final int count;

		IDS(String id, int count) {
			this.id = id;
			this.count = count;
		}

		public MutableComponent get(Object... objs) {
			if (objs.length != count)
				throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
			return translate(L2Foundation.MODID + "." + id, objs);
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

	public static void addTranslations(BiConsumer<String, String> pvd) {
		for (IDS id : IDS.values()) {
			String[] strs = id.id.split("\\.");
			String str = strs[strs.length - 1];
			pvd.accept(L2Foundation.MODID + "." + id.id,
					RegistrateLangProvider.toEnglishName(str) + getParams(id.count));
		}
		pvd.accept("itemGroup.l2foundation.material", "L2Fundation Materials");
		pvd.accept("itemGroup.l2foundation.generated", "L2Fundation Equipments");

		List<Item> list = List.of(Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION);
		for (RegistryEntry<? extends Potion> ent : LFEffects.POTION_LIST) {
			for (Item item : list) {
				String str = ent.get().getName(item.getDescriptionId() + ".effect.");
				pvd.accept(str, RegistrateLangProvider.toEnglishName(str));
			}
		}
	}

	private static String getParams(int count) {
		if (count <= 0)
			return "";
		StringBuilder pad = new StringBuilder();
		for (int i = 0; i < count; i++) {
			pad.append(pad.length() == 0 ? ": " : "/");
			pad.append("%s");
		}
		return pad.toString();
	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public static MutableComponent translate(String key, Object... objs) {
		return Component.translatable(key, objs);
	}

}
