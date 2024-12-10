package dev.xkmc.l2complements.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.xkmc.l2complements.content.feature.EntityFeature;
import dev.xkmc.l2complements.events.SpecialEquipmentEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Shadow @Nullable protected Player lastHurtByPlayer;

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"), method = "getArmorCoverPercentage")
	public boolean l2complements_getArmorCoverPercentage_hideInvisibleArmorsFromMobs(ItemStack stack, Operation<Boolean> op) {
		LivingEntity self = (LivingEntity) (Object) this;
		return op.call(stack) || !SpecialEquipmentEvents.isVisible(self, stack);
	}

	@Inject(at = @At("HEAD"), method = "canStandOnFluid", cancellable = true)
	public void l2complements_canStandOnFluid_pandora(FluidState state, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity self = (LivingEntity) (Object) this;
		if (SpecialEquipmentEvents.canWalkOn(state, self)) {
			cir.setReturnValue(true);
		}
	}

	@Inject(at = @At("HEAD"), method = "canFreeze", cancellable = true)
	public void l2complements_canFreeze_checkFeature(CallbackInfoReturnable<Boolean> cir) {
		LivingEntity self = (LivingEntity) (Object) this;
		if (EntityFeature.SNOW_WALKER.test(self)) {
			cir.setReturnValue(false);
		}
	}

	@WrapOperation(method = "dropExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"))
	public void l2complements_awardExp(ServerLevel level, Vec3 pos, int exp, Operation<Void> original){
		SpecialEquipmentEvents.dropExp(level, pos, exp, original, lastHurtByPlayer);
	}

}
