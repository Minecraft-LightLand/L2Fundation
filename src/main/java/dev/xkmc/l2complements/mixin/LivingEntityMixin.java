package dev.xkmc.l2complements.mixin;

import dev.xkmc.l2complements.compat.CurioCompat;
import dev.xkmc.l2complements.content.item.misc.ILCTotem;
import dev.xkmc.l2complements.events.SpecialEquipmentEvents;
import dev.xkmc.l2complements.init.data.TagGen;
import dev.xkmc.l2library.util.code.Wrappers;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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

	/**
	 * @author L2Complements lcy0x1
	 * @reason Allow armors to hide themselves
	 * FIXME improve compatibility
	 */
	@Inject(at = @At("HEAD"), method = "getArmorCoverPercentage", cancellable = true)
	public void l2complements_getArmorCoverPercentage_hideInvisibleArmorsFromMobs(CallbackInfoReturnable<Float> cir) {
		LivingEntity self = (LivingEntity) (Object) this;
		Iterable<ItemStack> iterable = getArmorSlots();
		int total = 0;
		int visible = 0;
		for (ItemStack itemstack : iterable) {
			if (!itemstack.isEmpty()) {
				if (SpecialEquipmentEvents.isVisible(self, itemstack))
					++visible;
			}
			++total;
		}
		cir.setReturnValue(total > 0 ? (float) visible / (float) total : 0.0F);
	}

}
