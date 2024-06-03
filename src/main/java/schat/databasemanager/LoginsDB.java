package schat.databasemanager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginsDB {
		
	public static void initialise() {
		
	}
	
	public static boolean addUser(String username, String password) {
		// insert into logins(username, password) values('johasasn', 12);
		String command = "insert into logins(username, password) values(\'" + username +"\', \'" + password + "\')";
		
		try {
			int rowUpdated = DBManager.statement.executeUpdate(command);
			
			if (rowUpdated == 0) return false;
			
		} catch (SQLException e) {
			return false;
		}
		
		return true;

	}
	
	public static boolean isUserPresent(String username) {
		String command = "select * from logins where username=\'" + username + "\'";
		
		try {
			ResultSet resultSet = DBManager.statement.executeQuery(command);
			int rowCount = 0;
			
			while(resultSet.next()) {
				rowCount++;
			}
			
			if (rowCount != 0) return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean isPasswordCorrect(String username, String password) {
		String command = "select * from logins where username=\'" + username + "\' and password=\'" + password + "\'";
		
		try {
			ResultSet resultSet = DBManager.statement.executeQuery(command);
			int rowCount = 0;
			
			while(resultSet.next()) {
				rowCount++;
			}
			
			if (rowCount != 0) return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
