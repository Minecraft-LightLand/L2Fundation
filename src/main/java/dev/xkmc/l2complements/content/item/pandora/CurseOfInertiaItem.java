package dev.xkmc.l2complements.content.item.pandora;

import dev.xkmc.l2complements.content.item.curios.CurioItem;
import dev.xkmc.l2complements.content.item.curios.ICapItem;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2library.capability.conditionals.*;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CurseOfInertiaItem extends CurioItem implements ICapItem<CurseOfInertiaItem.Data> {

	private static int getCap() {
		return 3;
	}

	private static double getBase() {
		return 0.5;
	}

	private static double getBonus() {
		return 0.5;
	}

	public CurseOfInertiaItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(LangData.IDS.CURSE_INERTIA.get(getCap(), getBase(), Math.round(getBonus() * 100)).withStyle(ChatFormatting.GRAY));
	}

	@Override
	public Data create(ItemStack stack) {
		return new Data(this, stack);
	}

	public record Data(CurseOfInertiaItem item, ItemStack stack)
			implements ICurio, TokenProvider<Ticker, Data>, Context {

		@Override
		public ItemStack getStack() {
			return stack;
		}

		@Override
		public void curioTick(SlotContext slotContext) {
			if (slotContext.entity() instanceof Player player && player.isAlive()) {
				ConditionalData.HOLDER.get(player).getOrCreateData(this, this).update();
			}
		}

		@Override
		public Ticker getData(Data data) {
			return new Ticker();
		}

		@Override
		public TokenKey<Ticker> getKey() {
			return new TokenKey<>(L2Complements.MODID, "restriction_speed");
		}

	}

	@SerialClass
	public static class Ticker extends ConditionalToken {

		private static final UUID NEGATE_SPEED_ADD = MathHelper.getUUIDFromString("speed_negation_add");
		private static final UUID NEGATE_SPEED_BASE = MathHelper.getUUIDFromString("speed_negation_mult_base");
		private static final UUID NEGATE_SPEED_TOTAL = MathHelper.getUUIDFromString("speed_negation_mult_total");

		private static final UUID WEAPON_SPEED = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

		@SerialClass.SerialField
		public int life;

		public boolean tick(Player player) {
			if (life > 0)
				life--;
			if (life <= 0) {
				removeAttributes(player);
			} else {
				calculateAttributes(player);
			}
			return life <= 0;
		}

		public void update() {
			life = 2;
		}

		private void calculateAttributes(Player player) {
			var attr = player.getAttribute(Attributes.ATTACK_SPEED);
			if (attr == null) return;
			CursePandoraUtil.Add valAdd = new CursePandoraUtil.Add();
			CursePandoraUtil.remove(attr, AttributeModifier.Operation.ADDITION,
					NEGATE_SPEED_ADD, "restriction_negate_speed_add",
					Set.of(WEAPON_SPEED), new CursePandoraUtil.Add(), valAdd);
			CursePandoraUtil.Add valBase = new CursePandoraUtil.Add();
			CursePandoraUtil.remove(attr, AttributeModifier.Operation.MULTIPLY_BASE,
					NEGATE_SPEED_BASE, "restriction_negate_speed_base",
					Set.of(), new CursePandoraUtil.Add(), valBase);
			double finVal = (attr.getBaseValue() + valAdd.get()) * (1 + valBase.get());
			CursePandoraUtil.Mult valMult = new CursePandoraUtil.Mult();
			CursePandoraUtil.remove(attr, AttributeModifier.Operation.MULTIPLY_TOTAL,
					NEGATE_SPEED_TOTAL, "restriction_negate_speed_total",
					Set.of(), new CurseMult(finVal, valMult), valMult);
		}

		private void removeAttributes(Player player) {
			var attr = player.getAttribute(Attributes.ATTACK_SPEED);
			if (attr == null) return;
			attr.removeModifier(NEGATE_SPEED_ADD);
			attr.removeModifier(NEGATE_SPEED_BASE);
			attr.removeModifier(NEGATE_SPEED_TOTAL);
		}

	}

	private static class CurseMult extends CursePandoraUtil.Mult {

		private final double finVal;
		private final CursePandoraUtil.ValueConsumer last;

		private CurseMult(double finVal, CursePandoraUtil.ValueConsumer last) {
			this.finVal = finVal;
			this.last = last;
		}

		@Override
		public double reverse() {
			double base = 1;
			double fv = finVal * last.get() / val;
			if (fv > getCap()) {
				val *= fv / getCap();
			} else if (fv <= getBase()) {
				base += getBonus();
			}
			return base / val - 1;
		}

	}

}

