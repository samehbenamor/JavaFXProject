/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.services;

import ggaming.entity.*;

import ggaming.cnx.MaConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServiceJeux {
    
    private Connection cnx;
    
    public void initConnection() {
        cnx = MaConnection.getInstance().getCnx();
    }
    
    public void ajouter(Jeux j) {
        try {
            String sql = "insert into Jeux(id,ref,libelle,logo_jeux,image_jeux,date_creation,note,note_count,note_myonne,total_note,views) values (?,?,?,?,?,?,?,?,?,?,?) ";
            Timestamp timestamp = Timestamp.valueOf(j.getDateCreation());
            PreparedStatement stmt = cnx.prepareStatement(sql);

            stmt.setInt(1, j.getId());
            stmt.setString(2, j.getRef());
            stmt.setString(3, j.getLibelle());
            stmt.setString(4, j.getLogoJeux());
            stmt.setString(5, j.getImageJeux());
            stmt.setTimestamp(6, timestamp);
            stmt.setFloat(7, j.getNote());
            stmt.setInt(8, j.getNoteCount());
            stmt.setFloat(9, j.getNoteMyonne());
            stmt.setFloat(10, j.getTotalNote());
            stmt.setInt(11, j.getViews());
            stmt.executeUpdate();
            System.out.println("Jeux ajoutee avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    
   
    public List<Jeux> getAll() {
   
        List<Jeux> all = new ArrayList<>();
        Statement stmt;
        try {
            String sql = "select * from jeux";
            
          stmt = cnx.createStatement();
            
               ResultSet rs =  stmt.executeQuery(sql);
                
                while(rs.next()){
                   Jeux b = new Jeux(
                           rs.getInt(1),
                           rs.getString(2),
                           rs.getString(3),
                           rs.getString(4), 
                           rs.getString(5), 
                           rs.getTimestamp(6).toLocalDateTime(),
                           rs.getFloat(7),
                           rs.getInt(8), 
                           rs.getFloat(9), 
                           rs.getFloat(10), 
                           rs.getInt(11) );
                
                    
                    
                    all.add(b);                   
                }
                return all;
                    
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }
        return all;
            }

public Jeux findById(int id) {

   Jeux b=null ;
        Statement stmt;
        
        try {
            String sql = "select * from jeux where id = "+id;
            
             stmt = cnx.createStatement();
            
               ResultSet rs =  stmt.executeQuery(sql);
                
                if(rs.next()){

                    b = new Jeux(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getTimestamp(6).toLocalDateTime(), rs.getFloat(7), rs.getInt(8), rs.getFloat(9), rs.getFloat(10), rs.getInt(11) );
                }
                return b;
                    
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }
        return b;
    }


  
    public void update(Jeux t) {
  try {
            String sql = "update jeux set libelle=? ,logo_jeux = ? ,image_jeux = ? where id = ?";
            
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setString(1,t.getLibelle());
            stmt.setString(2, t.getLogoJeux());
            stmt.setString(3, t.getImageJeux());
            stmt.setInt(4, t.getId());
         
            stmt.executeUpdate();
            System.out.println("Jeux modifier avec success");
            
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }
            }

     public void updatelibelle(Jeux t) {
  try {
            String sql = "update jeux set libelle=? where id = ?";
            
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setString(1,t.getLibelle()); 
            stmt.setInt(2, t.getId());
         
            stmt.executeUpdate();
            System.out.println("Jeux modifier avec success");
            
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }
            }
   
    public void delete(Jeux t) {
 try {
            String sql = "delete from jeux where id = ? ";
            
            PreparedStatement stmt = cnx.prepareStatement(sql);

            stmt.setInt(1, t.getId());
         
            stmt.executeUpdate();
            System.out.println("Jeux supprimer avec success");
            
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }


    }
public List<Jeux> afficherJeux()
    {
        List<Jeux> myList=new ArrayList<>();
        
         try {
             String requete3="SELECT * FROM jeux";
             Statement st=cnx.createStatement();
             ResultSet rs=st.executeQuery(requete3);
             
             while(rs.next())
             {
                 Jeux p=new Jeux();
                 p.setId(rs.getInt(1));
                 p.setLibelle(rs.getString("libelle"));
                 //p.setImageJeux(rs.getString("image_jeux"));
                 //p.setLogoJeux(rs.getString("logo_jeux"));
                 p.setViews(rs.getInt("views"));
                 myList.add(p);
                 
             }
         } catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
         
         return myList;
    }

    
   public List<Jeux> rechercherJeuxParNom(String name) {
        ArrayList<Jeux> result = new ArrayList<>();
        try {
           String sql = "SELECT * FROM jeux WHERE LOWER(libelle) LIKE CONCAT('%', LOWER(?), '%') limit 1;";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, name); // set the value of the first parameter to the name you want to match
            ResultSet s = ste.executeQuery();

           while (s.next()) {
                LocalDateTime dateCreation = s.getTimestamp("date_creation").toLocalDateTime();
                Jeux r = new Jeux(
                        
                        s.getInt("id"),
                        s.getString("ref"),
                        s.getString("libelle"),
                        dateCreation,
                        s.getInt("views"),
                        s.getFloat("note_myonne"));
                result.add(r);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
