/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.cnx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author dhia
 */
public class MyConnection {
    
    private Connection cnx;
static MyConnection instance;
String url = "jdbc:mysql://localhost:3306/ggaming";
String user="root";
String pwd="";
    private MyConnection() {
        
    try {
        cnx=DriverManager.getConnection(url, user, pwd);
        
        System.out.println("Connection établie!!");
    } catch (SQLException ex) {
        System.out.println("Probleme de connection");    
    }
    } 
        
     
    public static MyConnection getInstance(){
        if(instance==null){
            instance=new MyConnection();
        }
        return instance;
        }

    public Connection getCnx() {
        return cnx;
    }
    
    
}
