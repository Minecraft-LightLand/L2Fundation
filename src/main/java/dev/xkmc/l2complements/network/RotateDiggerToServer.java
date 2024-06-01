package dev.xkmc.l2complements.network;

import dev.xkmc.l2complements.content.enchantment.digging.DiggerHelper;
import dev.xkmc.l2serial.network.SerialPacketBase;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraftforge.network.NetworkEvent;

@SerialClass
public class RotateDiggerToServer extends SerialPacketBase {

	@SerialClass.SerialField
	public boolean reverse;

	public RotateDiggerToServer() {
	}

	public RotateDiggerToServer(boolean reverse) {
		this.reverse = reverse;
	}

	@Override
	public void handle(NetworkEvent.Context context) {
		var pl = context.getSender();
		if (pl == null) return;
		DiggerHelper.rotateDigger(pl.getMainHandItem(), reverse);
	}

}
