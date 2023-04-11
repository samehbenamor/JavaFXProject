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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private TableView<Commentaire> CommentairesTable;
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
    private TextField idtxt;
    @FXML
    private TextField etattxt;
    @FXML
    private TextField contenutxt;
    @FXML
    private TextField contenuCommentairetxt;
    @FXML
    private TextField idBlogTxt;
    @FXML
    private TextField idCommenttxt;


    @FXML
    private TableColumn<Commentaire, Integer> idcommentaire;
    @FXML
    private TableColumn<Commentaire, String> contenucolC;
    @FXML
    private TableColumn<Commentaire, LocalDateTime> datecreationcolC;
    @FXML
    private TableColumn<Commentaire, LocalDateTime> datemodificationcolC;

    private ObservableList<Commentaire> commentaireList = FXCollections.observableArrayList();

/************************************************************/
    String query = null;
    private ServicesBlog servicesBlog;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Blog blog = null ;
    ObservableList<Blog>  blogList = FXCollections.observableArrayList();
    private boolean isCommentView = false;
    int index = -1;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        servicesBlog = new ServicesBlog();
        servicesBlog.initConnection();
        loadDataBlog();
        loadDataCommentaire();
        isCommentView = false; 
    }

    @FXML
    public void GoComments(ActionEvent event) throws IOException {
        isCommentView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/commentaireB.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        loadDataCommentaire();
        refreshTableC();
    }

    @FXML
    public void RetourBlog(ActionEvent event) throws IOException {
        isCommentView = false;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/blogBack.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        loadDataBlog();
        refreshTable();
    }

    public void clearBlog(){
        titretxt.setText("");
        contenutxt.setText("");
        idtxt.setText("");
        etattxt.setText("");
    }

    public void clearComment(){
        contenuCommentairetxt.setText("");
        idBlogTxt.setText("");
        idCommenttxt.setText("");
    }

    public void AnnulerBlog(ActionEvent event){
        clearBlog();
    }

    public void AnnulerCommentaire(ActionEvent event){
        clearComment();
    }






