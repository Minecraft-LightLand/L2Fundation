package dev.xkmc.l2complements.init.data;

import com.tterrag.registrate.providers.RegistrateDataMapProvider;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2damagetracker.init.data.ArmorImmunity;
import net.minecraft.world.effect.MobEffects;

public class LCDataMapGen {

	public static void onGather(RegistrateDataMapProvider pvd) {
		var b = pvd.builder(L2DamageTracker.ARMOR.reg());
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