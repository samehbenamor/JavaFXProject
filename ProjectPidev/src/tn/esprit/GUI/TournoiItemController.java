/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import tn.esprit.entity.Tournoi;

/**
 * FXML Controller class
 *
 * @author hayth
 */
public class TournoiItemController implements Initializable {

    @FXML
    private Label lblDate;

    @FXML
    private Label lblJeu;

    @FXML
    private Label lblPrix;

    @FXML
    private VBox bxTournoi;

    @FXML
    private Label lblTournoiNom;

    @FXML
    private Label lblType;

        
    @FXML
    private ImageView imgTournoi;
        
    private Tournoi tournoi;
    public void setData(Tournoi tournoi)
    {
        this.tournoi=tournoi;
        lblDate.setText(tournoi.getDateDebut().toString());
        lblType.setText(tournoi.getTypeTournoi());
        lblPrix.setText(Integer.toString(tournoi.getPrix())+"dt");
        lblJeu.setText(tournoi.getJeu().getLibelle());
        lblTournoiNom.setText(tournoi.getNomTournoi());
        Image image = new Image(getClass().getResourceAsStream("ggaminglogo.png"));
    
imgTournoi.setImage(image);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bxTournoi.setStyle("-fx-background-color: #F5F5DC;");

    }    
    
}
