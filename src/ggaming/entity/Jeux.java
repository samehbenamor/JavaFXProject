
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author dell
 */
public class Jeux {


    private int id;
    private String ref, libelle;
    private LocalDateTime dateCreation;
    private String imageJeux, logoJeux;
    private int noteCount, views;
    private float note, noteMyonne, totalNote;


    public Jeux(int id, String ref, String libelle, String imageJeux, String logoJeux, LocalDateTime dateCreation,
                float note, int noteCount, float noteMyonne, float totalNote, int views) {
        this.id = id;
        this.ref = ref;
        this.libelle = libelle;
        this.imageJeux = imageJeux;
        this.logoJeux = logoJeux;
        this.dateCreation = dateCreation;
        this.note = note;
        this.noteCount = noteCount;
        this.noteMyonne = noteMyonne;
        this.totalNote = totalNote;
        this.views = views;
    }


public Jeux() {
    }

public Jeux(int id,String libelle) {
        this.libelle = libelle; 
this.id = id; 
    }
public Jeux(String libelle) {
        this.libelle = libelle;  
    }

public Jeux(String ref,String libelle) {
        this.libelle = libelle;  
        this .ref=ref;
    }
public Jeux(int id,String libelle, String imageJeux) {
        this.id=id;
        this.libelle = libelle;  
        this .imageJeux=imageJeux;
    }

 public Jeux(int id, String libelle, String ref,LocalDateTime dateCreation) {
        this.id = id;
        this.libelle = libelle;
        this.ref = ref;
        this.dateCreation =dateCreation;
    }
public Jeux( String libelle, String ref, LocalDateTime dateCreation) {
        
        this.libelle = libelle;
        this.ref = ref;
        this.dateCreation =dateCreation;
    }
public Jeux( String libelle, String ref, LocalDateTime dateCreation,String imageJeux) {
        
        this.libelle = libelle;
        this.ref = ref;
        this.dateCreation =dateCreation;
        this.imageJeux=imageJeux;
    }
public Jeux( String libelle, String ref, LocalDateTime dateCreation,String imageJeux,String logoJeux) {
        
        this.libelle = libelle;
        this.ref = ref;
        this.dateCreation =dateCreation;
        this.imageJeux=imageJeux;
        this.logoJeux=logoJeux;
    }



public String getImageJeux() {
        return imageJeux;
    }

    public void setImageJeux(String imageJeux) {
        this.imageJeux = imageJeux;
    }
public String getLogoJeux() {
        return logoJeux;
    }

    public void setLogoJeux(String logoJeux) {
        this.logoJeux = logoJeux;
    }
public int getId() {
        return id;
    }
public void setId(int id) {
        this.id = id;
    }
public String getRef() {
        return ref;
    }


public void setRef(String ref) {
    
    this.ref = ref;
}


 public LocalDateTime getDateCreation() {
        return dateCreation;
    }
 public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
public String getLibelle() {
        return libelle;
    }
public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

public float getNote() {
        return note;
    }
public void setNote(float note) {
        this.noteCount++;
        this.totalNote += note;
        this.noteMyonne = this.totalNote / this.noteCount;
        this.note = note;
    }
public void setNoteBack() {
        this.note = 0;
        this.views = 0;
        this.noteCount = 0;
        this.noteMyonne = 0;
        this.totalNote = 0;
    }
public float getNoteMyonne() {
        return noteMyonne;
    }
public void setNoteMyonne(float noteMyonne) {
        this.noteMyonne = noteMyonne;
    }
public float getTotalNote() {
        return totalNote;
    }
public void setTotalNote(float totalNote) {
        this.totalNote = totalNote;
    }
public int getViews() {
        return views;
    }
public void setViews(int views) {
        this.views = views;
    }
public int getNoteCount() {
        return noteCount;
    }
public void setNoteCount(int noteCount) {
        this.noteCount = noteCount;
    }
public Jeux(int id, String libelle, String imageJeux, String logoJeux, LocalDateTime dateCreation, int views) {
    this.id = id;
    this.libelle = libelle;
    this.imageJeux = imageJeux;
    this.logoJeux = logoJeux;
    this.dateCreation = dateCreation;
    this.views = views;
    this.ref = generateRef(); // generates a unique reference
    this.noteCount = 0;
    this.note = 0.0f;
    this.noteMyonne = 0.0f;
    this.totalNote = 0.0f;
}

private String generateRef() {
    // generate a unique reference here and return it
return "09svez";
}

}
