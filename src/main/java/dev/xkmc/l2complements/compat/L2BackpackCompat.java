package dev.xkmc.l2complements.compat;

import dev.xkmc.l2backpack.events.quickaccess.QuickAccessClickHandler;
import dev.xkmc.l2backpack.events.quickaccess.SimpleMenuAction;
import dev.xkmc.l2complements.init.registrate.LCBlocks;
import net.minecraft.world.inventory.AnvilMenu;

public class L2BackpackCompat {

	public static void register(){
		QuickAccessClickHandler.register(LCBlocks.ETERNAL_ANVIL.get().asItem(), new SimpleMenuAction(AnvilMenu::new, "container.repair"));
	}

}
