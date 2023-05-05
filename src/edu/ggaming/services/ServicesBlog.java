/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ggaming.services;

import edu.ggaming.utils.MyConnection;
import edu.ggaming.entities.Blog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author oness
 */
public class ServicesBlog {
    private Connection cnx;
    
    public void initConnection() {
        cnx = MyConnection.getInstance().getCnx();
    }
    
    public void ajouter(Blog b) {
         
        try {
            String sql = "INSERT INTO blog (titre, contenu, date_creation, date_modification, image_blog, etat, likeblog, dislikeblog) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";

            System.out.println("cnx is null: " + (cnx == null));
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, b.getTitre());
            ps.setString(2, b.getContenu());
            ps.setObject(3, Date.from(b.getDate_creation().atZone(ZoneId.systemDefault()).toInstant()));
            ps.setObject(4, Date.from(b.getDate_modification().atZone(ZoneId.systemDefault()).toInstant()));
            ps.setString(5, b.getImageblog());
            ps.setInt(6, b.getEtat());
            ps.setInt(7, b.getLike());
            ps.setInt(8, b.getDislike());
          
            ps.executeUpdate();
              System.out.println("ajouter avec succes");
            System.out.println("Blog ajoutee avec succes");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        try {
            String sql = "SELECT * FROM blog";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String titre = rs.getString("titre");
                String contenu = rs.getString("contenu");
                Timestamp date_creation = rs.getTimestamp("date_creation");
                LocalDateTime localDateTime_creation = date_creation.toLocalDateTime();
                Timestamp date_modification = rs.getTimestamp("date_modification");
                LocalDateTime localDateTime_modification = date_modification.toLocalDateTime();
                String image_blog = rs.getString("image_blog");
                int etat = rs.getInt("etat");
                int like = rs.getInt("likeblog");
                int dislike = rs.getInt("dislikeblog");
                Blog b = new Blog(id, etat, titre, contenu, localDateTime_creation, localDateTime_modification, image_blog,like,dislike);
                blogs.add(b);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return blogs;
    }

    public void deleteBlog(int blogId) {
        try {
            String sql = "DELETE FROM blog WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, blogId);
            ps.executeUpdate();
            System.out.println("Blog with ID " + blogId + " has been deleted.");
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


public void updateBlogFF(Blog b) {
    try {
        String sql = "UPDATE blog SET titre = ?, contenu = ?, date_modification = ?, image_blog = ?, etat = ? WHERE id = ?";

        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, b.getTitre());
        ps.setString(2, b.getContenu());
        ps.setObject(3, Date.from(b.getDate_modification().atZone(ZoneId.systemDefault()).toInstant()));
        ps.setString(4, b.getImageblog());
        ps.setInt(5, b.getEtat());
        ps.setInt(6, b.getId());

        ps.executeUpdate();

        System.out.println("Blog updated successfully");

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}


    public void updateBlog(Blog b) {
        try {
            String sql = "UPDATE blog SET titre = ?, contenu = ?, date_modification = ?, image_blog = ?, etat = ?, likeblog = ?, dislikeblog = ? WHERE id = ?";

            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, b.getTitre());
            ps.setString(2, b.getContenu());
            ps.setObject(3, Date.from(b.getDate_modification().atZone(ZoneId.systemDefault()).toInstant()));
            ps.setString(4, b.getImageblog());
            ps.setInt(5, b.getEtat());
            ps.setInt(6, b.getLike());
            ps.setInt(7, b.getDislike());
            ps.setInt(8, b.getId());

            ps.executeUpdate();

            System.out.println("Blog updated successfully");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Blog> getTop3Blogs(List<Blog> blogs) {
        blogs.sort(Comparator.comparingInt(Blog::getLike).reversed());

        List<Blog> top3Blogs = new ArrayList<>();
        for (int i = 0; i < Math.min(3, blogs.size()); i++) {
            top3Blogs.add(blogs.get(i));
        }

        return top3Blogs;
    }


    public List<Blog> searchBlogs(String titleKeyword, String contentKeyword, String dateKeyword) {
    List<Blog> blogs = new ArrayList<>();
    try {
        String sql = "SELECT * FROM blog WHERE titre LIKE ? AND contenu LIKE ? AND (date_creation LIKE ? OR date_modification LIKE ?)";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, "%" + titleKeyword + "%");
        ps.setString(2, "%" + contentKeyword + "%");
        ps.setString(3, "%" + dateKeyword + "%");
        ps.setString(4, "%" + dateKeyword + "%");

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Blog blog = new Blog();
            blog.setId(rs.getInt("id"));
            blog.setTitre(rs.getString("titre"));
blog.setImageblog(rs.getString("image_blog"));
            blogs.add(blog);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return blogs;
}
}
