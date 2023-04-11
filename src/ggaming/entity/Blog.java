/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oness
 */
public class Blog {
    private int id,etat;
    private String titre,contenu,imageblog;
    private LocalDateTime date_creation,date_modification;
    private List<Commentaire> commentaires;


    public Blog() {
        commentaires = new ArrayList<Commentaire>();
    }

    public Blog(int id,String titre, String contenu, String imageblog, LocalDateTime date_modification,int etat) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.imageblog = imageblog;
        this.date_modification = date_modification;
        this.etat = etat;
    }


    public Blog(String titre, String contenu) {
        commentaires = new ArrayList<Commentaire>();
        this.titre = titre;
        this.contenu = contenu;
    }

    public Blog(int id, int etat, String titre, String contenu, LocalDateTime date_creation, LocalDateTime date_modification, String imageblog) {
        this.id = id;
        this.etat = etat;
        this.titre = titre;
        this.contenu = contenu;
        this.date_creation = date_creation;
        this.date_modification = date_modification;
        this.imageblog = imageblog;
        commentaires = new ArrayList<Commentaire>();
    }

    public Blog(int etat, String titre, String contenu, LocalDateTime date_creation, LocalDateTime date_modification, String imageblog) {
        this.etat = etat;
        this.titre = titre;
        this.contenu = contenu;
        this.date_creation = date_creation;
        this.date_modification = date_modification;
        this.imageblog = imageblog;
        commentaires = new ArrayList<Commentaire>();
    }

    public Blog(String titre, String contenu, LocalDateTime date_creation, LocalDateTime date_modification, String imageblog, int etat) {
        this.titre = titre;
        this.contenu = contenu;
        this.date_creation = date_creation;
        this.date_modification = date_modification;
        this.imageblog = imageblog;
        this.etat = etat;
        commentaires = new ArrayList<Commentaire>();
    }

    public Blog(int id, String titre, String contenu, String imageblog) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.imageblog = imageblog;
        commentaires = new ArrayList<Commentaire>();
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public void addCommentaire(Commentaire commentaire) {
        commentaires.add(commentaire);
    }

    public void removeCommentaire(Commentaire commentaire) {
        commentaires.remove(commentaire);
    }

    public int getId() {
        return id;
    }

    public int getEtat() {
        return etat;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDateTime getDate_creation() {
        return date_creation;
    }

    public LocalDateTime getDate_modification() {
        return date_modification;
    }

    public String getImageblog() {
        return imageblog;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDate_creation(LocalDateTime date_creation) {
        this.date_creation = date_creation;
    }

    public void setDate_modification(LocalDateTime date_modification) {
        this.date_modification = date_modification;
    }

    public void setImageblog(String imageblog) {
        this.imageblog = imageblog;
    }

}
