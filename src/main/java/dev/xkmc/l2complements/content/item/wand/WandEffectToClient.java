package dev.xkmc.l2complements.content.item.wand;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2serial.network.SerialPacketBase;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

@SerialClass
public class WandEffectToClient extends SerialPacketBase {

	@SerialClass.SerialField
	public Type type;
	@SerialClass.SerialField
	public Vec3 vec;
	@SerialClass.SerialField
	public int time;

	public WandEffectToClient() {

	}

	public WandEffectToClient(Type type, Vec3 vec, int time) {
		this.type = type;
		this.vec = vec;
		this.time = time;
	}

	@Override
	public void handle(NetworkEvent.Context context) {
		var level = Proxy.getWorld();
		if (level != null)
			type.handler.handle(level, vec, time);
	}

	public enum Type {
		HELLFIRE_TICK(HellfireWand::renderRegionClient),
		HELLFIRE_TRIGGER(HellfireWand::renderPentagonClient),
		WINTERSTORM(WinterStormWand::tickClient);

		private final Handler handler;

		Type(Handler handler) {
			this.handler = handler;
		}

		public void send(LivingEntity user, Vec3 center, int time) {
			L2Complements.HANDLER.toTrackingPlayers(new WandEffectToClient(this, center, time), user);
		}

	}

	public interface Handler {

		void handle(Level level, Vec3 pos, int time);

	}

}
