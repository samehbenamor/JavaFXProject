/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.services;

import ggaming.cnx.MyConnection;
import ggaming.entity.Equipe;
import ggaming.interfaces.equipeinterfaces;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author dhia
 */
public class EquipeService implements equipeinterfaces <Equipe> {
    
    
 private static EquipeService instance;
    private Statement st;
    private ResultSet rs;
 Connection cnx2;
    
    /*
    {
        cnx2=MyConnection.getInstance().getCnx();
    }
*/
    public EquipeService() {
        MyConnection cs = MyConnection.getInstance();
        try {
            st = cs.getCnx().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        cnx2=MyConnection.getInstance().getCnx();

    }
    
     public void initConnection() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public static EquipeService getInstance() {
        if (instance == null) {
            instance = new EquipeService();
        }
        return instance;
    }
    @Override
    public void insert(Equipe o) {
       String req = "INSERT INTO Equipe (id, nom_equipe , description_equipe, nb_joueurs, logo_equipe, site_web ,date_creation ) VALUES ('" + o.getId() + "', '" + o.getNom_equipe() + "', '" + o.getDescription_equipe() + "', '" + o.getNb_joueurs() + "', '" + o.getLogo_equipe() + "', '" + o.getSite_web() + "', '" + o.getDate_creation() + "')";

        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
      public void ajouterEquipe(Equipe e)
    {
         
         try {
             Alert alert;
             String requete2="INSERT INTO Equipe"
                + "(nom_equipe , description_equipe, nb_joueurs, logo_equipe, site_web ,date_creation )"
                + "VALUES (?,?,?,?,?,?)";
         
             PreparedStatement pst=cnx2.prepareStatement(requete2);
             pst.setString(1, e.getNom_equipe());
             pst.setString(2, e.getDescription_equipe());
             pst.setInt(3, e.getNb_joueurs());
             pst.setString(4, e.getLogo_equipe());
             pst.setString(5, e.getSite_web());
             pst.setDate(6, e.getDate_creation());
          
             pst.executeUpdate();
             System.out.println("equipe Ajouté avec succès...");
              alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("equipe ajouté avec succès!");
                    alert.showAndWait();
         } catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
    }

    @Override
    public void delete(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void deletee(Equipe e) {
 try {
            String sql = "delete from Equipe where id = ? ";
            
            PreparedStatement stmt = cnx2.prepareStatement(sql);

            stmt.setInt(1, e.getId());
         
            stmt.executeUpdate();
            System.out.println("equipe supprimer avec success");
            
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }


    }
    
      public void supprimerequipe(int id)
    {
        String requete="DELETE FROM Equipe WHERE id=?";
        try {
            Alert alert;
            PreparedStatement pst=cnx2.prepareStatement(requete);
            pst.setInt(1,id);
            pst.executeUpdate();
            pst.close();
            System.out.println("equipe supprimé avec succès...");
             alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("equipe supprimé avec succès!");
                    alert.showAndWait();
        } catch (SQLException ex) {
           System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Equipe> displayAll() {
        List<Equipe> all = new ArrayList<>();
        Statement stmt;
        try {
            String sql = "select * from Equipe";
            
          stmt = cnx2.createStatement();
            
               ResultSet rs =  stmt.executeQuery(sql);
                
                while(rs.next()){
                   Equipe b = new Equipe(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getInt(6),rs.getDate(7));               
                    
                    all.add(b);                   
                }
                return all;
                    
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }
        return all;
    }

    @Override
    public Equipe displayById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Equipe os) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public void modifierEquipe(int id, Equipe e)
    {
        String requete="UPDATE Equipe SET nom_equipe=?,description_equipe=?,nb_joueurs=?,logo_equipe=?,site_web=? WHERE id=? ";
        try {
            Alert alert;
             PreparedStatement pst=cnx2.prepareStatement(requete);
             pst.setString(1,e.getNom_equipe());
             pst.setString(2,e.getDescription_equipe());
             pst.setInt(3,e.getNb_joueurs());
             pst.setString(4,e.getLogo_equipe());
             pst.setString(5,e.getSite_web());
             
             
             pst.setInt(6,id);
             
             pst.executeUpdate();
             pst.close();
             System.out.println("equipe Modifié avec succès...");
              alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("equipe modifié avec succès!");
                    alert.showAndWait();
        } catch (SQLException ex) {
           System.err.println(ex.getMessage());
        }
       
        
    }
    
}
