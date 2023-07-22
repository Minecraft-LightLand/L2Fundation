package dev.xkmc.l2complements.network;

import dev.xkmc.l2serial.network.SerialPacketBase;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;

@SerialClass
public class TotemUseToClient extends SerialPacketBase {

	@SerialClass.SerialField
	public int id;
	@SerialClass.SerialField
	public UUID uid;
	@SerialClass.SerialField
	public ItemStack item;

	@Deprecated
	public TotemUseToClient() {

	}

	public TotemUseToClient(Entity entity, ItemStack stack) {
		id = entity.getId();
		uid = entity.getUUID();
		item = stack.copy();
	}

	@Override
	public void handle(NetworkEvent.Context context) {
		ClientHandlers.handleTotemUse(item, id, uid);
	}
}
