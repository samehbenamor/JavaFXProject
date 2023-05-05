/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.Controller;

import edu.ggaming.entities.Match;
import edu.ggaming.entities.Tournoi;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author hayth
 */
public class MatchItemController implements Initializable {
    
    @FXML
    private Label equipeDom;

    @FXML
    private Label equipeExt;

    @FXML
    private Label scoreDom;

    @FXML
    private Label scoreExt;

    @FXML
    private Label tour;
    @FXML
    private BorderPane border;

    @FXML
    private HBox bxMatch;

    public void setData(Match match)
    {
        equipeExt.setText(match.getEquipe1().getNom_equipe());
        equipeDom.setText(match.getEquipe2().getNom_equipe());
        scoreExt.setText(Integer.toString(match.getScore1()));
        scoreDom.setText(Integer.toString(match.getScore2()));
        tour.setText(match.getTour());
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bxMatch.setStyle("-fx-background-color: #F5F5DC;");

    }    
    
    
}
