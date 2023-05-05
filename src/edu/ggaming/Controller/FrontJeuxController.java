/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ggaming.Controller;

import edu.ggaming.entities.Jeux;
import edu.ggaming.services.ServiceJeux;
import ggaming2.MyListener;
import java.awt.image.BufferedImage;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;


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
    private Label newestbtn;
        @FXML
    private Label topbtn;
            @FXML
    private Button mainpage;
             private boolean catView = false;
              @FXML
    private TextField rech;
                @FXML
    private Button rechbtn;
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
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Jeuxdetails.fxml"));
    Parent root = loader.load();
    JeuxdetailsController jeuxController = loader.getController();
    jeuxController.setJeux(j);
    sj.updateViews(j);
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
      
        Image image = new Image(imageUrl);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        Rectangle clip = new Rectangle(
            imageView.getFitWidth(), imageView.getFitHeight()
        );
        imageView.setClip(clip);

        return image;
    } catch (Exception e) {
      
        return null;
    }
}

   @FXML
void Newest(MouseEvent event) {
    try {
        ServiceJeux sj = new ServiceJeux();
        sj.initConnection();
        VBox jeuxContainer = new VBox();
        List<Jeux> jeuxes = sj.TridateJeux();
        for (Jeux j : jeuxes) {
            Label titleLabel = new Label(j.getLibelle());
            titleLabel.setFont(new Font(30));
            System.out.println(titleLabel);
            
            VBox jBox = new VBox(titleLabel);
            jBox.setAlignment(Pos.CENTER);
            jBox.setSpacing(20);
            jeuxContainer.getChildren().add(jBox);
            
            titleLabel.setOnMouseClicked(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Jeuxdetails.fxml"));
                    Parent root = loader.load();
                    JeuxdetailsController jeuxController = loader.getController();
                    jeuxController.setJeux(j);
                    sj.updateViews(j);
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
        
        Vjeux.getChildren().clear();

        Vjeux.getChildren().add(scrollPane);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
 @FXML
    void again(ActionEvent event) throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("FrontJeux2.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void AfBlog(ActionEvent event) throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("../views/blogfront.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void AfEquipe(ActionEvent event) throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("../views/FrontEquipe.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
     @FXML
    public void afficherBoutique(ActionEvent event)
     {
              
              Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("../views/boutique.fxml"));
              Scene scene = new Scene(root);
                
                Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
         } catch (IOException ex) {
             Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
         }
               
    }
    
     @FXML
    void afficherTournoiFront(ActionEvent event) {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("TournoiHome.fxml"));
              Scene scene = new Scene(root);
                
                Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
         } catch (IOException ex) {
             Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @FXML
    void Top(MouseEvent event) {
try {
        ServiceJeux sj = new ServiceJeux();
        sj.initConnection();
        VBox jeuxContainer = new VBox();
        List<Jeux> jeuxes = sj.TribestJeux();
        for (Jeux j : jeuxes) {
            Label titleLabel = new Label(j.getLibelle());
            titleLabel.setFont(new Font(30));
            System.out.println(titleLabel);
            
            VBox jBox = new VBox(titleLabel);
            jBox.setAlignment(Pos.CENTER);
            jBox.setSpacing(20);
            jeuxContainer.getChildren().add(jBox);
            
            titleLabel.setOnMouseClicked(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Jeuxdetails.fxml"));
                    Parent root = loader.load();
                    JeuxdetailsController jeuxController = loader.getController();
                    jeuxController.setJeux(j);
                    sj.updateViews(j);
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
        
        Vjeux.getChildren().clear();

        Vjeux.getChildren().add(scrollPane);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
@FXML
    void recherche(ActionEvent event) {
          VBox jeuxContainer = new VBox();
    ServiceJeux sj = new ServiceJeux();
        sj.initConnection();
 if (event.getSource() == rechbtn) {
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setHeaderText("");
        //nothing found message
        if(rech.getText().isEmpty()){ //rien saisie
            alert.setContentText("Rien Saisie !");
            alert.showAndWait();
        }
        else if(!rech.getText().isEmpty())
        { 
            List<Jeux> jeuxes = sj.rechercherJeuxParNom(rech.getText());
            for (Jeux j : jeuxes) {
            Label titleLabel = new Label(j.getLibelle());
            titleLabel.setFont(new Font(30));
            System.out.println(titleLabel);
            
            VBox jBox = new VBox(titleLabel);
            jBox.setAlignment(Pos.CENTER);
            jBox.setSpacing(20);
            jeuxContainer.getChildren().add(jBox);
            
            titleLabel.setOnMouseClicked(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Jeuxdetails.fxml"));
                    Parent root = loader.load();
                    JeuxdetailsController jeuxController = loader.getController();
                    jeuxController.setJeux(j);
                    sj.updateViews(j);
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
        
        Vjeux.getChildren().clear();

        Vjeux.getChildren().add(scrollPane);
      
        }  
 
    }
}
    
    
}
     
    

