/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming2;
import java.util.HashMap;
import java.util.UUID;
import ggaming.entity.Joueur;
/**
 *
 * @author DELL
 */
public class SessionManager {
    private static HashMap<String, Joueur> sessions = new HashMap<>();

    public static String createSession(Joueur joueur) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, joueur);
        return sessionId;
    }

    public static Joueur getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
