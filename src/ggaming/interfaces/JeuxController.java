/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ggaming.interfaces;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.sun.jdi.connect.spi.Connection;
import ggaming.cnx.MaConnection;
import ggaming.entity.CategorieJeux;
import ggaming.entity.Jeux;
import ggaming.services.ServiceCatJeux;
import ggaming.services.ServiceJeux;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.time.LocalDateTime;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class JeuxController implements Initializable {
    @FXML
    private TextField rech;
    @FXML
    private Button btnrech;
    
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
    private TableColumn<Jeux, Float> tcnoteM;

    @FXML
    private Button jeuxbtn;

    @FXML
    private TableColumn<Jeux, LocalDateTime> tcdate;

    @FXML
    private Button jeuxaddBtn;

    @FXML
    private TableColumn<Jeux, Integer> tcviews;
    @FXML
    private TableColumn<Jeux, Integer> tcid;
    @FXML
    private TableColumn<Jeux, String> tclibelle;

    @FXML
    private Button jeuxupdateBtn;

    @FXML
    private ImageView jeuxlogo;

    @FXML
    private ComboBox<?> listeType;

    @FXML
    private ComboBox<CategorieJeux> listeCat;

    @FXML
    private Button jeuxdeleteBtn;

    @FXML
    private TableColumn<Jeux, String> tcref;

    @FXML
    private Button logoimportBtn;

    @FXML
    private TableView<Jeux> jeuxtable;
  

    @FXML
    private ImageView jeuximage;
    
    @FXML
    private ImageView jeuxlg;

    @FXML
    private TextField tfid;

    @FXML
    private Button catjeuxbtn;
    @FXML
    private Button newjeux;
    @FXML
    private Label errormsg;
   
    @FXML
    private Button imageimportBtn;

    @FXML
    private Button close;
    
    private int selectedJeuxId;
    int index=-1;
    private boolean catView = false;
    private ObservableList<Jeux> Data;
    private Connection con;
    String query = null;
    //Connection connection = null ;
    private ServiceJeux servicesJeux;
    private ServiceCatJeux servicesCatJeux;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Jeux jeux = null ;
    Jeux j = new Jeux();
    ObservableList<Jeux>  jeuxList = FXCollections.observableArrayList();
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        servicesJeux = new ServiceJeux();
        servicesJeux.initConnection();
        loadDataJeux();
         jeuxtable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // Get the id of the selected Jeux item
            selectedJeuxId = newSelection.getId();
            // Populate the input fields with the selected Jeux data
            tfLibelle.setText(newSelection.getLibelle());
            tfid.setText(Integer.toString(newSelection.getId()));
        }
    });
     // listeCat.setItems(FXCollections.observableArrayList(new ServiceCatJeux().getAllNames()));


    }
