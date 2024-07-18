package dev.xkmc.l2complements.content.item.misc;

import dev.xkmc.l2complements.content.entity.fireball.BaseFireball;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2library.content.raytrace.RayTraceUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Supplier;

public class FireChargeItem<T extends BaseFireball<T>> extends TooltipItem {

	public interface PlayerFire<T extends BaseFireball<T>> {

		T create(Player player, Vec3 vec, Level level);

	}

	public interface BlockFire<T extends BaseFireball<T>> {

		T create(double x, double y, double z, Vec3 vec, Level level);

	}

	public class FireChargeBehavior extends DefaultDispenseItemBehavior {

		public ItemStack execute(BlockSource source, ItemStack stack) {
			Direction direction = source.state().getValue(DispenserBlock.FACING);
			Position position = DispenserBlock.getDispensePosition(source);
			double d0 = position.x() + (double) ((float) direction.getStepX() * 0.3F);
			double d1 = position.y() + (double) ((float) direction.getStepY() * 0.3F);
			double d2 = position.z() + (double) ((float) direction.getStepZ() * 0.3F);
			Level level = source.level();
			RandomSource randomsource = level.random;
			double d3 = randomsource.triangle(direction.getStepX(), 0.11485D);
			double d4 = randomsource.triangle(direction.getStepY(), 0.11485D);
			double d5 = randomsource.triangle(direction.getStepZ(), 0.11485D);
			T t = blockFire.create(d0, d1, d2, Vec3.ZERO, level);
			t.setItem(stack);
			t.setDeltaMovement(new Vec3(d3, d4, d5).normalize());
			level.addFreshEntity(t);
			stack.shrink(1);
			return stack;
		}

		protected void playSound(BlockSource level) {
			level.level().levelEvent(1018, level.pos(), 0);
		}

	}

	private final PlayerFire<T> playerFire;
	private final BlockFire<T> blockFire;

	public FireChargeItem(Properties pProperties, PlayerFire<T> playerFire, BlockFire<T> blockFire,
						  Supplier<MutableComponent> tooltip) {
		super(pProperties, tooltip);
		this.playerFire = playerFire;
		this.blockFire = blockFire;
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		RandomSource r = player.getRandom();
		level.playSound(player, player.getX(), player.getY(), player.getZ(),
				SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS,
				1.0F, (r.nextFloat() - r.nextFloat()) * 0.2F + 1.0F);
		if (!level.isClientSide) {
			Vec3 v = RayTraceUtil.getRayTerm(Vec3.ZERO, player.getXRot(), player.getYRot(), 1);
			T t = playerFire.create(player, Vec3.ZERO, level);
			t.setItem(itemstack);
			t.setPos(player.getEyePosition().add(0, -0.1, 0).add(v));
			t.setDeltaMovement(v);
			level.addFreshEntity(t);
		}
		player.awardStat(Stats.ITEM_USED.get(this));
		if (!player.getAbilities().instabuild) {
			itemstack.shrink(1);
		}
		return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag flag) {
		list.add(LangData.IDS.CHARGE_THROW.get().withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, level, list, flag);

	}
}