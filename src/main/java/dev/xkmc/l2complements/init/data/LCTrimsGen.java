package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.materials.LCMats;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.Arrays;
import java.util.Map;

public class LCTrimsGen {

	public static void build(BootstrapContext<TrimMaterial> ctx) {
		Arrays.stream(LCMats.values())
				.forEach(e -> ctx.register(ResourceKey.create(Registries.TRIM_MATERIAL,
								L2Complements.loc(e.getID())),
						TrimMaterial.create(e.getID(), e.getIngot(), 0,
								e.getIngot().getDescription().copy().withStyle(e.trim_text_color), Map.of())));
	}

}
