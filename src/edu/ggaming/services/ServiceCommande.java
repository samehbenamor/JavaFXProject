/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.services;

import edu.ggaming.entities.CategorieProduit;
import edu.ggaming.entities.Commande;
import edu.ggaming.utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author balla
 */
public class ServiceCommande {
    
    
    Connection cnx2;
    
    public ServiceCommande()
    {
        cnx2=MyConnection.getInstance().getCnx();
    }
    
     public void ajouterCommande(Commande c)
    {
         
         try {
             Alert alert;
             String requete2="INSERT INTO commmande"
                + "(reference,date,montant)"
                + "VALUES (?)";
         
             PreparedStatement pst=cnx2.prepareStatement(requete2);
             pst.setString(1, c.getReference());
             pst.setString(2, c.getDate());
             pst.setDouble(1, c.getMontant());
            
          
             pst.executeUpdate();
             System.out.println("Commande Ajouté avec succès...");
            
         } catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
    }
}
