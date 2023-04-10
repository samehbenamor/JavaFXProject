/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.services;

import edu.ggaming.entities.CategorieProduit;
import edu.ggaming.entities.Produit;
import edu.ggaming.utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author balla
 */
public class ServiceCategorieProduit {
    Connection cnx2;
    
    public ServiceCategorieProduit()
    {
        cnx2=MyConnection.getInstance().getCnx();
    }
    
     public void ajouterCategorieProduit(CategorieProduit cp)
    {
         
         try {
             Alert alert;
             String requete2="INSERT INTO categorie_produit"
                + "(nom)"
                + "VALUES (?)";
         
             PreparedStatement pst=cnx2.prepareStatement(requete2);
             pst.setString(1, cp.getNom());
            
          
             pst.executeUpdate();
             System.out.println("Categorie Produit Ajouté avec succès...");
              alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Categorie Produit ajouté avec succès!");
                    alert.showAndWait();
         } catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
    }
     public void modifierCategorieProduit(int id, CategorieProduit cp)
    {
        String requete="UPDATE categorie_produit SET nom=? WHERE id=? ";
        try {
            Alert alert;
             PreparedStatement pst=cnx2.prepareStatement(requete);
             pst.setString(1,cp.getNom());
             pst.setInt(2,id);
             
             pst.executeUpdate();
             pst.close();
             System.out.println("Categorie Prdouit Modifié avec succès...");
              alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Produit modifié avec succès!");
                    alert.showAndWait();
        } catch (SQLException ex) {
           System.err.println(ex.getMessage());
        }
       
        
    }
    public void supprimerCategorieProduit(int id)
    {
        String requete="DELETE FROM categorie_produit WHERE id=?";
        try {
            Alert alert;
            PreparedStatement pst=cnx2.prepareStatement(requete);
            pst.setInt(1,id);
            pst.executeUpdate();
            pst.close();
            System.out.println("Categorie Produit  supprimé avec succès...");
             alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Categorie Produit supprimé avec succès!");
                    alert.showAndWait();
        } catch (SQLException ex) {
           System.err.println(ex.getMessage());
        }
    }
   public CategorieProduit rechercherCategorieByName(String nom_categorie) throws SQLException {
    String req = "SELECT * FROM categorie_produit WHERE nom=?";
    CategorieProduit categorie = null;
    try (PreparedStatement ps = cnx2.prepareStatement(req)) {
        ps.setString(1, nom_categorie);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                categorie = new CategorieProduit();
                categorie.setId(rs.getInt("id"));
                categorie.setNom(rs.getString("nom"));
                categorie.setRefer(rs.getString("refer"));
            }
        }
    } catch (SQLException ex) {
        throw new SQLException("Erreur lors de la récupération de la catégorie de produit par nom : " + ex.getMessage(), ex);
    }
    return categorie;
    }
   
   
   public CategorieProduit rechercherCategorieById(int id)throws SQLException
   {
        String req = "SELECT * FROM categorie_produit WHERE id=?";
        CategorieProduit categorie = null;
        try (PreparedStatement ps = cnx2.prepareStatement(req)) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                categorie = new CategorieProduit();
                categorie.setId(rs.getInt("id"));
                categorie.setNom(rs.getString("nom"));
                categorie.setRefer(rs.getString("refer"));
            }
        }
    } catch (SQLException ex) {
        throw new SQLException("Erreur lors de la récupération de la catégorie de produit par nom : " + ex.getMessage(), ex);
    }
    return categorie;
   }
   
   
   
     public ObservableList<CategorieProduit> getall() {
        ObservableList<CategorieProduit> categories = FXCollections.observableArrayList();
        try {
            String req = "SELECT * FROM categorie_produit"; //ORDER BY date_produit ASC à mettre lorsqu'on va ajouter la date
            Statement st = cnx2.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                CategorieProduit cp=new CategorieProduit();
                 cp.setId(rs.getInt(1));
                 cp.setNom(rs.getString("nom"));
                 cp.setRefer(rs.getString("refer"));
                 
                 categories.add(cp);
            }
            System.out.print(categories);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;
    }
}
