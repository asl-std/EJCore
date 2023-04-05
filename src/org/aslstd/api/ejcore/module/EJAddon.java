package org.aslstd.api.ejcore.module;

import java.io.IOException;

import org.aslstd.api.bukkit.yaml.YAML;
import org.aslstd.core.Core;
import org.aslstd.core.managers.ModuleManager;
import org.bukkit.configuration.InvalidConfigurationException;

/**
 * <p>Abstract EJAddon class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public abstract class EJAddon implements EJModule {

	protected YAML moduleConfiguration;

	/**
	 * <p>isModuleRegistered.</p>
	 *
	 * @return a boolean
	 */
	public boolean isModuleRegistered() {
		return ModuleManager.isRegistered(getModuleName());
	}

	/**
	 * <p>Constructor for EJAddon.</p>
	 */
	public EJAddon() { }

	/** {@inheritDoc} */
	@Override
	public YAML getModuleConfig() {
		if (moduleConfiguration == null) {
			moduleConfiguration = YAML.of(getModuleName().toLowerCase() + ".yml", Core.instance());
			if (!moduleConfiguration.getFile().exists()) try {
				moduleConfiguration.getFile().createNewFile();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		return moduleConfiguration;
	}

	/** {@inheritDoc} */
	@Override
	public void reloadModule() {
		try {
			moduleConfiguration.load();
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

}
