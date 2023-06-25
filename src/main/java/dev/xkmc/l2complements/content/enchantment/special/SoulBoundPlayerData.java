package dev.xkmc.l2complements.content.enchantment.special;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.capability.player.PlayerCapabilityHolder;
import dev.xkmc.l2library.capability.player.PlayerCapabilityNetworkHandler;
import dev.xkmc.l2library.capability.player.PlayerCapabilityTemplate;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

import java.util.ArrayList;
import java.util.List;

@SerialClass
public class SoulBoundPlayerData extends PlayerCapabilityTemplate<SoulBoundPlayerData> {

	public static final Capability<SoulBoundPlayerData> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final PlayerCapabilityHolder<SoulBoundPlayerData> HOLDER = new PlayerCapabilityHolder<>(
			new ResourceLocation(L2Complements.MODID, "soulbound"), CAPABILITY,
			SoulBoundPlayerData.class, SoulBoundPlayerData::new, PlayerCapabilityNetworkHandler::new
	);

	public static void register() {

	}

	@SerialClass.SerialField
	public final List<ItemStack> list = new ArrayList<>();

	public static boolean addToPlayer(ServerPlayer player, ItemStack item) {
		HOLDER.get(player).list.add(item.copy());
		return true;
	}

	@Override
	public void onClone(boolean isWasDeath) {
		if (isWasDeath) {
			for (ItemStack stack : list) {
				player.getInventory().placeItemBackInInventory(stack);
			}
			list.clear();
		}
	}

}
