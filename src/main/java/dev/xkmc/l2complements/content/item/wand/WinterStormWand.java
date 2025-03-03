package dev.xkmc.l2complements.content.item.wand;

import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2library.base.effects.EffectUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WinterStormWand extends Item {

	public static final int RANGE = 64, CHARGE = 100, SIZE_0 = 3, SIZE_1 = 7;

	public WinterStormWand(Properties properties) {
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
		var center = player.position();
		int time = getUseDuration(stack) - remain;
		if (!level.isClientSide()) {
			if (remain % 20 == 0) {
				stack.hurtAndBreak(1, user, e -> e.broadcastBreakEvent(e.getUsedItemHand()));
			}
			tickServer(user, level, center, time);
		}
	}

	public static void tickServer(LivingEntity user, Level level, Vec3 center, int time) {
		WandEffectToClient.Type.WINTERSTORM.send(user, center, time);
		double radius = SIZE_0 + Math.min(CHARGE, time) * 1.0 * SIZE_1 / CHARGE;
		List<? extends LivingEntity> list = level.getEntities(EntityTypeTest.forClass(LivingEntity.class),
				user.getBoundingBox().inflate(radius),
				e -> e instanceof Mob && e != user &&
						!e.isAlliedTo(user) && !user.isAlliedTo(e));
		for (var e : list) {
			double dist = user.distanceTo(e) / radius;
			if (dist > 1) continue;
			Vec3 vec = e.position().subtract(user.position()).normalize().scale((1 - dist) * 0.2);
			e.push(vec.x, vec.y, vec.z);
			if (e.getTicksFrozen() < 140) {
				e.setTicksFrozen(140);
			}
			EffectUtil.refreshEffect(e, new MobEffectInstance(LCEffects.ICE.get(), 40), EffectUtil.AddReason.NONE, user);
		}
	}

	public static void tickClient(Level level, Vec3 center, int time) {
		double radius = SIZE_0 + Math.min(CHARGE, time) * 1.0 * SIZE_1 / CHARGE;
		for (int i = 0; i < 5; i++) {
			float tpi = (float) (Math.PI * 2);
			Vec3 v = new Vec3(0, 1, 0);
			v = v.xRot(tpi / 4).yRot(level.getRandom().nextFloat() * tpi);
			Vec3 v0 = v.scale(radius);
			Vec3 v1 = v.yRot(tpi * 0.375f);
			level.addAlwaysVisibleParticle(ParticleTypes.SNOWFLAKE,
					center.x + v0.x,
					center.y + v0.y + 0.5f,
					center.z + v0.z, v1.x, v1.y, v1.z);
		}
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int remain) {
		if (!(user instanceof Player)) return;
		stack.hurtAndBreak(1, user, e -> e.broadcastBreakEvent(e.getUsedItemHand()));
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
		list.add(LangData.IDS.WINTERSTORM_WAND.get().withStyle(ChatFormatting.GRAY));
	}

}
