package dev.xkmc.l2complements.init.data;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.init.data.MaterialDamageTypeMultiplex;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DamageTypeTagGen extends TagsProvider<DamageType> {

	public DamageTypeTagGen(PackOutput output,
							CompletableFuture<HolderLookup.Provider> pvd,
							@Nullable ExistingFileHelper files) {
		super(output, Registries.DAMAGE_TYPE, pvd, L2Complements.MODID, files);
	}

	@Override
	protected void addTags(HolderLookup.Provider pvd) {
		tag(MaterialDamageTypeMultiplex.MAGIC).add(DamageTypeGen.SOUL_FLAME);
		tag(DamageTypeTags.BYPASSES_ARMOR).add(DamageTypeGen.SOUL_FLAME, DamageTypeGen.BLEED, DamageTypeGen.VOID_EYE, DamageTypeGen.LIFE_SYNC);
		tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(DamageTypeGen.BLEED, DamageTypeGen.VOID_EYE, DamageTypeGen.LIFE_SYNC);
		tag(DamageTypeTags.BYPASSES_RESISTANCE).add(DamageTypeGen.BLEED, DamageTypeGen.VOID_EYE, DamageTypeGen.LIFE_SYNC);
		tag(DamageTypeTags.BYPASSES_INVULNERABILITY).add(DamageTypeGen.VOID_EYE);
	}

}
