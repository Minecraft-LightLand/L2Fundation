package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.init.L2Complements;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DamageTypeGen extends DatapackBuiltinEntriesProvider {

	public static final ResourceKey<DamageType> EMERALD = create("emerald");
	public static final ResourceKey<DamageType> SOUL_FLAME = create("soul_flame");
	public static final ResourceKey<DamageType> BLEED = create("bleed");
	public static final ResourceKey<DamageType> LIFE_SYNC = create("life_sync");
	public static final ResourceKey<DamageType> VOID_EYE = create("void_eye");

	public static Holder<DamageType> forKey(Level level, ResourceKey<DamageType> key) {
		return level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
	}

	private static ResourceKey<DamageType> create(String id) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(L2Complements.MODID, id));
	}

	private static void add(BootstapContext<DamageType> ctx) {
		ctx.register(EMERALD, new DamageType("emerald", DamageScaling.NEVER, 0.1f));
		ctx.register(SOUL_FLAME, new DamageType("soul_flame", DamageScaling.NEVER, 0, DamageEffects.BURNING));
		ctx.register(BLEED, new DamageType("bleed", DamageScaling.NEVER, 0.1f));
		ctx.register(LIFE_SYNC, new DamageType("life_sync", DamageScaling.NEVER, 0f));
		ctx.register(VOID_EYE, new DamageType("void_eye", DamageScaling.NEVER, 0f));
	}

	public DamageTypeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, new RegistrySetBuilder().add(Registries.DAMAGE_TYPE, DamageTypeGen::add), Set.of(L2Complements.MODID));
	}

}
