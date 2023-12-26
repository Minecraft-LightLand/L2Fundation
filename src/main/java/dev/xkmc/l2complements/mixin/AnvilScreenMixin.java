package dev.xkmc.l2complements.mixin;

import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ItemCombinerScreen<AnvilMenu> {

	public AnvilScreenMixin(AnvilMenu p_98901_, Inventory p_98902_, Component p_98903_, ResourceLocation p_98904_) {
		super(p_98901_, p_98902_, p_98903_, p_98904_);
	}

	@Inject(at = @At(value = "HEAD"), method = "keyPressed", cancellable = true)
	public void l2complements$removed$preventEarlyRemoval(int p_97878_, int p_97879_, int p_97880_, CallbackInfoReturnable<Boolean> cir) {
		if (p_97878_ == 256) {
			if (super.keyPressed(p_97878_, p_97879_, p_97880_)) {
				cir.setReturnValue(true);
			}
		}
	}

}
