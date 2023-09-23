package dev.xkmc.l2complements.content.enchantment.digging;

import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import dev.xkmc.l2complements.init.L2Complements;
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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.apache.logging.log4j.Level.ERROR;

public class RangeDiggingEnchantment extends UnobtainableEnchantment {

	private static final Set<UUID> BREAKER = new HashSet<>();

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
		return 3;
	}

	private static boolean canBreak(BlockPos i, Level level, Player player, double hardness) {
		BlockState state = level.getBlockState(i);
		if (state.isAir()) return false;
		if (!player.hasCorrectToolForDrops(state))
			return false;
		float speed = state.getDestroySpeed(player.level(), i);
		return speed >= 0 && speed <= hardness;
	}

	private final BlockBreaker breaker;

	public RangeDiggingEnchantment(BlockBreaker breaker, Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
		this.breaker = breaker;
	}

	@Override
	public int getMaxCost(int lv) {
		return breaker.getMaxLevel();
	}

	public Collection<BlockPos> getTargets(Player player, BlockPos pos, ItemStack stack, int lv) {
		Level level = player.level();
		BlockState state = level.getBlockState(pos);
		double hardness = state.getDestroySpeed(level, pos) * hardnessFactor();
		return breaker.getInstance(new DiggerContext(player, getFace(player), stack, lv, pos, state))
				.find(level, pos, i -> canBreak(i, level, player, hardness));
	}

	public void onBlockBreak(ServerPlayer player, BlockPos pos, ItemStack stack, int lv) {
		if (player.isShiftKeyDown()) return;
		var blocks = getTargets(player, pos, stack, lv);
		synchronized (BREAKER) {
			if (BREAKER.contains(player.getUUID())) return;
			BREAKER.add(player.getUUID());
			try {
				for (var i : blocks) {
					player.gameMode.destroyBlock(i);
				}
			} catch (Exception e) {
				L2Complements.LOGGER.throwing(ERROR, e);
			}
			BREAKER.remove(player.getUUID());
		}
	}

}
