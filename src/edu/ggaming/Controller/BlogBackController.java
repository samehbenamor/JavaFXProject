/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ggaming.Controller;

import ggaming.cnx.MyConnection;
import ggaming.entity.Blog;
import ggaming.entity.Commentaire;
import ggaming.services.ServicesBlog;
import ggaming.services.ServicesCommentaire;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author oness
 */
public class BlogBackController implements Initializable {

    @FXML
    private TableView<Blog> blogsTable;
    @FXML
    private TableColumn<Blog, Integer> idcol;
    @FXML
    private TableColumn<Blog, Integer> etatcol;
    @FXML
    private TableColumn<Blog, String> titrecol;
    @FXML
    private TableColumn<Blog, String> contenucol;
    @FXML
    private TableColumn<Blog, LocalDateTime> datecreationcol;
    @FXML
    private TableColumn<Blog, LocalDateTime> datemodificationcol;
    @FXML
    private TableColumn<Blog, String> imagecol;
    @FXML
    private TextField titretxt;
    @FXML
    private TextField contenutxt;
    @FXML
    private TextField contenuCommentairetxt;
    @FXML
    private TextField idBlogTxt;

/************************************************************/
    private ObservableList<Blog> blogData;
    private Connection con;
    String query = null;
    Connection connection = null ;
    private ServicesBlog servicesBlog;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Blog blog = null ;
    ObservableList<Blog>  blogList = FXCollections.observableArrayList();




    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        servicesBlog = new ServicesBlog();
        servicesBlog.initConnection();
        loadDataBlog();
    }

    @FXML
    public void GoComments(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/commentaireB.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void RetourBlog(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/blogBack.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void AjouterBlog(ActionEvent event){
        String titre = titretxt.getText();
        String contenu = contenutxt.getText();
        MyConnection mc = new MyConnection();
        LocalDateTime currentDate = LocalDateTime.now();
        Blog b = new Blog(titre,contenu,currentDate,currentDate,"gg", 2);

        ServicesBlog sb = new ServicesBlog();
        sb.initConnection();
        sb.ajouter(b);
    }

    @FXML
    public void AjouterCommentaire(ActionEvent event){
        String contenu = contenuCommentairetxt.getText();
        String idblogstring = idBlogTxt.getText();
        int idblog = Integer.parseInt(idblogstring);

        MyConnection mc = new MyConnection();
        LocalDateTime currentDate = LocalDateTime.now();
        Commentaire c = new Commentaire(contenu,currentDate,currentDate);
        ServicesCommentaire sc = new ServicesCommentaire();
        sc.initConnection();
        sc.ajouterCommentaire(c,idblog);
    }

    private void loadDataBlog() {
        /*List<Blog> blogs = servicesBlog.getAllBlogs();
        ObservableList<Blog> blogData = FXCollections.observableArrayList(blogs);
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        etatcol.setCellValueFactory(new PropertyValueFactory<>("etat"));
        titrecol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        contenucol.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        datecreationcol.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        datemodificationcol.setCellValueFactory(new PropertyValueFactory<>("dateModification"));
        imagecol.setCellValueFactory(new PropertyValueFactory<>("image"));
        blogsTable.setItems(blogData);*/


        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titrecol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        contenucol.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        datecreationcol.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
        datemodificationcol.setCellValueFactory(new PropertyValueFactory<>("date_modification"));
        imagecol.setCellValueFactory(new PropertyValueFactory<>("imageblog"));
        etatcol.setCellValueFactory(new PropertyValueFactory<>("etat"));

        List<Blog> blogs = servicesBlog.getAllBlogs();
        ObservableList<Blog> data = FXCollections.observableArrayList(blogs);
        blogsTable.setItems(data);
    }
}
