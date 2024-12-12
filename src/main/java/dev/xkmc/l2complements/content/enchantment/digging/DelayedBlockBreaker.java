package dev.xkmc.l2complements.content.enchantment.digging;

import dev.xkmc.l2complements.init.data.LCConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class DelayedBlockBreaker {

	private final ServerPlayer player;
	private final Level level;
	private final ItemStack stack;
	private final List<BlockPos> list;

	private int count = 0;

	public DelayedBlockBreaker(ServerPlayer player, List<BlockPos> list) {
		this.player = player;
		this.level = player.level();
		this.stack = player.getMainHandItem();
		this.list = list;
	}

	private boolean check() {
		return player.isAlive() && player.level() == level && player.getMainHandItem() == stack;
	}

	public boolean tick() {
		if (!check()) return true;
		RangeDiggingEnchantment.execute(player, () -> {
			int n = LCConfig.COMMON.chainDiggingBlockPerTick.get();
			for (int i = 0; i < n; i++) {
				RangeDiggingEnchantment.breakBlockWrapped(player, list.get(count));
				count++;
				if (count >= list.size()) return;
			}
		});
		return count >= list.size();
	}

}
