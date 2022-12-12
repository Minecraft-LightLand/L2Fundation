package dev.xkmc.l2complements.compat;

import com.mojang.datafixers.util.Pair;
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
		var opt = CuriosApi.getCuriosHelper().getCuriosHandler(self);
		if (opt.resolve().isPresent()) {
			var curio = opt.resolve().get();
			for (var handler : curio.getCurios().values()) {
				var stacks = handler.getStacks();
				int n = stacks.getSlots();
				for (int i = 0; i < n; i++) {
					int finalI = i;
					ans.add(Pair.of(stacks.getStackInSlot(i), stack -> stacks.setStackInSlot(finalI, stack)));
				}
			}
		}
	}

}
