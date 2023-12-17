package dev.xkmc.curseofpandora.init.data;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import dev.xkmc.l2complements.init.L2Complements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CoPTagGen {
	public static final TagKey<Item> PANDORA_BASE = ItemTags.create(new ResourceLocation(L2Complements.MODID,"pandora_base"));
	public static final TagKey<Item> CURSE = ItemTags.create(new ResourceLocation(L2Complements.MODID,"pandora_curse"));

	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {
	}
}
