package dev.xkmc.l2complements.content.item.misc;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Supplier;

public class TransformItem extends TooltipItem {

	private final Supplier<EntityType<? extends Mob>> from;
	private final Supplier<EntityType<? extends Mob>> to;

	public TransformItem(Properties properties, Supplier<MutableComponent> sup,
						 Supplier<EntityType<? extends Mob>> from,
						 Supplier<EntityType<? extends Mob>> to) {
		super(properties, sup);
		this.from = from;
		this.to = to;
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
		Level level = target.level();
		if (target.getType() != from.get()) return InteractionResult.FAIL;
		if (level.getDifficulty() == Difficulty.PEACEFUL) return InteractionResult.FAIL;
		if (level instanceof ServerLevel server) {
			if (ForgeEventFactory.canLivingConvert(target, to.get(), (timer) -> {
			})) {
				Mob result = to.get().create(level);
				assert result != null;
				result.moveTo(target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
				ForgeEventFactory.onFinalizeSpawn(result, server, level.getCurrentDifficultyAt(result.blockPosition()), MobSpawnType.CONVERSION, null, null);
				result.setNoAi(((Mob) target).isNoAi());
				if (target.hasCustomName()) {
					result.setCustomName(target.getCustomName());
					result.setCustomNameVisible(target.isCustomNameVisible());
				}
				result.setPersistenceRequired();
				ForgeEventFactory.onLivingConvert(target, result);
				level.addFreshEntity(result);
				target.discard();
				stack.shrink(1);
				return InteractionResult.CONSUME;
			}
			return InteractionResult.FAIL;
		}
		return InteractionResult.SUCCESS;
	}

}
