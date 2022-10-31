package org.aslstd.database.impl;

import org.aslstd.core.Core;
import org.aslstd.database.api.DBType;
import org.aslstd.database.impl.configs.MySQLConfiguration;
import org.aslstd.database.impl.configs.SQLiteConfiguration;
import org.bukkit.plugin.Plugin;

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
