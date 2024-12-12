package dev.xkmc.l2complements.mixin;

import dev.xkmc.l2complements.content.enchantment.digging.RangeDiggingEnchantment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {

	@Inject(at = @At("HEAD"), method = "spawnDestroyParticles", cancellable = true)
	public void l2complements$spawnDestroyParticles$cancelParticle(Level level, Player player, BlockPos pos, BlockState state, CallbackInfo ci) {
		if (RangeDiggingEnchantment.isSuppressed(player.getUUID())) {
			ci.cancel();
		}
	}


}
