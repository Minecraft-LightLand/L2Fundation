package dev.xkmc.curseofpandora.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.curseofpandora.content.effect.FakeRenderEffect;
import dev.xkmc.l2complements.init.L2Complements;

public class CoPFakeEffects {

	public static final RegistryEntry<FakeRenderEffect> FAKE_TERROR_PRE = L2Complements.REGISTRATE.effect("terror_token_pre", FakeRenderEffect::new, "For render only").register();
	public static final RegistryEntry<FakeRenderEffect> FAKE_TERROR = L2Complements.REGISTRATE.effect("terror_token", FakeRenderEffect::new, "For render only").register();
	public static final RegistryEntry<FakeRenderEffect> FAKE_TERRORIZED = L2Complements.REGISTRATE.effect("terrorized", FakeRenderEffect::new, "For render only").register();
	public static final RegistryEntry<FakeRenderEffect> PRUDENCE = L2Complements.REGISTRATE.effect("prudence", FakeRenderEffect::new, "For render only").register();

	public static void register() {

	}

}
