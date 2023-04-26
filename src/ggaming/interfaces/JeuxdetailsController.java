/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ggaming.interfaces;

import ggaming.entity.Jeux;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class JeuxdetailsController implements Initializable {
    
    @FXML
    private Button back;
    @FXML
    private Label titleLabel;
    @FXML
    private VBox containerPane;
    private int id;
    private Jeux jeux;
    @FXML
    private ImageView imageView;
public void setJeux(Jeux jeux) {
        this.jeux = jeux;
        this.id = jeux.getId();
         if (jeux != null) {
        titleLabel.setText(jeux.getLibelle());
     //imageView.setImage(new Image(jeux.getImageJeux()));
        
        
       // StackPane imagePane = new StackPane(imageView);
        //imagePane.setAlignment(Pos.CENTER);
        
        VBox blogContainer = new VBox(titleLabel);
        blogContainer.setAlignment(Pos.CENTER);
        blogContainer.setSpacing(20);
        
        ScrollPane scrollPane = new ScrollPane(blogContainer);
        scrollPane.setFitToWidth(true);
        
        // set the content of the view to the scroll pane
        // (assuming the view is already loaded)
        // viewPane is the container pane in the Blog.fxml file
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
        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/FrontJeux2.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        stage.show();
    }
}
