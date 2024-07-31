package dev.xkmc.l2complements.content.item.misc;

import dev.xkmc.l2complements.init.data.LCLang;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.util.TeleportTool;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Consumer;

public class WarpStone extends Item {

	public record PosData(ResourceLocation id, Vec3 pos) {
	}

	public static void set(ItemStack stack, Level level, Entity e) {
		LCItems.POS_DATA.set(stack, new PosData(level.dimension().location(), e.position().add(0, 1e-3, 0)));
	}

	public final boolean fragile;

	public WarpStone(Properties props, boolean fragile) {
		super(props);
		this.fragile = fragile;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		return use(level, player, stack, e -> player.onEquippedItemBroken(e, LivingEntity.getSlotForHand(hand)));
	}

	public void use(ServerPlayer player, ItemStack stack) {
		use(player.level(), player, stack, e -> {
		});
	}

	private InteractionResultHolder<ItemStack> use(Level level, Player player, ItemStack stack, Consumer<Item> breaker) {
		var ppos = LCItems.POS_DATA.get(stack);
		if (ppos != null) {
			if (!level.isClientSide()) {
				var dim = ResourceKey.create(Registries.DIMENSION, ppos.id());
				var pos = ppos.pos();
				ServerLevel lv = ((ServerLevel) level).getServer().getLevel(dim);
				if (lv != null) {
					TeleportTool.performTeleport(player, lv, pos.x, pos.y, pos.z, player.getYRot(), player.getXRot());
					if (!fragile) {
						stack.hurtAndBreak(1, lv, player, breaker);
					}
				}
			}
			if (fragile) {
				stack.shrink(1);
				return InteractionResultHolder.consume(stack);
			} else {
				return InteractionResultHolder.success(stack);
			}
		} else {
			if (!level.isClientSide()) {
				set(stack, level, player);
			}
			return InteractionResultHolder.success(stack);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag flag) {
		var pos = LCItems.POS_DATA.get(stack);
		int dur = fragile ? 1 : stack.getMaxDamage() - getDamage(stack);
		if (pos != null) {
			var clevel = Component.translatable(pos.id().toString());
			var bpos = pos.pos();
			list.add(LCLang.IDS.WARP_POS
					.get(clevel, Math.round(bpos.x), Math.round(bpos.y), Math.round(bpos.x))
					.withStyle(ChatFormatting.GRAY));
			list.add(LCLang.IDS.WARP_TELEPORT.get(dur).withStyle(ChatFormatting.GRAY));
			if (!fragile) list.add(LCLang.IDS.WARP_GRIND.get().withStyle(ChatFormatting.GRAY));
		} else {
			list.add(LCLang.IDS.WARP_RECORD.get(dur).withStyle(ChatFormatting.GRAY));
		}
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return stack.has(LCItems.POS_DATA.get());
	}

}
