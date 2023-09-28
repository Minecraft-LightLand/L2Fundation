package dev.xkmc.l2complements.content.enchantment.digging;

import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2complements.events.MagicEventHandler;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.apache.logging.log4j.Level.ERROR;

public class RangeDiggingEnchantment extends UnobtainableEnchantment {

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
		double reach = player.getAttributeValue(ForgeMod.BLOCK_REACH.get());
		Vec3 target = base.add(look.x * reach, look.y * reach, look.z * reach);
		BlockHitResult trace = level.clip(new ClipContext(base, target, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
		return trace.getDirection();
	}

	private static double hardnessFactor() {
		return LCConfig.COMMON.chainDiggingHardnessRange.get();
	}

	private static boolean canBreak(BlockPos i, Level level, Player player, double hardness) {
		BlockState state = level.getBlockState(i);
		if (state.isAir()) return false;
		if (!player.hasCorrectToolForDrops(state))
			return false;
		float speed = state.getDestroySpeed(player.level(), i);
		if (speed < 0) return false;
		return hardness < 0 || speed <= hardness;
	}

	private final BlockBreaker breaker;

	public RangeDiggingEnchantment(BlockBreaker breaker, Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
		this.breaker = breaker;
	}

	@Override
	public int getMaxLevel() {
		return breaker.getMaxLevel();
	}

	public List<BlockPos> getTargets(Player player, BlockPos pos, ItemStack stack, int lv) {
		Level level = player.level();
		BlockState state = level.getBlockState(pos);
		double hardness = breaker.ignoreHardness() ? -1 : state.getDestroySpeed(level, pos) * hardnessFactor();
		return breaker.getInstance(new DiggerContext(player, getFace(player), stack, lv, pos, state))
				.find(level, pos, i -> !pos.equals(i) && canBreak(i, level, player, hardness));
	}

	public void onBlockBreak(ServerPlayer player, BlockPos pos, ItemStack stack, int lv) {
		if (player.isShiftKeyDown()) return;
		var blocks = getTargets(player, pos, stack, lv);
		execute(player, () -> {
			int max = LCConfig.COMMON.chainDiggingDelayThreshold.get();
			if (blocks.size() <= max) {
				for (var i : blocks) {
					player.gameMode.destroyBlock(i);
				}
			} else {
				if (LCConfig.COMMON.delayDiggingRequireEnder.get()) {
					if (stack.getEnchantmentLevel(LCEnchantments.ENDER.get()) <= 0) {
						player.sendSystemMessage(LangData.IDS.DELAY_WARNING.get(
										LCEnchantments.ENDER.get().getFullname(1), max)
								.withStyle(ChatFormatting.RED), true);
						return;
					}
				}
				MagicEventHandler.schedulePersistent(new DelayedBlockBreaker(player, blocks)::tick);
			}
		});
	}

	@Override
	public int getDecoColor(String s) {
		return 0xffafafaf;
	}
}
