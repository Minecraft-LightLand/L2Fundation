package dev.xkmc.l2complements.content.enchantment.digging;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LCLang;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2core.events.SchedulerHandler;
import dev.xkmc.l2core.init.reg.ench.CustomDescEnchantment;
import dev.xkmc.l2core.init.reg.ench.EnchColor;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.apache.logging.log4j.Level.ERROR;

public class RangeDiggingEnchantment extends LegacyEnchantment implements CustomDescEnchantment {

	private static final Set<UUID> BREAKER = new HashSet<>();

	public static void execute(Player player, Runnable run) {
		synchronized (BREAKER) {
			if (BREAKER.contains(player.getUUID())) return;
			BREAKER.add(player.getUUID());
			try {
				run.run();
			} catch (Exception e) {
				L2Complements.LOGGER.throwing(ERROR, e);
			}
			BREAKER.remove(player.getUUID());
		}
	}

	private static Direction getFace(Player player) {
		Level level = player.level();
		Vec3 base = player.getEyePosition(0);
		Vec3 look = player.getLookAngle();
		double reach = player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE);
		Vec3 target = base.add(look.x * reach, look.y * reach, look.z * reach);
		BlockHitResult trace = level.clip(new ClipContext(base, target, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
		return trace.getDirection();
	}

	private static double hardnessFactor() {
		return LCConfig.SERVER.chainDiggingHardnessRange.get();
	}

	private static boolean canBreak(BlockPos i, Level level, Player player, double hardness) {
		BlockState state = level.getBlockState(i);
		if (state.isAir()) return false;
		if (!player.hasCorrectToolForDrops(state, level, i))
			return false;
		float speed = state.getDestroySpeed(player.level(), i);
		if (speed < 0) return false;
		return hardness < 0 || speed <= hardness;
	}

	private final BlockBreaker breaker;

	public RangeDiggingEnchantment(BlockBreaker breaker) {
		this.breaker = breaker;
	}

	public List<BlockPos> getTargets(Player player, BlockPos pos, ItemStack stack, int lv) {
		Level level = player.level();
		BlockState state = level.getBlockState(pos);
		double hardness = breaker.ignoreHardness() ? -1 : state.getDestroySpeed(level, pos) * hardnessFactor();
		return breaker.getInstance(new DiggerContext(player, getFace(player), stack, lv, pos, state))
				.find(level, pos, i -> !pos.equals(i) && canBreak(i, level, player, hardness));
	}

	public void onBlockBreak(ServerPlayer player, BlockPos pos, ItemStack stack, int lv) {
		var blocks = getTargets(player, pos, stack, lv);
		execute(player, () -> {
			int max = LCConfig.SERVER.chainDiggingDelayThreshold.get();
			if (blocks.size() <= max) {
				for (var i : blocks) {
					player.gameMode.destroyBlock(i);
				}
			} else {
				if (LCConfig.SERVER.delayDiggingRequireEnder.get()) {
					if (stack.getEnchantmentLevel(LCEnchantments.ENDER_TRANSPORT.holder()) <= 0) {
						player.sendSystemMessage(LCLang.IDS.DELAY_WARNING.get(
										Enchantment.getFullname(LCEnchantments.ENDER_TRANSPORT.holder(), 1), max)
								.withStyle(ChatFormatting.RED), true);
						return;
					}
				}
				SchedulerHandler.schedulePersistent(new DelayedBlockBreaker(player, blocks)::tick);
			}
		});
	}

	@Override
	public List<Component> descFull(int lv, String key, boolean alt, boolean book, EnchColor color) {
		return breaker.descFull(lv, key, alt, book);
	}

}
