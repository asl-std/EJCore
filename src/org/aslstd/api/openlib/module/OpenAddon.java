package org.aslstd.api.openlib.module;

import java.io.IOException;

import org.aslstd.api.bukkit.yaml.Yaml;
import org.aslstd.core.OpenLib;
import org.aslstd.core.manager.ModuleManager;
import org.bukkit.configuration.InvalidConfigurationException;

public abstract class OpenAddon implements OpenModule {

	protected Yaml config;

	/**
	 * <p>isModuleRegistered.</p>
	 *
	 * @return a boolean
	 */
	public boolean isModuleRegistered() {
		return ModuleManager.isRegistered(name());
	}

	/**
	 * <p>Constructor for OpenAddon.</p>
	 */
	public OpenAddon() { }

	/** {@inheritDoc} */
	@Override
	public Yaml config() {
		if (config == null) {
			config = Yaml.of(name().toLowerCase() + ".yml", OpenLib.instance());
			if (!config.getFile().exists()) try {
				config.getFile().createNewFile();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		return config;
	}

	/** {@inheritDoc} */
	@Override
	public void reload() {
		try {
			config.load();
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

}
