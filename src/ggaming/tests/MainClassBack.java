/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.tests;


import ggaming.cnx.MyConnection;
import ggaming.entity.Blog;
import ggaming.services.ServicesBlog;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author oness
 */
public class MainClassBack extends Application {

    @Override
    public void start(Stage primaryStage){
        Parent root;
        try {
            root = FXMLLoader.load(getClass()
                    .getResource("/ggaming/interfaces/blogBack.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
    //public static void main(String[] args){
//tester connection base de donnee
        //MyConnection mc = new MyConnection();

//Ajouter Blog
        //LocalDateTime currentDate = LocalDateTime.now();
        //Blog b = new Blog("testt", "test", currentDate, currentDate, "zklekj", 2);

//Afficher
        /*ServicesBlog sb = new ServicesBlog();
        sb.initConnection();
        List<Blog> blogs = sb.getAllBlogs();
        for (Blog b : blogs) {
            System.out.println(b.getTitre() + ": " + b.getContenu() 
                + "   Creation date : " + b.getDate_creation() 
                +"    Modification time : "+ b.getDate_modification() 
                + "   Image : " + b.getImageblog());
        }*/

//Supprimer un blog
        //ServicesBlog sb = new ServicesBlog();
        //sb.initConnection();
        //sb.deleteBlog(5);

//Update blog
        /*ServicesBlog sb = new ServicesBlog();
        sb.initConnection();
        LocalDateTime currentDate = LocalDateTime.now();
        Blog updatedBlog = new Blog(6, 1, "New Title", "New Content", null, currentDate, "new_image.jpg");
        sb.updateBlog(updatedBlog);*/

    //}
