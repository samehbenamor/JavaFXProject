/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.entities;

import java.util.Date;
import java.util.Objects;

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
    private CategorieProduit categorie;
    private String date_creation;

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
    public Produit(String nom, String description, String image, String prix, int quantite,CategorieProduit categorie) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.quantite = quantite;
        this.categorie=categorie;
    }
     public Produit(String nom, String description, String image, String prix, int quantite,CategorieProduit categorie, String date_creation) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.quantite = quantite;
        this.categorie=categorie;
        this.date_creation=date_creation;
    }

    public CategorieProduit getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieProduit categorie) {
        this.categorie = categorie;
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
   
     public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.nom);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.image);
        hash = 41 * hash + Objects.hashCode(this.prix);
        hash = 41 * hash + this.quantite;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produit other = (Produit) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.quantite != other.quantite) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.prix, other.prix)) {
            return false;
        }
        return true;
    }
    
}
