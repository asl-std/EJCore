package org.aslstd.core.configs;

import org.aslstd.api.bukkit.yaml.EJConf;
import org.aslstd.api.ejcore.plugin.EJPlugin;

/**
 * <p>LangConfig class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class LangConfig extends EJConf {

	public String NAME_DURABILITY, DURABILITY_SUFFIX_COLOR_DECORATOR;
	public String ERR_CONSOLE, ERR_NO_PERMISSION, ERR_ARG_CANT_BE_NULL, ERR_PLAYER_ONLINE;

	/**
	 * <p>Constructor for LangConfig.</p>
	 *
	 * @param fileName a {@link java.lang.String} object
	 * @param plugin a {@link org.aslstd.api.ejcore.plugin.EJPlugin} object
	 */
	public LangConfig(String fileName, EJPlugin plugin) {
		super(fileName, plugin);
	}

	/** {@inheritDoc} */
	@Override
	public void loadConfig() {
		ERR_CONSOLE = this.getString("err.console-error", "&4Join to server for using this command.", true);
		ERR_NO_PERMISSION = this.getString("err.no-permission", "&4You don't have permission to do this.", true);
		ERR_ARG_CANT_BE_NULL = this.getString("err.arg-null", "&4Args can't be null", true);
		ERR_PLAYER_ONLINE = this.getString("err.player-online", "&4Offline player can't be used in commands", true);

		NAME_DURABILITY = this.getString("util.durability", "&7Durability", true);
		DURABILITY_SUFFIX_COLOR_DECORATOR = this.getString("util.durability-suffix-color-decorator", "&7", true);
	}

}
