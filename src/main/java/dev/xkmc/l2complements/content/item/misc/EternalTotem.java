package dev.xkmc.l2complements.content.item.misc;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.util.Proxy;
import dev.xkmc.l2core.util.TeleportTool;
import dev.xkmc.l2damagetracker.contents.curios.TotemUseToClient;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.function.Consumer;

public class EternalTotem extends Item implements ILCTotem {

	public EternalTotem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean allow(LivingEntity self, DamageSource pDamageSource) {
		return self instanceof Player player && !player.getCooldowns().isOnCooldown(this);
	}

	@Override
	public void trigger(LivingEntity self, ItemStack holded, Consumer<ItemStack> second) {
		L2DamageTracker.PACKET_HANDLER.toTrackingPlayers(TotemUseToClient.of(self, holded), self);
		self.removeAllEffects();
		self.setHealth(self.getMaxHealth());
		self.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
		self.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
		self.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
		if (self instanceof ServerPlayer player && self.level() instanceof ServerLevel level) {
			player.getCooldowns().addCooldown(this, LCConfig.SERVER.eternalTotemCoolDown.get());
			if (LCConfig.SERVER.eternalTotemGiveWarpStone.get()) {
				ItemStack stone = LCItems.FRAGILE_WARP_STONE.asStack();
				WarpStone.set(stone, self.level(), self);
				player.getInventory().add(stone);
			}
			TeleportTool.teleportHome(level, player);

		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag flag) {
		int time = LCConfig.SERVER.eternalTotemCoolDown.get() / 20;
		Component cd = Component.literal("" + time);
		if (level.registries() != null) {
			var player = Proxy.getClientPlayer();
			if (player != null && player.getCooldowns().isOnCooldown(this)) {
				time = (int) (player.getCooldowns().getCooldownPercent(this, 0) * time);
				cd = Component.literal("" + time).withStyle(ChatFormatting.RED);
			}
		}
		list.add(LangData.IDS.TOTEM_ETERNAL.get(cd).withStyle(ChatFormatting.GRAY));
	}

}
