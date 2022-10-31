package ru.aslcraft.webserver.core;

import java.io.File;

import lombok.Getter;
import ru.aslcraft.api.bukkit.yaml.YAML;
import ru.aslcraft.api.ejcore.plugin.EJPlugin;
import ru.aslcraft.core.Core;
import ru.aslcraft.webserver.core.config.GConfig;

public class EWS extends EJPlugin {

	@Override
	public int getPriority() { return 0; }

	@Getter private static GConfig cfg;

	@Getter private static Server webServer;

	@Override
	public void init() {
		checkConfig();

		if (Server.createServer()) {
			webServer = new Server();
			webServer.start();
		}
	}

	private void checkConfig() {
		final File file = new File(Core.instance().getDataFolder() + "/webserver.yml");

		if (!file.exists())
			YAML.exportFile(file.getName(), this, file.getParentFile());

		cfg = new GConfig();
	}

}
