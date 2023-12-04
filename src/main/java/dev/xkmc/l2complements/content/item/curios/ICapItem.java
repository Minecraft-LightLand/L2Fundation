package dev.xkmc.l2complements.content.item.curios;

import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurio;

public interface ICapItem<T extends ICurio> {

	T create(ItemStack stack);

}
