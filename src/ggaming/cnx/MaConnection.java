/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.cnx;

import java.sql.*;
/**
 *
 * @author dell
 */
public class MaConnection {
    
    private Connection cnx;
    private static MaConnection instance;
    
    private MaConnection() {
        try {
            cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/ggaming", "root", "");
            System.out.println("Connection successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static MaConnection getInstance() {
        if(instance == null) {
            instance = new MaConnection();
        }
        return instance;
    }
    
    public Connection getCnx() {
        return cnx;
    }
    
}