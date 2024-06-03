package schat.chatservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import schat.databasemanager.ChatsDB;
import schat.sessionmanager.SessionManager;

@SuppressWarnings("serial")
public class SendMessageServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		Cookie loginCookie = checkSession(req.getCookies());
		
		if (loginCookie != null) {
			String username = SessionManager.getUsernameFromSessionId(loginCookie.getValue());
			String message = req.getParameter("message");
			
			ChatsDB.storeNewMessage(username, message);
		}
	}
	
	
	public Cookie checkSession(Cookie[] cookies) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(SessionManager.sessionCookieId)) {
					if (SessionManager.doesSessionExist(cookie.getValue())) {
						return cookie;
					}
				}
			}
		}
		
		return null;
	}
}
