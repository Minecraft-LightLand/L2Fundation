package dev.xkmc.l2complements.events.event;

import jdk.jfr.Event;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class EnderPickupEvent extends Event {

	private final ServerPlayer player;
	private final ItemStack stack;

	public EnderPickupEvent(ServerPlayer player, ItemStack stack) {
		this.player = player;
		this.stack = stack;
	}

	public ServerPlayer getPlayer() {
		return player;
	}

	public ItemStack getStack() {
		return stack;
	}

}
