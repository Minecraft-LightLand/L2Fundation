package dev.xkmc.l2complements.network;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.serial.config.ConfigMerger;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.serial.network.PacketHandlerWithConfig;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;

public enum NetworkManager {
	;

	public String getID() {
		return name().toLowerCase(Locale.ROOT);
	}

	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(L2Complements.MODID, "main"), 2, L2Complements.MODID,
			e -> e.create(EmptyRightClickToServer.class, PLAY_TO_SERVER),
			e -> e.create(TotemUseToClient.class, PLAY_TO_CLIENT)
	);

	public <T extends BaseConfig> T getMerged() {
		return HANDLER.getCachedConfig(getID());
	}

	public static void register() {
	}

}
