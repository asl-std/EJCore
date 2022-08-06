package ru.aslcraft.api.ejcore.module;

import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;

import ru.aslcraft.api.ejcore.yaml.YAML;
import ru.aslcraft.core.Core;
import ru.aslcraft.core.managers.ModuleManager;

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
			moduleConfiguration = new YAML(getModuleName().toLowerCase() + ".yml", Core.instance());
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
