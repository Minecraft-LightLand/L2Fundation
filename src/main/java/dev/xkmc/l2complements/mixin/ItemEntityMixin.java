package dev.xkmc.l2complements.mixin;

import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

	@Inject(at = @At("HEAD"), method = "dampensVibrations", cancellable = true)
	public void l2complements_dampensVibrations_sculkiumMinedItem(CallbackInfoReturnable<Boolean> cir) {
		ItemEntity self = (ItemEntity) (Object) this;
		if (self.getPersistentData().contains("dampensVibrations")) {
			cir.setReturnValue(true);
		}
	}

}
