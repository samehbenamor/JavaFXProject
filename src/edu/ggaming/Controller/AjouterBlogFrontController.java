/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ggaming.Controller;

import ggaming.entity.Blog;
import ggaming.services.ServicesBlog;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author oness
 */
public class AjouterBlogFrontController implements Initializable {



    @FXML
    private TextArea contenutxt;
    @FXML
    private TextField titretxt;
    @FXML
    private TextField urlpathimage;
    @FXML
    private ImageView imageviewBlog;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    public void addimageblog(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();

        FileChooser.ExtensionFilter exxFilterJPG = new FileChooser.ExtensionFilter("JPG files (.jpg)", "*.jpg");
        FileChooser.ExtensionFilter exxFilterPNG = new FileChooser.ExtensionFilter("PNG files (.png)", "*.png");
        chooser.getExtensionFilters().addAll(exxFilterJPG, exxFilterPNG);
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            try {
                BufferedImage bufferedimg = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedimg, null);
                imageviewBlog.setImage(image);
                String imageUrl = file.toURI().toString();
                urlpathimage.setText(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (imageviewBlog.getImage() != null) {
            String imageUrl = imageviewBlog.getImage().impl_getUrl();
            urlpathimage.setText(imageUrl);
        }
    }

    @FXML
    public void AjouterBlog(ActionEvent event) throws IOException{
        String titre = titretxt.getText().trim();
        String contenu = contenutxt.getText().trim();
        String imageBlog=urlpathimage.getText().trim();


        if(titre.isEmpty()){
            titretxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le titre");
            alert.showAndWait();
            return;
        }

        if(contenu.isEmpty()){
            contenutxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le contenu");
            alert.showAndWait();
            return;
        }

        if(imageBlog.isEmpty()){
            contenutxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez ajouter image");
            alert.showAndWait();
            return;
        }

        LocalDateTime currentDate = LocalDateTime.now();
        Blog b = new Blog(titre,contenu,currentDate,currentDate,imageBlog, 2);
        ServicesBlog sb = new ServicesBlog();
        sb.initConnection();
        sb.ajouter(b);

        //retour au main page
        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/blogfront.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        stage.show();
    }
    

 @FXML
    public void retourmainpageblog(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/blogfront.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        stage.show();
    }

}
