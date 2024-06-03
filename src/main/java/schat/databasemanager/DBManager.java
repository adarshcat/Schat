package schat.databasemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	static Connection connection;
	static Statement statement;
	
	public static void initialiseDatabase() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schatdb", "adarsh", "123");
		    statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
