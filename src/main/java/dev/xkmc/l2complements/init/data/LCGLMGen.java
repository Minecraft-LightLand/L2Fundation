package dev.xkmc.l2complements.init.data;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.serial.loot.AddItemModifier;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class LCGLMGen extends GlobalLootModifierProvider {

	public LCGLMGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, L2Complements.MODID);
	}

	@Override
	protected void start() {
		source(LCItems.STORM_CORE, EntityType.PHANTOM, DamageTypeTags.IS_EXPLOSION);
		source(LCItems.HARD_ICE, EntityType.DROWNED, DamageTypeTags.IS_FREEZING);
		source(LCItems.GUARDIAN_EYE, EntityType.ELDER_GUARDIAN, DamageTypeTags.IS_LIGHTNING);
		source(LCItems.FORCE_FIELD, EntityType.WITHER, DamageTypeTags.IS_PROJECTILE);

		effect(LCItems.SOUL_FLAME, EntityType.GHAST, LCEffects.FLAME);
		effect(LCItems.BLACKSTONE_CORE, EntityType.PIGLIN_BRUTE, LCEffects.INCARCERATE);

		add("warden_bone_shard", new AddItemModifier(LCItems.WARDEN_BONE_SHARD.get(), null,
				LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
						new EntityPredicate.Builder().entityType(EntityTypePredicate.of(EntityType.WARDEN))).build(),
				LootItemKilledByPlayerCondition.killedByPlayer().build()));

	}

	private void source(ItemEntry<? extends Item> item, EntityType<?> entity, TagKey<DamageType> tag) {
		add(item.getId().getPath(), new AddItemModifier(item.get(), null,
				LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
						new EntityPredicate.Builder().entityType(EntityTypePredicate.of(entity))).build(),
				DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType()
						.tag(TagPredicate.is(tag))).build()
		));
	}

	private void effect(ItemEntry<? extends Item> item, EntityType<?> entity, Holder<MobEffect> effect) {
		add(item.getId().getPath(), new AddItemModifier(item.get(), null,
				LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
						new EntityPredicate.Builder()
								.entityType(EntityTypePredicate.of(entity))
								.effects(MobEffectsPredicate.Builder.effects().and(effect))
				).build()));
	}

}
