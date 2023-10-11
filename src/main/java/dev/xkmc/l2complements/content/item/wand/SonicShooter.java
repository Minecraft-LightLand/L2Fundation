package dev.xkmc.l2complements.content.item.wand;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2library.util.raytrace.IGlowingTarget;
import dev.xkmc.l2library.util.raytrace.RayTraceUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SonicShooter extends Item implements IGlowingTarget {

	private static final int RANGE = 17;

	public SonicShooter(Properties properties) {
		super(properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		if (level.isClientSide() && selected && entity instanceof Player player) {
			RayTraceUtil.clientUpdateTarget(player, RANGE);
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		player.playSound(SoundEvents.WARDEN_SONIC_CHARGE, 3, 1);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
		if (level instanceof ServerLevel sl) {
			int size = 1;
			Vec3 src = user.getEyePosition();
			Vec3 dst = RayTraceUtil.getRayTerm(src, user.getXRot(), user.getYRot(), RANGE);
			Vec3 dir = dst.subtract(src).normalize();

			for (int i = 1; i < RANGE; ++i) {
				Vec3 vec33 = src.add(dir.scale(i));
				sl.sendParticles(ParticleTypes.SONIC_BOOM, vec33.x, vec33.y, vec33.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
			}

			List<LivingEntity> target = new ArrayList<>();
			AABB aabb = new AABB(src, src.add(dir.scale(RANGE)));
			for (var e : level.getEntities(user, aabb)) {
				if (e instanceof LivingEntity x) {
					AABB box = x.getBoundingBox().inflate(size);
					for (int i = 0; i <= RANGE; i++) {
						if (box.contains(src.add(dir.scale(i)))) {
							target.add(x);
							break;
						}
					}
				}
			}
			for (var e : target) {
				e.hurt(sl.damageSources().sonicBoom(user), LCConfig.COMMON.sonicShooterDamage.get());
				double d1 = 0.5D * (1.0D - e.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
				double d0 = 2.5D * (1.0D - e.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
				e.push(dir.x() * d0, dir.y() * d1, dir.z() * d0);
			}
		}
		user.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
		stack.hurtAndBreak(1, user, e -> e.broadcastBreakEvent(e.getUsedItemHand()));
		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 34;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public int getDistance(ItemStack itemStack) {
		return RANGE;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(LangData.IDS.SONIC_SHOOTER.get().withStyle(ChatFormatting.GRAY));
	}

}
