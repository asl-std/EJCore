package ru.aslcraft.database.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.database.impl.configs.MySQLConfiguration;
import ru.aslcraft.database.impl.configs.SQLiteConfiguration;

/**
 * <p>DB class.</p>
 *
 * @author ZooMMaX
 * @version 1.2.17
 */
@Builder
public class DB {
	@Getter
	@Setter
	@NonNull
	private DBType dataBaseType;
	
	@Getter
	@Setter
	@NonNull
	private String sql;
	
	@Getter
	@Setter
	private HashMap<Integer, Object> setData;
	
	private Connection connection() {
		switch (dataBaseType) {
		case SQLITE:
			return SQLiteConfiguration.getCon();
			
			
		case MYSQL:
			return MySQLConfiguration.getCon();
			
		default:
			return SQLiteConfiguration.getCon();
		}
	}
	
	/**
	 * <p>execSQL.</p>
	 *
	 * @return a {@link java.lang.Boolean}
	 */
	public boolean execSQL(){	
        try(PreparedStatement stmt = connection().prepareStatement(sql)){
        	if (setData != null && !setData.isEmpty()) {
				for(int x = 0; x < setData.size(); x++) {
					stmt.setObject(x+1, setData.get(x+1));
				}
			}
            stmt.execute();
            return true;
        }catch (SQLException e){
            EText.warn(e.toString(), "EJC DataBase error execSQL");
            return false;
        }
    }
	
	
	/**
	 * <p>getMultiResultSet.</p>
	 *
	 * @return a {@link java.util.ArrayList} of the {@link java.util.HashMap}
	 */
	public ArrayList<HashMap<String, Object>> getMultiResultSet() {
		ArrayList<HashMap<String, Object>> tmpHashMaps = new ArrayList<>();
		ArrayList<String> colNames = new ArrayList<>();
		try(PreparedStatement stmt = connection().prepareStatement(sql)){
				if (setData != null && !setData.isEmpty()) {
					for(int x = 0; x < setData.size(); x++) {
						stmt.setObject(x+1, setData.get(x+1));
					}
				}
	           	ResultSet rs = stmt.executeQuery();
	           	ResultSetMetaData rsmd = rs.getMetaData();
	           	HashMap<String, Object> tmpHashMap = new HashMap<>();
	           	for(int x = 1; x < rsmd.getColumnCount()+1; x++) {
	           		colNames.add(rsmd.getColumnName(x));
	           		}
	           	while(rs.next()) {
	           		for(String s : colNames) {
	           			tmpHashMap.put(s, rs.getObject(s));
	           		}
	           		tmpHashMaps.add(tmpHashMap);
	           	}
	           	return tmpHashMaps;
 	        }catch (SQLException e){
	            EText.warn(e.toString(),"EJC DataBase error getMultiResultSet");
	            return null;
	        }
	}
}
