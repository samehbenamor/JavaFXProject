/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnection {
        private Connection cnx;
        String url = "jdbc:mysql://localhost:3306/ggaming";
        String user = "root";
        String pwd = "";
        public static MaConnection ct;

    public MaConnection() {
        try {
            cnx = DriverManager.getConnection(url,user,pwd);
            System.out.println("Connexion Etablie ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static MaConnection getInstance(){
        if(ct ==null)
            ct= new MaConnection();  //pour utiliser le meme object maconnection pour ne pas faire appel au constructeur 
        //de maconnection qui etablit la connection a chaque fois
        return ct;
    }

    public Connection getCnx() {
        return cnx;
    }
//review this
   
        
}
