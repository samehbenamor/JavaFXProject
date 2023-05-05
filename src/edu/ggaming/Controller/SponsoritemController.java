/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.Controller;

import edu.ggaming.entities.Sponsor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author dhia
 */

public class SponsoritemController implements Initializable {
private Sponsor sponsor;
    @FXML
    private Label namesp;
    @FXML
    private ImageView imgsp;
    /**
     * Initializes the controller class.
     */
    
     public void setData(Sponsor sponsor)
    {
        this.sponsor=sponsor;
        namesp.setText(sponsor.getNom_sponsor());
      //  Image image = new Image(getClass().getResourceAsStream(equipe.getLogo_equipe()));
       // img.setImage(image);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
