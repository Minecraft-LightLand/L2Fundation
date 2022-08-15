package dev.xkmc.l2foundation.init.data;

import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.serial.network.ConfigDataProvider;
import dev.xkmc.l2foundation.network.ConfigType;
import net.minecraft.data.DataGenerator;

import java.util.Map;

public class ConfigGenDispatcher extends ConfigDataProvider {

	public ConfigGenDispatcher(DataGenerator generator) {
		super(generator, "data/l2foundation/l2foundation_config/", "L2Foundation Json Config Generator");
	}

	@Override
	public void add(Map<String, BaseConfig> map) {
		SlimeConfigGen.addPotionDrop((id, config) -> map.put(ConfigType.POTION_SLIME_DROP.getID() + "/" + id, config));
	}

}
