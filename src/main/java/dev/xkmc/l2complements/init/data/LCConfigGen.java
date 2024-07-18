package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2damagetracker.init.data.ArmorImmunity;
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
		b.add(LCMats.TOTEMIC_GOLD.getArmorMaterial(), ArmorImmunity.of(false,
				MobEffects.POISON.value(), MobEffects.WITHER.value(), MobEffects.HUNGER.value()
		), false);
		b.add(LCMats.POSEIDITE.getArmorMaterial(), ArmorImmunity.of(false,
				MobEffects.DIG_SLOWDOWN.value()
		), false);
		b.add(LCMats.POSEIDITE.getArmorMaterial(), ArmorImmunity.of(false,
				MobEffects.DIG_SLOWDOWN.value()
		), false);
		b.add(LCMats.SCULKIUM.getArmorMaterial(), ArmorImmunity.of(false,
				MobEffects.DARKNESS.value(), MobEffects.BLINDNESS.value(), MobEffects.CONFUSION.value(),
				MobEffects.DIG_SLOWDOWN.value(), MobEffects.WEAKNESS.value(), MobEffects.MOVEMENT_SLOWDOWN.value()
		), false);
	}

}