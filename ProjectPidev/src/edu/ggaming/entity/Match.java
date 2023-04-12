/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ggaming.entity;

/**
 *
 * @author hayth
 */
public class Match {
    
    int id,score1,score2/*,equipe1,equipe2*/;
    String tour;
    Tournoi tournoi;
    Equipe equipe1,equipe2;
    public Match() {
    }
  public Match(Tournoi tournoi) {
              this.tournoi= tournoi;

    }

    public Match(int id, String tour, int score1, int score2, Equipe equipe1, Equipe equipe2,Tournoi tournoi) {
        this.id = id;
        this.tour = tour;
        this.score1 = score1;
        this.score2 = score2;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.tournoi= tournoi;

    }
        public Match(String tour, int score1, int score2, Equipe equipe1, Equipe equipe2,Tournoi tournoi) {
        this.tournoi= tournoi;
        this.tour = tour;
        this.score1 = score1;
        this.score2 = score2;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;        
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTour() {
        return tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public Equipe getEquipe1() {
        return equipe1;
    }

    public void setEquipe1(Equipe equipe1) {
        this.equipe1 = equipe1;
    }

    public Equipe getEquipe2() {
        return equipe2;
    }

    public void setEquipe2(Equipe equipe2) {
        this.equipe2 = equipe2;
    }

    public Tournoi getTournoi() {
        return tournoi;
    }

    public void setTournoi(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

    @Override
    public String toString() {
        return "Match{" + "id=" + id + ", Score1=" + score1 + ", Score2=" + score2 + ", tournoi=" + tournoi + ", equipe1=" + equipe1 + ", equipe2=" + equipe2 + ", tour=" + tour + '}';
    }

    
}
