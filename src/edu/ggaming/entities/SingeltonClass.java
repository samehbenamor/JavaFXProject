/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.entities;

/**
 *
 * @author dhia
 */
public class SingeltonClass {
     private Equipe equipe;
    public final static SingeltonClass INSTANCE = new SingeltonClass();

    public SingeltonClass() {
    }

    public Equipe getTournoi() {
        return equipe;
    }

    public void setTournoi(Equipe ee) {
        this.equipe = ee;
    }
  
    
}
