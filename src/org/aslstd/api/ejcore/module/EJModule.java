package org.aslstd.api.ejcore.module;

import java.util.List;

import org.aslstd.api.bukkit.yaml.Yaml;
import org.aslstd.api.ejcore.plugin.EJPlugin;
import org.aslstd.core.Core;

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
	 * @return a {@link Yaml.aslcraft.api.ejcore.yaml.YAML} object
	 */
	Yaml config();

	/**
	 * <p>getModuleName.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	String name();

	/**
	 * <p>getModuleVersion.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	String version();

	/**
	 * <p>getUncompatibleModules.</p>
	 *
	 * @return a {@link java.util.List} object
	 */
	List<String> uncompatible();

	/**
	 * <p>loadModule.</p>
	 */
	void load();

	/**
	 * <p>reloadModule.</p>
	 */
	void reload();

}
