package dev.xkmc.l2complements.init.materials.api;

import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

public interface ITool {

	int getDamage(int base_damage);

	float getSpeed(float base_speed);

	Item create(Tier tier, int damage, float speed, Item.Properties prop, ExtraToolConfig config);

}
