/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ggaming.Controller;

import ggaming.entity.Blog;
import ggaming.services.ServicesBlog;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author oness
 */
public class BlogFrontController implements Initializable {



    @FXML
    private VBox mycontainerBlog;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ServicesBlog servicesBlog = new ServicesBlog();
            servicesBlog.initConnection();
            List<Blog> blogs = servicesBlog.getAllBlogs();
            VBox blogContainer = new VBox();
            for (Blog blog : blogs) {
                Label titleLabel = new Label(blog.getTitre());
                titleLabel.setFont(new Font(30));

                ImageView imageView = new ImageView(new Image(blog.getImageblog()));
                imageView.setFitWidth(800);
                imageView.setFitHeight(500);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                imageView.setCache(true);
                Rectangle clip = new Rectangle(
                    imageView.getFitWidth(), imageView.getFitHeight()
                );
                clip.setArcWidth(30);
                clip.setArcHeight(30);
                imageView.setClip(clip);

                VBox blogBox = new VBox(imageView, titleLabel);
                blogBox.setAlignment(Pos.CENTER);
                blogBox.setSpacing(20);
                blogContainer.getChildren().add(blogBox);
                imageView.setOnMouseClicked(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ggaming/interfaces/Blog.fxml"));
                        Parent root = loader.load();
                        BlogController blogController = loader.getController();
                        blogController.setBlog(blog);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                titleLabel.setOnMouseClicked(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ggaming/interfaces/Blog.fxml"));
                        Parent root = loader.load();
                        BlogController blogController = loader.getController();
                        blogController.setBlog(blog);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
            ScrollPane scrollPane = new ScrollPane(blogContainer);
            scrollPane.setFitToWidth(true);
            mycontainerBlog.getChildren().add(scrollPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void creeBlogFront(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/ajouterBlogfront.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        stage.show();
    }

    
}
