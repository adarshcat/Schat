package schat.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import schat.databasemanager.LoginsDB;
import schat.sessionmanager.SessionManager;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet implements WebPage{
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// If they have a valid session id, means they are already logged in, redirect them to home
		boolean isLoggedIn = checkSession(req.getCookies()) != null;
		
		if (!isLoggedIn)
			serveHtml(resp);
		else
			resp.sendRedirect("/home");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String username = req.getParameter("username");
    	String password = req.getParameter("password");
    	
    	if (username != null && password != null) {
	    	if (LoginsDB.isUserPresent(username)) {
	    		if (LoginsDB.isPasswordCorrect(username, password)) {
	    			// Authentication successful
	    			
	    			// attach login cookie to the user
	    			String sessionId = SessionManager.createSession(username);
	    			
	    			Cookie loginCookie = new Cookie(SessionManager.sessionCookieId, sessionId);
	    			loginCookie.setMaxAge(30 * 60);
	    			resp.addCookie(loginCookie);
	    			
	    			resp.sendRedirect("/home");
	    		} else {
	    			// Password entered is wrong
	    			resp.sendRedirect("/login?login=passwordfail");
	    		}
	    	} else {
	    		// User is not present
	    		resp.sendRedirect("/login?login=usernamefail");
	    	}
    	}
    }
    
    // Web page implementation
	@Override
	public void serveHtml(HttpServletResponse resp) throws IOException{
		String responseHtml = "";
		
		try (BufferedReader br = Files.newBufferedReader(Paths.get("src/main/resources/web_pages/login/login.html"))) {
			for (String line; (line = br.readLine()) != null;) {
	            responseHtml += line+"\n";
	        }
		}

		resp.setContentType("text/html");
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().write(responseHtml);
	}
	
	@Override
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
