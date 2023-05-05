/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ggaming.entities;

import java.time.LocalDateTime;

/**
 *
 * @author oness
 */
public class Commentaire {

    private int id;
    private String contenu;
    private LocalDateTime date_creation,date_modification;
    private Blog blog;
    private int reportCount;

    public Commentaire() {
    }

    public Commentaire(int id, String contenu, LocalDateTime date_modification, int reportCount) {
        this.id = id;
        this.contenu = contenu;
        this.date_modification = date_modification;
        this.reportCount=reportCount;
    }

    public Commentaire(int id, String contenu, LocalDateTime date_creation, LocalDateTime date_modification, int reportCount) {
        this.id = id;
        this.contenu = contenu;
        this.date_creation = date_creation;
        this.date_modification = date_modification;
        this.reportCount=reportCount;
    }

    public Commentaire(int id, String contenu, LocalDateTime date_creation, LocalDateTime date_modification, Blog blog, int reportCount) {
        this.id = id;
        this.contenu = contenu;
        this.date_creation = date_creation;
        this.date_modification = date_modification;
        this.blog = blog;
        this.reportCount=reportCount;
    }

    public Commentaire(String contenu, LocalDateTime date_creation, LocalDateTime date_modification, Blog blog, int reportCount) {
        this.contenu = contenu;
        this.date_creation = date_creation;
        this.date_modification = date_modification;
        this.blog = blog;
        this.reportCount=reportCount;
    }

    public Commentaire(String contenu, LocalDateTime date_creation, LocalDateTime date_modification, int reportCount) {
        this.contenu = contenu;
        this.date_creation = date_creation;
        this.date_modification = date_modification;
        this.reportCount=reportCount;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(LocalDateTime date_creation) {
        this.date_creation = date_creation;
    }

    public LocalDateTime getDate_modification() {
        return date_modification;
    }

    public void setDate_modification(LocalDateTime date_modification) {
        this.date_modification = date_modification;
    }


}
