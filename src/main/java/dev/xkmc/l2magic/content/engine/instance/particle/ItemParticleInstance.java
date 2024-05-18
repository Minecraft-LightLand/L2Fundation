package dev.xkmc.l2magic.content.engine.instance.particle;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public record ItemParticleInstance(@Nullable Item item, String speed)
		implements ParticleInstance<ItemParticleInstance> {

	@Nullable
	@Override
	public ParticleOptions particle() {
		if (item == null) return null;
		return new ItemParticleOption(ParticleTypes.ITEM, item.getDefaultInstance());
	}

	@Override
	public boolean verify(Logger logger, String path) {
		if (item == null || item == Items.AIR) {
			logger.error(path + ": [item] is not a valid item id");
			return false;
		}
		return true;
	}

}
