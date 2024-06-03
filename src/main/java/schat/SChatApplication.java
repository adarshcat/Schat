package schat;

import schat.webserver.WebServer;
import schat.databasemanager.ChatsDB;
import schat.databasemanager.DBManager;
import schat.databasemanager.LoginsDB;
import schat.sessionmanager.SessionManager;

public class SChatApplication {

	public static void main(String[] args) {
		// Database initialisation
		DBManager.initialiseDatabase();
		LoginsDB.initialise();
		ChatsDB.initialise();
		// ----------------------
		
		SessionManager.initialise();
		
		WebServer webServer = new WebServer(80);
		
		try {
			webServer.initialiseWebServer();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}