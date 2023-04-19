/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ggaming.entity.Jeux;
import ggaming.entity.StatTournoi;
import ggaming.entity.Tournoi;
import ggaming.interfaces.interfaceTournoi;
import ggaming.tools.MaConnection;
import java.sql.Date;

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
            ste.setInt(1, p.getId());
            ste.executeUpdate();//when preparing statement i no longer pass sql in parameter of execute because it is already in prepare statement
            //when creating statement i do pass it to the execute, it doesnt work if i do not respect this 
            System.out.println("tournoi supprimé avec succés");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<String> getJeux() {  //the code is for the reference in my request but i am using the libelle constructor and get/set
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
        Jeux j=new Jeux();
        try {  //do trys in controller
            String sql = "select * from jeux where id = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet s = ste.executeQuery(); //had to remove ste here why tho
            while (s.next()) { //I need next to go through data
            j.setLibelle(s.getString("libelle"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return j; //what should i put this    
    }

    public Jeux findIdByJeu(String lib) {
        Jeux j=new Jeux();
        try {
            String sql = "select * from jeux where  libelle = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, lib);
            ResultSet s = ste.executeQuery(); 
            while (s.next()) { 
                j.setId(s.getInt("id"));         
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return j; //what should i put this    
    }
     public ArrayList<StatTournoi> getStatsType() {
        ArrayList<StatTournoi> result = new ArrayList<>();
        try {
            String sql = "SELECT count(*) as number ,type_tournoi "
                    + "as type FROM `tournoi` GROUP by type_tournoi;";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                StatTournoi r = new StatTournoi(
                        s.getString("type"),
                        s.getInt("number")
                        );
                result.add(r);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;    
     }
     public int getTotalType() {  //SELECT avg(score_equipe_a+score_equipe_b),tour_actuel FROM `matches` group by tour_actuel;
        int total=0;
         try {
            String sql = "SELECT count(*) as total from tournoi;";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                total = s.getInt("total");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return total;    
     }
     
     
    public List<Tournoi> getTournoiAujourdhui() {
        ArrayList<Tournoi> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tournoi WHERE DATE(date_debut) = CURDATE();";
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
                result.add(r);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
    
    public List<Tournoi> rechercherTournoiParNom(String name) {
        ArrayList<Tournoi> result = new ArrayList<>();
        try {
           String sql = "SELECT * FROM tournoi WHERE LOWER(nom_tournoi) LIKE CONCAT('%', LOWER(?), '%') limit 1;";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, name); // set the value of the first parameter to the name you want to match
            ResultSet s = ste.executeQuery();

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
                result.add(r);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
    
    public List<Tournoi> rechercherTournoiParNomEtDate(String name,Date dateFrom,Date dateTo) {
    ArrayList<Tournoi> result = new ArrayList<>();
    try {
       String sql = "SELECT * FROM tournoi WHERE LOWER(nom_tournoi) LIKE CONCAT('%', LOWER(?), '%') and DATE(date_debut) between ? and ? limit 1 ;";
        PreparedStatement ste = cnx.prepareStatement(sql);
        ste.setString(1, name);
        ste.setDate(2, dateFrom);
        ste.setDate(3, dateTo);
        ResultSet s = ste.executeQuery();

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
            result.add(r);

        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return result;
}
    public List<Tournoi> rechercherTournoiParDate(Date dateFrom,Date dateTo) {
        ArrayList<Tournoi> result = new ArrayList<>();
        try {
           String sql = "SELECT * FROM tournoi WHERE DATE(date_debut) between ? and ? limit 1 ;";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setDate(1, dateFrom); 
            ste.setDate(2, dateTo); 
            ResultSet s = ste.executeQuery();

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
                result.add(r);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
    public List<Tournoi> TournoiSimilaire(Tournoi t) {
        ArrayList<Tournoi> result = new ArrayList<>();
        try {
           String sql = "SELECT * FROM tournoi WHERE type_tournoi = ? or jeu_id= ?;";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getTypeTournoi()); 
            ste.setInt(2,t.getJeu().getId()); 
            ResultSet s = ste.executeQuery();

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
                result.add(r);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
   
}
