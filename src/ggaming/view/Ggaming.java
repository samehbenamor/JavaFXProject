/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.view;

import ggaming.entity.Equipe;
import ggaming.services.EquipeService;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author dhia
 */
public class Ggaming extends Application {
    
    @Override
     public void start(Stage primaryStage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/ggaming/view/BackEquipe.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("Gestion equipe!");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
        
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      launch(args);
      /*
      ObservableList<Equipe> list=FXCollections.observableArrayList();
       EquipeService pd=new EquipeService();
       list=pd.GetBynomequipe("ccd");
       System.out.println(list);
       */
    }
    
}
