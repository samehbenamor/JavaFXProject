/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.sql.Date;

/**
 *
 * @author dhia
 */
public class Sponsor {
    private int id;
    private String nom_sponsor ,description_sponsor, logo_sponsor, site_webs;
    
    private Date date_creationn;

    public Sponsor(int id, String nom_sponsor, String description_sponsor, String logo_sponsor, String site_webs, Date date_creationn) {
        this.id = id;
        this.nom_sponsor = nom_sponsor;
        this.description_sponsor = description_sponsor;
        this.logo_sponsor = logo_sponsor;
        this.site_webs = site_webs;
        this.date_creationn = date_creationn;
    }

    public Sponsor(String nom_sponsor, String description_sponsor, String logo_sponsor, String site_webs, Date date_creationn) {
        this.nom_sponsor = nom_sponsor;
        this.description_sponsor = description_sponsor;
        this.logo_sponsor = logo_sponsor;
        this.site_webs = site_webs;
        this.date_creationn = date_creationn;
    }

    public Sponsor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_sponsor() {
        return nom_sponsor;
    }

    public void setNom_sponsor(String nom_sponsor) {
        this.nom_sponsor = nom_sponsor;
    }

    public String getDescription_sponsor() {
        return description_sponsor;
    }

    public void setDescription_sponsor(String description_sponsor) {
        this.description_sponsor = description_sponsor;
    }

    public String getLogo_sponsor() {
        return logo_sponsor;
    }

    public void setLogo_sponsor(String logo_sponsor) {
        this.logo_sponsor = logo_sponsor;
    }

    public String getSite_webs() {
        return site_webs;
    }

    public void setSite_webs(String site_webs) {
        this.site_webs = site_webs;
    }

    public Date getDate_creationn() {
        return date_creationn;
    }

    public void setDate_creationn(Date date_creationn) {
        this.date_creationn = date_creationn;
    }

    @Override
    public String toString() {
        return "Sponsor{" + "id=" + id + ", nom_sponsor=" + nom_sponsor + ", description_sponsor=" + description_sponsor + ", logo_sponsor=" + logo_sponsor + ", site_webs=" + site_webs + ", date_creationn=" + date_creationn + '}';
    }
    
    
    
    
}