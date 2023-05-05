/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package ggaming2;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author dell
 */
public class FXMainFront extends Application {
    
    @Override
    public void start(Stage primaryStage) {
         Parent root ;
         //System.out.println(getClass().getResource("/ggaming/interfaces/item.fxml"));
        // System.out.println(getClass().getResource("/ggaming/interfaces/FrontJeux2.fxml"));


        try{
            root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/FrontJeux2.fxml"));
            Scene scene = new Scene(root, 1315, 695);
        
            primaryStage.initStyle(StageStyle.DECORATED); 
            primaryStage.setScene(scene);
            primaryStage.show();
           
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
