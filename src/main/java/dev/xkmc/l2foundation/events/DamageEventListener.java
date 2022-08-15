package dev.xkmc.l2foundation.events;

import dev.xkmc.l2foundation.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2foundation.content.item.generic.GenericTieredItem;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.init.events.attack.AttackListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class DamageEventListener implements AttackListener {

	@Override
	public void onAttack(AttackCache cache, ItemStack weapon) {
		LivingAttackEvent event = cache.getLivingAttackEvent();
		assert event != null;
		DamageSource source = event.getSource();
		if (weapon.getItem() instanceof GenericTieredItem gen) {
			ExtraToolConfig config = gen.getExtraConfig();
			if (config.bypassMagic && !source.isBypassMagic()) source.bypassMagic();
			if (config.bypassArmor && !source.isBypassArmor()) source.bypassArmor();
		}
	}

}
