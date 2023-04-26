/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ggaming.interfaces;

import ggaming.entity.Jeux;
import ggaming.services.ServiceJeux;
import ggaming2.MyListener;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author dell
 */
public class FrontJeuxController implements Initializable {
    
    
      @FXML
    private VBox Vjeux;

    @FXML
    private Label note;
    
    @FXML
    private Label best;
    @FXML
    private ScrollPane scroll;
    private MyListener myListener;
    private Image image;
    private List<Jeux> jeuxes;
    
    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ServiceJeux sj = new ServiceJeux();
            sj.initConnection();
            List<Jeux> jeuxes = sj.afficherJeux();
             Jeux gameWithMaxNote = sj.bestJeux();
                System.out.println("Game with highest noteMyonne: " + gameWithMaxNote.getLibelle() + " - Note: " + gameWithMaxNote.getNoteMyonne());
            best.setText(gameWithMaxNote.getLibelle());
            note.setText(Float.toString(gameWithMaxNote.getNoteMyonne()));
            VBox jeuxContainer = new VBox();
            for (Jeux j : jeuxes) {
                Label titleLabel = new Label(j.getLibelle());
                titleLabel.setFont(new Font(30));
            System.out.println(titleLabel);
              System.out.println(j.getImageJeux());
              Image image = loadImage(j.getImageJeux());
            ImageView imageView = new ImageView(image);

            
            
                
                 VBox jBox = new VBox( imageView,titleLabel);
                jBox.setAlignment(Pos.CENTER);
                jBox.setSpacing(20);
                jeuxContainer.getChildren().add(jBox);
            
                titleLabel.setOnMouseClicked(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ggaming/interfaces/Jeuxdetails.fxml"));
                        Parent root = loader.load();
                        JeuxdetailsController jeuxController = loader.getController();
                        jeuxController.setJeux(j);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
            ScrollPane scrollPane = new ScrollPane(jeuxContainer);
            scrollPane.setFitToWidth(true);
            Vjeux.getChildren().add(scrollPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Image loadImage(String imageUrl) {
    try {
        // Try to load the image from the URL
        Image image = new Image(imageUrl);

        // Create an ImageView to display the image (optional)
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        // Create a Rectangle to clip the ImageView (optional)
        Rectangle clip = new Rectangle(
            imageView.getFitWidth(), imageView.getFitHeight()
        );
        imageView.setClip(clip);

        // Return the loaded image
        return image;
    } catch (Exception e) {
        // If the URL is invalid or the image cannot be loaded, return null
        return null;
    }
}

   
}

     
    

