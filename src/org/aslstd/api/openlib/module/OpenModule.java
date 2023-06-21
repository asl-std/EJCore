package org.aslstd.api.openlib.module;

import java.util.List;

import org.aslstd.api.bukkit.yaml.Yaml;
import org.aslstd.api.openlib.plugin.OpenPlugin;
import org.aslstd.core.OpenLib;

/**
 * <p>OpenModule interface.</p>
 *
 * @author Snoop1CattZ69
 */
public interface OpenModule {

	/** Constant <code>CORE_PLUGIN</code> */
	OpenPlugin CORE_PLUGIN = OpenLib.instance();

	/**
	 * <p>getModuleConfig.</p>
	 *
	 * @return a {@link Yaml} object
	 */
	Yaml config();

	/**
	 * <p>getModuleName.</p>
	 *
	 * @return a {@link String} object
	 */
	String name();

	/**
	 * <p>getModuleVersion.</p>
	 *
	 * @return a {@link String} object
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
