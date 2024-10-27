package dev.xkmc.l2complements.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.xkmc.l2complements.events.MagicEventHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.stream.Stream;

@Mixin(EnchantRandomlyFunction.class)
public class EnchantRandomlyFunctionMixin {

	@WrapOperation(method = "run", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;toList()Ljava/util/List;"))
	public List<Holder<Enchantment>> l2complements$forceOnRandomLoot(Stream<Holder<Enchantment>> instance, Operation<List<Holder<Enchantment>>> original) {
		return original.call(MagicEventHandler.lootEnch(instance));
	}

}
