package dev.xkmc.l2complements.compat;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import net.neoforged.fml.ModList;

public class TFCompat {

	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {
		if (ModList.get().isLoaded("twilightforest")) {
			onItemTagGenImpl(pvd);
		}
	}

	public static void onItemTagGenImpl(RegistrateItemTagsProvider pvd) {
		/* TODO TwilightForest
		pvd.addTag(ItemTagGenerator.BANNED_UNCRAFTING_INGREDIENTS)
				.add(Items.ENCHANTED_BOOK)
				.addTag(LCTagGen.SPECIAL_ITEM)
				.add(LCMats.TOTEMIC_GOLD.getIngot());
		pvd.addTag(ItemTagGenerator.BANNED_UNCRAFTABLES)
				.add(Items.ENCHANTED_BOOK);

		 */

	}

}
