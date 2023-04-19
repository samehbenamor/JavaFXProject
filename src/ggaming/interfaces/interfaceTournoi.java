/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.interfaces;

import java.util.List;
import ggaming.entity.Tournoi;

/**
 *
 * @author hayth
 */
public interface interfaceTournoi {
    public void ajouterTournoi(Tournoi p);
    public void modifierTournoi(Tournoi p,int id);
    public void supprimerTournoi(Tournoi t);
    public List<Tournoi> afficherTournoi();
   
}
