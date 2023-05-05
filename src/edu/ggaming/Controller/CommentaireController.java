/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ggaming.Controller;

import edu.ggaming.entities.Blog;
import edu.ggaming.entities.Commentaire;
import edu.ggaming.services.ServicesCommentaire;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author oness
 */
public class CommentaireController implements Initializable {

    @FXML
    private VBox mycontainerCommentaires;
    @FXML
    private Label commentsLabel;
    @FXML
    private TextField commentsTXT;
    @FXML
    private TextField commenttxtPLS;
    private int blogId;
    private boolean catView = false;
    

    private void loadComments() {
        try {
            ServicesCommentaire servicesCommentaire = new ServicesCommentaire();
            servicesCommentaire.initConnection();
            List<Commentaire> commentaires = servicesCommentaire.getAllCommentairesByBlogId(blogId);
            VBox commentsContainer = new VBox();

            for (Commentaire commentaire : commentaires) {
                Label contenuLabel = new Label(commentaire.getContenu());
                contenuLabel.setFont(new Font(20));
                contenuLabel.setStyle("-fx-padding: 20px;");

                ImageView imageView = new ImageView(new Image("file:///C:/Users/oness/OneDrive/Desktop/Images/gggg.png"));
                imageView.setFitHeight(30);
                imageView.setPreserveRatio(true);
                contenuLabel.setGraphic(imageView);

                Button editButton = new Button("Edit");
                editButton.setOnAction(event -> {
                    TextInputDialog dialog = new TextInputDialog(commentaire.getContenu());
                    dialog.setTitle("Edit Comment");
                    dialog.setHeaderText(null);
                    dialog.setContentText("Enter the new comment:");

                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(newComment -> {
                        commentaire.setContenu(newComment);
                        servicesCommentaire.updateCommentaire(commentaire);
                        loadComments();
                    });
                });

                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Comment");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to delete this comment?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        servicesCommentaire.deleteCommentaire(commentaire.getId());
                        loadComments();
                    }
                });

                Button reportButton = new Button("Report");
                reportButton.setOnAction(event -> {
                    int reportCount = commentaire.getReportCount();
                    commentaire.setReportCount(reportCount + 1);
                    servicesCommentaire.initConnection();
                    servicesCommentaire.updateCommentaire(commentaire);
                    reportCount = commentaire.getReportCount();
                    System.out.println(reportCount);
                });

                HBox buttonBox = new HBox(editButton, deleteButton, reportButton);
                buttonBox.setSpacing(10);
                VBox commentaireBox = new VBox(contenuLabel, buttonBox);
                commentaireBox.setSpacing(20);
                commentsContainer.getChildren().add(commentaireBox);
            }
            ScrollPane scrollPane = new ScrollPane(commentsContainer);
            scrollPane.setFitToWidth(true);
            mycontainerCommentaires.getChildren().add(scrollPane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
        loadComments(); 
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void again(ActionEvent event) throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/FrontJeux2.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void AfBlog(ActionEvent event) throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/views/blogfront.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void AfEquipe(ActionEvent event) throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/views/FrontEquipe.fxml"));
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
    

    public void ajoutercomment(ActionEvent event){
        String contenu = commenttxtPLS.getText().trim();

        if(contenu.isEmpty()){
            commentsTXT.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le contenu");
            alert.showAndWait();
            return;
        }
        LocalDateTime currentDate = LocalDateTime.now();
        Commentaire c = new Commentaire(contenu,currentDate,currentDate,0);

        ServicesCommentaire sc = new ServicesCommentaire();
        sc.initConnection();
        sc.ajouterCommentaire(c,blogId);
        loadComments();
    }


    @FXML
    public void retourr(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../views/blogfront.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        stage.show();
    }
    
}
