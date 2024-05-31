package schat.webserver;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

interface WebPage {
	void serveHtml(HttpServletResponse resp) throws IOException;
	Cookie checkSession(Cookie[] cookies);
}
