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

import schat.sessionmanager.SessionManager;


@SuppressWarnings("serial")
public class HomeServlet extends HttpServlet implements WebPage{
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setStatus(HttpServletResponse.SC_OK);
		
		Cookie loginCookie = checkSession(req.getCookies());
		
		if (loginCookie != null) {
			//String username = SessionManager.getUsernameFromSessionId(loginCookie.getValue());
			serveHtml(resp);
		} else {
			resp.sendRedirect("/login");
		}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    }

    // Web page implementation
	@Override
	public void serveHtml(HttpServletResponse resp) throws IOException{
		String responseHtml = "";
		
		try (BufferedReader br = Files.newBufferedReader(Paths.get("src/main/resources/web_pages/home/home.html"))) {
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
