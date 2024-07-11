package dev.xkmc.l2complements.network;

import dev.xkmc.l2complements.events.ItemUseEventHandler;
import dev.xkmc.l2serial.network.SerialPacketBase;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public record EmptyRightClickToServer(
		boolean hand, boolean right
) implements SerialPacketBase<EmptyRightClickToServer> {

	@Override
	public void handle(Player pl) {
		if (right) {
			PlayerInteractEvent.RightClickEmpty event = new PlayerInteractEvent.RightClickEmpty(pl,
					hand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
			ItemUseEventHandler.onPlayerRightClickEmpty(event);
		} else {
			PlayerInteractEvent.LeftClickEmpty event = new PlayerInteractEvent.LeftClickEmpty(pl);
			ItemUseEventHandler.onPlayerLeftClickEmpty(event);

		}
	}

}
