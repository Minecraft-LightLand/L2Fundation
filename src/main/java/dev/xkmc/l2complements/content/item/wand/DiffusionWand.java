package dev.xkmc.l2complements.content.item.wand;

import dev.xkmc.l2complements.content.recipe.DiffusionRecipe;
import dev.xkmc.l2complements.init.data.LCLang;
import dev.xkmc.l2complements.init.registrate.LCRecipes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.function.Consumers;

import java.util.List;

public class DiffusionWand extends WandItem {

	public DiffusionWand(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		BlockPos pos = ctx.getClickedPos();
		Level level = ctx.getLevel();
		BlockState state = level.getBlockState(pos);
		if (level instanceof ServerLevel sl) {
			BlockState replace = null;
			for (Direction dire : Direction.values()) {
				BlockPos npos = pos.relative(dire);
				BlockState nstate = level.getBlockState(npos);
				var inv = new DiffusionRecipe.Inv();
				inv.setItem(0, state.getBlock().asItem().getDefaultInstance());
				inv.setItem(1, nstate.getBlock().asItem().getDefaultInstance());
				var opt = level.getRecipeManager().getRecipeFor(LCRecipes.RT_DIFFUSION.get(), inv, level);
				if (opt.isPresent()) {
					BlockState result = opt.get().value().result.defaultBlockState();
					level.setBlockAndUpdate(npos, result);
					replace = result;
				}
			}
			if (replace != null) {
				level.setBlockAndUpdate(pos, replace);
				ItemStack stack = ctx.getItemInHand();
				if (ctx.getPlayer() != null) {
					stack.hurtAndBreak(1, ctx.getPlayer(), LivingEntity.getSlotForHand(ctx.getHand()));
				} else {
					stack.hurtAndBreak(1, sl, null, Consumers.nop());
				}
			}
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(LCLang.IDS.DIFFUSION_WAND.get().withStyle(ChatFormatting.GRAY));
	}

}
