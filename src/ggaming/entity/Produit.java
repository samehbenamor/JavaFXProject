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
public class Produit {
    private int id;
    private String nom;
    private String description;
    private String image;
    private String prix;
    private int quantite;

    public Produit()
    {
        
    }
    public Produit(int id, String nom, String description, String image, String prix, int quantite) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.quantite = quantite;
    }

    public Produit(String nom, String description, String image, String prix, int quantite) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", image=" + image + ", prix=" + prix + ", quantite=" + quantite + '}';
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
}
