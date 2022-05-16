package ru.asl.core.database.configs;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.plugin.Plugin;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Getter;

/**
 * <p>HikariConfSQLite class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class HikariConfSQLite {
	@Getter private static Connection con;
	/**
	 * <p>init.</p>
	 *
	 * @param plugin a {@link org.bukkit.plugin.Plugin} object
	 */
	public void init(Plugin plugin) {
		HikariConfig config = new HikariConfig();
		config.setPoolName("EJDataBaseSQLite");
		config.setDriverClassName("org.sqlite.JDBC");
		config.setJdbcUrl("jdbc:sqlite:"+plugin.getDataFolder()+"/EJDataBaseSQlite.db");
		config.setConnectionTestQuery("SELECT 1");
		config.setMaxLifetime(60000); // 60 Second
		config.setIdleTimeout(45000); // 45 Second
		config.setMaximumPoolSize(50); // 50 Connections (including idle connections)
		HikariDataSource ds = new HikariDataSource(config);
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
