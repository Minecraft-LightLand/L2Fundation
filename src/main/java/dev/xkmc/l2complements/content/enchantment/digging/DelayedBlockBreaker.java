package dev.xkmc.l2complements.content.enchantment.digging;

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
		for (int i = 0; i < 16; i++) {
			player.gameMode.destroyBlock(list.get(count));
			count++;
			if (count >= list.size()) return true;
		}
		return false;
	}

}
