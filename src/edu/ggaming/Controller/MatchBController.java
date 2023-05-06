/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.Controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import edu.ggaming.entities.Equipe;
import edu.ggaming.entities.Match;
import edu.ggaming.entities.Tournoi;
import edu.ggaming.services.MatchService;
import edu.ggaming.services.TournoiService;
import ggaming2.Global;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author hayth
 */
public class MatchBController implements Initializable {
    MatchService ms;
    @FXML
    private TableView<Match> matchTable;
    @FXML
    private TableColumn<Match, String> tcTour;
    @FXML
    private TableColumn<Match, String> tcEquipeExt;
    @FXML
    private TableColumn<Match, String> tcEquipeDom;
    @FXML
    private TableColumn<Match, String> tcScoreDom;
    @FXML
    private TableColumn<Match, String> tcScoreExt;
    @FXML
    private TableColumn<Match, String> tcTournoi;
   
    @FXML
    private TextField tournoiSearch;
    @FXML
    private ComboBox <String> cmbTour;
    @FXML
    private ComboBox <String> cmbTournoi;
    @FXML
    private TextField tfScoreDom; 
     @FXML
    private Button sessionlogout;
    @FXML
    private TextField tfScoreExt; 
    @FXML
    private ComboBox <String>  cmbEquipeDom; 
    @FXML
    private ComboBox <String>  cmbEquipeExt;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            ms= new MatchService();
            List<Match> matchList = ms.afficherMatch();
            ObservableList<Match> data = FXCollections.observableArrayList(matchList);
            tcTour.setCellValueFactory(new PropertyValueFactory<>("tour"));
            tcScoreDom.setCellValueFactory(new PropertyValueFactory<>("score1"));
            tcScoreExt.setCellValueFactory(new PropertyValueFactory<>("score2"));
            tcEquipeDom.setCellValueFactory(cellData -> {
                Equipe equipe = cellData.getValue().getEquipe1();
                String nom = "";
                if (equipe != null) {
                    nom = String.valueOf(equipe.getNom_equipe());
                }
                return new SimpleStringProperty(nom);
            });
            tcEquipeExt.setCellValueFactory(cellData -> {
                Equipe equipe = cellData.getValue().getEquipe2();
                String nom = "";
                if (equipe != null) {
                    nom = String.valueOf(equipe.getNom_equipe());
                }
                return new SimpleStringProperty(nom);
            });
            tcTournoi.setCellValueFactory(cellData -> {
                Tournoi tournoi = cellData.getValue().getTournoi();
                String nom = "";
                if (tournoi != null) {
                    nom = String.valueOf(tournoi.getNomTournoi());
                }
                return new SimpleStringProperty(nom);
            });
            matchTable.setItems(data);
            
            
            ObservableList<String> options = FXCollections.observableArrayList(
                "Stage de Groupe",
                "Quart de Finale",
                "Demi-Finale",
                "Finale"
            );
            cmbTour.setValue("Stage de Groupe");
            cmbTour.setItems(options);
            
            ObservableList<String> optTournois = FXCollections.observableArrayList(ms.getTournoi());
            cmbTournoi.setItems(optTournois);
            
            
                        
            ObservableList<String> optEqpExt = FXCollections.observableArrayList(ms.getEquipe());
            cmbEquipeExt.setItems(optEqpExt);
            
                        
            ObservableList<String> optEqpDom = FXCollections.observableArrayList(ms.getEquipe());
            cmbEquipeDom.setItems(optEqpDom);

