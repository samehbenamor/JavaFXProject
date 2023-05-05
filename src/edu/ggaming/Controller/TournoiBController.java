/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.Controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import edu.ggaming.entities.Equipe;
import edu.ggaming.entities.Jeux;
import edu.ggaming.entities.Tournoi;
import edu.ggaming.entities.TypeTournoi;
import edu.ggaming.services.TournoiService;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author hayth
 */
public class TournoiBController implements Initializable {

    TournoiService ts;
    @FXML
    private TableView<Tournoi> tournoiTable;
    @FXML
    private TableColumn<Tournoi, String> nomCl;
    @FXML
    private TableColumn<Tournoi, String> jeuCl;
    @FXML
    private TableColumn<Tournoi, String> typeCl;
    @FXML
    private TableColumn<Tournoi, String> partCl;
    @FXML
    private TableColumn<Tournoi, String> prixCl;
    @FXML
    private TableColumn<Tournoi, String> imgCl;
    @FXML
    private TableColumn<Tournoi, String> dateCl;
   
    @FXML
    private TextField tournoiSearch;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfJeu;
    @FXML
    private TextField tfid;
    @FXML
    private ImageView produit_image;
    @FXML
    private Button addTournoiBtn;
    @FXML
    private Button addTournoi_updateBtn;
    @FXML
    private Button delTournoiBtn;
    @FXML
    private Button annulerTournoi;
    @FXML
    private DatePicker tfDate;
    @FXML
    private TextField tfType;
    @FXML
    private ComboBox <String> cmbType;
    @FXML
    private ComboBox <String> cmbJeu;
    @FXML
    private ImageView imgTournoi;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            ts= new TournoiService();
            List<Tournoi> tournoiList = ts.afficherTournoi();
            ObservableList<Tournoi> data = FXCollections.observableArrayList(tournoiList);
            nomCl.setCellValueFactory(new PropertyValueFactory<>("nomTournoi"));
            prixCl.setCellValueFactory(new PropertyValueFactory<>("prix"));
            typeCl.setCellValueFactory(new PropertyValueFactory<>("typeTournoi"));
            dateCl.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
            jeuCl.setCellValueFactory(cellData -> {
                Jeux jeu = cellData.getValue().getJeu();
                String nom = "";
                if (jeu != null) {
                    nom = String.valueOf(jeu.getLibelle());
                }
                return new SimpleStringProperty(nom);
            });
            tournoiTable.setItems(data);

            tfDate.setValue(LocalDate.now());
            TypeTournoi tournamentType = TypeTournoi.Single;

            ObservableList<String> options = FXCollections.observableArrayList(
                TypeTournoi.Single.toString(),TypeTournoi.Double.toString(),TypeTournoi.RoundRobin.toString(),
                    TypeTournoi.Multilevel.toString(),TypeTournoi.Extended.toString()
            );
            cmbType.setValue("Single");
            cmbType.setItems(options);

            ObservableList<String> jeux = FXCollections.observableArrayList(ts.getJeux());
            cmbJeu.setItems(jeux);

            

