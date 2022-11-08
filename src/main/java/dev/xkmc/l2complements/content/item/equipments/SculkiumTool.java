package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class SculkiumTool extends ExtraToolConfig {

	public static float cachedHardness;

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state, float old) {
		return cachedHardness > 0 ? old * cachedHardness : old;
	}

}
