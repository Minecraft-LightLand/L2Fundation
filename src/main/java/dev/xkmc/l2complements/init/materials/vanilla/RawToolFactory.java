package dev.xkmc.l2complements.init.materials.vanilla;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

@FunctionalInterface
public interface RawToolFactory {

	TieredItem get(Tier tier, int dmg, float speed, Item.Properties props, ExtraToolConfig config);
}
