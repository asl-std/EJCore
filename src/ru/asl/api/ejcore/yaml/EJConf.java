package ru.asl.api.ejcore.yaml;

import ru.asl.api.bukkit.plugin.EJPlugin;

/**
 * <p>Abstract EJConf class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public abstract class EJConf extends YAML {

	/**
	 * <p>Constructor for EJConf.</p>
	 *
	 * @param fileName a {@link java.lang.String} object
	 * @param plugin a {@link ru.asl.api.bukkit.plugin.EJPlugin} object
	 */
	public EJConf(String fileName, EJPlugin plugin) {
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
