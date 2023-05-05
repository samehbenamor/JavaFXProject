/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ggaming.Controller;

import edu.ggaming.entities.Blog;
import edu.ggaming.services.ServicesBlog;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author oness
 */
public class BlogController implements Initializable {


    @FXML
    private Label titleLabel;
    @FXML
    private Label likenum;
    @FXML
    private Label dislikenum;
    @FXML
    private ImageView imageView;
    @FXML
    private Label contentLabel;
    @FXML
    private VBox containerPane;
    private int id;
    private Blog blog;
    private boolean catView = false;

    public void setBlog(Blog blog) {
        this.blog = blog;
        this.id = blog.getId();
         if (blog != null) {
        titleLabel.setText(blog.getTitre());
        imageView.setImage(new Image(blog.getImageblog()));
        contentLabel.setText(blog.getContenu());
        likenum.setText(String.valueOf(blog.getLike()));
        dislikenum.setText(String.valueOf(blog.getDislike()));

        
        StackPane imagePane = new StackPane(imageView);
        imagePane.setAlignment(Pos.CENTER);
        
        VBox blogContainer = new VBox(titleLabel, imagePane, contentLabel);
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

    @FXML
    public void retourbtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/blogfront.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        stage.show();
    }

    @FXML
    public void EditerBlogfront(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/plswork.fxml"));
        Parent root = loader.load();
        EditBlogController editBlogController = loader.getController();
        editBlogController.setBlog(blog);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        stage.show();
    }


    @FXML
    public void Accedercommentaires(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/commentaires.fxml"));
        Parent root = loader.load();

        CommentaireController controller = loader.getController();
        controller.setBlogId(id);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        stage.show();
    }


    @FXML
    public void SupprimerBlog(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression du blog");
        alert.setContentText("Voulez-vous vraiment supprimer ce blog ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // delete the blog from the database
            ServicesBlog sb = new ServicesBlog();
            sb.initConnection();
            sb.deleteBlog(id);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Information");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Blog supprimé avec succès");

            successAlert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("../views/blogfront.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            stage.show();
        }
    }

    @FXML
    public void likeblog(ActionEvent event){
        blog.setLike(blog.getLike() + 1);
        likenum.setText("" + blog.getLike());

        ServicesBlog sb = new ServicesBlog();
        sb.initConnection();
        sb.updateBlog(blog);
    }



    @FXML
    public void dislikeblog(ActionEvent event){
        blog.setDislike(blog.getDislike() + 1);
        dislikenum.setText("" + blog.getDislike());

        ServicesBlog sb = new ServicesBlog();
        sb.initConnection();
        sb.updateBlog(blog);
    }
}