        tournoiTable.setRowFactory( tv -> {
        TableRow<Tournoi> myRow = new TableRow<>();
        myRow.setOnMouseClicked (event ->
        {
           if (event.getClickCount() == 1 && (!myRow.isEmpty()))
           {
              int myIndex =  tournoiTable.getSelectionModel().getSelectedIndex();
              tfNom.setText(tournoiTable.getItems().get(myIndex).getNomTournoi());
              cmbJeu.setValue(tournoiTable.getItems().get(myIndex).getJeu().getLibelle());

              LocalDate date = LocalDate.parse(tournoiTable.getItems().get(myIndex).getDateDebut().toString());
              tfDate.setValue(date);
              int prix = Integer.parseInt(String.valueOf(tournoiTable.getItems().get(myIndex).getPrix()));
              tfPrix.setText(Integer.toString(prix)); 

              cmbType.setValue(tournoiTable.getItems().get(myIndex).getTypeTournoi());
           }
        });
           return myRow;
        });

                  
                  
    }     

    @FXML
    private void addTournoi(ActionEvent event) { 
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("");
        Boolean test;
        try {
            Integer.parseInt(tfPrix.getText());
            test=true;
        } catch (NumberFormatException e) {
            test=false;        
        }
        if (tfNom.getText().isEmpty() || cmbJeu.getValue().isEmpty() ||cmbType.getValue().isEmpty()||
            tfPrix.getText().isEmpty() ||tfDate.getValue()==null){
		alert.setContentText("Remplir tous les champs");
                alert.showAndWait();
        }
        else if(tfNom.getText().length()>30){
           alert.setContentText("Le nom doit contenir moins de 30 caracteres");
           alert.showAndWait(); 
        }
        else if(tfDate.getValue().isBefore(LocalDate.now())){
           alert.setContentText("La date doit etre superieur a la date d'aujourd hui ");
           alert.showAndWait(); 
        }
        
        else if(!test){
           alert.setContentText("Veuillez saisir un entier comme prix");
           alert.showAndWait(); 
        }

        else{
        String date  = (tfDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).toString();
        String nom= tfNom.getText();
        Jeux jeu = ts.findIdByJeu(cmbJeu.getValue());

        String type = cmbType.getValue();

        String image = null;  //change this
        String prixText = tfPrix.getText();
        int prix= Integer.parseInt(prixText);
         
        ts.ajouterTournoi(new Tournoi(0, nom, jeu, prix,type, image, Date.valueOf(date)));
        refreshtable();
        alert.setContentText("Ajotué avec Succes");
        alert.showAndWait();
    
        }
    }
    @FXML
    private void modifierTournoi(ActionEvent event) {
        Tournoi t=tournoiTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("");
        Alert alertConf = new Alert(AlertType.WARNING,"Etes-vous sur de vouloir proceder",ButtonType.YES);
        alertConf.setTitle("Attention !");
        Boolean test;
        try {
            Integer.parseInt(tfPrix.getText());
            test=true;
        } catch (NumberFormatException e) {
            test=false;        
        }
        if(t==null)
        {
            
        alert.setContentText("Rien Selectionné");
        alert.showAndWait();
        }
        else if (tfNom.getText().isEmpty() || cmbJeu.getValue().isEmpty() ||cmbType.getValue().isEmpty()||
            tfPrix.getText().isEmpty() ||tfDate.getValue()==null){
		alert.setContentText("Remplir tous les champs");
                alert.showAndWait();
        }
        else if(tfNom.getText().length()>30){
           alert.setContentText("Le nom doit contenir moins de 30 caracteres");
           alert.showAndWait(); 
        }
        else if(tfDate.getValue().isBefore(LocalDate.now())){
           alert.setContentText("La date doit etre superieur a la date d'aujourd hui ");
           alert.showAndWait(); 
        }
        
        else if(!test){
           alert.setContentText("Veuillez saisir un entier comme prix");
           alert.showAndWait(); 
        }
        else{
        String date  = (tfDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).toString();
        String nom= tfNom.getText();
        Jeux jeu = ts.findIdByJeu(cmbJeu.getValue());
        String type = cmbType.getValue();
        String image = null;  //change this
        String prixText = tfPrix.getText();
        int prix= Integer.parseInt(prixText);
        
        t.setNomTournoi(nom);
        t.setPrix(prix);
        t.setTypeTournoi(type);
        t.setJeu(jeu);
        t.setDateDebut(Date.valueOf(date));
        t.setImageTournoi(image);
        Optional<ButtonType> result = alertConf.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            ts.modifierTournoi(t,t.getId());
            refreshtable();
            alert.setContentText("Modifié avec Succes");
            alert.showAndWait();
            }
        }
    }

    @FXML
    private void deleteTournoi(ActionEvent event) {
        Tournoi t=tournoiTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("");
        Alert alertConf = new Alert(AlertType.WARNING,"Etes-vous sur de vouloir proceder",ButtonType.YES);
        alertConf.setTitle("Attention !");
        if(t==null)
        {
            
        alert.setContentText("Rien Selectionné");
        alert.showAndWait();
        }
        else{
        Optional<ButtonType> result = alertConf.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
    // user clicked "yes"
        System.out.print("heyy");
        ts.supprimerTournoi(t);
        refreshtable();
        //add bool in function
         alert.setContentText("Supprimé avec Succes");
         alert.showAndWait();
}

    }
    }
    @FXML
    private void annuler(ActionEvent event) {
        tfPrix.setText("");
        cmbJeu.setValue("");
        tfDate.setValue(LocalDate.now());
        cmbType.setValue("Single");
        tfNom.setText("");
       tournoiTable.getSelectionModel().clearSelection();
    }
    private void refreshtable()
    {

            List<Tournoi> tournoiList = ts.afficherTournoi();
            ObservableList<Tournoi> data = FXCollections.observableArrayList(tournoiList);
            prixCl.setCellValueFactory(new PropertyValueFactory<>("prix"));
            typeCl.setCellValueFactory(new PropertyValueFactory<>("typeTournoi"));
            dateCl.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
jeuCl.setCellValueFactory(cellData -> {
                Jeux jeu = cellData.getValue().getJeu();
                String nom = "";
                if (jeu != null) {
                    nom = String.valueOf(jeu.getLibelle());
                }
                return new SimpleStringProperty(nom);
            });            tournoiTable.setItems(data);
            
            tfPrix.setText("");
        cmbJeu.setValue("");
        tfDate.setValue(LocalDate.now());
        cmbType.setValue("Single");
        tfNom.setText("");

    }
    @FXML
    private void importImage(ActionEvent event) throws IOException //problems
    {
   
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imgTournoi.setImage(image);
        }
        catch (IOException ex) {
                System.out.print(ex);
            }
      } 
             @FXML
    void allerMatch(ActionEvent event) {
try {
            Parent root = FXMLLoader.load(getClass().getResource("matchBack.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex);        
        }
    }
    
       @FXML
    void afficherProduits(ActionEvent event) {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("../views/boutiqueBack.fxml"));
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
    void afficherJeux(ActionEvent event) {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("jeuxb.fxml"));
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
    void afficherBlogs(ActionEvent event) {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("../views/blogBack.fxml"));
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
    void afficherEquipe(ActionEvent event) {
        Parent root;
         try {
              root = FXMLLoader.load(getClass().getResource("../views/BackEquipe.fxml"));
              Scene scene = new Scene(root);
                
                Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
         } catch (IOException ex) {
             Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

}
