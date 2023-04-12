/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ggaming.interfaces;

import ggaming.entity.Jeux;
import ggaming2.MyListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

   
    
/**
 * FXML Controller class
 *
 * @author dell
 */
public class ItemjeuxController implements Initializable {
     @FXML
    private ImageView img;

    @FXML
    private ImageView logo;

    @FXML
    private Label nameLabel;

private Jeux jeux;
    private MyListener myListener;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
  
    @FXML
    void click(ActionEvent event) {
                myListener.onClickListener(jeux);
    }
    
    public void setData(Jeux jeux, MyListener myListener) {
        this.jeux = jeux;
        this.myListener = myListener;
        nameLabel.setText(jeux.getLibelle());
    
           Image     imagej = new Image(getClass().getResourceAsStream("../img/souris3.jpg"));

        img.setImage(imagej);
        Image     logoj = new Image(getClass().getResourceAsStream("../img/souris3.jpg"));

        logo.setImage(logoj);
    }
}
