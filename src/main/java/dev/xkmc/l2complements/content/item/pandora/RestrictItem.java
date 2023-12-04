package dev.xkmc.l2complements.content.item.pandora;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.l2complements.content.item.curios.CurioItem;
import dev.xkmc.l2complements.content.item.curios.ICapItem;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2library.capability.conditionals.*;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
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

public class RestrictItem extends CurioItem implements ICapItem<RestrictItem.Data> {

	private static int getCap() {
		return 2;
	}

	private static int getBase() {
		return 1;
	}

	private static double getBonus() {
		return 0.3;
	}

	public RestrictItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(LangData.IDS.BIND.get().withStyle(ChatFormatting.RED));
		list.add(LangData.IDS.RESTRICTION.get(getCap(), getBase(), Math.round(getBonus() * 100)).withStyle(ChatFormatting.GRAY));
	}

	@Override
	public Data create(ItemStack stack) {
		return new Data(this, stack);
	}

	public record Data(RestrictItem item, ItemStack stack) implements ICurio, TokenProvider<Ticker, Data>, Context {

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

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
			Multimap<Attribute, AttributeModifier> ans = HashMultimap.create();
			ans.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "restriction_speed",
					1, AttributeModifier.Operation.MULTIPLY_TOTAL));
			return ans;
		}

		@Override
		public boolean canUnequip(SlotContext ctx) {
			return ctx.entity() instanceof Player player && player.getAbilities().instabuild;
		}

	}

	@SerialClass
	public static class Ticker extends ConditionalToken {

		private static final UUID WEAPON_SPEED = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

		private static final UUID NEGATE_SPEED_ADD = MathHelper.getUUIDFromString("speed_negation_add");
		private static final UUID NEGATE_SPEED_BASE = MathHelper.getUUIDFromString("speed_negation_mult_base");
		private static final UUID NEGATE_SPEED_TOTAL = MathHelper.getUUIDFromString("speed_negation_mult_total");

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
			Set<AttributeModifier> list;
			double value = attr.getBaseValue();
			{
				list = attr.getModifiers(AttributeModifier.Operation.ADDITION);
				double negate = 0;
				double val = 0;
				for (var e : list) {
					if (e.getId().equals(NEGATE_SPEED_ADD)) continue;
					val += e.getAmount();
					if (e.getId().equals(WEAPON_SPEED)) continue;
					if (e.getAmount() > 0) negate += e.getAmount();
				}
				var old = attr.getModifier(NEGATE_SPEED_ADD);
				if (old == null || old.getAmount() != -negate) {
					attr.removeModifier(NEGATE_SPEED_ADD);
					attr.addTransientModifier(new AttributeModifier(NEGATE_SPEED_ADD, "restriction_negate_speed_add",
							-negate, AttributeModifier.Operation.ADDITION));
				}
				value += val;
			}
			{
				list = attr.getModifiers(AttributeModifier.Operation.MULTIPLY_BASE);
				double negate = 0;
				double val = 1;
				for (var e : list) {
					if (e.getId().equals(NEGATE_SPEED_BASE)) continue;
					val += e.getAmount();
					if (e.getAmount() > 0) negate += e.getAmount();
				}
				var old = attr.getModifier(NEGATE_SPEED_BASE);
				if (old == null || old.getAmount() != -negate) {
					attr.removeModifier(NEGATE_SPEED_BASE);
					attr.addTransientModifier(new AttributeModifier(NEGATE_SPEED_BASE, "restriction_negate_speed_mult_base",
							-negate, AttributeModifier.Operation.MULTIPLY_BASE));
				}
				value *= val;
			}

			{
				list = attr.getModifiers(AttributeModifier.Operation.MULTIPLY_TOTAL);
				double negate = 0;
				double val = 1;
				for (var e : list) {
					if (e.getId().equals(NEGATE_SPEED_TOTAL)) continue;
					val *= (1 + e.getAmount());
					if (e.getAmount() > 0) negate *= (1 + e.getAmount());
				}
				value *= val;
				double base = 1;
				if (value > getCap()) {
					negate *= value / getCap();
				} else if (value <= getBase()) {
					base += getBonus();
				}
				negate = base / negate - 1;
				var old = attr.getModifier(NEGATE_SPEED_TOTAL);
				if (old == null || old.getAmount() != negate) {
					attr.removeModifier(NEGATE_SPEED_TOTAL);
					attr.addTransientModifier(new AttributeModifier(NEGATE_SPEED_TOTAL, "restriction_negate_speed_mult_total",
							negate, AttributeModifier.Operation.MULTIPLY_TOTAL));
				}
			}
		}

		private void removeAttributes(Player player) {
			var attr = player.getAttribute(Attributes.ATTACK_SPEED);
			if (attr == null) return;
			attr.removeModifier(NEGATE_SPEED_ADD);
			attr.removeModifier(NEGATE_SPEED_BASE);
			attr.removeModifier(NEGATE_SPEED_TOTAL);
		}

	}

}

