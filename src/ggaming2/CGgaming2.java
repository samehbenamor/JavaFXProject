/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ggaming2;

import ggaming.entity.CategorieJeux;
import ggaming.entity.Jeux;
import ggaming.services.ServiceCatJeux;
import ggaming.services.ServiceJeux;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author dell
 */
public class CGgaming2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
       Jeux b = new Jeux(3, "test2", "09ncizbiq.png", "09ngcizbiq.jpg", LocalDateTime.now(), 5);
        CategorieJeux cat= new CategorieJeux(20,"test2");
        ServiceJeux bs = new ServiceJeux();
        ServiceCatJeux bc = new ServiceCatJeux();
        bs.initConnection(); 
        bc.initConnection();
//aff all method
     List<Jeux> allJeux = bs.getAll();
    for (Jeux jeu : allJeux) {
    System.out.println("Reference: " + jeu.getRef());
    System.out.println("Libelle: " + jeu.getLibelle());
    System.out.println("Date de creation: " + jeu.getDateCreation());
    System.out.println("Image: " + jeu.getImageJeux());
    System.out.println("Logo: " + jeu.getLogoJeux());
    System.out.println("Nombre de notes: " + jeu.getNoteCount());
    System.out.println("Nombre de vues: " + jeu.getViews());
    System.out.println("Note moyenne: " + jeu.getNoteMyonne());
    System.out.println("Note totale: " + jeu.getTotalNote());
    System.out.println();
       }

// call the initConnection() method
//bc.ajouter(cat);
//List<CategorieJeux> allCatJeux = bc.getAll();
       /* 
//add method
    bs.ajouter(b);


aff all method
     List<Jeux> allJeux = bs.getAll();
    for (Jeux jeu : allJeux) {
    System.out.println("Référence: " + jeu.getRef());
    System.out.println("Libellé: " + jeu.getLibelle());
    System.out.println("Date de création: " + jeu.getDateCreation());
    System.out.println("Image: " + jeu.getImageJeux());
    System.out.println("Logo: " + jeu.getLogoJeux());
    System.out.println("Nombre de notes: " + jeu.getNoteCount());
    System.out.println("Nombre de vues: " + jeu.getViews());
    System.out.println("Note moyenne: " + jeu.getNoteMyonne());
    System.out.println("Note totale: " + jeu.getTotalNote());
    System.out.println();
       }

//update by id method
    Jeux jeuxToUpdate = bs.findById(2);
    
    // Update the libelle property of the Jeux object
    jeuxToUpdate.setLibelle("update");
    
    // Call the update method with the updated Jeux object
    bs.update(jeuxToUpdate);



//supp method
    Jeux jeuxTodelete = bs.findById(2);
    bs.delete(jeuxTodelete);
*/
    }
    
}