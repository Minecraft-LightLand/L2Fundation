package dev.xkmc.l2complements.content.item.misc;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2damagetracker.contents.curios.TotemUseToClient;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2library.util.tools.TeleportTool;
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
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

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
		L2DamageTracker.PACKET_HANDLER.toTrackingPlayers(new TotemUseToClient(self, holded), self);
		self.removeAllEffects();
		self.setHealth(self.getMaxHealth());
		self.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
		self.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
		self.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
		if (self instanceof ServerPlayer player && self.level() instanceof ServerLevel level) {
			player.getCooldowns().addCooldown(this, LCConfig.COMMON.eternalTotemCoolDown.get());
			if (LCConfig.COMMON.eternalTotemGiveWarpStone.get()) {
				ItemStack stone = LCItems.FRAGILE_WARP_STONE.asStack();
				WarpStone.setPos(stone, self.level(), self.getX(), self.getY() + 1e-3, self.getZ());
				player.getInventory().add(stone);
			}
			TeleportTool.teleportHome(level, player);

		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		int time = LCConfig.COMMON.eternalTotemCoolDown.get() / 20;
		Component cd = Component.literal("" + time);
		if (level != null && level.isClientSide()) {
			var player = Proxy.getClientPlayer();
			if (player != null && player.getCooldowns().isOnCooldown(this)) {
				time = (int) (player.getCooldowns().getCooldownPercent(this, 0) * time);
				cd = Component.literal("" + time).withStyle(ChatFormatting.RED);
			}
		}
		list.add(LangData.IDS.TOTEM_ETERNAL.get(cd).withStyle(ChatFormatting.GRAY));
	}

}
