package schat.sessionmanager;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
	public static final String sessionCookieId = "sessionId";
	
	static ConcurrentHashMap<String, String> sessionMap;
	static SecureRandom secureRandom;
	
	public static void initialise() {
		sessionMap = new ConcurrentHashMap<String, String>();
		 secureRandom = new SecureRandom();
	}
	
	public static String getUsernameFromSessionId(String sessionId) {
		if (!doesSessionExist(sessionId)) return null;
		
		return sessionMap.get(sessionId);
	}
	
	public static String createSession(String username) {
		String sessionId = generateSessionId();
		sessionMap.put(sessionId, username);
		
		return sessionId;
	}
	
	public static boolean doesSessionExist(String sessionId) {
		return sessionMap.containsKey(sessionId);
	}
	
	private static String generateSessionId() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        String id = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        
        return id;
    }
}
