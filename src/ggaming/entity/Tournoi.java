/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.entity;

import java.sql.Date;




/**
 *
 * @author hayth
 */
public class Tournoi {  
    private int id,participantTotal,prix;
    private String nomTournoi,typeTournoi,imageTournoi;
    private Date dateDebut;
    private Jeux jeu;
    
    public Tournoi() {
    }
    //I use these two variable constructors... for joins 
    public Tournoi(int id,String nomTournoi) {
        this.id=id;
        this.nomTournoi=nomTournoi;
    }
    public Tournoi(int id, int participantTotal, String nomTournoi, Jeux jeu, int prix, String typeTournoi, String imageTournoi, java.sql.Date  dateDebut) {
        this.id = id;
        this.participantTotal = participantTotal;
        this.nomTournoi = nomTournoi;
        this.jeu = jeu;
        this.prix = prix;
        this.typeTournoi = typeTournoi;
        this.imageTournoi = imageTournoi;
        this.dateDebut = dateDebut;
    }

        public Tournoi(int participantTotal, String nomTournoi, Jeux jeu, int prix, String typeTournoi, String imageTournoi, java.sql.Date  dateDebut) {
        this.participantTotal = participantTotal;
        this.nomTournoi = nomTournoi;
        this.jeu = jeu;
        this.prix = prix;
        this.typeTournoi = typeTournoi;
        this.imageTournoi = imageTournoi;
        this.dateDebut = dateDebut;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
        
       
    public int getParticipantTotal() {
        return participantTotal;
    }

    public void setParticipantTotal(int participantTotal) {
        this.participantTotal = participantTotal;
    }

    public String getNomTournoi() {
        return nomTournoi;
    }

    public void setNomTournoi(String nomTournoi) {
        this.nomTournoi = nomTournoi;
    }

    public Jeux getJeu() {
        return jeu;
    }

    public void setJeu(Jeux jeu) {
        this.jeu = jeu;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getTypeTournoi() {
        return typeTournoi;
    }

    public void setTypeTournoi(String typeTournoi) {
        this.typeTournoi = typeTournoi;
    }

    public String getImageTournoi() {
        return imageTournoi;
    }

    public void setImageTournoi(String imageTournoi) {
        this.imageTournoi = imageTournoi;
    }

    public java.sql.Date  getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(java.sql.Date  dateDebut) {
        this.dateDebut = dateDebut;
    }

    @Override
    public String toString() {
        return "Tournoi{" + "id=" + id + ", participantTotal=" + participantTotal + ", nomTournoi=" + nomTournoi + ", jeu=" + jeu + ", prix=" + prix + ", typeTournoi=" + typeTournoi + ", imageTournoi=" + imageTournoi + ", dateDebut=" + dateDebut + '}';
    }

}
