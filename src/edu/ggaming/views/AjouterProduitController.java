/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.views;

import edu.ggaming.entities.Produit;
import edu.ggaming.services.ServiceProduit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author balla
 */
public class AjouterProduitController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfQuantite;
    @FXML
    private TextField tfImage;
    @FXML
    private Button btnAjouter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void saveProduit(ActionEvent event) {
        
        String nom=tfNom.getText();
        String description=tfDescription.getText();
        String prix=tfPrix.getText();
        int quantite=Integer.parseInt(tfQuantite.getText());
        String image=tfImage.getText();
        
        Produit p=new Produit(nom,description,image,prix,quantite);
        ServiceProduit sp=new ServiceProduit();
        sp.ajouterProduit2(p);
        
    }
    
}
