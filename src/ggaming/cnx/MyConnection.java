/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.cnx;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author oness
 */
public class MyConnection {
    private Connection cnx;
    private static MyConnection instance;
    
    public MyConnection() {
        try {
            cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/ggaming", "root", "");
            System.out.println("Connection successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static MyConnection getInstance() {
        if(instance == null) {
            instance = new MyConnection();
        }
        return instance;
    }
    
    public Connection getCnx() {
        return cnx;
    }
}
