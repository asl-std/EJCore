package org.aslstd.webserver.core.config;

import org.aslstd.api.bukkit.yaml.YAML;
import org.aslstd.core.Core;

public class GConfig extends YAML {

	public GConfig() {
		super(Core.instance().getDataFolder() + "/webserver.yml");
		loadConfig();
	}

	private void loadConfig() {

	}


}