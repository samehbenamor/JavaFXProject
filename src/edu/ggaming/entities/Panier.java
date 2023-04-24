/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.entities;

import java.util.ArrayList;

/**
 *
 * @author balla
 */
public class Panier {
    private ArrayList<Produit> produits;
    private ArrayList<Integer> quantites;
    private double total;
      public Panier() {
        this.produits = new ArrayList<Produit>();
        this.quantites = new ArrayList<Integer>();
        this.total = 0;
        
    }

    public void ajouterArticle(Produit produit) {
        this.produits.add(produit);
        this.quantites.add(produit.getQuantite());
        this.total += Integer.parseInt(produit.getPrix()) * produit.getQuantite();
    }

    public void retirerArticle(Produit produit) {
        
         int index = this.produits.indexOf(produit);
    if (index != -1) {
        int quantite = this.quantites.get(index);
        this.produits.remove(index);
        this.quantites.remove(index);
        this.total -= Integer.parseInt(produit.getPrix()) * quantite;
    }
    else
            System.out.println("produit no ntrouvé");
    }
    
    public void afficherPanier() {
 

    for (int i = 0; i < produits.size(); i++) {
        Produit produit = produits.get(i);
        int quantite = quantites.get(i);

        System.out.println("- " + produit.getNom() + " (x" + quantite + ")");
    }

    System.out.println("Total : " + total + "€");
}

    public ArrayList<Produit> getProduits() {
        return produits;
    }

    public void setProduits(ArrayList<Produit> produits) {
        this.produits = produits;
    }

    public ArrayList<Integer> getQuantites() {
        return quantites;
    }

    public void setQuantites(ArrayList<Integer> quantites) {
        this.quantites = quantites;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
