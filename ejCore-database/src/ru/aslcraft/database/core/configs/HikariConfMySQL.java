package ru.aslcraft.database.core.configs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import org.bukkit.plugin.Plugin;
import ru.aslcraft.api.bukkit.yaml.EJConf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>HikariConfMySQL class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class HikariConfMySQL {
	@Getter private static Connection con;
	private static EJConf ejConf;
	/**
	 * <p>init.</p>
	 * @param plugin
	 */
	public void init(Plugin plugin) {
		String databaseName = "EJdb";
		String username = ejConf.getString("mysql.mysql-username","root", true);
		String password = ejConf.getString("mysql.mysql-password","toor", true);
		try {
	        String url = "jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull&useSSL=false";
	        Connection connection = DriverManager.getConnection(url,username, password);

	        String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;

	        Statement statement = connection.createStatement();
	        statement.executeUpdate(sql);
	        statement.close();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
		HikariConfig config = new HikariConfig();
		config.setPoolName("EJDataBaseMySQL");
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://localhost:3306/"+databaseName+"?useSSL=false");
		config.setUsername(username);
		config.setPassword(password);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		HikariDataSource ds = new HikariDataSource(config);
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
