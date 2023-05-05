/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.interfaces;

import java.util.List;
import edu.ggaming.entities.Match;

/**
 *
 * @author hayth
 */
public interface interfaceMatch {
    public void ajouterMatch(Match p);
    public void modifierMatch(Match p,int id);
    public void supprimerMatch(Match m);
    public List<Match> afficherMatch();

}
