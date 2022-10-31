package ru.aslcraft.database.api;

/**
 * <p>DBType class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public enum DBType {
	SQLITE,
	MYSQL;

	public static DBType byString(String type) {
		switch(type.toLowerCase()) {
		case "mysql":
		case "msql":
			return MYSQL;
		default: return SQLITE;
		}
	}
}
