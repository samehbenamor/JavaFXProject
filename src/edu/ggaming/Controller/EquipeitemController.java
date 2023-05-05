/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.Controller;

import edu.ggaming.entities.Equipe;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author dhia
 */
public class EquipeitemController implements Initializable {

private Equipe equipe ;
    @FXML
    private Label nameLabel;
    @FXML
    private ImageView img;




    /**
     * Initializes the controller class.
     */
  
     public void setData(Equipe equipe)
    {
        this.equipe=equipe;
        nameLabel.setText(equipe.getNom_equipe());
        //Image image = new Image(getClass().getResourceAsStream(equipe.getLogo_equipe()));
        //img.setImage(image);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        
        
    }    

    @FXML
    private void click(MouseEvent event) {
    }

    @FXML
    private void select(MouseEvent event) {
        
        
        
        
    }

    
}
