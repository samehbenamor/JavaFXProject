/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ggaming.services;

import edu.ggaming.entities.Classement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.ggaming.entities.Equipe;
import edu.ggaming.entities.Match;
import edu.ggaming.entities.Tournoi;
import edu.ggaming.interfaces.interfaceMatch;
import edu.ggaming.utils.MyConnection;

/**
 *
 * @author hayth
 */
public class MatchService implements interfaceMatch {
          Connection cnx;

    public MatchService() {
        cnx = MyConnection.getInstance().getCnx();  //pour utiliser la meme connexion a travers toute l'aaplication 
    }

    @Override
    public void ajouterMatch(Match t) {
 try {
            String sql = "insert into Matches(tour_actuel,score_equipe_a,score_equipe_b,equipe1_id,equipe2_id,id_tournoi_id)"
                    + "values (?,?,?,?,?,?)";   
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getTour());
            ste.setInt(2, t.getScore1());
            ste.setInt(3, t.getScore2());
            ste.setInt(4, t.getEquipe1().getId());
            ste.setInt(5, t.getEquipe2().getId());
            ste.setInt(6, t.getTournoi().getId());
            ste.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        }

    @Override
    public void modifierMatch(Match m,int id) {
  String sql = "update matches set tour_actuel=?,score_equipe_a=?,score_equipe_b=?,equipe1_id=?,equipe2_id=?,id_tournoi_id=? where id=?"; 
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, m.getTour());
            ste.setInt(2, m.getScore1());
            ste.setInt(3, m.getScore2());
            ste.setInt(4, m.getEquipe1().getId());
            ste.setInt(5, m.getEquipe2().getId());
            ste.setInt(6, m.getTournoi().getId());
            ste.setInt(7, id);
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
}
    @Override
    public void supprimerMatch(Match m) {
    try {
            String sql = "Delete from matches where id='"+m.getId()+"'";
            Statement ste = cnx.createStatement();
            ste.executeUpdate(sql);
            System.out.println("match supprimé avec succés");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    
    }

    @Override
    public List<Match> afficherMatch() {
ArrayList<Match> result = new ArrayList<>();
        try {
            String sql = "select * from Matches";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                Match r = new Match(
                s.getInt("id"),  //column name in database or column index
                s.getString("tour_actuel"),
                s.getInt("score_equipe_a"),
                s.getInt("score_equipe_b"),
                findByIdE(s.getInt("equipe1_id")),
                findByIdE(s.getInt("equipe2_id")),
                findById(s.getInt("id_tournoi_id")));
                System.out.println(r);
                result.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
       public Tournoi findById(int id) {
        Tournoi t= new Tournoi();
           try {
            String sql = "select * from tournoi where id = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1,id);
            ResultSet s = ste.executeQuery();
                while (s.next()) {
                t.setNomTournoi(s.getString("nom_tournoi"));
                
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return t;
    }
       
        public Tournoi findTournoiByNom(String nom) {
        Tournoi t=new Tournoi();
            try {
            String sql = "select * from tournoi where nom_tournoi = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1,nom);
            ResultSet s = ste.executeQuery();

                while (s.next()) {
                t.setId(s.getInt("id"));
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return t; //what should i put this
    }
     public Equipe findEquipeByNom(String nom) {
        Equipe e=new Equipe();
         try {
            String sql = "select * from equipe where nom_equipe = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1,nom);
            ResultSet s = ste.executeQuery();

                while (s.next()) {
                    e.setId(s.getInt("id"));
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return e; //what should i put this
    }
    public Equipe findByIdE(int id) {
        Equipe e=new Equipe();
        try {
            String sql = "select * from Equipe where id = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1,id);
            ResultSet s = ste.executeQuery();

                while (s.next()) {
                    e.setNom_equipe(s.getString("nom_equipe"));
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return e; //what should i put this
    }
    public List<String> getTournoi() {
ArrayList<String> result = new ArrayList<>();
        try {
            String sql = "select nom_tournoi from Tournoi";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                result.add(s.getString("nom_tournoi"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
    
    public List<String> getEquipe() {
ArrayList<String> result = new ArrayList<>();
        try {
            String sql = "select nom_equipe from Equipe";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                result.add(s.getString("nom_equipe"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
        public List<Match> getMatchs(int id) {
            ArrayList<Match> result = new ArrayList<>();

            try {
            String sql = "select * from Matches where id_tournoi_id = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1,id);
            ResultSet s = ste.executeQuery();
                while (s.next()) {
                Match r = new Match(
                s.getInt("id"),  //column name in database or column index
                s.getString("tour_actuel"),
                s.getInt("score_equipe_a"),
                s.getInt("score_equipe_b"),
                findByIdE(s.getInt("equipe1_id")),
                findByIdE(s.getInt("equipe2_id")),
                findById(s.getInt("id_tournoi_id")));
                System.out.println(r);
                result.add(r);
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result; 
    }
       public List<Classement> getClassement(int id) { 
           ArrayList<Classement> result = new ArrayList<>();
            try {
            String sql = "SELECT team_id, SUM(points) AS total_points FROM "
                    + "( SELECT equipe1_id AS team_id, CASE WHEN score_equipe_a > "
                    + "score_equipe_b THEN 3 WHEN score_equipe_a = score_equipe_b THEN "
                    + "1 ELSE 0 END AS points FROM matches where id_tournoi_id= ? UNION "
                    + "ALL SELECT equipe2_id AS team_id, CASE WHEN score_equipe_b > "
                    + "score_equipe_a THEN 3 WHEN score_equipe_b = score_equipe_a "
                    + "THEN 1 ELSE 0 END AS points FROM matches where id_tournoi_id= ? ) "
                    + "AS results GROUP BY team_id ORDER BY total_points DESC";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1,id);
            ste.setInt(2,id);
            ResultSet s = ste.executeQuery();
                while (s.next()) {
                Classement r = new Classement(
                findByIdE(s.getInt("team_id")),
                s.getString("total_points"));
                result.add(r);
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result; 
    }
          
}
