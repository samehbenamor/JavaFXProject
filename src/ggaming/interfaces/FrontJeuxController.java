/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ggaming.interfaces;

import ggaming.entity.Jeux;
import ggaming.services.ServiceJeux;
import ggaming2.MyListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class FrontJeuxController implements Initializable {
    
    
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    private Image image;
    private List<Jeux> jeuxes;
    ServiceJeux sp=new ServiceJeux();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
      /*
        jeuxes = new ArrayList<>();
        jeuxes.addAll(sp.afficherJeux());  
        int column = 0;
        int row = 1;
            for (int i = 0; i < jeuxes.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("itemj.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemjeuxController itemController = fxmlLoader.getController();
                itemController.setData(jeuxes.get(i));
                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                GridPane.setMargin(anchorPane, new Insets(10));

               //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

            } 
            catch (IOException ex) {
                System.out.print(ex);
            }
            } */
    }
}
