package dev.xkmc.l2complements.content.effect.force;

import dev.xkmc.l2library.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2library.base.effects.api.IconOverlayEffect;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;

@Deprecated
public interface NoSelfRenderEffect extends IconOverlayEffect {

	@Override
	default void render(LivingEntity entity, int lv, Consumer<DelayedEntityRender> adder) {
		if (entity == Proxy.getClientPlayer()) {
			return;
		}
		IconOverlayEffect.super.render(entity, lv, adder);
	}
}
