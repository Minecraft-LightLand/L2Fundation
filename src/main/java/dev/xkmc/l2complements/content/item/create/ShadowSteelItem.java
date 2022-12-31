package dev.xkmc.l2complements.content.item.create;

import dev.xkmc.l2complements.init.data.LangData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ShadowSteelItem extends NoGravMagicalDohickyItem {

	public ShadowSteelItem(Properties properties, Supplier<MutableComponent> sup) {
		super(properties, sup);
	}

	@Override
	protected void onCreated(ItemEntity entity, CompoundTag persistentData) {
		super.onCreated(entity, persistentData);
		float yMotion = (entity.fallDistance + 3) / 50f;
		entity.setDeltaMovement(0, yMotion, 0);
	}

	@Override
	protected float getIdleParticleChance(ItemEntity entity) {
		return (float) (Mth.clamp(entity.getItem()
				.getCount() - 10, Mth.clamp(entity.getDeltaMovement().y * 20, 5, 20), 100) / 64f);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(stack, level, list, flag);
		list.add(LangData.IDS.FLOAT.get());
	}

}
