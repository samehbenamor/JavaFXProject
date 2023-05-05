/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ggaming.Controller;

import edu.ggaming.entities.Blog;
import edu.ggaming.services.ServicesBlog;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author oness
 */
public class BlogFrontController implements Initializable {



    @FXML
    private VBox mycontainerBlog;
    @FXML
    private VBox topBlogscontainer;
    @FXML
    private TextField titreField;
    @FXML
    private TextField contenuField;
    @FXML
    private TextField dateField;

    private boolean catView = false;
private List<Blog> allBlogs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ServicesBlog servicesBlog = new ServicesBlog();
            servicesBlog.initConnection();
            allBlogs = servicesBlog.getAllBlogs();
            displayBlogs(allBlogs);
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
    public void creeBlogFront(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/ajouterBlogfront.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        stage.show();
    }

    @FXML
    private void SearchButton(ActionEvent event) {
        String titreKeyword = titreField.getText().trim().toLowerCase();
        String contenuKeyword = contenuField.getText().trim().toLowerCase();
        String dateKeyword = dateField.getText().trim().toLowerCase();
        ServicesBlog servicesBlog = new ServicesBlog();
        servicesBlog.initConnection();
        List<Blog> filteredBlogs = servicesBlog.searchBlogs(titreKeyword, contenuKeyword, dateKeyword);
        displayBlogs(filteredBlogs);
    }

    private void displayBlogs(List<Blog> blogs) {
        mycontainerBlog.getChildren().clear();
        VBox blogContainer = new VBox();
        ServicesBlog servicesBlog = new ServicesBlog();
        servicesBlog.initConnection();
        List<Blog> topblogs = servicesBlog.getTop3Blogs(blogs);

            VBox topblogsContainer = new VBox();

            for (Blog blog : topblogs) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDateTime = blog.getDate_creation().format(formatter);

                Label dateLabel = new Label(formattedDateTime);
                Label blogLabel = new Label(blog.getTitre());
                blogLabel.setWrapText(true);
                blogLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16)); 
                blogLabel.setTextAlignment(TextAlignment.LEFT); 

                if(blog.getTitre().length() > 10) {
                    blogLabel.setText(blog.getTitre().substring(0, 10) + "...");
                }

                Pane spacer = new Pane();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                HBox blogEntry = new HBox();
                blogEntry.setSpacing(10);
                blogEntry.getChildren().addAll(dateLabel, spacer, blogLabel); // add spacer between dateLabel and blogLabel

                topBlogscontainer.getChildren().add(blogEntry);

                blogLabel.setOnMouseClicked(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Blog.fxml"));
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


        for (Blog blog : blogs) {
            Label titleLabel = new Label(blog.getTitre());
            titleLabel.setFont(new Font(30));

            ImageView imageView = new ImageView(new Image(blog.getImageblog()));
            imageView.setFitWidth(800);
            imageView.setFitHeight(500);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
            Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
            imageView.setClip(clip);

            VBox blogBox = new VBox(imageView, titleLabel);
            blogBox.setAlignment(Pos.CENTER);
            blogBox.setSpacing(20);
            blogContainer.getChildren().add(blogBox);
            imageView.setOnMouseClicked(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Blog.fxml"));
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Blog.fxml"));
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
    }
}
