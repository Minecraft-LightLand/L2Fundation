package dev.xkmc.l2magic.content.engine.instance.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2magic.content.engine.core.ConfigurationRegistry;
import dev.xkmc.l2magic.content.engine.core.ConfigurationType;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import dev.xkmc.l2magic.content.engine.variable.DoubleVariable;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public record ItemParticleInstance(Item item, DoubleVariable speed)
		implements ParticleInstance<ItemParticleInstance> {

	public static final Codec<ItemParticleInstance> CODEC = RecordCodecBuilder.create(i -> i.group(
			ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(e -> e.item),
			DoubleVariable.CODEC.fieldOf("speed").forGetter(e -> e.speed)
	).apply(i, ItemParticleInstance::new));

	@Override
	public ConfigurationType<ItemParticleInstance> type() {
		return ConfigurationRegistry.ITEM_PARTICLE.get();
	}

	@Override
	public ParticleOptions particle(EngineContext ctx) {
		return new ItemParticleOption(ParticleTypes.ITEM, item.getDefaultInstance());
	}

}
