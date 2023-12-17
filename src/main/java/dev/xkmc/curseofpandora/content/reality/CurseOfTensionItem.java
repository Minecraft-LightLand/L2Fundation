package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CurseOfTensionItem extends ITokenProviderItem<CurseOfTensionItem.Ticker> {

	public static final TokenKey<Ticker> KEY = new TokenKey<>(L2Complements.MODID, "curse_of_tension");

	public static int getPenaltyDuration() {
		return 60;
	}

	public static double getDamageThreshold() {
		return 0.1;
	}

	public static int getTokenMature() {
		return 200;
	}

	public static int getTokenLife() {
		return 200;
	}

	public static float getDamageBonus() {
		return 0.2f;
	}

	public static int getMaxLevel() {
		return 5;
	}

	public CurseOfTensionItem(Properties properties) {
		super(properties, KEY, Ticker::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		int tkMat = getTokenMature();
		int tkLife = getTokenLife();
		int dur = getPenaltyDuration();
		int max = getMaxLevel();
		int damage = Math.round(getDamageBonus() * 100);
		int th = (int) Math.round(getDamageThreshold() * 100);
		list.add(CoPLangData.IDS.CURSE_TENSION_1.get(tkMat / 20, damage, tkLife / 20, max).withStyle(ChatFormatting.GRAY));
		list.add(CoPLangData.IDS.CURSE_TENSION_2.get(th, dur).withStyle(ChatFormatting.RED));
	}

	@SerialClass
	public static class Ticker extends BaseTickingToken implements IAttackListenerToken {

		@SerialClass.SerialField
		public HashMap<UUID, Long> terror = new HashMap<>();


		@SerialClass.SerialField
		public HashMap<UUID, ArrayList<Long>> brave = new HashMap<>();

		private boolean sync = false;

		@Override
		protected void removeImpl(Player player) {
			terror.clear();
		}

		@Override
		protected void tickImpl(Player player) {
			Level level = player.level();
			sync = false;
			if (player instanceof ServerPlayer sp) {
				ServerLevel sl = sp.serverLevel();
				sync = terror.entrySet().removeIf(e ->
						!(sl.getEntity(e.getKey()) instanceof LivingEntity le && le.isAlive()) ||
								level.getGameTime() > e.getValue() + getPenaltyDuration());
				sync |= brave.entrySet().removeIf(ent -> {
					if (!(sl.getEntity(ent.getKey()) instanceof LivingEntity le) || !le.isAlive())
						return true;
					sync |= ent.getValue().removeIf(t -> level.getGameTime() > t + getTokenLife() + getTokenMature());
					return ent.getValue().isEmpty();
				});
				if (sync) {
					sync(sp);
				}
			}
		}

		@Override
		public void onPlayerDamagedFinal(Player player, AttackCache cache) {
			if (!(player instanceof ServerPlayer sp)) return;
			var attacker = cache.getAttacker();
			if (attacker == null) return;
			if (cache.getDamageDealt() >= player.getMaxHealth() * getDamageThreshold())
				terror.put(attacker.getUUID(), player.level().getGameTime());
			brave.remove(attacker.getUUID());
			sync(sp);
		}

		@Override
		public void onPlayerHurtTarget(Player player, AttackCache cache) {
			if (!(player instanceof ServerPlayer sp)) return;
			long time = player.level().getGameTime();
			var target = cache.getAttackTarget();
			List<Long> list = brave.get(target.getUUID());
			int count = 0;
			if (list != null) {
				for (var e : list) {
					if (time > e + getTokenMature()) {
						count++;
					}
				}
			}
			if (count > 0) {
				count = Math.min(count, getMaxLevel());
				cache.addHurtModifier(DamageModifier.multTotal(1 + count * getDamageBonus()));
			}
			brave.computeIfAbsent(target.getUUID(), k -> new ArrayList<>()).add(time);
			sync(sp);
		}

		private void sync(ServerPlayer sp) {
			//ConditionalData.HOLDER.network.toClientSyncAll(sp);//TODO
		}

	}

}
