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
        Parent root = FXMLLoader.load(getClass().getResource("../views/boutiqueBack.fxml"));
        
        ServiceCategorieProduit scp = new ServiceCategorieProduit();
        CategorieProduit categorie=scp.rechercherCategorieByName("souris");
        System.out.println("\n");
        System.out.println("la categorie:"+categorie);
        Scene scene=new Scene(root);
        primaryStage.setTitle("Boutique");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
