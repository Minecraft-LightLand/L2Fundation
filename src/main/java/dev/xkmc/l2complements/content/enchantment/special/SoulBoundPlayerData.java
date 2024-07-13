package dev.xkmc.l2complements.content.enchantment.special;

import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2core.capability.player.PlayerCapabilityTemplate;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

@SerialClass
public class SoulBoundPlayerData extends PlayerCapabilityTemplate<SoulBoundPlayerData> {

	@SerialField
	public final List<ItemStack> list = new ArrayList<>();

	public static boolean addToPlayer(ServerPlayer player, ItemStack item) {
		LCEnchantments.ATT_SOULBOUND.type().getOrCreate(player).list.add(item.copy());
		return true;
	}

	@Override
	public void onClone(Player player, boolean isWasDeath) {
		if (isWasDeath) {
			for (ItemStack stack : list) {
				player.getInventory().placeItemBackInInventory(stack);
			}
			list.clear();
		}
	}

}
