package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2damagetracker.init.data.DamageTypeAndTagsGen;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class DamageTypeGen extends DamageTypeAndTagsGen {

	public static final ResourceKey<DamageType> EMERALD = create("emerald");
	public static final ResourceKey<DamageType> SOUL_FLAME = create("soul_flame");
	public static final ResourceKey<DamageType> BLEED = create("bleed");
	public static final ResourceKey<DamageType> LIFE_SYNC = create("life_sync");
	public static final ResourceKey<DamageType> VOID_EYE = create("void_eye");

	public DamageTypeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> pvd, ExistingFileHelper helper) {
		super(output, pvd, helper, L2Complements.MODID);
		new DamageTypeHolder(EMERALD, new DamageType("emerald", DamageScaling.NEVER, 0.1f))
				.add(DamageTypeTags.AVOIDS_GUARDIAN_THORNS);

		new DamageTypeHolder(SOUL_FLAME, new DamageType("soul_flame", DamageScaling.NEVER, 0, DamageEffects.BURNING))
				.add(DamageTypeTags.BYPASSES_ARMOR, Tags.DamageTypes.IS_MAGIC, DamageTypeTags.AVOIDS_GUARDIAN_THORNS, L2DamageTypes.NO_SCALE, DamageTypeTags.NO_KNOCKBACK);
		new DamageTypeHolder(BLEED, new DamageType("bleed", DamageScaling.NEVER, 0.1f))
				.add(DamageTypeTags.BYPASSES_ARMOR, L2DamageTypes.NO_SCALE, DamageTypeTags.NO_KNOCKBACK).add(L2DamageTypes.BYPASS_MAGIC);
		new DamageTypeHolder(LIFE_SYNC, new DamageType("life_sync", DamageScaling.NEVER, 0f))
				.add(DamageTypeTags.BYPASSES_ARMOR, L2DamageTypes.NO_SCALE, DamageTypeTags.NO_KNOCKBACK).add(L2DamageTypes.BYPASS_MAGIC);
		new DamageTypeHolder(VOID_EYE, new DamageType("void_eye", DamageScaling.NEVER, 0f))
				.add(DamageTypeTags.NO_KNOCKBACK).add(L2DamageTypes.BYPASS_INVUL);
	}

	public static Holder<DamageType> forKey(Level level, ResourceKey<DamageType> key) {
		return level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
	}

	private static ResourceKey<DamageType> create(String id) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, L2Complements.loc(id));
	}

}
