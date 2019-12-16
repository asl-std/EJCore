package ru.asl.api.ejcore.yaml;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;

import ru.asl.api.bukkit.plugin.EJPlugin;

public abstract class EJConf extends YAML {

	public EJConf(File file, EJPlugin plugin) {
		super(file);
		if (!fileExists()) plugin.saveResource(file.getName(), false);

		try {
			load();
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return;
		}

		loadConfig();
	}

	public EJConf(String path, EJPlugin plugin) {
		this(new File(path), plugin);
	}

	public abstract void loadConfig();

	@Override
	public void reload(){
		super.reload();
		loadConfig();
	}
}
