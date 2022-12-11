package dev.xkmc.l2complements.content.item.misc;

import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SpecialRenderItem extends TooltipItem {

	public SpecialRenderItem(Properties properties, Supplier<MutableComponent> sup) {
		super(properties, sup);
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(LCBEWLR.EXTENSIONS);
	}

}
