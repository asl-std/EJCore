package ru.aslcraft.api.ejcore.module;

import java.util.List;

import ru.aslcraft.api.bukkit.plugin.EJPlugin;
import ru.aslcraft.api.ejcore.yaml.YAML;
import ru.aslcraft.core.Core;

/**
 * <p>EJModule interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface EJModule {

	/** Constant <code>CORE_PLUGIN</code> */
	EJPlugin CORE_PLUGIN = Core.instance();

	/**
	 * <p>getModuleConfig.</p>
	 *
	 * @return a {@link ru.aslcraft.api.ejcore.yaml.YAML} object
	 */
	YAML getModuleConfig();

	/**
	 * <p>getModuleName.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	String getModuleName();

	/**
	 * <p>getModuleVersion.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	String getModuleVersion();

	/**
	 * <p>getUncompatibleModules.</p>
	 *
	 * @return a {@link java.util.List} object
	 */
	List<String> getUncompatibleModules();

	/**
	 * <p>loadModule.</p>
	 */
	void loadModule();

	/**
	 * <p>reloadModule.</p>
	 */
	void reloadModule();

}
