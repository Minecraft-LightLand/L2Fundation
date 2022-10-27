package dev.xkmc.l2foundation.network;

import dev.xkmc.l2foundation.init.L2Foundation;
import dev.xkmc.l2library.serial.network.PacketHandler;
import net.minecraft.resources.ResourceLocation;

import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;

public class NetworkManager {

	static final PacketHandler HANDLER = new PacketHandler(
			new ResourceLocation(L2Foundation.MODID, "main"), 1,
			e -> e.create(EmptyRightClickToServer.class, PLAY_TO_SERVER)
	);

	public static void register() {
	}

}
