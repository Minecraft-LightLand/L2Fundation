package dev.xkmc.l2magic.content.engine.core;

import com.mojang.serialization.Codec;

public interface ConfigurationType<T extends Record & EngineConfiguration<T>> {

	Codec<T> codec();

}
