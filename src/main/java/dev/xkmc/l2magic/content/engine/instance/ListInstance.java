package dev.xkmc.l2magic.content.engine.instance;

import dev.xkmc.l2magic.content.engine.core.EngineConfiguration;
import dev.xkmc.l2magic.content.engine.core.EngineContext;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public record ListInstance(ArrayList<EngineConfiguration<?>> children)
		implements EngineConfiguration<ListInstance> {

	@Override
	public void execute(EngineContext ctx) {
		for (var e : children) {
			e.execute(ctx);
		}
	}

	@Override
	public boolean verify(Logger logger, String path) {
		boolean ans = true;
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) == null) {
				logger.error(path + ": entry at index " + i + " is null");
				ans = false;
				continue;
			}
			ans &= children.get(i).verify(logger, path + "/children[" + i + "]");
		}
		return ans;
	}

}
