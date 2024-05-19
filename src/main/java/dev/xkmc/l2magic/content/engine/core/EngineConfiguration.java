package dev.xkmc.l2magic.content.engine.core;


import com.mojang.serialization.Codec;
import dev.xkmc.l2serial.util.Wrappers;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

public interface EngineConfiguration<T extends Record & EngineConfiguration<T>> {

	Codec<EngineConfiguration<?>> CODEC = ConfigurationRegistry.REGISTRY.get().byNameCodec()
			.dispatch(EngineConfiguration::type, ConfigurationType::codec);

	void execute(EngineContext ctx);

	default T self() {
		return Wrappers.cast(this);
	}

	@MustBeInvokedByOverriders
	default boolean verify(BuilderContext ctx) {
		ConfigurationAutomation.verifyVars(self(), ctx, Wrappers.cast(self().getClass()));
		return true;
	}

	ConfigurationType<T> type();

}