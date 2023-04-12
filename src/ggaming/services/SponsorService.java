/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.services;

import ggaming.cnx.MyConnection;
import ggaming.entity.Equipe;
import ggaming.entity.Sponsor;
import ggaming.interfaces.sponsorinterfaces;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author dhia
 */
public class SponsorService implements sponsorinterfaces <Sponsor>{
    
     Connection cnx2;
    
    public SponsorService()
    {
        cnx2=MyConnection.getInstance().getCnx();
    }
     public void initConnection() {
        cnx2 = MyConnection.getInstance().getCnx();
    }
    
     public void ajouterSponsor(Sponsor s)
    {
         
         try {
             Alert alert;
             String requete2="INSERT INTO Sponsor"
                + "(nom_sponsor ,id_equipe_id, description_sponsor, logo_sponsor, site_webs, date_creationn  )"
                + "VALUES (?,?,?,?,?)";
         Timestamp timestampp = Timestamp.valueOf(s.getDate_creationn());
             PreparedStatement pst=cnx2.prepareStatement(requete2);
             pst.setString(1, s.getNom_sponsor());
            pst.setInt(2,s.getEquipe().getId());
             pst.setString(3, s.getDescription_sponsor());
             pst.setString(4, s.getLogo_sponsor());
             pst.setString(5, s.getSite_webs());
             pst.setTimestamp(6, timestampp);
             
          
             pst.executeUpdate();
             System.out.println("sponsor Ajouté avec succès...");
              alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("sponsor ajouté avec succès!");
                    alert.showAndWait();
         } catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
         
         
         

    
         
    }

    @Override
    public void inserts(Sponsor o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletes(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sponsor> displayAll() {
        List<Sponsor> all = new ArrayList<>();
        Statement stmt;
        try {
            String sql = "select * from Sponsor";
            
          stmt = cnx2.createStatement();
            
               ResultSet rs =  stmt.executeQuery(sql);
                
                while(rs.next()){
                   Sponsor a = new Sponsor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getTimestamp(6).toLocalDateTime());               
                    
                    all.add(a);                   
                }
                return all;
                    
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }
        return all;
    }

    @Override
    public Sponsor displayById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updates(Sponsor os) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public void supprimersponsor(int id)
    {
        String requete="DELETE FROM Sponsor WHERE id=?";
        try {
            Alert alert;
            PreparedStatement pst=cnx2.prepareStatement(requete);
            pst.setInt(1,id);
            pst.executeUpdate();
            pst.close();
            System.out.println("sponsor supprimé avec succès...");
             alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("sponsor supprimé avec succès!");
                    alert.showAndWait();
        } catch (SQLException ex) {
           System.err.println(ex.getMessage());
        }
    }
    
     public void modifiersponsor(Sponsor s) {
  try {
            String sql = "update Sponsor set nom_sponsor=? ,description_sponsor = ? ,logo_sponsor = ? ,site_webs = ? where id = ?";
            
            PreparedStatement stmt = cnx2.prepareStatement(sql);
             stmt.setString(1, s.getNom_sponsor());
             stmt.setString(2, s.getDescription_sponsor());
             stmt.setString(3, s.getLogo_sponsor());
            stmt.setString(4, s.getSite_webs());
            
         
            stmt.executeUpdate();
            System.out.println("sponsor modifié avec success");
            
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }
            }
    
    
   
    
    
    
    
    
    
    
    
}
