package dev.xkmc.l2complements.content.enchantment.core;

import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

public class BattleEnchantment extends LegacyEnchantment {

	@Nullable
	protected LivingEntity getTarget(Entity target) {
		if (target instanceof LivingEntity le) return le;
		if (target instanceof PartEntity<?> part) {
			if (part.getParent() == target) return null;
			return getTarget(part.getParent());
		}
		return null;
	}

}
