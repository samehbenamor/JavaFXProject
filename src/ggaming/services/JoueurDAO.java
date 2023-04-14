/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import ggaming.entity.Joueur;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ggaming.cnx.MaConnection;

/**
 *
 * @author DELL
 */
public class JoueurDAO {

    private final Connection cnx;

    public JoueurDAO() {
        this.cnx = MaConnection.getInstance().getCnx();
    }

    public List<Joueur> getAllJoueurs() {
        List<Joueur> joueurs = new ArrayList<>();
        try {
            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM joueur ORDER BY id DESC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Joueur joueur = new Joueur();
                joueur.setId(rs.getInt("id"));
                joueur.setEmail(rs.getString("email"));
                joueur.setPassword(rs.getString("password"));
                joueur.setVerified(rs.getBoolean("is_verified"));
                joueur.setNom(rs.getString("nom"));
                joueur.setPrenom(rs.getString("prenom"));
                joueur.setDatenai(rs.getDate("datenai"));
                joueur.setProfile(rs.getString("pprofile"));

                joueur.setBanned(rs.getBoolean("is_banned"));
                joueur.setIgn(rs.getString("ign"));
                joueur.setWins(rs.getInt("wins"));
                joueur.setLoses(rs.getInt("loses"));
                //joueur.setCreatedAt(rs.getDate("created_at"));
                //System.out.println(joueur);
                joueurs.add(joueur);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return joueurs;
    }

    public void insert(Joueur joueur) throws SQLException {
        PreparedStatement preparedStatement = cnx.prepareStatement("INSERT INTO joueur (email, roles, password, is_verified, nom, prenom, datenai, pprofile, is_banned, ign, wins, loses) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, joueur.getEmail());
        preparedStatement.setString(2, Arrays.toString(joueur.getRoles()));
        preparedStatement.setString(3, joueur.getPassword());
        preparedStatement.setBoolean(4, joueur.isVerified());
        preparedStatement.setString(5, joueur.getNom());
        preparedStatement.setString(6, joueur.getPrenom());
        preparedStatement.setDate(7, new java.sql.Date(joueur.getDatenai().getTime()));
        preparedStatement.setString(8, joueur.getProfile());
        preparedStatement.setBoolean(9, joueur.isBanned());
        preparedStatement.setString(10, joueur.getIgn());
        preparedStatement.setInt(11, joueur.getWins());
        preparedStatement.setInt(12, joueur.getLoses());
        //preparedStatement.setTimestamp(13, new Timestamp(joueur.getCreatedAt().getTime()));
        preparedStatement.executeUpdate();
    }

    public void insertTest(Joueur joueur) {
        try {
            String query = "INSERT INTO joueur (email, roles, password, is_verified, nom, prenom, datenai, ign, is_banned, pprofile,roleJava_joueur_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = cnx.prepareStatement(query);
            stmt.setString(1, joueur.getEmail());
            stmt.setString(2, Arrays.toString(joueur.getRoles()));
            stmt.setString(3, joueur.getPassword());
            stmt.setBoolean(4, joueur.isVerified());
            stmt.setString(5, joueur.getNom());
            stmt.setString(6, joueur.getPrenom());
            stmt.setDate(7, new java.sql.Date(joueur.getDatenai().getTime()));
            stmt.setString(8, joueur.getIgn());
            stmt.setBoolean(9, joueur.isBanned());
            stmt.setString(10, joueur.getProfile());
            stmt.setInt(11, 1);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting joueur failed, no rows affected.");
            }
            System.out.println("Joueur successfully inserted.");
        } catch (SQLException ex) {
            System.err.println("Error inserting joueur: " + ex.getMessage());
        }
    }

    public int getJoueurIdByEmail(String email) throws SQLException {
        int joueurId = -1;
        String query = "SELECT id FROM joueur WHERE email = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    joueurId = rs.getInt("id");
                }
            }
        }
        return joueurId;
    }

    public Joueur getJoueurByEmail(String email) {
        Joueur joueur = null;
        try {
            PreparedStatement statement = cnx.prepareStatement("SELECT * FROM joueur WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String emailuser = resultSet.getString("email");
                String prenom = resultSet.getString("prenom");
                int rolez = resultSet.getInt("roleJava_joueur_id");
                //String password = resultSet.getString("password");
                String ign = resultSet.getString("ign");
                String pprofile = resultSet.getString("pprofile");
                Date datenais = resultSet.getDate("datenai");
                int wins = resultSet.getInt("wins");
                int loses = resultSet.getInt("loses");
                Boolean is_verified = resultSet.getBoolean("is_verified");
                Boolean is_banned = resultSet.getBoolean("is_banned");
                //String role = resultSet.getString("role");
                //int roleJava = 
                joueur = new Joueur(id, emailuser, is_verified, nom, prenom, datenais, pprofile, is_banned, ign, wins, loses, rolez);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return joueur;
    }

    public void updateJoueur(Joueur updatedJoueur) throws SQLException {
        String sql = "UPDATE joueur SET nom=?, prenom=?, email=?, datenai=?, pprofile=?, ign=? WHERE id=?";
        try (
                PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, updatedJoueur.getNom());
            statement.setString(2, updatedJoueur.getPrenom());
            statement.setString(3, updatedJoueur.getEmail());

            statement.setDate(4, new java.sql.Date(updatedJoueur.getDatenai().getTime()));
            statement.setString(5, updatedJoueur.getProfile());
            statement.setString(6, updatedJoueur.getIgn());
            statement.setInt(7, updatedJoueur.getId());
            statement.executeUpdate();
        }
    }

}
