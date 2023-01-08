package dev.xkmc.l2complements.network;

import dev.xkmc.l2complements.content.item.misc.ILCTotem;
import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.network.SerialPacketBase;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
		Level level = Proxy.getClientWorld();
		Entity entity = level.getEntity(id);
		if (entity == null) return;
		if (!entity.getUUID().equals(uid)) return;
		if (item.isEmpty() || !(item.getItem() instanceof ILCTotem totem)) return;
		totem.onClientTrigger(entity, item);
	}
}
