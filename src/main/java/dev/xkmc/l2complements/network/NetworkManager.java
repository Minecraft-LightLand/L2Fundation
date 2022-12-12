package dev.xkmc.l2complements.network;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.serial.network.PacketHandler;
import net.minecraft.resources.ResourceLocation;

import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;

public class NetworkManager {

	public static final PacketHandler HANDLER = new PacketHandler(
			new ResourceLocation(L2Complements.MODID, "main"), 1,
			e -> e.create(EmptyRightClickToServer.class, PLAY_TO_SERVER),
			e -> e.create(TotemUseToClient.class, PLAY_TO_CLIENT)
	);

	public static void register() {
	}

}
