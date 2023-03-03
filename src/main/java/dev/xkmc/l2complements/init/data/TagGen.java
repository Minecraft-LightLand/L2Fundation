package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.compat.TFCompat;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateItemTagsProvider;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class TagGen {

	public static final TagKey<Block> REQUIRES_SCULK_TOOL = BlockTags.create(new ResourceLocation(L2Complements.MODID, "requires_sculk_tool"));
	public static final TagKey<Item> SCULK_MATS = ItemTags.create(new ResourceLocation("modulargolems", "sculk_materials"));
	public static final TagKey<Item> TOTEM = ItemTags.create(new ResourceLocation(L2Complements.MODID, "l2c_totems"));
	public static final TagKey<Item> SPECIAL_ITEM = ItemTags.create(new ResourceLocation(L2Complements.MODID, "l2c_legendary"));

	public static void onBlockTagGen(RegistrateTagsProvider<Block> pvd) {
		pvd.tag(REQUIRES_SCULK_TOOL).add(Blocks.REINFORCED_DEEPSLATE);
		pvd.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(Blocks.REINFORCED_DEEPSLATE);
	}

	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {
		pvd.tag(SCULK_MATS).add(LCMats.SCULKIUM.getIngot());
		pvd.tag(TOTEM).add(LCItems.TOTEM_OF_DREAM.get(), LCItems.TOTEM_OF_THE_SEA.get());
		TFCompat.onItemTagGen(pvd);
	}

}
