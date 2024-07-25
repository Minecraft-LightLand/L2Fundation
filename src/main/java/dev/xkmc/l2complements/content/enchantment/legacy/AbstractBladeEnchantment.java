package dev.xkmc.l2complements.content.enchantment.legacy;

import dev.xkmc.l2core.init.reg.ench.CustomDescEnchantment;
import dev.xkmc.l2core.init.reg.ench.EnchColor;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class AbstractBladeEnchantment extends LegacyEnchantment implements CustomDescEnchantment {

	public void onAttack(LivingEntity attacker, LivingEntity target, int level) {
	}

}
