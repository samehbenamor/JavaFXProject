/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.GUI;

import edu.ggaming.entity.SingletonClass;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import edu.ggaming.entity.Tournoi;
import edu.ggaming.services.MatchService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    private AnchorPane border;

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
//Border color = new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(2)));
//border.setBorder(color);

    }    
    
    
    @FXML
    void selectionnerTournoi(MouseEvent event) {
        try {
            SingletonClass.INSTANCE.setTournoi(tournoi);

            Parent root = FXMLLoader.load(getClass().getResource("TournoiSelect.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //stage.setUserData(tournoi);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex);        
        }
    }
    
}
