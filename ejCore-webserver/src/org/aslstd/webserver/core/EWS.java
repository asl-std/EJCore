package org.aslstd.webserver.core;

import java.io.File;

import org.aslstd.api.bukkit.yaml.YAML;
import org.aslstd.api.ejcore.plugin.EJPlugin;
import org.aslstd.core.Core;
import org.aslstd.webserver.core.config.GConfig;

import lombok.Getter;

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
