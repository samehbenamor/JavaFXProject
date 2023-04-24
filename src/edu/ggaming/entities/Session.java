/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.entities;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author balla
 */
public class Session {
     private static Session instance;
    private Map<String, Panier> data = new HashMap<>();

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setAttribute(String key, Panier value) {
        data.put(key, value);
    }

    public Panier getAttribute(String key) {
        return data.get(key);
    }

    public void removeAttribute(String key) {
        data.remove(key);
    }

    public void clear() {
        data.clear();
    }
}
