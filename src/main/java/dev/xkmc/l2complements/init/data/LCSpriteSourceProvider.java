package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.materials.LCMats;
import net.minecraft.Util;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

public class LCSpriteSourceProvider extends SpriteSourceProvider {

	private static final List<ResourceLocation> DEF;

	static {
		DEF = Stream.of(
				"trims/models/armor/coast",
				"trims/models/armor/coast_leggings",
				"trims/models/armor/sentry",
				"trims/models/armor/sentry_leggings",
				"trims/models/armor/dune",
				"trims/models/armor/dune_leggings",
				"trims/models/armor/wild",
				"trims/models/armor/wild_leggings",
				"trims/models/armor/ward",
				"trims/models/armor/ward_leggings",
				"trims/models/armor/eye",
				"trims/models/armor/eye_leggings",
				"trims/models/armor/vex",
				"trims/models/armor/vex_leggings",
				"trims/models/armor/tide",
				"trims/models/armor/tide_leggings",
				"trims/models/armor/snout",
				"trims/models/armor/snout_leggings",
				"trims/models/armor/rib",
				"trims/models/armor/rib_leggings",
				"trims/models/armor/spire",
				"trims/models/armor/spire_leggings",
				"trims/models/armor/wayfinder",
				"trims/models/armor/wayfinder_leggings",
				"trims/models/armor/shaper",
				"trims/models/armor/shaper_leggings",
				"trims/models/armor/silence",
				"trims/models/armor/silence_leggings",
				"trims/models/armor/raiser",
				"trims/models/armor/raiser_leggings",
				"trims/models/armor/host",
				"trims/models/armor/host_leggings"
		).map(ResourceLocation::new).toList();
	}

	public LCSpriteSourceProvider(PackOutput output, ExistingFileHelper fileHelper) {
		super(output, fileHelper, L2Complements.MODID);
	}

	@Override
	protected void addSources() {
		atlas(new ResourceLocation("armor_trims"))
				.addSource(new PalettedPermutations(DEF,
						new ResourceLocation("trims/color_palettes/trim_palette"),
						Util.make(new LinkedHashMap<>(), map -> Arrays.stream(LCMats.values())
								.forEach(e -> map.put(e.getID(), new ResourceLocation(L2Complements.MODID,
										"trims/color_palettes/" + e.getID()))))));
	}

}
