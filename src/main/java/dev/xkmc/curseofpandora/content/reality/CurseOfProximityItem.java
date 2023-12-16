package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.l2complements.content.item.curios.CurioItem;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class CurseOfProximityItem extends CurioItem implements CursePandoraUtil.CurseItem<CurseOfProximityItem.Ticker> {

	private static final String SLOT = "bracelet";

	private static int getCap() {
		return 6;
	}

	private static double getBase() {
		return 2;
	}

	private static double getBonus() {
		return 0.5;
	}

	public CurseOfProximityItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(LangData.IDS.CURSE_PROXIMITY.get(getCap(), getBase(), Math.round(getBonus() * 100)).withStyle(ChatFormatting.GRAY));
	}

	@Override
	public Ticker getTicker() {
		return new Ticker();
	}

	@Override
	public String getName() {
		return "curse_of_proximity";
	}

	@Override
	public String getSlotId() {
		return SLOT;
	}

	@SerialClass
	public static class Ticker extends CursePandoraUtil.BaseTicker {

		protected Ticker() {
			super("proximity", SLOT);
		}

		@Override
		protected CursePandoraUtil.ValueConsumer curseMult(double finVal, CursePandoraUtil.Mult valMult) {
			return new CurseMult(finVal, valMult);
		}

		protected void calculateAttributes(Player player) {
			var attr = player.getAttribute(ForgeMod.ENTITY_REACH.get());
			if (attr == null) return;
			var map = player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND);
			var list = map.get(ForgeMod.ENTITY_REACH.get());
			var set = list.stream().map(AttributeModifier::getId).collect(Collectors.toSet());
			doAttributeLimit(player, attr, set);
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

