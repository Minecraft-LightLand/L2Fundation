package dev.xkmc.l2foundation.content.questline.mobs.layline;

import dev.xkmc.l2library.repack.registrate.providers.loot.RegistrateEntityLootTables;
import dev.xkmc.l2library.util.data.LootTableTemplate;
import dev.xkmc.l2foundation.init.registrate.LFItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

public class LaylineSkeleton extends BaseLaylineMob<LaylineSkeleton> {

	public static void loot(RegistrateEntityLootTables table, EntityType<?> type) {
		table.add(type, new LootTable.Builder()
				.withPool(LootTableTemplate.getPool(1, 0)
						.add(LootTableTemplate.getItem(Items.BONE, 0, 2, 1)))
				.withPool(LootTableTemplate.getPool(1, 0)
						.add(LootTableTemplate.getItem(LFItems.LAYLINE_ORB.get(), 1))
						.when(LootTableTemplate.byPlayer())
						.when(LootTableTemplate.chance(0.1f, 0.01f)))
		);
	}

	public LaylineSkeleton(EntityType<LaylineSkeleton> type, Level level) {
		super(type, level, LaylineProperties.CONFIG_SKELETON);
	}

}
