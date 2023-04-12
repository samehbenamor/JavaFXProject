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
import java.util.HashSet;
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
public class ServiceProduit {
    Connection cnx2;
    
    public ServiceProduit()
    {
        cnx2=MyConnection.getInstance().getCnx();
    }
    
public boolean isNumeric(String text) {
             if (text == null || text.trim().equals("")) {
                 return false;
             }
             for (int iCount = 0; iCount < text.length(); iCount++) {
                 if (!Character.isDigit(text.charAt(iCount))) {
                    return false;
               }
           }
            return true;
        }
     public void ajouterProduit()
    {
        String requete="INSERT INTO produit"
                + "(nom,description,prix,image,quantite)"
                + "VALUES ('casque','casque','casque','esprit.png',12)";
         try {
              Alert alert;
             Statement st=cnx2.createStatement();
             st.executeUpdate(requete);
             System.out.println("Produit ajouté avec succès");
               alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Produit ajouté avec succès!");
                    alert.showAndWait();
                    
         } catch (SQLException ex) {
            System.err.println(ex.getMessage());
         }
    }
    
     public void ajouterProduit2(Produit p)
    {
         
         try {
             Alert alert;
             String requete2="INSERT INTO produit"
                + "(nom,description,prix,image,quantite,categorie_produit_id)"
                + "VALUES (?,?,?,?,?,?)";
         
             PreparedStatement pst=cnx2.prepareStatement(requete2);
             pst.setString(1, p.getNom());
             pst.setString(2, p.getDescription());
             pst.setString(3, p.getPrix());
             pst.setString(4, p.getImage());
             pst.setInt(5, p.getQuantite());
             pst.setInt(6,p.getCategorie().getId());
          
             pst.executeUpdate();
             System.out.println("Produit Ajouté avec succès...");
              alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Produit ajouté avec succès!");
                    alert.showAndWait();
         } catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
    }
  
    public void modifierProduit(int id, Produit p)
    {
        String requete="UPDATE produit SET nom=?,description=?,prix=?,quantite=?,image=? WHERE id=? ";
        try {
            Alert alert;
             PreparedStatement pst=cnx2.prepareStatement(requete);
             pst.setString(1,p.getNom());
             pst.setString(2,p.getDescription());
             pst.setString(3,p.getPrix());
             pst.setInt(4,p.getQuantite());
             pst.setString(5,p.getImage());
             pst.setInt(6,id);
             
             pst.executeUpdate();
             pst.close();
             System.out.println("Produit Modifié avec succès...");
              alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Produit modifié avec succès!");
                    alert.showAndWait();
        } catch (SQLException ex) {
           System.err.println(ex.getMessage());
        }
       
        
    }
    public void supprimerProduit(int id)
    {
        String requete="DELETE FROM produit WHERE id=?";
        try {
            Alert alert;
            PreparedStatement pst=cnx2.prepareStatement(requete);
            pst.setInt(1,id);
            pst.executeUpdate();
            pst.close();
            System.out.println("Produit supprimé avec succès...");
             alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Produit supprimé avec succès!");
                    alert.showAndWait();
        } catch (SQLException ex) {
           System.err.println(ex.getMessage());
        }
    }
    public List<Produit> afficherProduit()
    {
        List<Produit> myList=new ArrayList<>();
        
         try {
             String requete3="SELECT * FROM produit";
             Statement st=cnx2.createStatement();
             ResultSet rs=st.executeQuery(requete3);
             
             while(rs.next())
             {
                 Produit p=new Produit();
                 p.setId(rs.getInt(1));
                 p.setNom(rs.getString("nom"));
                 p.setDescription(rs.getString("description"));
                 p.setImage(rs.getString("image"));
                 p.setPrix(rs.getString("prix"));
                 p.setQuantite(rs.getInt("quantite"));
                 myList.add(p);
                 
             }
         } catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
         
         return myList;
    }
    public Produit rechercherProduitByName(String nom_produit) throws SQLException
    {
         String req = "SELECT * FROM produit WHERE nom=?";
        Produit produit = null;
    try (PreparedStatement ps = cnx2.prepareStatement(req)) {
        ps.setString(1, nom_produit);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                produit = new Produit();
                produit.setId(rs.getInt("id"));
                produit.setNom(rs.getString("nom"));
              
            }
        }
    } catch (SQLException ex) {
        throw new SQLException("Erreur lors de la récupération de la catégorie de produit par nom : " + ex.getMessage(), ex);
    }
    return produit;
    }
     public ObservableList<Produit> getall() {
        ObservableList<Produit> produits = FXCollections.observableArrayList();
        ServiceCategorieProduit scp=new ServiceCategorieProduit();
        try {
            String req = "SELECT * FROM produit"; //ORDER BY date_produit ASC à mettre lorsqu'on va ajouter la date
            Statement st = cnx2.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                 Produit p=new Produit();
                 p.setId(rs.getInt(1));
                 p.setNom(rs.getString("nom"));
                 p.setDescription(rs.getString("description"));
                 p.setImage(rs.getString("image"));
                 p.setPrix(rs.getString("prix"));
                 p.setQuantite(rs.getInt("quantite"));
                 CategorieProduit categorie=scp.rechercherCategorieById(rs.getInt("categorie_produit_id"));
                 
                         
                 p.setCategorie(categorie);
                
                
                 produits.add(p);
            }
            System.out.print(produits);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return produits;
    }
}
