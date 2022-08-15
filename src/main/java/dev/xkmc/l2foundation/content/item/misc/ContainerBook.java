package dev.xkmc.l2foundation.content.item.misc;

import dev.xkmc.l2library.repack.registrate.util.entry.MenuEntry;
import dev.xkmc.l2foundation.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

public class ContainerBook extends Item implements MenuProvider {

	private final Supplier<MenuEntry<?>> cont;

	public ContainerBook(Properties props, Supplier<MenuEntry<?>> cont) {
		super(props);
		this.cont = cont;
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide()) {
			player.openMenu(this);
		} else {
			player.playSound(SoundEvents.BOOK_PAGE_TURN, 1.0f, 1.0f);
		}
		return InteractionResultHolder.success(stack);
	}

	@Override
	public Component getDisplayName() {
		return LangData.translate(getLangKey(cont.get().get()));
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int wid, Inventory plInv, Player pl) {
		return cont.get().create(wid, plInv);
	}

	public interface IFac {

		AbstractContainerMenu create(int wid, Inventory plInv, Player pl);

	}


	public static String getLangKey(MenuType<?> menu) {
		ResourceLocation rl = Objects.requireNonNull(ForgeRegistries.MENU_TYPES.getKey(menu));
		return "container." + rl.getNamespace() + "." + rl.getPath();
	}

}
