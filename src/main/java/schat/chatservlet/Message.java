package schat.chatservlet;

import java.sql.Timestamp;

public class Message {
	int id;
	String username;
	String message;
	Timestamp timestamp;
	
	public Message(int id, String username, String message, Timestamp timestamp) {
        this.id = id;
        this.username = username;
        this.message = message;
        this.timestamp = timestamp;
    }
}
