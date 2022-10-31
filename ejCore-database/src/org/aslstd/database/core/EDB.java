package org.aslstd.database.core;

import org.aslstd.api.ejcore.plugin.EJPlugin;
import org.aslstd.core.Core;
import org.aslstd.database.impl.DatabaseInitializer;

public class EDB extends EJPlugin {

	@Override
	public int getPriority() { return 0; }

	@Override
	public void init() {
		DatabaseInitializer.init(Core.instance());
	}

}
