/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.entity;

/**
 *
 * @author hayth
 */
public class SingletonClass  {
    private Tournoi tournoi;
    public final static SingletonClass INSTANCE = new SingletonClass();
  
  private SingletonClass() {}
 /* 
  public static SingletonClass getInstance() {
    return INSTANCE;
  }*/
  
  public void setTournoi(Tournoi u) {
    this.tournoi = u;
  }
  
  public Tournoi getTournoi() {
    return this.tournoi;
  }
}
