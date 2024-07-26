package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.init.L2Complements;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LCDatapackRegistriesGen extends DatapackBuiltinEntriesProvider {

	private final String name;

	public LCDatapackRegistriesGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, RegistrySetBuilder builder, String name) {
		super(output, registries, builder, Set.of(L2Complements.MODID));
		this.name = name;
	}

	@NotNull
	public String getName() {
		return name;
	}

}
