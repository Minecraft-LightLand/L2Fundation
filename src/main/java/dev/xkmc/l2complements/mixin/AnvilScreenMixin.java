package dev.xkmc.l2complements.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;closeContainer()V"), method = "keyPressed")
	public void l2complements$removed$preventEarlyRemoval(LocalPlayer player, Operation<Void> op) {
	}

}
