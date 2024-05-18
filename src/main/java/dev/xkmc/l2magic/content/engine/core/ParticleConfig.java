package dev.xkmc.l2magic.content.engine.core;

import dev.xkmc.l2library.serial.config.BaseConfig;
import dev.xkmc.l2library.serial.config.CollectType;
import dev.xkmc.l2library.serial.config.ConfigCollect;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;


@SerialClass
public class ParticleConfig extends BaseConfig {

	@ConfigCollect(CollectType.MAP_OVERWRITE)
	@SerialClass.SerialField
	public final Map<ResourceLocation, EngineConfiguration> engines = new LinkedHashMap<>();

}
