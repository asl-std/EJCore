package org.aslstd.database.impl.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.aslstd.api.bukkit.yaml.EJConf;
import org.bukkit.plugin.Plugin;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Getter;

/**
 * <p>HikariConfMySQL class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class MySQLConfiguration {
	@Getter private static Connection con;
	private static EJConf ejConf;
	/**
	 * <p>init.</p>
	 * @param plugin
	 */
	public static void init(Plugin plugin) {
		final String databaseName = "EJdb";
		final String username = ejConf.getString("mysql.mysql-username", "root", true);
		final String password = ejConf.getString("mysql.mysql-password", "toor", true);
		try {
			final String url = "jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull&useSSL=false";
			final Connection connection = DriverManager.getConnection(url,username, password);

			final String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;

			final Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		final HikariConfig config = new HikariConfig();
		config.setPoolName("EJDataBaseMySQL");
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://localhost:3306/"+databaseName+"?useSSL=false");
		config.setUsername(username);
		config.setPassword(password);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		final HikariDataSource ds = new HikariDataSource(config);
		try {
			con = ds.getConnection();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
