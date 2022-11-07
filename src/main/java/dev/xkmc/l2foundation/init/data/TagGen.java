package dev.xkmc.l2foundation.init.data;

import dev.xkmc.l2foundation.init.L2Foundation;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class TagGen {

	public static final TagKey<Block> REQUIRES_SCULK_TOOL = BlockTags.create(new ResourceLocation(L2Foundation.MODID, "requires_sculk_tool"));

	public static void onBlockTagGen(RegistrateTagsProvider<Block> pvd) {
		pvd.tag(REQUIRES_SCULK_TOOL).add(Blocks.REINFORCED_DEEPSLATE);
		pvd.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(Blocks.REINFORCED_DEEPSLATE);
	}

	public static void onItemTagGen(RegistrateTagsProvider<Item> pvd) {

	}

}
