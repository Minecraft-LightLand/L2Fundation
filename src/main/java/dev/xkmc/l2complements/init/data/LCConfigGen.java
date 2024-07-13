package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2damagetracker.init.data.ArmorEffectConfig;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class LCConfigGen extends DataMapProvider {

	public LCConfigGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
		super(output, lookup);
	}

	@Override
	protected void gather() {
		var b = builder(L2DamageTracker.ARMOR.reg());

		collector.add(L2DamageTracker.ARMOR, L2Complements.loc("default"), new ArmorEffectConfig()
				.add(LCMats.TOTEMIC_GOLD.armorPrefix(), MobEffects.POISON, MobEffects.WITHER, MobEffects.HUNGER)
				.add(LCMats.POSEIDITE.armorPrefix(), MobEffects.DIG_SLOWDOWN)
				.add(LCMats.POSEIDITE.armorPrefix(), MobEffects.DIG_SLOWDOWN)
				.add(LCMats.SCULKIUM.armorPrefix(),
						MobEffects.DARKNESS, MobEffects.BLINDNESS, MobEffects.CONFUSION,
						MobEffects.DIG_SLOWDOWN, MobEffects.WEAKNESS, MobEffects.MOVEMENT_SLOWDOWN));
	}

}