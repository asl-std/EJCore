package org.aslstd.api.bukkit.yaml;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * <p>Abstract EJConf class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public abstract class EJConf extends Yaml {

	/**
	 * <p>Constructor for EJConf.</p>
	 *
	 * @param fileName a {@link java.lang.String} object
	 * @param plugin a {@link ru.aslcraft.api.bukkit.plugin.EJPlugin} object
	 */
	public EJConf(String fileName, JavaPlugin plugin) {
		super(fileName, plugin);

		loadConfig();
	}

	/**
	 * <p>loadConfig.</p>
	 */
	public abstract void loadConfig();

	/** {@inheritDoc} */
	@Override
	public void reload(){
		super.reload();
		loadConfig();
	}
}
