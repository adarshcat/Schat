package schat;

import schat.webserver.WebServer;
import schat.databasemanager.LoginsDB;
import schat.sessionmanager.SessionManager;

public class SChatApplication {

	public static void main(String[] args) {
		LoginsDB.initialiseDatabase();
		SessionManager.initialise();
		
		WebServer webServer = new WebServer(8080);
		
		try {
			webServer.initialiseWebServer();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}
