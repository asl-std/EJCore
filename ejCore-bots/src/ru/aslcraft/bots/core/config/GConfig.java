package ru.aslcraft.bots.core.config;

import ru.aslcraft.api.bukkit.yaml.YAML;
import ru.aslcraft.core.Core;

public class GConfig extends YAML {

	public GConfig() {
		super(Core.instance().getDataFolder() + "/bots.yml");
		loadConfig();
	}

	private void loadConfig() {

	}


}
