package dev.xkmc.l2complements.content.item.pandora;

import dev.xkmc.l2complements.content.item.curios.CurioItem;
import dev.xkmc.l2complements.content.item.curios.ICapItem;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import dev.xkmc.l2library.capability.conditionals.Context;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2library.capability.conditionals.TokenProvider;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

public class CurseOfInertiaItem extends CurioItem implements CursePandoraUtil.CurseItem<CurseOfInertiaItem.Ticker> {

	private static final String SLOT = "necklace";

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
	public Ticker getTicker() {
		return new Ticker();
	}

	@Override
	public String getName() {
		return "curse_of_inertia";
	}

	@Override
	public String getSlotId() {
		return SLOT;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(LangData.IDS.CURSE_INERTIA.get(getCap(), getBase(), Math.round(getBonus() * 100)).withStyle(ChatFormatting.GRAY));
	}

	@SerialClass
	public static class Ticker extends CursePandoraUtil.BaseTicker {

		private static final UUID WEAPON_SPEED = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

		protected Ticker() {
			super("inertia", SLOT);
		}

		protected void calculateAttributes(Player player) {
			var attr = player.getAttribute(Attributes.ATTACK_SPEED);
			if (attr == null) return;
			doAttributeLimit(player, attr, Set.of(WEAPON_SPEED));
		}

		@Override
		protected CursePandoraUtil.ValueConsumer curseMult(double finVal, CursePandoraUtil.Mult valMult) {
			return new CurseMult(finVal, valMult);
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

