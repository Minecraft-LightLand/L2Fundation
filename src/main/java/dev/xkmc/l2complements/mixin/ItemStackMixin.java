package dev.xkmc.l2complements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.xkmc.l2complements.content.enchantment.special.LifeSyncEnchantment;
import dev.xkmc.l2complements.events.MagicEventHandler;
import dev.xkmc.l2complements.events.SpecialEquipmentEvents;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.TagGen;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2library.init.events.GeneralEventHandler;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.extensions.IForgeItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements IForgeItemStack {

	@ModifyVariable(at = @At("LOAD"), method = "hurtAndBreak", argsOnly = true)
	public int l2complements_hurtAndBreak_hardened(int pAmount) {
		ItemStack self = (ItemStack) (Object) this;
		if (pAmount > 1 && self.getEnchantmentLevel(LCEnchantments.HARDENED.get()) > 0) {
			return 1;
		}
		return pAmount;
	}

	@Inject(at = @At("HEAD"), method = "hurtAndBreak", cancellable = true)
	public <T extends LivingEntity> void l2complements_hurtAndBreak_lifeSync(int pAmount, T pEntity, Consumer<T> pOnBroken, CallbackInfo ci) {
		ItemStack self = (ItemStack) (Object) this;
		if (pEntity.level().isClientSide()) return;
		if (self.getEnchantmentLevel(LCEnchantments.ETERNAL.get()) > 0) {
			ci.cancel();
		}
		if (self.getEnchantmentLevel(LCEnchantments.LIFE_SYNC.get()) > 0) {
			GeneralEventHandler.schedule(() -> pEntity.hurt(LifeSyncEnchantment.getSource(pEntity.level()),
					(float) (pAmount * LCConfig.COMMON.lifeSyncFactor.get())));
			ci.cancel();
		}
		if (!SpecialEquipmentEvents.PLAYER.get().isEmpty()) {
			BlockState state = SpecialEquipmentEvents.PLAYER.get().peek().getSecond();
			if (self.getEnchantmentLevel(LCEnchantments.TREE.get()) >= 2) {
				if (state.is(TagGen.AS_LEAF)) {
					ci.cancel();
				}
			}
		}
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V"), method = "hurt")
	public void l2complements_hurt_safeguard_setDamage(ItemStack self, int val, Operation<Void> op) {
		int max = self.getMaxDamage();
		if (max <= val + 1 && self.getEnchantmentLevel(LCEnchantments.SAFEGUARD.get()) > 0) {
			var opt = Proxy.getServer();
			if (opt.isPresent()) {
				int old = self.getDamageValue();
				String key = LCEnchantments.SAFEGUARD.getId().toString();
				long time = self.getOrCreateTag().getLong(key);
				long current = opt.get().overworld().getGameTime();
				if (max <= val) {
					if (current == time) {
						val = old;
					} else if (max > old + 1) {
						val = max - 1;
						self.getOrCreateTag().putLong(key, current);
					}
				} else if (max == val + 1) {
					self.getOrCreateTag().putLong(key, current);
				}
			}
		}
		op.call(self, val);
	}

	@ModifyReturnValue(at = @At("RETURN"), method = "hurt")
	public boolean l2complements_hurt_safeguard_preventBreaking(boolean broken) {
		ItemStack self = (ItemStack) (Object) this;
		return broken && self.getDamageValue() >= self.getMaxDamage();
	}

	@ModifyReturnValue(at = @At("RETURN"), method = "getMaxDamage")
	public int l2complements_getMaxDamage_durabilityEnchantment(int max) {
		ItemStack self = (ItemStack) (Object) this;
		int lv = self.getEnchantmentLevel(LCEnchantments.DURABLE_ARMOR.get());
		return max * (1 + lv);
	}

	@Override
	public @NotNull AABB getSweepHitBox(@NotNull Player player, @NotNull Entity target) {
		ItemStack self = (ItemStack) (Object) this;
		return self.getItem().getSweepHitBox(self, player, target);
	}

	@ModifyReturnValue(at = @At("RETURN"), method = "getSweepHitBox", remap = false)
	public AABB l2complements_getSweepHitBox_enchantOverride(AABB box) {
		int lv = getEnchantmentLevel(LCEnchantments.WIND_SWEEP.get());
		if (lv > 0) {
			double amount = LCConfig.COMMON.windSweepIncrement.get();
			box = box.inflate(amount * lv, amount * lv, amount * lv);
		}
		return box;
	}

	@Override
	public boolean makesPiglinsNeutral(LivingEntity wearer) {
		ItemStack self = (ItemStack) (Object) this;
		return self.getItem().makesPiglinsNeutral(self, wearer);
	}

	@Inject(at = @At("HEAD"), method = "makesPiglinsNeutral", cancellable = true, remap = false)
	public void l2complements_makesPiglinsNeutral_enchantOverride(LivingEntity wearer, CallbackInfoReturnable<Boolean> cir) {
		if (getEnchantmentLevel(LCEnchantments.SHINNY.get()) > 0) {
			cir.setReturnValue(true);
		}
	}

	@Override
	public boolean isPiglinCurrency() {
		ItemStack self = (ItemStack) (Object) this;
		return self.getItem().isPiglinCurrency(self);
	}

	@Inject(at = @At("HEAD"), method = "isPiglinCurrency", cancellable = true, remap = false)
	public void l2complements_isPiglinCurrency_enchantOverride(CallbackInfoReturnable<Boolean> cir) {
		if (getEnchantmentLevel(LCEnchantments.SHINNY.get()) > 0) {
			cir.setReturnValue(true);
		}
	}

	@Override
	public boolean isEnderMask(Player player, EnderMan endermanEntity) {
		ItemStack self = (ItemStack) (Object) this;
		return self.getItem().isEnderMask(self, player, endermanEntity);
	}

	@Inject(at = @At("HEAD"), method = "isEnderMask", cancellable = true, remap = false)
	public void l2complements_isEnderMask_enchantOverride(CallbackInfoReturnable<Boolean> cir) {
		if (getEnchantmentLevel(LCEnchantments.ENDER_MASK.get()) > 0) {
			cir.setReturnValue(true);
		}
	}

	@Override
	public boolean canWalkOnPowderedSnow(LivingEntity wearer) {
		ItemStack self = (ItemStack) (Object) this;
		return self.getItem().canWalkOnPowderedSnow(self, wearer);
	}

	@Inject(at = @At("HEAD"), method = "canWalkOnPowderedSnow", cancellable = true, remap = false)
	public void l2complements_canWalkOnPowderedSnow_enchantOverride(CallbackInfoReturnable<Boolean> cir) {
		if (getEnchantmentLevel(LCEnchantments.SNOW_WALKER.get()) > 0) {
			cir.setReturnValue(true);
		}
	}

}
