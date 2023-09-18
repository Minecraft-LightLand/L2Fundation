package dev.xkmc.l2complements.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.xkmc.l2complements.compat.CurioCompat;
import dev.xkmc.l2complements.content.item.misc.ILCTotem;
import dev.xkmc.l2complements.events.SpecialEquipmentEvents;
import dev.xkmc.l2complements.init.data.TagGen;
import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Shadow
	public abstract @NotNull Iterable<ItemStack> getArmorSlots();

	@Inject(at = @At("HEAD"), method = "checkTotemDeathProtection", cancellable = true)
	public void l2complements_checkTotemDeathProtection_addCustomTotem(DamageSource pDamageSource, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity self = Wrappers.cast(this);
		for (var pair : CurioCompat.totemSlots(self)) {
			ItemStack holded = pair.getFirst();
			if (!holded.isEmpty() && holded.is(TagGen.TOTEM)) {
				Item item = holded.getItem();
				if (!(item instanceof ILCTotem totem))
					continue;
				if (!totem.allow(self, pDamageSource))
					continue;
				if (self instanceof ServerPlayer serverplayer) {
					serverplayer.awardStat(Stats.ITEM_USED.get(holded.getItem()), 1);
					CriteriaTriggers.USED_TOTEM.trigger(serverplayer, holded);
				}
				totem.trigger(self, holded, pair.getSecond());
				cir.setReturnValue(true);
				break;
			}
		}
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"), method = "getArmorCoverPercentage")
	public boolean l2complements_getArmorCoverPercentage_hideInvisibleArmorsFromMobs(ItemStack stack, Operation<Boolean> op) {
		LivingEntity self = (LivingEntity) (Object) this;
		return op.call(stack) || !SpecialEquipmentEvents.isVisible(self, stack);
	}

}
