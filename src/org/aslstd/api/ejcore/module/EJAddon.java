package org.aslstd.api.ejcore.module;

import java.io.IOException;

import org.aslstd.api.bukkit.yaml.Yaml;
import org.aslstd.core.Core;
import org.aslstd.core.manager.ModuleManager;
import org.bukkit.configuration.InvalidConfigurationException;

public abstract class EJAddon implements EJModule {

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
	 * <p>Constructor for EJAddon.</p>
	 */
	public EJAddon() { }

	/** {@inheritDoc} */
	@Override
	public Yaml config() {
		if (config == null) {
			config = Yaml.of(name().toLowerCase() + ".yml", Core.instance());
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
