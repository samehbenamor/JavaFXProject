/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.Controller;

import edu.ggaming.entities.Sponsor;
import edu.ggaming.services.SponsorService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dhia
 */
public class FrontSponsorController implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
private List<Sponsor> sp;
private final SponsorService ss=new SponsorService();
private boolean equipeview = false;
    @FXML
    private Button btn_fequipe;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         sp = new ArrayList<>();
        sp.addAll(ss.displayAlll());   
        
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < sp.size(); i++) {
              FXMLLoader fxmlLoader = new FXMLLoader();
              fxmlLoader.setLocation(getClass().getResource("../views/sponsoritem.fxml"));
               AnchorPane anchorPane = fxmlLoader.load();

                SponsoritemController itemControllerr = fxmlLoader.getController();
               itemControllerr.setData(sp.get(i));

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

               GridPane.setMargin(anchorPane, new Insets(10));
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        
    }    

    @FXML
    private void gotofequipe(ActionEvent event)  throws IOException {
        equipeview = true;

        Parent root = FXMLLoader.load(getClass().getResource("../views/FrontEquipe.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    
    
    
    
    
    
    
    
    
    
    }    
    

