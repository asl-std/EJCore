package ru.aslcraft.database.impl;

import org.bukkit.plugin.Plugin;

import ru.aslcraft.core.Core;
import ru.aslcraft.database.api.DBType;
import ru.aslcraft.database.impl.configs.MySQLConfiguration;
import ru.aslcraft.database.impl.configs.SQLiteConfiguration;

/**
 * <p>DBinit class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class DatabaseInitializer {


	public static void init(Plugin plugin) {
		final DBType type = DBType.byString(Core.getCfg().getString("database.db-type", "sqlite", true));

		if (type == DBType.SQLITE) SQLiteConfiguration.init(plugin);

		if (type == DBType.MYSQL) MySQLConfiguration.init(plugin);
	}
}
