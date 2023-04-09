/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ggaming.interfaces;

import ggaming.entity.CategorieJeux;
import ggaming.services.ServiceCatJeux;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class JeuxController implements Initializable {
    
  @FXML
    private Button minimize;

    @FXML
    private AnchorPane jeuxback;

    @FXML
    private Button jeuxclearBtn;

    @FXML
    private AnchorPane jeuxform;

    @FXML
    private TextField tfLibelle;

    @FXML
    private TableColumn<?, ?> tcnoteM;

    @FXML
    private Button jeuxbtn;

    @FXML
    private TableColumn<?, ?> tcdate;

    @FXML
    private Button jeuxaddBtn;

    @FXML
    private TableColumn<?, ?> tcviews;

    @FXML
    private TableColumn<?, ?> tclibelle;

    @FXML
    private Button jeuxupdateBtn;

    @FXML
    private ImageView jeuxlogo;

    @FXML
    private ComboBox<?> listeType;

    @FXML
    private ComboBox<?> listeCat;

    @FXML
    private Button jeuxdeleteBtn;

    @FXML
    private TableColumn<?, ?> tcref;

    @FXML
    private Button logoimportBtn;

    @FXML
    private TableView<?> jeuxableView;

    @FXML
    private ImageView jeuximage;

    @FXML
    private TextField tfid;

    @FXML
    private Button catjeuxbtn;

    @FXML
    private Button imageimportBtn;

    @FXML
    private Button close;
    
/**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

/*ServiceCatJeux cs = new ServiceCatJeux();
        catListObservable = FXCollections.observableArrayList(cs.getAll());
        listcat.setItems(catListObservable);
        tablabel.setText("Liste des categorie jeux : " + catListObservable.size());*/
}
        
    @FXML
    private void handler(ActionEvent event) {
        
        if(event.getSource()== jeuxbtn)
     {
         jeuxback.toFront();
     }else  if(event.getSource()== catjeuxbtn)
     {
       // pnCatJeux.toFront();
        
     }
         
    }
        
    
    }
    
    




