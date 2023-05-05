package edu.ggaming.main;

import edu.ggaming.entities.CategorieProduit;
import edu.ggaming.services.ServiceCategorieProduit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String CURRENCY = "DT";
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("../../../ggaming2/HomePage.fxml"));
        
       
        Scene scene=new Scene(root);
        primaryStage.setTitle("Boutique");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
