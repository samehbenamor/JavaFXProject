/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ggaming.Controller;

import edu.ggaming.entities.Jeux;
import edu.ggaming.services.ServiceJeux;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class JeuxdetailsController implements Initializable {
    
    
      @FXML
    private VBox Vjeux;
    @FXML
    private Button back;
    @FXML
    private Label rate;
    @FXML
    private Label titleLabel;
    @FXML
    private VBox containerPane;
    private int id;
    private Jeux jeux;
    @FXML
    private ImageView imageView;
    private boolean catView = false;
    
public void setJeux(Jeux jeux) {
    this.jeux = jeux;
    this.id = jeux.getId();

    if (jeux != null) {
       ServiceJeux sj = new ServiceJeux();
            sj.initConnection();
        HBox titleAndRatingContainer = new HBox();
        titleAndRatingContainer.setAlignment(Pos.CENTER_LEFT);
        titleAndRatingContainer.setSpacing(10);

         titleLabel.setText(jeux.getLibelle());


        ImageView filledStarImage = new ImageView(new Image("/ggaming/images/filled.png"));
        filledStarImage.setFitHeight(25);
        filledStarImage.setFitWidth(25);

        Label ratingLabel = new Label(String.format("%.1f", jeux.getNoteMyonne()));
     
        titleAndRatingContainer.getChildren().addAll(titleLabel, ratingLabel, filledStarImage);

   
        ImageView imageView = new ImageView(new Image("/ggaming/images/valorant-1640045685890.jpg"));
        imageView.setFitHeight(338);
        imageView.setFitWidth(337);
        HBox gameDetailsContainer = new HBox(imageView);
        
        VBox gameDetailsVBox = new VBox(titleAndRatingContainer);

        HBox ratingContainer = new HBox();
        ratingContainer.setAlignment(Pos.CENTER);
        ratingContainer.setSpacing(5);

      

      ImageView[] starImages = new ImageView[5];
for (int i = 0; i < 5; i++) {
    starImages[i] = new ImageView(new Image("/ggaming/images/empty.png"));
    starImages[i].setFitHeight(25);
    starImages[i].setFitWidth(25);
    ratingContainer.getChildren().add(starImages[i]);

    int index = i;
    starImages[i].setOnMouseClicked(e -> {
        int rating = index * 2 + 2;
jeux.setNoteMyonne(rating);
int filledStars = (int) Math.round(jeux.getNoteMyonne() / 2.0f);
ratingLabel.setText(String.format("%.1f", jeux.getNoteMyonne()));

for (int j = 0; j < 5; j++) {
    if (j < filledStars) {
        starImages[j].setImage(new Image("/ggaming/images/filled.png"));
    } else {
        starImages[j].setImage(new Image("/ggaming/images/empty.png"));
    }
}

jeux.setNote(rating);
 sj.updateNoteMyonne(jeux);
    });
}



        gameDetailsVBox.getChildren().add(ratingContainer);

        gameDetailsContainer.getChildren().add(gameDetailsVBox);

        VBox blogContainer = new VBox(gameDetailsContainer);
        blogContainer.setAlignment(Pos.CENTER);
        blogContainer.setSpacing(20);

        ScrollPane scrollPane = new ScrollPane(blogContainer);
        scrollPane.setFitToWidth(true);

        containerPane.getChildren().setAll(scrollPane);
    }
}




    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
      @FXML
    public void backbtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FrontJeux2.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

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
        
        //Vjeux.getChildren().clear();

        Vjeux.getChildren().add(scrollPane);
    } catch (Exception e) {
        e.printStackTrace();
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
}
