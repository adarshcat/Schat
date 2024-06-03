package schat.databasemanager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import schat.chatservlet.Message;

public class ChatsDB {
	
	final static int messageLimit = 20;
	
	public static void initialise() {
		
	}
	
	public static List<Message> getOlderMessages(int cursorId){
		try {
			String sqlQuery = "SELECT * FROM chats WHERE id < ? ORDER BY id DESC LIMIT ?";
			
			PreparedStatement stmt = DBManager.connection.prepareStatement(sqlQuery);
			stmt.setInt(1, cursorId);
			stmt.setInt(2, messageLimit);
			
			ResultSet rset = stmt.executeQuery();
			
			List<Message> messages = new ArrayList<>();
            while (rset.next()) {
                messages.add(new Message(rset.getInt("id"), rset.getString("username"), rset.getString("message"), rset.getTimestamp("timestamp")));
            }
            
            if (messages.size() != 0) return messages;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static List<Message> getNewerMessages(int cursorId){
		try {
			String sqlQuery = "SELECT * FROM chats WHERE id > ? ORDER BY id ASC";
			
			PreparedStatement stmt = DBManager.connection.prepareStatement(sqlQuery);
			stmt.setInt(1, cursorId);
			
			ResultSet rset = stmt.executeQuery();
			
			List<Message> messages = new ArrayList<>();
            while (rset.next()) {
                messages.add(new Message(rset.getInt("id"), rset.getString("username"), rset.getString("message"), rset.getTimestamp("timestamp")));
            }
            
            if (messages.size() != 0) return messages;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean storeNewMessage(String username, String message) {
		try {
			String sqlQuery = "INSERT INTO chats(username, message) values(?,?)";
			
			PreparedStatement stmt = DBManager.connection.prepareStatement(sqlQuery);
			stmt.setString(1, username);
			stmt.setString(2, message);
			
			int rowsUpdated = stmt.executeUpdate();
			
			if (rowsUpdated != 0) return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
