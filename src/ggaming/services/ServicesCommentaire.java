/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.services;

import ggaming.cnx.MyConnection;
import ggaming.entity.Commentaire;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author oness
 */
public class ServicesCommentaire {
    private Connection cnx;
    
    public void initConnection() {
        cnx = MyConnection.getInstance().getCnx();
    }
    
    public void ajouterCommentaire(Commentaire c, int blogId) {
        try {
            String sql = "INSERT INTO commentaire (contenu, date_creation, date_modification, blog_id) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, c.getContenu());
            ps.setObject(2, c.getDate_creation());
            ps.setObject(3, c.getDate_modification());
            ps.setInt(4, blogId);

            ps.executeUpdate();

            System.out.println("Commentaire ajoutee avec succes");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteCommentaire(int commentId) {
        try {
            String sql = "DELETE FROM commentaire WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, commentId);
            ps.executeUpdate();
            System.out.println("commentaire with ID " + commentId + " has been deleted.");
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateCommentaire(Commentaire c) {
        try {
            String sql = "UPDATE commentaire SET contenu = ?, date_modification = ? WHERE id = ?";

            PreparedStatement ps = cnx.prepareStatement(sql);

            ps.setString(1, c.getContenu());
            ps.setObject(2, c.getDate_modification());

            ps.executeUpdate();

            System.out.println("commentaire updated successfully");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}