        matchTable.setRowFactory( tv -> {
        TableRow<Match> myRow = new TableRow<>();
        myRow.setOnMouseClicked (event ->
        {
           if (event.getClickCount() == 1 && (!myRow.isEmpty()))
           {
              int myIndex =  matchTable.getSelectionModel().getSelectedIndex();
              cmbTour.setValue(matchTable.getItems().get(myIndex).getTour());
              cmbTournoi.setValue(matchTable.getItems().get(myIndex).getTournoi().getNomTournoi());
              cmbEquipeExt.setValue(matchTable.getItems().get(myIndex).getEquipe2().getNom_equipe());
              cmbEquipeDom.setValue(matchTable.getItems().get(myIndex).getEquipe1().getNom_equipe());

              int scoreDom= Integer.parseInt(String.valueOf(matchTable.getItems().get(myIndex).getScore1()));
              tfScoreDom.setText(Integer.toString(scoreDom));

              int scoreExt= Integer.parseInt(String.valueOf(matchTable.getItems().get(myIndex).getScore2()));              
              tfScoreExt.setText(Integer.toString(scoreExt));
              
           }
        });
           return myRow;
        });             
    }    
    @FXML
    private void addMatch()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("");
        Boolean test,test2;
        try {
            Integer.parseInt(tfScoreDom.getText());
            test=true;
        } catch (NumberFormatException e) {
            test=false;        
        }
        try {
            Integer.parseInt(tfScoreExt.getText());
            test2=true;
        } catch (NumberFormatException e) {
            test2=false;        
        }
        if (tfScoreExt.getText().isEmpty() || tfScoreDom.getText().isEmpty() ||cmbEquipeDom.getValue().isEmpty()||
            cmbEquipeExt.getValue().isEmpty() ||cmbTour.getValue().isEmpty()|| cmbTournoi.getValue().isEmpty()){
		alert.setContentText("Remplir tous les champs");
                alert.showAndWait();
        }
        else if(!test){
           alert.setContentText("Veuillez saisir un entier comme Score");
           alert.showAndWait(); 
        }
        else if(!test2){
           alert.setContentText("Veuillez saisir un entier comme Score");
           alert.showAndWait(); 
        }        
        else if(cmbEquipeDom.getValue().equals(cmbEquipeExt.getValue()))
        {
           alert.setContentText("Veuillez saisir des equipes differentes");
           alert.showAndWait(); 
        }
        else{
        String scoreExt= tfScoreExt.getText();  //ca
        String scoreDom= tfScoreDom.getText(); //ca
        String tour = cmbTour.getValue(); 
        String tournoi = cmbTournoi.getValue(); //ca
        String equipeExt = cmbEquipeExt.getValue(); //ca
        String equipeDom= cmbEquipeDom.getValue(); //ca

        int scExt= Integer.parseInt(scoreExt);
        int scDom= Integer.parseInt(scoreDom);


        Tournoi trnoi=ms.findTournoiByNom(tournoi);
        Equipe eqpDom=ms.findEquipeByNom(equipeDom);
        Equipe eqpExt=ms.findEquipeByNom(equipeExt);
        ms.ajouterMatch(new Match(tour, scDom, scExt, eqpDom,eqpExt, trnoi));
        refreshtable();
        alert.setContentText("Ajouté avec Succes");
        alert.showAndWait();
        }
    }
    @FXML
    private void supprimerMatch()
    {  Match m=matchTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("");
        Alert alertConf = new Alert(Alert.AlertType.WARNING,"Etes-vous sur de vouloir proceder",ButtonType.YES);
        alertConf.setTitle("Attention !");
        if(m==null)
        {
            
        alert.setContentText("Rien Selectionné");
        alert.showAndWait();
        }
        else{
             Optional<ButtonType> result = alertConf.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
        ms.supprimerMatch(m);
        refreshtable();
        alert.setContentText("Supprimé avec Succes");
        alert.showAndWait();}
        }
        
    }
    @FXML
    private void modifierMatch()
    {
        Match m=matchTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("");
        Alert alertConf = new Alert(Alert.AlertType.WARNING,"Etes-vous sur de vouloir proceder",ButtonType.YES);
        alertConf.setTitle("Attention !");
        Boolean test,test2;

        try {
            Integer.parseInt(tfScoreDom.getText());
            test=true;
        } catch (NumberFormatException e) {
            test=false;        
        }
        try {
            Integer.parseInt(tfScoreExt.getText());
            test2=true;
        } catch (NumberFormatException e) {
            test2=false;        
        }
        if(m==null)
        {
            
        alert.setContentText("Rien Selectionné");
        alert.showAndWait();
        }
        else if (tfScoreExt.getText().isEmpty() || tfScoreDom.getText().isEmpty() ||cmbEquipeDom.getValue().isEmpty()||
            cmbEquipeExt.getValue().isEmpty() ||cmbTour.getValue().isEmpty()|| cmbTournoi.getValue().isEmpty()){
		alert.setContentText("Remplir tous les champs");
                alert.showAndWait();
        }
        else if(!test){
           alert.setContentText("Veuillez saisir un entier comme prix");
           alert.showAndWait(); 
        }
        else if(!test2){
           alert.setContentText("Veuillez saisir un entier comme prix");
           alert.showAndWait(); 
        }
        else if(cmbEquipeDom.getValue().equals(cmbEquipeExt.getValue()))
        {
           alert.setContentText("Veuillez saisir des equipes differentes");
           alert.showAndWait(); 
        }
        else{
        String scoreExt= tfScoreExt.getText();  //ca
        String scoreDom= tfScoreDom.getText(); //ca
        String tour = cmbTour.getValue(); 
        String tournoi = cmbTournoi.getValue(); //ca
        String equipeExt = cmbEquipeExt.getValue(); //ca
        String equipeDom= cmbEquipeDom.getValue(); //ca

        int scExt= Integer.parseInt(scoreExt);
        int scDom= Integer.parseInt(scoreDom);
        
        Tournoi trnoi=ms.findTournoiByNom(tournoi);
        Equipe eqpDom=ms.findEquipeByNom(equipeDom);
        Equipe eqpExt=ms.findEquipeByNom(equipeExt);
        
        m.setTour(tour);
        m.setEquipe1(eqpDom);
        m.setEquipe2(eqpExt);
        m.setScore1(scDom);
        m.setScore2(scExt);
        m.setTournoi(trnoi);
         Optional<ButtonType> result = alertConf.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
        ms.modifierMatch(m,m.getId());
        refreshtable();
        alert.setContentText("Modifié avec Succes");
        alert.showAndWait();}
        }
    }
    @FXML
    private void annulerMatch()
    { 
        cmbTour.setValue("Stage de Groupe");
        cmbTournoi.setValue("");
        tfScoreDom.setText("");
        tfScoreExt.setText("");
        cmbEquipeDom.setValue("");
        cmbEquipeExt.setValue("");
       matchTable.getSelectionModel().clearSelection();

    }   
    private void refreshtable()
    {

          List<Match> matchList = ms.afficherMatch();
            ObservableList<Match> data = FXCollections.observableArrayList(matchList);
            
            tcTour.setCellValueFactory(new PropertyValueFactory<>("tour"));
            
            tcScoreDom.setCellValueFactory(new PropertyValueFactory<>("score1"));
            tcScoreExt.setCellValueFactory(new PropertyValueFactory<>("score2"));
            tcEquipeDom.setCellValueFactory(cellData -> {
                Equipe equipe = cellData.getValue().getEquipe1();
                String nom = "";
                if (equipe != null) {
                    nom = String.valueOf(equipe.getNom_equipe());
                }
                return new SimpleStringProperty(nom);
            });
            tcEquipeExt.setCellValueFactory(cellData -> {
                Equipe equipe = cellData.getValue().getEquipe2();
                String nom = "";
                if (equipe != null) {
                    nom = String.valueOf(equipe.getNom_equipe());
                }
                return new SimpleStringProperty(nom);
            });
            tcTournoi.setCellValueFactory(cellData -> {
                Tournoi tournoi = cellData.getValue().getTournoi();
                String nom = "";
                if (tournoi != null) {
                    nom = String.valueOf(tournoi.getNomTournoi());
                }
                return new SimpleStringProperty(nom);
            });
matchTable.setItems(data);

        cmbTour.setValue("Stage de Groupe");
        cmbTournoi.setValue("");
        tfScoreDom.setText("");
        tfScoreExt.setText("");
        cmbEquipeDom.setValue("");
        cmbEquipeExt.setValue("");

    }
    
        @FXML
    void allerTournoi(ActionEvent event) {

try {
            Parent root = FXMLLoader.load(getClass().getResource("tournoiBack.fxml"));
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
    
    public void afficherTournois(ActionEvent event)
    {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("tournoiBack.fxml"));
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
    void afficherEquipes(ActionEvent event) {
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
     @FXML
    void afficherJoueurs(ActionEvent event) {
        Parent root;
         try {
              root = FXMLLoader.load(getClass().getResource("../../../ggaming2/boutiqueBack.fxml"));
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
    public void logoutback() throws IOException {
        // remove the session ID
        Global.sessionId = null;

        // redirect the user to the register page
        Parent root = FXMLLoader.load(getClass().getResource("../../../ggaming2/HomePage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) sessionlogout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
     
}
