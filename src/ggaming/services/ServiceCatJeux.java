/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.services;

import ggaming.cnx.MaConnection;
import ggaming.entity.CategorieJeux;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dell
 */
public class ServiceCatJeux {

    
    private Connection cnx;
    
    public void initConnection() {
        cnx = MaConnection.getInstance().getCnx();
    }
    
    public void ajouter(CategorieJeux j) {
        try {
            String sql = "insert into categorie_jeux(id,nom_cat) values (?,?) ";
        
            PreparedStatement stmt = cnx.prepareStatement(sql);

            stmt.setInt(1, j.getId());
            stmt.setString(2, j.getNomCat());
            stmt.executeUpdate();
            System.out.println("CategorieJeux ajoutee avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    
   
    public List<CategorieJeux> getAll() {
   
        List<CategorieJeux> all = new ArrayList<>();
        Statement stmt;
        try {
            String sql = "select * from categorie_jeux";
            
          stmt = cnx.createStatement();
            
               ResultSet rs =  stmt.executeQuery(sql);
                
                while(rs.next()){
                   CategorieJeux b = new CategorieJeux(rs.getInt(1), rs.getString(2) );
 
                    all.add(b);                   
                }
                return all;
                    
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }
        return all;
            }

public CategorieJeux findById(int id) {
    CategorieJeux categorieJeux = null;
    Statement stmt;
    try {
        String sql = "SELECT * FROM categorie_jeux WHERE id = " + id;
        stmt = cnx.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            categorieJeux = new CategorieJeux(rs.getInt(1), rs.getString(2));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return categorieJeux;
}



  
    public void update(CategorieJeux t) {
  try {
            String sql = "update categorie_jeux set nom_cat=?  where id = ?";
            
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setString(1,t.getNomCat());
            stmt.setInt(2, t.getId());
         
            stmt.executeUpdate();
            System.out.println("Categorie Jeux modifier avec success");
            
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }
            }

    
    
   
    public void delete(CategorieJeux t) {
 try {
            String sql = "delete from categorie_jeux where id = ? ";
            
            PreparedStatement stmt = cnx.prepareStatement(sql);

            stmt.setInt(1, t.getId());
         
            stmt.executeUpdate();
            System.out.println("CategorieJeux supprimer avec success");
            
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }


    }
public List<CategorieJeux> getAllNames() {
    List<CategorieJeux> all = new ArrayList<>();
    Statement stmt;
    try {
        String sql = "select * from categorie_jeux";

        stmt = cnx.createStatement();

        ResultSet rs =  stmt.executeQuery(sql);

        while(rs.next()){
            CategorieJeux b = new CategorieJeux(rs.getInt(1), rs.getString(2) );

            all.add(b);
        }
        return all;
//throws
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return all;
}


    
}
