package dev.xkmc.l2complements.compat;

import com.mojang.datafixers.util.Pair;
import dev.xkmc.l2complements.content.item.curios.EffectValidItem;
import dev.xkmc.l2complements.init.data.TagGen;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CurioCompat {

	public static List<Pair<ItemStack, Consumer<ItemStack>>> totemSlots(LivingEntity self) {
		List<Pair<ItemStack, Consumer<ItemStack>>> ans = new ArrayList<>();
		ans.add(Pair.of(self.getMainHandItem(), stack -> self.setItemSlot(EquipmentSlot.MAINHAND, stack)));
		ans.add(Pair.of(self.getOffhandItem(), stack -> self.setItemSlot(EquipmentSlot.OFFHAND, stack)));
		if (ModList.get().isLoaded("curios")) {
			curioTotemSlots(self, ans);
		}
		return ans;
	}

	private static void curioTotemSlots(LivingEntity self, List<Pair<ItemStack, Consumer<ItemStack>>> ans) {
		var opt = CuriosApi.getCuriosInventory(self);
		if (opt.resolve().isPresent()) {
			var curio = opt.resolve().get();
			for (var handler : curio.getCurios().values()) {
				var stacks = handler.getStacks();
				int n = stacks.getSlots();
				for (int i = 0; i < n; i++) {
					int finalI = i;
					if (stacks.getStackInSlot(i).is(TagGen.TOTEM)) {
						ans.add(Pair.of(stacks.getStackInSlot(i), stack -> stacks.setStackInSlot(finalI, stack)));
					}
				}
			}
		}
	}

	public static List<ItemStack> getAllSlots(LivingEntity le) {
		List<ItemStack> list = new ArrayList<>();
		for (EquipmentSlot e : EquipmentSlot.values()) {
			list.add(le.getItemBySlot(e));
		}
		if (ModList.get().isLoaded("curios")) {
			fillSlots(le, list);
		}
		return list;
	}

	private static void fillSlots(LivingEntity le, List<ItemStack> list) {
		var opt = CuriosApi.getCuriosInventory(le);
		if (opt.resolve().isPresent()) {
			var curio = opt.resolve().get();
			for (var handler : curio.getCurios().values()) {
				var stacks = handler.getStacks();
				int n = stacks.getSlots();
				for (int i = 0; i < n; i++) {
					var stack = stacks.getStackInSlot(i);
					if (!stack.isEmpty())
						list.add(stack);
				}
			}
		}
	}

	public static boolean testEffect(MobEffectInstance ins, LivingEntity entity) {
		if (ModList.get().isLoaded("curios")) {
			return testEffectImpl(ins, entity);
		}
		return false;
	}

	private static boolean testEffectImpl(MobEffectInstance ins, LivingEntity entity) {
		return CuriosApi.getCuriosInventory(entity).resolve().flatMap(cap ->
				cap.findFirstCurio(e -> e.getItem() instanceof EffectValidItem item &&
						item.isEffectValid(ins, e, entity))).isPresent();
	}

}
