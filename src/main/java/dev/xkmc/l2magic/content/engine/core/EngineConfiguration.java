package dev.xkmc.l2magic.content.engine.core;


import org.apache.logging.log4j.Logger;

public interface EngineConfiguration<T extends Record & EngineConfiguration<T>> {

	void execute(EngineContext ctx);

	boolean verify(Logger logger, String path);

}