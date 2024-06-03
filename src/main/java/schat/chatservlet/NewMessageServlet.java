package schat.chatservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import schat.databasemanager.ChatsDB;

@SuppressWarnings("serial")
public class NewMessageServlet extends HttpServlet {
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		
        String newestMessageId = req.getParameter("newestMessageId");
        
        boolean parsed = false;
        
        if (newestMessageId != null) {
	        List<Message> messages = ChatsDB.getNewerMessages(Integer.parseInt(newestMessageId));
	        if (messages != null) {
	        	String messageJson = new Gson().toJson(messages);
	        	out.println(messageJson);
	        	resp.setStatus(200);
	        	parsed = true;
	        }
        }
        
        if (!parsed) {
        	resp.setContentType("text/plain");
        	resp.setStatus(204);
        	out.println("Something went wrong");
        }
	}
}
