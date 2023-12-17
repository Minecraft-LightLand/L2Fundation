package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ISlotAdderItem;
import dev.xkmc.curseofpandora.content.complex.SlotAdder;
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

import java.util.*;

public class CurseOfPrudenceItem extends ISlotAdderItem<CurseOfPrudenceItem.Ticker> {

	public static final TokenKey<Ticker> KEY = new TokenKey<>(L2Complements.MODID, "curse_of_prudence");

	public static final SlotAdder ADDER = SlotAdder.of("curse_of_prudence", "charm", 3);

	public static int getMaxLevel() {
		return 20;
	}

	public static double getDamageFactor() {
		return 0.5;
	}

	public static int getDuration() {
		return 40;
	}

	public static double getMaxHurtDamage() {
		return 0.3;
	}

	public CurseOfPrudenceItem(Properties properties) {
		super(properties, KEY, Ticker::new, ADDER);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		int dur = getDuration();
		int damage = (int) Math.round(getDamageFactor() * 100);
		int hurt = (int) Math.round(getMaxHurtDamage() * 100);
		list.add(CoPLangData.IDS.CURSE_PRUDENCE_1.get(dur / 20, damage).withStyle(ChatFormatting.RED));
		list.add(CoPLangData.IDS.CURSE_PRUDENCE_2.get(hurt).withStyle(ChatFormatting.RED));
	}

	@SerialClass
	public static class Ticker extends BaseTickingToken implements IAttackListenerToken {

		@SerialClass.SerialField
		public HashMap<UUID, HashSet<Long>> fear = new HashMap<>();

		private boolean sync = false;

		@Override
		protected void removeImpl(Player player) {
			ADDER.removeImpl(player);
			fear.clear();
		}

		@Override
		protected void tickImpl(Player player) {
			ADDER.tickImpl(player);
			Level level = player.level();
			sync = false;
			if (player instanceof ServerPlayer sp) {
				ServerLevel sl = sp.serverLevel();
				sync |= fear.entrySet().removeIf(ent -> {
					if (!(sl.getEntity(ent.getKey()) instanceof LivingEntity le) || !le.isAlive())
						return true;
					sync |= ent.getValue().removeIf(t -> level.getGameTime() >= t + getDuration());
					return ent.getValue().isEmpty();
				});
				if (sync) {
					sync(sp);
				}
			}
		}

		@Override
		public void onPlayerDamageTarget(Player player, AttackCache cache) {
			if (!(player instanceof ServerPlayer sp)) return;
			long time = player.level().getGameTime();
			var target = cache.getAttackTarget();
			Set<Long> list = fear.get(target.getUUID());
			int count = list == null ? 0 : list.size();
			if (count > 0) {
				count = Math.min(count, getMaxLevel());
				cache.addHurtModifier(DamageModifier.multTotal((float) Math.pow(getDamageFactor(), count)));
			}
			fear.computeIfAbsent(target.getUUID(), k -> new HashSet<>()).add(time);
			sync(sp);
			double maxDamage = cache.getAttackTarget().getMaxHealth() * getMaxHurtDamage();
			cache.addDealtModifier(DamageModifier.nonlinearFinal(9000, e -> Math.min(e, (float) maxDamage)));
		}

		private void sync(ServerPlayer sp) {
			//ConditionalData.HOLDER.network.toClientSyncAll(sp);//TODO
		}

	}

}