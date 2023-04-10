/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.entities;

/**
 *
 * @author balla
 */
public class CategorieProduit {
    private int id;
    private String nom;
    private String refer;

    public CategorieProduit(String nom) {
        this.nom = nom;
    }

    public CategorieProduit() {
   
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    @Override
    public String toString() {
        return  nom;
    }
    
}
