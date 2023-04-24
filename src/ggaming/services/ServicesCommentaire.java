/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.services;

import ggaming.cnx.MyConnection;
import ggaming.entity.Blog;
import ggaming.entity.Commentaire;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            String sql = "INSERT INTO commentaire (contenu, date_creation, date_modification, blog_id, reportCount) VALUES (?,?, ?, ?, ?)";

            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, c.getContenu());
            ps.setObject(2, c.getDate_creation());
            ps.setObject(3, c.getDate_modification());
            ps.setInt(4, blogId);
            ps.setInt(5, c.getReportCount());

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
            String sql = "UPDATE commentaire SET contenu = ?, date_modification = ?, reportCount = ? WHERE id = ?";

            PreparedStatement ps = cnx.prepareStatement(sql);

            ps.setString(1, c.getContenu());
            ps.setObject(2, c.getDate_modification());
            ps.setObject(3, c.getReportCount());

            ps.executeUpdate();

            System.out.println("commentaire updated successfully");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public List<Commentaire> getAllCommentairesByBlogId(int blogId) {
        List<Commentaire> commentaires = new ArrayList<>();
        try {
            String sql = "SELECT * FROM commentaire WHERE blog_id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, blogId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String contenu = rs.getString("contenu");
                Timestamp date_creation = rs.getTimestamp("date_creation");
                LocalDateTime localDateTime_creation = date_creation.toLocalDateTime();
                Timestamp date_modification = rs.getTimestamp("date_modification");
                LocalDateTime localDateTime_modification = date_modification.toLocalDateTime();
                Blog blog = getBlogById(blogId);
                int reportcount = rs.getInt("reportCount");
                Commentaire c = new Commentaire(id, contenu, localDateTime_creation, localDateTime_modification, blog, reportcount);
                commentaires.add(c);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentaires;
    }

    public Blog getBlogById(int blogId) {
        Blog blog = null;
        try {
            String sql = "SELECT * FROM blog WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, blogId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String titre = rs.getString("titre");
                String contenu = rs.getString("contenu");
                String image_blog = rs.getString("image_blog");
                Timestamp date_creation = rs.getTimestamp("date_creation");
                LocalDateTime localDateTime_creation = date_creation.toLocalDateTime();
                Timestamp date_modification = rs.getTimestamp("date_modification");
                LocalDateTime localDateTime_modification = date_modification.toLocalDateTime();
                int etat = rs.getInt("etat");
                blog = new Blog(blogId, etat, titre, contenu, localDateTime_creation, localDateTime_modification, image_blog);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blog;
    }
}