private void refreshTable() {
    try {
        jeuxList.clear();
        query = "SELECT * FROM jeux";
       java.sql.Connection connection = MaConnection.getInstance().getCnx();


        if (connection != null) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                LocalDateTime dateCreation = resultSet.getTimestamp("date_creation").toLocalDateTime();
             jeuxList.add(new Jeux(
     resultSet.getInt("id"),
        resultSet.getString("ref"),
        resultSet.getString("libelle"),
        dateCreation,
       resultSet.getInt("views"),
                     
        resultSet.getFloat("note_myonne")
    ));
            }
            jeuxtable.setItems(jeuxList);
        } else {
            System.out.println("Database connection is null");
        }
    } catch (SQLException ex) {
        Logger.getLogger(JeuxController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

@FXML
public void rechercherJeux(ActionEvent event) {
  if (event.getSource() == btnrech) {
        String name = rech.getText();
        List<Jeux> result = servicesJeux.rechercherJeuxParNom(name);

        // Add the result to the table
        ObservableList<Jeux> observableList = FXCollections.observableArrayList(result);
        jeuxtable.setItems(observableList);
    }
}
 @FXML
    private void emptyfields(ActionEvent event){
       tfid.clear();
        tfLibelle.clear();
    }
private void loadDataJeux() {
    try {
        refreshTable();
        tcref.setCellValueFactory(new PropertyValueFactory<>("ref"));
        tclibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        tcdate.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        tcviews.setCellValueFactory(new PropertyValueFactory<>("views"));
        tcnoteM.setCellValueFactory(new PropertyValueFactory<>("noteMyonne"));
        tcid.setCellValueFactory(new PropertyValueFactory<>("id"));
    } catch (Exception e) {
        e.printStackTrace();
    }
}
     @FXML
    void ajouterJeux(ActionEvent event) {
         String libelle = tfLibelle.getText().trim();
         String stringid = tfid.getText().trim();
         String logo_jeux=jeuxlg.getImage().toString();
         String image_jeux=jeuximage.getImage().toString();
         int id = parseInt(stringid);
        if(libelle.isEmpty()){
            
            tfLibelle.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le libelle");
            alert.showAndWait();
            return;
        }

        LocalDateTime currentDate = LocalDateTime.now();
        Jeux b = new Jeux(id, libelle, image_jeux, logo_jeux, currentDate, 0);
        ServiceJeux sb = new ServiceJeux();
        sb.initConnection();
        sb.ajouter(b);
        refreshTable();
       tfid.clear();
        tfLibelle.clear();
    }
    @FXML
    private void addlogo(ActionEvent event) throws IOException {
        
        FileChooser Chooser = new FileChooser();
        
        FileChooser.ExtensionFilter exxFilterJPG= new FileChooser.ExtensionFilter("JPG files (*.jpg)","*.JPG");
        FileChooser.ExtensionFilter exxFilterPNG= new FileChooser.ExtensionFilter("PNG files (*.png)","*.PNG");
        
        Chooser.getExtensionFilters().addAll(exxFilterJPG,exxFilterPNG);
        File file = Chooser.showOpenDialog(null);
        BufferedImage bufferedimg = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedimg, null);
        jeuxlg.setImage(image);
      
        
    }
    @FXML
    private void addimage(ActionEvent event) throws IOException {
        
        FileChooser Chooser = new FileChooser();
        
        FileChooser.ExtensionFilter exxFilterJPG= new FileChooser.ExtensionFilter("JPG files (*.jpg)","*.JPG");
        FileChooser.ExtensionFilter exxFilterPNG= new FileChooser.ExtensionFilter("PNG files (*.png)","*.PNG");
        
        Chooser.getExtensionFilters().addAll(exxFilterJPG,exxFilterPNG);
        File file = Chooser.showOpenDialog(null);
        BufferedImage bufferedimg = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedimg, null);
        jeuximage.setImage(image);
      
        
    }
    @FXML
    private void suppjeux(ActionEvent event){
        try{

            String idstring = tfid.getText();

            int id = Integer.parseInt(idstring);

            Jeux b = new Jeux(id);
        
            ServiceJeux sb = new ServiceJeux();
            sb.initConnection();
            sb.delete(b);
            refreshTable();
            tfid.clear();
        tfLibelle.clear();
        }catch(Exception e){
        }
    }
    @FXML
    private void Editerjeux(ActionEvent event){
        
        try{

            String idstring = tfid.getText();
            String libstring = tfLibelle.getText();
          

            int id = Integer.parseInt(idstring);

            Jeux b = new Jeux(id,libstring);
        
            ServiceJeux sb = new ServiceJeux();
            sb.initConnection();
            sb.updatelibelle(b);
            refreshTable();
            tfid.clear();
        tfLibelle.clear();
        }catch(Exception e){
        }
    }
    
    @FXML
    void affjeux(ActionEvent event) {

    }
    @FXML
    void checknew(ActionEvent event) throws IOException {
         catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/GameA.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
    
    @FXML
    void affcat(ActionEvent event) throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/Backcat.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

       
    }
    
}
