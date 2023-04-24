/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author balla
 */
public class Commande {
    private int id;
    private String reference;
    private String date;
    private double montant;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Commande( String date, double montant) {
        
         Date currentDate = new Date();
        
        // Formater la date en chaîne de caractères
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateString = dateFormat.format(currentDate);
        // Générer un nombre aléatoire de 4 chiffres
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000; // entre 1000 et 9999
        
    
        String reference = "REF-COM-"+dateString + "-" + randomNumber;
        this.id = id;
        
        this.reference=reference;
        this.date = date;
        this.montant = montant;
    }
    
    
    
}
