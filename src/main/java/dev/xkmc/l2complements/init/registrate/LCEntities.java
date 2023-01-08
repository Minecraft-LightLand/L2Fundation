package dev.xkmc.l2complements.init.registrate;

import dev.xkmc.l2complements.content.entity.ISizedItemEntity;
import dev.xkmc.l2complements.content.entity.SpecialSpriteRenderer;
import dev.xkmc.l2complements.content.entity.fireball.BlackFireball;
import dev.xkmc.l2complements.content.entity.fireball.SoulFireball;
import dev.xkmc.l2complements.content.entity.fireball.StrongFireball;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.repack.registrate.util.entry.EntityEntry;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

public class LCEntities {

	public static final EntityEntry<SoulFireball> ETFB_SOUL;
	public static final EntityEntry<StrongFireball> ETFB_STRONG;
	public static final EntityEntry<BlackFireball> ETFB_BLACK;

	static {
		ETFB_SOUL = L2Complements.REGISTRATE
				.<SoulFireball>entity("soul_fire_charge", SoulFireball::new, MobCategory.MISC)
				.properties(e -> e.sized(1f, 1f).clientTrackingRange(4).updateInterval(10))
				.renderer(() -> LCEntities::addRenderer)
				.defaultLang().register();

		ETFB_STRONG = L2Complements.REGISTRATE
				.<StrongFireball>entity("strong_fire_charge", StrongFireball::new, MobCategory.MISC)
				.properties(e -> e.sized(1f, 1f).clientTrackingRange(4).updateInterval(10))
				.renderer(() -> LCEntities::addRenderer)
				.defaultLang().register();

		ETFB_BLACK = L2Complements.REGISTRATE
				.<BlackFireball>entity("black_fire_charge", BlackFireball::new, MobCategory.MISC)
				.properties(e -> e.sized(1f, 1f).clientTrackingRange(4).updateInterval(10))
				.renderer(() -> LCEntities::addRenderer)
				.defaultLang().register();

	}

	@OnlyIn(Dist.CLIENT)
	private static <T extends Entity & ItemSupplier & ISizedItemEntity> EntityRenderer<T> addRenderer(EntityRendererProvider.Context ctx) {
		return new SpecialSpriteRenderer<>(ctx, ctx.getItemRenderer(), true);
	}

	public static void register() {
	}

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
	}

}
