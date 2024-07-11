package dev.xkmc.l2complements.network;

import dev.xkmc.l2complements.content.enchantment.digging.DiggerHelper;
import dev.xkmc.l2serial.network.SerialPacketBase;
import net.minecraft.world.entity.player.Player;

public record RotateDiggerToServer(boolean reverse)
		implements SerialPacketBase<RotateDiggerToServer> {

	@Override
	public void handle(Player pl) {
		DiggerHelper.rotateDigger(pl.getMainHandItem(), reverse);
	}

}
