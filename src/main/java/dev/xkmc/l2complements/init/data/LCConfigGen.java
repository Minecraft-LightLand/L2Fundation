package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2library.init.data.ArmorEffectConfig;
import dev.xkmc.l2library.init.data.L2ConfigManager;
import dev.xkmc.l2library.serial.config.BaseConfig;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.effect.MobEffects;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LCConfigGen extends ConfigDataProvider {

	public LCConfigGen(DataGenerator generator) {
		super(generator, "data/" + L2Complements.MODID + "/l2library_config/", "L2Complements Armor Config");
	}

	@Override
	public void add(Map<String, BaseConfig> map) {
		// stats of the material
		// the value represents what if the entire golem is made of this material
		var ans = new ArmorEffectConfig();
		ans.immune.put(LCMats.TOTEMIC_GOLD.armorPrefix(), new HashSet<>(Set.of(MobEffects.POISON, MobEffects.WITHER, MobEffects.HUNGER)));
		ans.immune.put(LCMats.POSEIDITE.armorPrefix(), new HashSet<>(Set.of(MobEffects.DIG_SLOWDOWN)));
		ans.immune.put(LCMats.SCULKIUM.armorPrefix(), new HashSet<>(Set.of(
				MobEffects.DARKNESS, MobEffects.BLINDNESS, MobEffects.CONFUSION,
				MobEffects.DIG_SLOWDOWN, MobEffects.WEAKNESS, MobEffects.MOVEMENT_SLOWDOWN)));
		map.put(L2ConfigManager.ARMOR.getID() + "/default", ans);

	}

}