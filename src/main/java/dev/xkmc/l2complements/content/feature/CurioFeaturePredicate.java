package dev.xkmc.l2complements.content.feature;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

public record CurioFeaturePredicate(Supplier<Item> item) implements FeaturePredicate {

	@Override
	public boolean test(LivingEntity e) {
		return CuriosApi.getCuriosInventory(e).flatMap(x -> x.findFirstCurio(item.get())).isPresent();
	}

}
