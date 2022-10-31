package ru.aslcraft.database.core;

import ru.aslcraft.api.ejcore.plugin.EJPlugin;
import ru.aslcraft.core.Core;
import ru.aslcraft.database.impl.DatabaseInitializer;

public class EDB extends EJPlugin {

	@Override
	public int getPriority() { return 0; }

	@Override
	public void init() {
		DatabaseInitializer.init(Core.instance());
	}

}
