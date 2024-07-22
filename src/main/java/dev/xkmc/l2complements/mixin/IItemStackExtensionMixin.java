package dev.xkmc.l2complements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IItemStackExtension.class)
public interface IItemStackExtensionMixin {

	@Shadow
	int getEnchantmentLevel(Holder<Enchantment> enchantment);

	@Inject(at = @At("HEAD"), method = "makesPiglinsNeutral", cancellable = true)
	default void l2complements_makesPiglinsNeutral_enchantOverride(LivingEntity wearer, CallbackInfoReturnable<Boolean> cir) {
		if (getEnchantmentLevel(LCEnchantments.SHINNY.holder()) > 0) {
			cir.setReturnValue(true);
		}
	}

	@Inject(at = @At("HEAD"), method = "isPiglinCurrency", cancellable = true)
	default void l2complements_isPiglinCurrency_enchantOverride(CallbackInfoReturnable<Boolean> cir) {
		if (getEnchantmentLevel(LCEnchantments.SHINNY.holder()) > 0) {
			cir.setReturnValue(true);
		}
	}

	@Inject(at = @At("HEAD"), method = "isEnderMask", cancellable = true)
	default void l2complements_isEnderMask_enchantOverride(CallbackInfoReturnable<Boolean> cir) {
		if (getEnchantmentLevel(LCEnchantments.ENDER_MASK.holder()) > 0) {
			cir.setReturnValue(true);
		}
	}

	@Inject(at = @At("HEAD"), method = "canWalkOnPowderedSnow", cancellable = true)
	default void l2complements_canWalkOnPowderedSnow_enchantOverride(CallbackInfoReturnable<Boolean> cir) {
		if (getEnchantmentLevel(LCEnchantments.SNOW_WALKER.holder()) > 0) {
			cir.setReturnValue(true);
		}
	}

	@ModifyReturnValue(at = @At("RETURN"), method = "getSweepHitBox", remap = false)
	default AABB l2complements_getSweepHitBox_enchantOverride(AABB box) {
		int lv = getEnchantmentLevel(LCEnchantments.WIND_SWEEP.holder());
		if (lv > 0) {
			double amount = LCConfig.SERVER.windSweepIncrement.get();
			box = box.inflate(amount * lv, amount * lv, amount * lv);
		}
		return box;
	}


}
