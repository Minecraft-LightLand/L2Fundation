package dev.xkmc.l2complements.content.item.equipments;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LCLang;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.contents.materials.generic.ExtraToolConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public class TotemicTool extends ExtraToolConfig {

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity) {
		if (entity instanceof Player player) {
			player.heal(amount);
		}
		return super.damageItem(stack, amount, entity);
	}

	@Override
	public void addTooltip(ItemStack stack, List<Component> list) {
		list.add(LCLang.IDS.TOTEMIC_TOOL.get().withStyle(ChatFormatting.GRAY));
	}

	@Override
	public void onDamage(DamageData.Offence cache, ItemStack stack) {
		if (cache.getTarget().getType().is(EntityTypeTags.SENSITIVE_TO_SMITE)) {
			cache.addHurtModifier(DamageModifier.multAttr((float) (1 + LCConfig.SERVER.mobTypeBonus.get()),
					LCMats.POSEIDITE.id().withSuffix("_smite")));
		}
	}

}
