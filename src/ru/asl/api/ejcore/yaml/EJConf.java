package ru.asl.api.ejcore.yaml;

import ru.asl.api.bukkit.plugin.EJPlugin;

public abstract class EJConf extends YAML {

	public EJConf(String fileName, EJPlugin plugin) {
		super(fileName, plugin);

		loadConfig();
	}

	public abstract void loadConfig();

	@Override
	public void reload(){
		super.reload();
		loadConfig();
	}
}
