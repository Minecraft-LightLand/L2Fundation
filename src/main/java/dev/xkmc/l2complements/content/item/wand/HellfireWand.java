package dev.xkmc.l2complements.content.item.wand;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2library.util.raytrace.RayTraceUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HellfireWand extends Item {

	public static final int RANGE = 64, CHARGE = 200, SIZE = 10;

	public HellfireWand(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public void onUseTick(Level level, LivingEntity user, ItemStack stack, int remain) {
		if (!(user instanceof Player player)) return;
		var result = RayTraceUtil.rayTraceBlock(level, player, RANGE);
		var center = result.getLocation();
		int time = Math.min(CHARGE, getUseDuration(stack) - remain);
		double radius = time * 1.0 * SIZE / CHARGE;
		if (level.isClientSide()) {
			for (int i = 0; i < 5; i++) {
				float tpi = (float) (Math.PI * 2);
				Vec3 v0 = new Vec3(0, radius, 0);
				v0 = v0.xRot(tpi / 4).yRot(level.getRandom().nextFloat() * tpi);
				level.addAlwaysVisibleParticle(ParticleTypes.FLAME,
						center.x + v0.x,
						center.y + v0.y + 0.5f,
						center.z + v0.z, 0, 0, 0);
			}
		}
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int remain) {
		if (!(user instanceof Player player)) return;
		stack.hurtAndBreak(1, user, e -> e.broadcastBreakEvent(e.getUsedItemHand()));
		var result = RayTraceUtil.rayTraceBlock(level, player, RANGE);
		var center = result.getLocation();
		level.playSound(player, center.x, center.y, center.z, SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 3.0F, 1.0F);
		int time = Math.min(CHARGE, getUseDuration(stack) - remain);
		double radius = time * 1.0 * SIZE / CHARGE;
		if (level.isClientSide()) {
			double side = 1.644;
			double perimeter = Math.PI * 2;
			double total = side * 5 + perimeter;
			int size = time * 2;
			for (int i = 0; i < size; i++) {
				double perc = i * 1.0 / size * total;
				Vec3 v0;
				if (perc < side * 5) {
					int start = (int) Math.floor(perc / side);
					Vec3 tip = new Vec3(0, radius, 0);
					tip = tip.xRot((float) (Math.PI / 2)).yRot((float) (Math.PI * 4 / 5 * start));
					Vec3 next = tip.yRot((float) (Math.PI * 4 / 5));
					v0 = tip.add(next.subtract(tip).scale(perc / side - start));
					level.addAlwaysVisibleParticle(ParticleTypes.SOUL,
							center.x + v0.x,
							center.y + v0.y + 0.5,
							center.z + v0.z, 0, 1, 0);
				} else {
					v0 = new Vec3(0, radius, 0);
					v0 = v0.xRot((float) (Math.PI / 2)).yRot((float) (perc - side * 5));
					level.addAlwaysVisibleParticle(ParticleTypes.SOUL_FIRE_FLAME,
							center.x + v0.x,
							center.y + v0.y + 0.5,
							center.z + v0.z, 0, 1, 0);
				}
			}
		}
		if (level instanceof ServerLevel sl) {
			for (var e : level.getEntities(user, AABB.ofSize(center.add(0, radius, 0),
					radius * 2, radius * 2, radius * 2))) {
				if (e instanceof LivingEntity x) {
					x.hurt(sl.damageSources().indirectMagic(null, user), LCConfig.COMMON.hellfireWandDamage.get());
				}
			}
		}
		player.getCooldowns().addCooldown(this, 40);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(LangData.IDS.HELLFIRE_WAND.get().withStyle(ChatFormatting.GRAY));
	}

}
