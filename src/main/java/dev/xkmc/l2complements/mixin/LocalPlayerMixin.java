package dev.xkmc.l2complements.mixin;

import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

	@Inject(at = @At("TAIL"), method = "hurtTo")
	public void l2complements_hurtTo_stableBody(float v, CallbackInfo ci) {
		LocalPlayer self = (LocalPlayer) (Object) this;
		ItemStack stack = self.getItemBySlot(EquipmentSlot.CHEST);
		if (stack.getEnchantmentLevel(LCEnchantments.STABLE_BODY.get()) > 0) {
			self.hurtTime = 0;
		}
	}

	@Inject(at = @At("TAIL"), method = "handleEntityEvent")
	public void l2complements_handleEntityEvent_stableBody(byte pId, CallbackInfo ci) {
		LocalPlayer self = (LocalPlayer) (Object) this;
		ItemStack stack = self.getItemBySlot(EquipmentSlot.CHEST);
		if (stack.getEnchantmentLevel(LCEnchantments.STABLE_BODY.get()) > 0) {
			self.hurtTime = 0;
		}
	}

}
