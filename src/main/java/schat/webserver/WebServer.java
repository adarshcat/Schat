package schat.webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebServer {
	int port;
	
	Server server;
	
	ServletContextHandler servletContext;
	ResourceHandler resourceHandler;
	ContextHandler resourceContext;
	
	public WebServer(int _port){
		port = _port;	
	}
	
	@SuppressWarnings("serial")
	public void initialiseWebServer() throws Exception{
		 server = new Server(port);
		 
		 // create servlet context handler
		 servletContext = new ServletContextHandler();
		 servletContext.setContextPath("/");
		 
		 // add servlets
		 
		 // attach a redirect to /home for /
		 servletContext.addServlet(new ServletHolder(new HttpServlet() {
			 @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                resp.sendRedirect("/home");
            }
		 }), "/");
		 
		 servletContext.addServlet(new ServletHolder(new LoginServlet()), "/login");
		 servletContext.addServlet(new ServletHolder(new RegisterServlet()), "/register");
		 servletContext.addServlet(new ServletHolder(new HomeServlet()), "/home");
		 
		 // ------------
	     
		 // create resource handler to serve static content
		 resourceHandler = new ResourceHandler();
		 resourceHandler.setDirectoriesListed(true);
		 resourceHandler.setResourceBase("src/main/resources/web_pages");
		 
		 resourceContext = new ContextHandler("/web_pages");
		 resourceContext.setHandler(resourceHandler);
		 
		 // attach the handlers
		 server.setHandler(new org.eclipse.jetty.server.handler.HandlerList(resourceContext, servletContext));
	     
	     server.start();
	     server.join();
	}

}
