package dev.xkmc.curseofpandora.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.curseofpandora.content.effect.FakeRenderEffect;
import dev.xkmc.l2complements.init.L2Complements;
import net.minecraft.world.effect.MobEffect;

public class CoPFakeEffects {

	public static final RegistryEntry<FakeRenderEffect> FAKE_TERROR_PRE = L2Complements.REGISTRATE
			.effect("terror_token_pre", FakeRenderEffect::new,
					"For Render only. Shows premature Terror Token on target.")
			.lang(MobEffect::getDescriptionId).register();
	public static final RegistryEntry<FakeRenderEffect> FAKE_TERROR = L2Complements.REGISTRATE
			.effect("terror_token", () -> new FakeRenderEffect(4),
					"For render only. Shows Mature Terror Token count on target.")
			.lang(MobEffect::getDescriptionId).register();
	public static final RegistryEntry<FakeRenderEffect> FAKE_TERRORIZED = L2Complements.REGISTRATE
			.effect("terrorized", FakeRenderEffect::new,
					"For render only. Shows if you cannot deal damage against this target due to Curse of Tension effect.")
			.lang(MobEffect::getDescriptionId).register();
	public static final RegistryEntry<FakeRenderEffect> PRUDENCE = L2Complements.REGISTRATE
			.effect("prudence", FakeRenderEffect::new,
					"For render only. Shows if your damage is reduced due to Curse of Prudence effect.")
			.lang(MobEffect::getDescriptionId).register();

	public static void register() {

	}

}
