package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TotemicTool extends ExtraToolConfig {

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity) {
		if (entity instanceof Player player) {
			player.heal(amount);
		}
		return super.damageItem(stack, amount, entity);
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LangData.IDS.TOTEMIC_TOOL.get().withStyle(ChatFormatting.GRAY));
	}

	@Override
	public void onDamage(ItemStack pass, AttackCache cache) {
		if (cache.getAttackTarget().getMobType() == MobType.UNDEAD) {
			cache.setDamageModified((float) (cache.getDamageModified() * (1 + LCConfig.COMMON.mobTypeBonus.get())));
		}
	}

}
