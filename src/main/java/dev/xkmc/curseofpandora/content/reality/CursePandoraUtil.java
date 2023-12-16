package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.l2complements.content.item.curios.ICapItem;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.capability.conditionals.*;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public class CursePandoraUtil {

	public static void remove(AttributeInstance attr, AttributeModifier.Operation op, UUID negId, String negName,
							  Set<UUID> ignore, ValueConsumer negate, ValueConsumer val) {
		Set<AttributeModifier> list = attr.getModifiers(op);
		for (var e : list) {
			if (e.getId().equals(negId)) continue;
			val.accept(e.getAmount());
			if (ignore.contains(e.getId())) continue;
			if (e.getAmount() > 0) negate.accept(e.getAmount());
		}
		var old = attr.getModifier(negId);
		double mod = negate.reverse();
		if (old == null || old.getAmount() != mod) {
			attr.removeModifier(negId);
			attr.addTransientModifier(new AttributeModifier(negId, negName, mod, op));
		}
		val.accept(mod);
	}

	public static List<Component> getAttributesTooltip(List<Component> tooltips, String type) {
		tooltips = new ArrayList<>(tooltips);
		tooltips.add((Component.translatable(
				"attribute.modifier.plus." + AttributeModifier.Operation.ADDITION.toValue(),
				ATTRIBUTE_MODIFIER_FORMAT.format(1),
				Component.translatable("curios.identifier." + type)))
				.withStyle(ChatFormatting.BLUE));
		return tooltips;
	}

	public static void removeModifier(LivingEntity player, String slot, UUID id) {
		var opt = CuriosApi.getCuriosInventory(player).resolve()
				.flatMap(x -> x.getStacksHandler(slot));
		if (opt.isEmpty()) return;
		opt.get().removeModifier(id);
	}

	public static void addModifier(LivingEntity player, String slot, UUID id, String name) {
		var opt = CuriosApi.getCuriosInventory(player).resolve()
				.flatMap(x -> x.getStacksHandler(slot));
		if (opt.isEmpty()) return;
		var old = opt.get().getModifiers().get(id);
		if (old == null || old.getAmount() != 1) {
			opt.get().removeModifier(id);
			opt.get().addTransientModifier(new AttributeModifier(id, name,
					1, AttributeModifier.Operation.ADDITION));
		}
	}

	public interface CurseItem<T extends BaseTicker> extends ICapItem<Data<T>> {

		T getTicker();

		String getName();

		String getSlotId();

		@Override
		default Data<T> create(ItemStack stack) {
			return new Data<>(stack, this, getSlotId());
		}
	}

	@SerialClass
	public static abstract class BaseTicker extends ConditionalToken {

		protected final String baseName, slotId;
		protected final UUID add, base, total, slot;
		@SerialClass.SerialField
		public int life;

		protected BaseTicker(String baseName, String slotId) {
			add = MathHelper.getUUIDFromString(baseName + "_add");
			base = MathHelper.getUUIDFromString(baseName + "_mult_base");
			total = MathHelper.getUUIDFromString(baseName + "_mult_total");
			slot = MathHelper.getUUIDFromString(baseName + "_" + slotId);
			this.baseName = baseName;
			this.slotId = slotId;
		}

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

		protected abstract void calculateAttributes(Player player);

		protected abstract ValueConsumer curseMult(double finVal, CursePandoraUtil.Mult valMult);

		protected final void doAttributeLimit(Player player, AttributeInstance attr, Set<UUID> set) {
			CursePandoraUtil.Add valAdd = new CursePandoraUtil.Add();
			CursePandoraUtil.remove(attr, AttributeModifier.Operation.ADDITION,
					add, baseName + "_negate_add",
					set, new CursePandoraUtil.Add(), valAdd);
			CursePandoraUtil.Add valBase = new CursePandoraUtil.Add();
			CursePandoraUtil.remove(attr, AttributeModifier.Operation.MULTIPLY_BASE,
					base, baseName + "_negate_base",
					Set.of(), new CursePandoraUtil.Add(), valBase);
			double finVal = (attr.getBaseValue() + valAdd.get()) * (1 + valBase.get());
			CursePandoraUtil.Mult valMult = new CursePandoraUtil.Mult();
			CursePandoraUtil.remove(attr, AttributeModifier.Operation.MULTIPLY_TOTAL,
					total, baseName + "_negate_total",
					Set.of(), curseMult(finVal, valMult), valMult);
			CursePandoraUtil.addModifier(player, slotId, slot, baseName + "_bonus");
		}

		protected void removeAttributes(Player player) {
			var attr = player.getAttribute(ForgeMod.ENTITY_REACH.get());
			if (attr == null) return;
			attr.removeModifier(add);
			attr.removeModifier(base);
			attr.removeModifier(total);
			CursePandoraUtil.removeModifier(player, slotId, slot);
		}

		public void update() {
			life = 2;
		}

	}

	public record Data<T extends BaseTicker>(ItemStack stack, CurseItem<T> item, String slot)
			implements ICurio, TokenProvider<T, Data<T>>, Context {

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
		public List<Component> getAttributesTooltip(List<Component> tooltips) {
			return CursePandoraUtil.getAttributesTooltip(tooltips, slot);
		}

		@Override
		public T getData(Data data) {
			return item.getTicker();
		}

		@Override
		public TokenKey<T> getKey() {
			return new TokenKey<>(L2Complements.MODID, item.getName());
		}

	}

	public interface ValueConsumer {

		void accept(double val);

		double get();

		double reverse();
	}

	public static class Add implements ValueConsumer {

		private double val;

		public Add() {
			val = 0;
		}

		@Override
		public void accept(double v) {
			val += v;
		}

		@Override
		public double get() {
			return val;
		}

		@Override
		public double reverse() {
			return -val;
		}

	}

	public static class Mult implements ValueConsumer {

		protected double val;

		public Mult() {
			val = 1;
		}

		@Override
		public void accept(double v) {
			val *= 1 + v;
		}

		@Override
		public double get() {
			return val;
		}

		@Override
		public double reverse() {
			return 1 / val - 1;
		}

	}

}
