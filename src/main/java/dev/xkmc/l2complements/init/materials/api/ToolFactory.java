package dev.xkmc.l2complements.init.materials.api;

import dev.xkmc.l2complements.init.materials.api.IMatVanillaType;
import dev.xkmc.l2complements.init.materials.vanilla.Tools;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;

@FunctionalInterface
public interface ToolFactory {

	TieredItem get(IMatToolType mat, ITool tool, Item.Properties props);

}
