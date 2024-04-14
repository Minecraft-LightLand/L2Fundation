package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.materials.LCMats;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LCDatapackRegistriesGen extends DatapackBuiltinEntriesProvider {

	private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.TRIM_MATERIAL, ctx -> Arrays.stream(LCMats.values())
					.forEach(e -> ctx.register(ResourceKey.create(Registries.TRIM_MATERIAL,
									new ResourceLocation(L2Complements.MODID, e.getID())),
							TrimMaterial.create(e.getID(), e.getIngot(), e.ordinal() * 0.1f + 0.0943f,
									e.getIngot().getDescription().copy().withStyle(e.trim_text_color), Map.of()))));

	public LCDatapackRegistriesGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, BUILDER, Set.of("minecraft", L2Complements.MODID));
	}

	@NotNull
	public String getName() {
		return "L2Complements Data";
	}

}
