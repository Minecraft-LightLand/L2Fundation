package dev.xkmc.l2complements.init.data;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.l2complements.compat.TFCompat;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2complements.init.registrate.LCBlocks;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2complements.init.registrate.LCEntities;
import dev.xkmc.l2library.init.data.L2TagGen;
import dev.xkmc.l2screentracker.init.L2STTagGen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;

public class TagGen {

	public static final ProviderType<RegistrateTagsProvider.IntrinsicImpl<Enchantment>> ENCH_TAGS = L2TagGen.ENCH_TAGS;

	public static final ProviderType<RegistrateTagsProvider.IntrinsicImpl<MobEffect>> EFF_TAGS = L2TagGen.EFF_TAGS;

	public static final TagKey<Block> REQUIRES_SCULK_TOOL = BlockTags.create(new ResourceLocation(L2Complements.MODID, "requires_sculk_tool"));
	public static final TagKey<Block> AS_LEAF = BlockTags.create(new ResourceLocation(L2Complements.MODID, "as_leaf"));
	public static final TagKey<Item> SCULK_MATS = ItemTags.create(new ResourceLocation("modulargolems", "sculk_materials"));
	public static final TagKey<Item> SPECIAL_ITEM = ItemTags.create(new ResourceLocation(L2Complements.MODID, "l2c_legendary"));
	public static final TagKey<Item> DELICATE_BONE = ItemTags.create(new ResourceLocation(L2Complements.MODID, "delicate_bone"));
	public static final TagKey<MobEffect> SKILL_EFFECT = TagKey.create(ForgeRegistries.MOB_EFFECTS.getRegistryKey(), new ResourceLocation(L2Complements.MODID, "skill_effect"));
	public static final TagKey<Enchantment> DIGGER_ENCH = enchTag("chain_digging");
	public static final TagKey<Enchantment> WAND_ENCH = enchTag("applicable_on_wand");

	public static void onBlockTagGen(RegistrateTagsProvider.IntrinsicImpl<Block> pvd) {
		pvd.addTag(REQUIRES_SCULK_TOOL).add(Blocks.REINFORCED_DEEPSLATE);
		pvd.addTag(BlockTags.MINEABLE_WITH_PICKAXE).add(Blocks.REINFORCED_DEEPSLATE);
		pvd.addTag(AS_LEAF).addTag(BlockTags.LEAVES).addTag(BlockTags.WART_BLOCKS).add(Blocks.SHROOMLIGHT);
	}

	public static void onEffectTagGen(RegistrateTagsProvider.IntrinsicImpl<MobEffect> pvd) {
		pvd.addTag(SKILL_EFFECT).add(
				MobEffects.NIGHT_VISION,
				MobEffects.BAD_OMEN,
				MobEffects.HERO_OF_THE_VILLAGE,
				MobEffects.DOLPHINS_GRACE,
				MobEffects.CONDUIT_POWER,
				MobEffects.WATER_BREATHING
		);
		pvd.addTag(L2TagGen.TRACKED_EFFECTS).add(LCEffects.FLAME.get(), LCEffects.EMERALD.get(), LCEffects.ICE.get(),
				LCEffects.STONE_CAGE.get(), LCEffects.BLEED.get(), LCEffects.CLEANSE.get(), LCEffects.CURSE.get());
	}

	public static void onEnchTagGen(RegistrateTagsProvider.IntrinsicImpl<Enchantment> pvd) {
		pvd.addTag(DIGGER_ENCH);
		pvd.addTag(WAND_ENCH).add(
				Enchantments.UNBREAKING,
				LCEnchantments.LIFE_SYNC.get(),
				LCEnchantments.ETERNAL.get()
		);
	}

	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {
		pvd.addTag(SCULK_MATS).add(LCMats.SCULKIUM.getIngot());
		pvd.addTag(DELICATE_BONE).add(Items.SCULK_CATALYST, Items.SCULK_SHRIEKER);
		TFCompat.onItemTagGen(pvd);
		pvd.addTag(L2STTagGen.QUICK_ACCESS_VANILLA).add(LCBlocks.ETERNAL_ANVIL.asItem());
		pvd.addTag(ItemTags.TRIM_MATERIALS).add(Arrays.stream(LCMats.values()).map(LCMats::getIngot).toArray(Item[]::new));
	}

	public static void onEntityTagGen(RegistrateTagsProvider.IntrinsicImpl<EntityType<?>> pvd) {
		pvd.addTag(EntityTypeTags.IMPACT_PROJECTILES).add(
				LCEntities.ETFB_BLACK.get(),
				LCEntities.ETFB_SOUL.get(),
				LCEntities.ETFB_STRONG.get());
	}

	private static TagKey<Enchantment> enchTag(String id) {
		return TagKey.create(ForgeRegistries.ENCHANTMENTS.getRegistryKey(), new ResourceLocation(L2Complements.MODID, id));
	}

}