/*********************************************************************************/





    @FXML
    public void AjouterBlog(ActionEvent event){
        String titre = titretxt.getText().trim();
        String contenu = contenutxt.getText().trim();
        String idstring = idtxt.getText().trim();
        String etatstring = etattxt.getText().trim();

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

        int etat;
        try {
            etat = Integer.parseInt(etatstring);
        } catch (NumberFormatException e) {
            etattxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir l'etat");
            alert.showAndWait();
            return;
        }

        LocalDateTime currentDate = LocalDateTime.now();
        Blog b = new Blog(titre,contenu,currentDate,currentDate,"gg", etat);
        ServicesBlog sb = new ServicesBlog();
        sb.initConnection();
        sb.ajouter(b);
        refreshTable();
        clearBlog();
    }

    @FXML
    public void AjouterCommentaire(ActionEvent event){
        String contenu = contenuCommentairetxt.getText().trim();
        String idblogstring = idBlogTxt.getText().trim();

        int idblog;
        try {
            idblog = Integer.parseInt(idblogstring);
        } catch (NumberFormatException e) {
            etattxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir l'id du blog");
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

        LocalDateTime currentDate = LocalDateTime.now();
        Commentaire c = new Commentaire(contenu,currentDate,currentDate);

        ServicesCommentaire sc = new ServicesCommentaire();
        sc.initConnection();
        sc.ajouterCommentaire(c,idblog);
        refreshTableC();
    }




/****************************************************************************/




    private void refreshTable(){
        try{
            blogList.clear();
            query = "SELECT * FROM `blog`";
            Connection connection = MyConnection.getInstance().getCnx();
            if (connection != null) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                LocalDateTime dateCreation = resultSet.getDate("date_creation").toLocalDate().atStartOfDay();
                LocalDateTime dateModification = resultSet.getDate("date_modification").toLocalDate().atStartOfDay();
                    blogList.add(new Blog(
                        resultSet.getInt("id"),
                        resultSet.getInt("etat"),
                        resultSet.getString("titre"),
                        resultSet.getString("contenu"),
                        dateCreation,
                        dateModification,
                        resultSet.getString("image_blog")
                    ));
                    blogsTable.setItems(blogList);
                }
            } else {
                System.out.println("Database connection is null");
            }
        }catch(SQLException ex){
            Logger.getLogger(BlogBackController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    private void refreshTableC(){
        try{
            commentaireList.clear();
            blogList.clear();
            query = "SELECT * FROM `commentaire`";
            Connection connection = MyConnection.getInstance().getCnx();
            if (connection != null) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                LocalDateTime dateCreation = resultSet.getDate("date_creation").toLocalDate().atStartOfDay();
                LocalDateTime dateModification = resultSet.getDate("date_modification").toLocalDate().atStartOfDay();
                    commentaireList.add(new Commentaire(
                        resultSet.getInt("id"),
                        resultSet.getString("contenu"),
                        dateCreation,
                        dateModification
                    ));
                    if (CommentairesTable != null) {
                        CommentairesTable.setItems(commentaireList);
                    }
                }

            } else {
                System.out.println("Database connection is null");
            }
        }catch(SQLException ex){
            Logger.getLogger(BlogBackController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }


    private void loadDataBlog() {
        try {
            refreshTable();
            idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            etatcol.setCellValueFactory(new PropertyValueFactory<>("etat"));
            titrecol.setCellValueFactory(new PropertyValueFactory<>("titre"));
            contenucol.setCellValueFactory(new PropertyValueFactory<>("contenu"));
            datecreationcol.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
            datemodificationcol.setCellValueFactory(new PropertyValueFactory<>("date_modification"));
            imagecol.setCellValueFactory(new PropertyValueFactory<>("imageblog"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataCommentaire() {
        try {
            refreshTableC();
            System.out.println(idcommentaire);
            idcommentaire.setCellValueFactory(new PropertyValueFactory<>("id"));
            contenucolC.setCellValueFactory(new PropertyValueFactory<>("contenu"));
            datecreationcolC.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
            datemodificationcolC.setCellValueFactory(new PropertyValueFactory<>("date_modification"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





/********************************************************************************/





    @FXML
    private void GetSelected(MouseEvent event){
        index = blogsTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        titretxt.setText(titrecol.getCellData(index).toString());
        contenutxt.setText(contenucol.getCellData(index).toString());
        idtxt.setText(idcol.getCellData(index).toString());
        etattxt.setText(etatcol.getCellData(index).toString());
    }

    @FXML
    private void GetSelectedCommentaire(MouseEvent event){
        index = CommentairesTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        contenuCommentairetxt.setText(contenucolC.getCellData(index).toString());
        idCommenttxt.setText(idcommentaire.getCellData(index).toString());
    }


    @FXML
    private void SupprimerBlog(ActionEvent event){
        String idstring = idtxt.getText().trim();
        int id = Integer.parseInt(idstring);

        ServicesBlog sb = new ServicesBlog();
        sb.initConnection();
        sb.deleteBlog(id);
        refreshTable();
        clearBlog();
    }

    @FXML
    private void SupprimerCommentaire(ActionEvent event){
        String idstring = idCommenttxt.getText().trim();
        int id = Integer.parseInt(idstring);

        ServicesCommentaire sc = new ServicesCommentaire();
        sc.initConnection();
        sc.deleteCommentaire(id);
        refreshTableC();
        clearComment();
    }




/********************************************************************************/



    @FXML
    private void EditerCommentaire(ActionEvent event){
        String idstring = idCommenttxt.getText();
        String contenu = contenutxt.getText();

        int id = Integer.parseInt(idstring);
        LocalDateTime currentDate = LocalDateTime.now();
        Commentaire c = new Commentaire(id,contenu,currentDate);

        ServicesCommentaire sc = new ServicesCommentaire();
        sc.initConnection();
        sc.updateCommentaire(c);
        refreshTableC();
        clearComment();
    }

    @FXML
    private void EditerBlog(ActionEvent event){
        try{
            String idstring = idtxt.getText().trim();
            String etatstring = etattxt.getText().trim();
            String titre = titretxt.getText().trim();
            String contenu = contenutxt.getText().trim();

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

            int etat;
            try {
                etat = Integer.parseInt(etatstring);
            } catch (NumberFormatException e) {
                etattxt.setStyle("-fx-border-color: red");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir l'etat");
                alert.showAndWait();
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idstring);
            } catch (NumberFormatException e) {
                etattxt.setStyle("-fx-border-color: red");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir l'id");
                alert.showAndWait();
                return;
            }

            LocalDateTime currentDate = LocalDateTime.now();
            Blog b = new Blog(id,titre,contenu,"gg",currentDate,etat);

            ServicesBlog sb = new ServicesBlog();
            sb.initConnection();
            sb.updateBlog(b);
            refreshTable();
            clearBlog();
        }catch(Exception e){
        }
    }
}
