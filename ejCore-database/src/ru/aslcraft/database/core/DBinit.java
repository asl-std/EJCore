package ru.aslcraft.database.core;

import org.bukkit.plugin.Plugin;

import ru.aslcraft.api.bukkit.yaml.EJConf;
import ru.aslcraft.database.core.configs.HikariConfMySQL;
import ru.aslcraft.database.core.configs.HikariConfSQLite;

/**
 * <p>DBinit class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class DBinit {
	private static EJConf ejConf;
	/**
	 * <p>init.</p>
	 *
	 * @param plugin a {@link org.bukkit.plugin.Plugin} object
	 */
	public void init(Plugin plugin) {
		new HikariConfSQLite().init(plugin);
		if (ejConf.getBoolean("mysql.mysql-enabled",false,true)) {
			new HikariConfMySQL().init(plugin);
		}
	}
}
