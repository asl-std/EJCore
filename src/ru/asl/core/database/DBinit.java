package ru.asl.core.database;

import org.bukkit.plugin.Plugin;

import ru.asl.core.Core;
import ru.asl.core.database.configs.HikariConfMySQL;
import ru.asl.core.database.configs.HikariConfSQLite;

public class DBinit {
	public void init(Plugin plugin) {
		new HikariConfSQLite().init(plugin);
		if (Core.getCfg().getBoolean("mysql.mysql-enabled",false,true)) {
			new HikariConfMySQL().init(plugin);
		}
	}
}
