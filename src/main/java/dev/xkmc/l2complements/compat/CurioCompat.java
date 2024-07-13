package dev.xkmc.l2complements.compat;

import dev.xkmc.l2complements.content.item.curios.EffectValidItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;

public class CurioCompat {

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
		if (opt.isPresent()) {
			var curio = opt.get();
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
		return CuriosApi.getCuriosInventory(entity).flatMap(cap ->
				cap.findFirstCurio(e -> e.getItem() instanceof EffectValidItem item &&
						item.isEffectValid(ins, e, entity))).isPresent();
	}

}
