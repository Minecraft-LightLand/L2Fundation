package dev.xkmc.l2complements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.xkmc.l2complements.events.SpecialEquipmentEvents;
import dev.xkmc.l2complements.init.data.LCDamageTypes;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LCTagGen;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.events.SchedulerHandler;
import dev.xkmc.l2core.util.Proxy;
import dev.xkmc.l2core.util.ServerProxy;
import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements IItemStackExtension {

	@Shadow
	public abstract boolean is(TagKey<Item> tag);

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V"),
			method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V")
	public void l2complements_hurt_safeguard_setDamage(ItemStack self, int val, Operation<Void> op, @Local(argsOnly = true) LivingEntity user) {
		if (getEnchantmentLevel(LCEnchantments.ETERNAL.holder()) > 0) {
			return;
		}
		if (user != null && getEnchantmentLevel(LCEnchantments.LIFE_SYNC.holder()) > 0) {
			float dmg = (float) (val * LCConfig.SERVER.lifeSyncFactor.get());
			SchedulerHandler.schedule(() -> user.hurt(new DamageSource(
					LCDamageTypes.forKey(user.level(), LCDamageTypes.LIFE_SYNC)), dmg));
			return;
		}
		if (!SpecialEquipmentEvents.PLAYER.get().isEmpty()) {
			BlockState state = SpecialEquipmentEvents.PLAYER.get().peek().getSecond();
			if (getEnchantmentLevel(LCEnchantments.TREE.holder()) >= 2) {
				if (state.is(LCTagGen.AS_LEAF)) {
					return;
				}
			}
		}

		int max = self.getMaxDamage();
		if (max <= val + 1 && self.getEnchantmentLevel(LCEnchantments.SAFEGUARD.holder()) > 0) {
			var opt = ServerProxy.getServer();
			if (opt.isPresent()) {
				int old = self.getDamageValue();
				long time = LCItems.SAFEGUARD_TIME.getOrDefault(self, 0L);
				long current = opt.orElseThrow().overworld().getGameTime();
				if (max <= val) {
					if (current == time) {
						val = old;
					} else if (max > old + 1) {
						val = max - 1;
						LCItems.SAFEGUARD_TIME.set(self, current);
					}
				} else if (max == val + 1) {
					LCItems.SAFEGUARD_TIME.set(self, current);
				}
			}
		}
		op.call(self, val);
	}

	@ModifyReturnValue(at = @At("RETURN"), method = "getMaxDamage")
	public int l2complements_getMaxDamage_durabilityEnchantment(int max) {
		if (!is(ItemTags.ARMOR_ENCHANTABLE)) return max;
		int lv = LCEnchantments.DURABLE_ARMOR.getLvIntrinsic(Wrappers.cast(this));
		return max * (1 + lv);
	}

}
