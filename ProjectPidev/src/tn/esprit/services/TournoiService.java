/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entity.Jeux;
import tn.esprit.entity.Tournoi;
import tn.esprit.interfaces.interfaceTournoi;
import tn.esprit.tools.MaConnection;

/**
 *
 * @author hayth
 */
public class TournoiService implements interfaceTournoi {
       Connection cnx;

    public TournoiService() {
        cnx = MaConnection.getInstance().getCnx();
    }

    @Override
    public void ajouterTournoi(Tournoi t) {
  try {
            String sql = "insert into tournoi(nom_tournoi,type_tournoi,participant_total,image_tournoi,prix,date_debut,jeu_id)"
                    + "values (?,?,?,?,?,?,?)";

            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getNomTournoi());
            ste.setString(2, t.getTypeTournoi());
            ste.setInt(3, t.getParticipantTotal());
            ste.setString(4, t.getImageTournoi());
            ste.setFloat(5, t.getPrix());
            ste.setDate(6, t.getDateDebut()); //maybe change to date type
            ste.setInt(7, t.getJeu().getId());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    
    }

    @Override
    public void modifierTournoi(Tournoi t, int id) {
String sql = "update tournoi set nom_tournoi=?,type_tournoi=?,participant_total=?,image_tournoi=?,prix=?,date_debut=?,jeu_id=? where id=?"; 
       try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getNomTournoi());
            ste.setString(2, t.getTypeTournoi());
            ste.setInt(3, t.getParticipantTotal());
            ste.setString(4, t.getImageTournoi());
            ste.setFloat(5, t.getPrix());
            ste.setDate(6, t.getDateDebut());
            ste.setInt(7, t.getJeu().getId());
            ste.setInt(8, id);
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Tournoi> afficherTournoi() {
ArrayList<Tournoi> result = new ArrayList<>();
        try {
            String sql = "select * from tournoi";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {

                Tournoi r = new Tournoi(
                s.getInt("id"),
                s.getInt("participant_total"),
                s.getString("nom_tournoi"),
                findJeuById(s.getInt("jeu_id")),
                s.getInt("prix"),
                s.getString("type_tournoi"),
                s.getString("image_tournoi"),
                s.getDate("date_debut"));
                //System.out.println(r);
                result.add(r);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public void supprimerTournoi(Tournoi p) {
        try {
            String sql = "Delete from tournoi where id=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1,p.getId());
            ste.executeUpdate();//when preparing statement i no longer pass sql in parameter of execute because it is already in prepare statement
            //when creating statement i do pass it to the execute, it doesnt work if i do not respect this 
            System.out.println("tournoi supprimé avec succés");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    
    }
    
    public List<String> getJeux() {
ArrayList<String> result = new ArrayList<>();
        try {
            String sql = "select libelle from jeux";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                result.add(s.getString("libelle"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }


    public Jeux findJeuById(int id) {
        try {
            String sql = "select * from jeux where id = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1,id);
            ResultSet s = ste.executeQuery(); //had to remove ste here why tho
            while (s.next()) { //I need next to go through data
                Jeux t = new Jeux(
                s.getInt("id"),
                s.getString("libelle"));
                System.out.println(t);
                return t;
        }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return new Jeux(); //what should i put this    
    }
 
    public Jeux findIdByJeu(String lib) {
        try {
            String sql = "select * from jeux where libelle = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1,lib);
            ResultSet s = ste.executeQuery(); //had to remove ste here why tho
            while (s.next()) { //I need next to go through data
                Jeux t = new Jeux(
                s.getInt("id"),
                s.getString("libelle"));
                System.out.println(t);
                return t;
        }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return new Jeux(); //what should i put this    
    }
           public void findsthing() { //any function i call from this file in my controller just doesnt work
               System.out.print("heyyyyyyyyy");
           }
}
