/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.entity;

 import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;



/**
 *
 * @author dhia
 */
public class Equipe {
    
      private int id;
    private String nom_equipe ,description_equipe, logo_equipe, site_web;
    private int nb_joueurs;
    private Date date_creation;

    public Equipe(int id, String nom_equipe, String description_equipe, String logo_equipe, String site_web, int nb_joueurs, Date date_creation) {
        this.id = id;
        this.nom_equipe = nom_equipe;
        this.description_equipe = description_equipe;
        this.logo_equipe = logo_equipe;
        this.site_web = site_web;
        this.nb_joueurs = nb_joueurs;
        this.date_creation = date_creation;
    }

    public Equipe(String nom_equipe, String description_equipe, String logo_equipe, String site_web, int nb_joueurs, Date date_creation) {
        this.nom_equipe = nom_equipe;
        this.description_equipe = description_equipe;
        this.logo_equipe = logo_equipe;
        this.site_web = site_web;
        this.nb_joueurs = nb_joueurs;
        this.date_creation = date_creation;
    }

    public Equipe() {
    }

    @Override
    public String toString() {
        return "Equipe{" + "id=" + id + ", nom_equipe=" + nom_equipe + ", description_equipe=" + description_equipe + ", logo_equipe=" + logo_equipe + ", site_web=" + site_web + ", nb_joueurs=" + nb_joueurs + ", date_creation=" + date_creation + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_equipe() {
        return nom_equipe;
    }

    public void setNom_equipe(String nom_equipe) {
        this.nom_equipe = nom_equipe;
    }

    public String getDescription_equipe() {
        return description_equipe;
    }

    public void setDescription_equipe(String description_equipe) {
        this.description_equipe = description_equipe;
    }

    public String getLogo_equipe() {
        return logo_equipe;
    }

    public void setLogo_equipe(String logo_equipe) {
        this.logo_equipe = logo_equipe;
    }

    public String getSite_web() {
        return site_web;
    }

    public void setSite_web(String site_web) {
        this.site_web = site_web;
    }

    public int getNb_joueurs() {
        return nb_joueurs;
    }

    public void setNb_joueurs(int nb_joueurs) {
        this.nb_joueurs = nb_joueurs;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }
    
}
