package dev.xkmc.l2foundation.network;

import dev.xkmc.l2foundation.events.ItemUseEventHandler;
import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.network.SerialPacketBase;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.network.NetworkEvent;

@SerialClass
public class EmptyRightClickToServer extends SerialPacketBase {

	@SerialClass.SerialField
	public boolean hand, right;

	public EmptyRightClickToServer(boolean right, boolean hand) {
		this.right = right;
		this.hand = hand;
	}

	@Deprecated
	public EmptyRightClickToServer() {
		this(true, true);
	}

	public void handle(NetworkEvent.Context ctx) {
		ServerPlayer pl = ctx.getSender();
		if (pl != null) {
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

	public void toServer() {
		NetworkManager.HANDLER.toServer(this);
	}


}
