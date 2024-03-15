package dev.xkmc.l2complements.content.feature;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

public record CurioFeaturePredicate(Supplier<Item> item) implements FeaturePredicate {

	@Override
	public boolean test(LivingEntity e) {
		ForgeRegistries.ITEMS.tags().getReverseTag(item.get()).get().getTagKeys();
		return CuriosApi.getCuriosInventory(e).resolve().flatMap(x -> x.findFirstCurio(item.get())).isPresent();
	}

}
