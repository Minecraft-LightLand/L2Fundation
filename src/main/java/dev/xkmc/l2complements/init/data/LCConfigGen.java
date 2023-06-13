package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2damagetracker.init.data.ArmorEffectConfig;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;

import java.util.HashSet;
import java.util.Set;

public class LCConfigGen extends ConfigDataProvider {

	public LCConfigGen(DataGenerator generator) {
		super(generator, "L2Complements Armor Config");
	}

	@Override
	public void add(Collector collector) {
		// stats of the material
		// the value represents what if the entire golem is made of this material
		var ans = new ArmorEffectConfig();
		ans.immune.put(LCMats.TOTEMIC_GOLD.armorPrefix(), new HashSet<>(Set.of(MobEffects.POISON, MobEffects.WITHER, MobEffects.HUNGER)));
		ans.immune.put(LCMats.POSEIDITE.armorPrefix(), new HashSet<>(Set.of(MobEffects.DIG_SLOWDOWN)));
		ans.immune.put(LCMats.SCULKIUM.armorPrefix(), new HashSet<>(Set.of(
				MobEffects.DARKNESS, MobEffects.BLINDNESS, MobEffects.CONFUSION,
				MobEffects.DIG_SLOWDOWN, MobEffects.WEAKNESS, MobEffects.MOVEMENT_SLOWDOWN)));
		collector.add(L2DamageTracker.ARMOR, new ResourceLocation(L2Complements.MODID, "default"), ans);

	}

}