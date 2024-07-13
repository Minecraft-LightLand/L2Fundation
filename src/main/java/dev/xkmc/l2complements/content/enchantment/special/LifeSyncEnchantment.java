package dev.xkmc.l2complements.content.enchantment.special;

import dev.xkmc.l2complements.init.data.DamageTypeGen;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;

public class LifeSyncEnchantment {

	public static DamageSource getSource(Level level) {
		return new DamageSource(DamageTypeGen.forKey(level, DamageTypeGen.LIFE_SYNC));
	}

}
