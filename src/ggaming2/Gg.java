/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import java.io.IOException;
import edu.ggaming.services.JoueurController;

/**
 *
 * @author DELL
 */
public class Gg extends Application {

    @Override
    public void start(Stage primaryStage){

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("boutiqueBack.fxml"));
        //StackPane root = new StackPane();
        //root.getChildren().add(loader);
        //VBox root = loader.load();
        Parent root;
        try {
            
            root = new FXMLLoader(getClass().getResource("HomePage.fxml")).load();
            //root =FXMLLoader.load(getClass().getResource("boutiqueBack_1.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("Enregistrez votre compte !");
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
    }

}
