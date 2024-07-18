package dev.xkmc.l2complements.content.item.wand;

import dev.xkmc.l2complements.init.data.LangData;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2core.base.effects.EffectUtil;
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
		int time = Math.min(CHARGE, getUseDuration(stack, user) - remain);
		if (remain % 20 == 0) {
			stack.hurtAndBreak(1, user, LivingEntity.getSlotForHand(user.getUsedItemHand()));
		}
		double radius = SIZE_0 + time * 1.0 * SIZE_1 / CHARGE;
		if (level.isClientSide()) {
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
		} else {
			List<? extends LivingEntity> list = player.level().getEntities(EntityTypeTest.forClass(LivingEntity.class),
					player.getBoundingBox().inflate(radius), e -> e instanceof Mob);
			for (var e : list) {
				double dist = player.distanceTo(e) / radius;
				if (dist > 1) continue;
				Vec3 vec = e.position().subtract(player.position()).normalize().scale((1 - dist) * 0.2);
				e.push(vec.x, vec.y, vec.z);
				if (e.getTicksFrozen() < 140) {
					e.setTicksFrozen(140);
				}
				EffectUtil.refreshEffect(e, new MobEffectInstance(LCEffects.ICE.holder(), 40), player);
			}
		}
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int remain) {
		if (!(user instanceof Player)) return;
		stack.hurtAndBreak(1, user, LivingEntity.getSlotForHand(user.getUsedItemHand()));
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity le) {
		return 72000;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag flag) {
		list.add(LangData.IDS.WINTERSTORM_WAND.get().withStyle(ChatFormatting.GRAY));
	}

}
