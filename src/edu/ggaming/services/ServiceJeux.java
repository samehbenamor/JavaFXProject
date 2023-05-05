/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ggaming.services;

import edu.ggaming.entities.Jeux;

import edu.ggaming.utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServiceJeux {
    
    private Connection cnx;
    
    public void initConnection() {
        cnx = MyConnection.getInstance().getCnx();
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
                 p.setImageJeux(rs.getString("image_jeux"));
                 //p.setLogoJeux(rs.getString("logo_jeux"));
                 p.setViews(rs.getInt("views"));
                  p.setNoteMyonne(rs.getFloat("note_myonne"));
                 myList.add(p);
                 
             }
         } catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
         
         return myList;
    }
public Map<Date, Integer> getGamesAddedPerDay() {
    Map<Date, Integer> gamesAddedPerDay = new HashMap<>();
    try {
        String query = "SELECT DATE(date_creation), COUNT(*) FROM jeux GROUP BY DATE(date_creation)";
        Statement statement = cnx.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Date date = resultSet.getDate(1);
            int count = resultSet.getInt(2);
            gamesAddedPerDay.put(date, count);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return gamesAddedPerDay;
}

public Jeux bestJeux() {
    Jeux bestGame = null;
    
    try {
        String requete = "SELECT * FROM jeux ORDER BY note_myonne DESC LIMIT 1";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(requete);

        if (rs.next()) {
            bestGame = new Jeux();
            bestGame.setLibelle(rs.getString("libelle"));
            bestGame.setNoteMyonne(rs.getFloat("note_myonne"));
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }

    return bestGame;
}
public List<Jeux> TribestJeux() {
            List<Jeux> myList=new ArrayList<>();

    try {
        String requete = "SELECT * FROM jeux ORDER BY note_myonne DESC limit 3;";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(requete);

        if (rs.next()) {
           
                 Jeux p=new Jeux();
                 p.setId(rs.getInt(1));
                 p.setLibelle(rs.getString("libelle"));
                 p.setImageJeux(rs.getString("image_jeux"));
                 //p.setLogoJeux(rs.getString("logo_jeux"));
                 p.setViews(rs.getInt("views"));
                  p.setNoteMyonne(rs.getFloat("note_myonne"));
                 myList.add(p);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }

    return myList;
}
public List<Jeux> TridateJeux() {
         List<Jeux> myList=new ArrayList<>();
  
    try {
        String requete = "SELECT * FROM jeux ORDER BY date_creation DESC limit 3;";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(requete);

        if (rs.next()) {
                 Jeux p=new Jeux();
                 p.setId(rs.getInt(1));
                 p.setLibelle(rs.getString("libelle"));
                 p.setImageJeux(rs.getString("image_jeux"));
                 //p.setLogoJeux(rs.getString("logo_jeux"));
                 p.setViews(rs.getInt("views"));
                  p.setNoteMyonne(rs.getFloat("note_myonne"));
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
           String sql = "SELECT * FROM jeux WHERE LOWER(libelle) LIKE CONCAT('%', LOWER(?), '%') limit 3;";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, name); 
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
   public List<Jeux> rechercherJeuxParNomEtDate(String name,Date dateFrom,Date dateTo) {
         ArrayList<Jeux> result = new ArrayList<>();
    try {
       String sql = "SELECT * FROM jeux WHERE LOWER(libelle) LIKE CONCAT('%', LOWER(?), '%') and DATE(date_creation) between ? and ? limit 4 ;";
        PreparedStatement ste = cnx.prepareStatement(sql);
        ste.setString(1, name);
        ste.setDate(2, dateFrom);
        ste.setDate(3, dateTo);
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
   
    public List<Jeux> rechercherJeuxParDate(Date dateFrom,Date dateTo) {
        ArrayList<Jeux> result = new ArrayList<>();
        try {
           String sql = "SELECT * FROM jeux WHERE DATE(date_creation) between ? and ? limit 3 ;";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setDate(1, dateFrom); 
            ste.setDate(2, dateTo); 
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
   public void updateNoteMyonne(Jeux t) {
    try {
        String sql = "UPDATE jeux SET note_myonne = ? WHERE id = ?";
        PreparedStatement stmt = cnx.prepareStatement(sql);
        stmt.setDouble(1, t.getNoteMyonne());
        stmt.setInt(2, t.getId());
        stmt.executeUpdate();
        System.out.println("Jeux note updated successfully");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
   public void updateViews(Jeux t) {
  try {
    
    String selectSql = "select views from jeux where id = ?";
    PreparedStatement selectStmt = cnx.prepareStatement(selectSql);
    selectStmt.setInt(1, t.getId());
    ResultSet rs = selectStmt.executeQuery();
    int currentViews = 0;
    if (rs.next()) {
      currentViews = rs.getInt("views");
    }
    int newViews = currentViews + 1;


    String updateSql = "update jeux set views=? where id = ?";
    PreparedStatement updateStmt = cnx.prepareStatement(updateSql);
    updateStmt.setInt(1, newViews); 
    updateStmt.setInt(2, t.getId());
    updateStmt.executeUpdate();
    System.out.println("Views updated successfully for Jeux with id " + t.getId());

   
  } catch (SQLException ex) {
    System.out.println(ex.getMessage());
  }
}


}
