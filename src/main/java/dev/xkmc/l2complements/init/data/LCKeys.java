package dev.xkmc.l2complements.init.data;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public enum LCKeys {
	DIG("key.l2mods.dig", "Range Digging Toggle", GLFW.GLFW_KEY_GRAVE_ACCENT);

	public final String id, def;
	public final int key;
	public final KeyMapping map;

	LCKeys(String id, String def, int key) {
		this.id = id;
		this.def = def;
		this.key = key;
		this.map = new KeyMapping(id, key, "key.categories.l2mods");
	}
}
