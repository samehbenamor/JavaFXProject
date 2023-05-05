/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.entities;

/**
 *
 * @author hayth
 */
public class Classement {
    private String score;
    private Equipe nom_equipe;
    public Classement() {
    }

    public Classement(Equipe nom_equipe, String score) {
        this.nom_equipe = nom_equipe;
        this.score = score;
    }

    public Equipe getNom_equipe() {
        return nom_equipe;
    }

    public void setNom_equipe(Equipe nom_equipe) {
        this.nom_equipe = nom_equipe;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Classement{" + "nom_equipe=" + nom_equipe + ", score=" + score + '}';
    }
    
}
