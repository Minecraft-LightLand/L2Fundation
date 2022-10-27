package dev.xkmc.l2foundation.init.data;

import dev.xkmc.l2foundation.content.item.generic.*;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.repack.registrate.builders.ItemBuilder;
import dev.xkmc.l2library.repack.registrate.providers.DataGenContext;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateItemModelProvider;
import dev.xkmc.l2library.repack.registrate.util.entry.BlockEntry;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"unchecked", "rawtypes", "unsafe"})
public class GenItem {

	public static final Function<Tools, RawToolFactory> TOOL_GEN_FUNC = tool -> switch (tool) {
		case SWORD -> SwordItem::new;
		case AXE -> AxeItem::new;
		case SHOVEL -> ShovelItem::new;
		case PICKAXE -> PickaxeItem::new;
		case HOE -> HoeItem::new;
	};

	public static TieredItem genGenericTool(FoundationMats mat, Tools tool, Item.Properties prop) {
		int dmg = mat.tool_stats.add_dmg[tool.ordinal()] - 1;
		float speed = mat.tool_stats.add_speed[tool.ordinal()] - 4;
		return switch (tool) {
			case SWORD -> new GenericSwordItem(mat.tier, dmg, speed, prop, mat.tool_extra);
			case AXE -> new GenericAxeItem(mat.tier, dmg, speed, prop, mat.tool_extra);
			case SHOVEL -> new GenericShovelItem(mat.tier, dmg, speed, prop, mat.tool_extra);
			case PICKAXE -> new GenericPickaxeItem(mat.tier, dmg, speed, prop, mat.tool_extra);
			case HOE -> new GenericHoeItem(mat.tier, dmg, speed, prop, mat.tool_extra);
		};
	}

	public static final ToolConfig TOOL_DEF = new ToolConfig(fromToolGen(TOOL_GEN_FUNC));
	public static final ToolConfig TOOL_GEN = new ToolConfig(GenItem::genGenericTool);
	public static final ArmorConfig ARMOR_DEF = new ArmorConfig((mat, slot, prop) -> new ArmorItem(mat.mat, slot, prop));
	public static final ArmorConfig ARMOR_GEN = new ArmorConfig((mat, slot, prop) -> new GenericArmorItem(mat.mat, slot, prop, mat.armor_extra));

	public enum Tools {
		SWORD, AXE, SHOVEL, PICKAXE, HOE
	}

	public record ToolStats(int durability, int speed, int[] add_dmg, float[] add_speed, int enchant) {
	}

	public record ArmorStats(int durability, int[] protection, float tough, float kb, int enchant) {
	}

	@FunctionalInterface
	public interface ArmorFactory {

		ArmorItem get(FoundationMats mat, EquipmentSlot slot, Item.Properties props);

	}

	@FunctionalInterface
	public interface RawToolFactory {

		TieredItem get(Tier tier, int dmg, float speed, Item.Properties props);
	}

	@FunctionalInterface
	public interface ToolFactory {

		TieredItem get(FoundationMats mat, Tools tool, Item.Properties props);

	}

	@FunctionalInterface
	public interface EntryProcessor {

		ItemBuilder<Item, L2Registrate> apply(ItemBuilder<Item, L2Registrate> builder);

	}

	public record ToolConfig(ToolFactory sup) {
	}

	public record ArmorConfig(ArmorFactory sup) {
	}

	public static TagKey<Block> getBlockTag(int level) {
		return switch (level) {
			case 0 -> Tags.Blocks.NEEDS_WOOD_TOOL;
			case 1 -> BlockTags.NEEDS_STONE_TOOL;
			case 2 -> BlockTags.NEEDS_IRON_TOOL;
			case 3 -> BlockTags.NEEDS_DIAMOND_TOOL;
			default -> Tags.Blocks.NEEDS_NETHERITE_TOOL;
		};
	}

	private static ToolFactory fromToolGen(Function<Tools, RawToolFactory> gen) {
		return (mat, tool, prop) -> gen.apply(tool).get(mat.tier,
				mat.tool_stats.add_dmg[tool.ordinal()] - 1,
				mat.tool_stats.add_speed[tool.ordinal()] - 4, prop);
	}

	private final String modid;
	private final L2Registrate registrate;

	public GenItem(String modid, L2Registrate registrate) {
		this.modid = modid;
		this.registrate = registrate;
	}

	public ItemEntry<Item>[][] genItem() {
		int n = FoundationMats.values().length;
		ItemEntry[][] ans = new ItemEntry[n][9];
		for (int i = 0; i < n; i++) {
			FoundationMats mat = FoundationMats.values()[i];
			String id = mat.id;
			BiFunction<String, EquipmentSlot, ItemEntry> armor_gen = (str, slot) ->
					registrate.item(id + "_" + str, p -> mat.armor_config.sup.get(mat, slot, p))
							.model((ctx, pvd) -> generatedModel(ctx, pvd, id, str))
							.defaultLang().register();
			ans[i][3] = armor_gen.apply("helmet", EquipmentSlot.HEAD);
			ans[i][2] = armor_gen.apply("chestplate", EquipmentSlot.CHEST);
			ans[i][1] = armor_gen.apply("leggings", EquipmentSlot.LEGS);
			ans[i][0] = armor_gen.apply("boots", EquipmentSlot.FEET);
			BiFunction<String, Tools, ItemEntry> tool_gen = (str, tool) ->
					registrate.item(id + "_" + str, p -> mat.tool_config.sup.get(mat, tool, p))
							.model((ctx, pvd) -> handHeld(ctx, pvd, id, str))
							.defaultLang().register();
			for (int j = 0; j < Tools.values().length; j++) {
				Tools tool = Tools.values()[j];
				ans[i][4 + j] = tool_gen.apply(tool.name().toLowerCase(Locale.ROOT), tool);
			}
		}
		return ans;
	}

	public ItemEntry<Item>[] genMats(String suffix, TagKey<Item> tag) {
		int n = FoundationMats.values().length;
		ItemEntry[] ans = new ItemEntry[n];
		for (int i = 0; i < n; i++) {
			String id = FoundationMats.values()[i].id;
			ans[i] = registrate.item(id + "_" + suffix, Item::new)
					.model((ctx, pvd) -> generatedModel(ctx, pvd, id, suffix))
					.tag(tag).defaultLang().register();
		}
		return ans;
	}

	public BlockEntry<Block>[] genBlockMats() {
		int n = FoundationMats.values().length;
		BlockEntry[] ans = new BlockEntry[n];
		for (int i = 0; i < n; i++) {
			ans[i] = registrate.block(FoundationMats.values()[i].id + "_block", p -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK)))
					.defaultLoot().defaultBlockstate()
					.tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL, Tags.Blocks.STORAGE_BLOCKS)
					.item().tag(Tags.Items.STORAGE_BLOCKS).build().defaultLang().register();
		}
		return ans;
	}

	public <T extends Item> void generatedModel(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd, String id, String suf) {
		pvd.generated(ctx, new ResourceLocation(modid, "item/generated/" + id + "/" + suf));
	}

	public <T extends Item> void handHeld(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd, String id, String suf) {
		pvd.handheld(ctx, new ResourceLocation(modid, "item/generated/" + id + "/" + suf));
	}

}
