/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ggaming.Controller;

import java.sql.Connection;
import edu.ggaming.utils.MyConnection;
import edu.ggaming.entities.CategorieJeux;

import edu.ggaming.services.ServiceCatJeux;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * FXML Controller class
 *
 * @author dell
 */
public class BackcatController implements Initializable {
    @FXML
    private Button cataddBtn;

    @FXML
    private Button minimize;

    @FXML
    private AnchorPane jeuxback;

    @FXML
    private Button catclearBtn;

    @FXML
    private AnchorPane jeuxform;

    @FXML
    private Button jeuxbtn;

    @FXML
    private TableView<CategorieJeux> cattable;

    @FXML
    private TableColumn<CategorieJeux, String> tcnomcat;

    @FXML
    private Button catupdateBtn;

    @FXML
    private Label errormsg;

    @FXML
    private TextField tfnomcat;

    @FXML
    private TableColumn<CategorieJeux,Integer> tcidcat;

    @FXML
    private Button catdeleteBtn;

    @FXML
    private Button catjeuxbtn;

    @FXML
    private Button close;

    @FXML
    private TextField tfidcat;
    
    private int selectedcatId;
    private boolean catView = false;
private ObservableList<CategorieJeux> catdata;
    private Connection con;
    String query = null;
    //Connection connection = null ;
    private ServiceCatJeux servicescat;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    CategorieJeux cat = null ;
    ObservableList<CategorieJeux>  catList = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        servicescat = new ServiceCatJeux();
        servicescat.initConnection();
        loadDatacat();
        
        cattable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // Get the id of the selected Jeux item
            selectedcatId = newSelection.getId();
            // Populate the input fields with the selected Jeux data
            tfnomcat.setText(newSelection.getNomCat());
            tfidcat.setText(Integer.toString(newSelection.getId()));
        }
    });
    }    
    
    @FXML
    void affcat(ActionEvent event) {

    }
    @FXML
    void  affjeuxpage(ActionEvent event) 
throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/jeuxb.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

       
    }
    
private void refreshTable() {
    try {
        catList.clear();
        query = "SELECT * FROM categorie_jeux";
       java.sql.Connection connection = MyConnection.getInstance().getCnx();


        if (connection != null) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
             catList.add(new CategorieJeux(
     resultSet.getInt("id"),
        resultSet.getString("nom_cat")
    ));
            }
            cattable.setItems(catList);
        } else {
            System.out.println("Database connection is null");
        }
    } catch (SQLException ex) {
        Logger.getLogger(JeuxController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

private void loadDatacat() {
    try {
        refreshTable();
       tcnomcat.setCellValueFactory(new PropertyValueFactory<>("nom_cat"));
        tcidcat.setCellValueFactory(new PropertyValueFactory<>("id"));
    } catch (Exception e) {
        e.printStackTrace();
    }
}
 @FXML
    private void modifcat(ActionEvent event){
        try{

            String idstring = tfidcat.getText();
            String nomstring = tfnomcat.getText();
          

            int id = Integer.parseInt(idstring);

            CategorieJeux b = new CategorieJeux(id,nomstring);
        
            ServiceCatJeux sb = new ServiceCatJeux();
            sb.initConnection();
            sb.update(b);
            refreshTable();
            tfidcat.clear();
        tfnomcat.clear();
        }catch(Exception e){
        }
    }
     @FXML
    private void empty(ActionEvent event){
       tfidcat.clear();
        tfnomcat.clear();
    }
    @FXML
    private void suppcat(ActionEvent event){
        try{

            String idstring = tfidcat.getText();

            int id = Integer.parseInt(idstring);

            CategorieJeux b = new CategorieJeux(id);
        
            ServiceCatJeux sb = new ServiceCatJeux();
            sb.initConnection();
            sb.delete(b);
            System.out.println("working");
            refreshTable();
            tfidcat.clear();
        tfnomcat.clear();
        }catch(Exception e){
        }
    }
     @FXML
    void addcat(ActionEvent event) {
         String libelle = tfnomcat.getText().trim();
         String stringid = tfidcat.getText().trim();
       
         int id = parseInt(stringid);
        if(libelle.isEmpty()){
            
            tfnomcat.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le nom");
            alert.showAndWait();
            return;
        }
        CategorieJeux b = new CategorieJeux(id, libelle);
        ServiceCatJeux sb = new ServiceCatJeux();
        sb.initConnection();
        sb.ajouter(b);
        refreshTable();
       tfidcat.clear();
        tfnomcat.clear();
    }  
    }

