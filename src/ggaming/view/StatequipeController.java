/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.view;

import ggaming.entity.Equipe;
import ggaming.services.EquipeService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dhia
 */
public class StatequipeController implements Initializable {

    @FXML
    private Button close;
    @FXML
    private Button minimize;
    @FXML
    private Label username;
    @FXML
    private Button home_btn;
    @FXML
    private Button produits_btn;
    @FXML
    private Button Categorie_produit_btn;
    @FXML
    private Button Tournois_btn;
    @FXML
    private Button Equipes_btn;
    @FXML
    private Button Jeux_btn;
    @FXML
    private Button Blogs_btn;
    @FXML
    private Button Statistiques_btn;
    @FXML
    private Button logout;
    @FXML
    private BarChart barChart;
    @FXML
    private NumberAxis numberAxis;
    @FXML
    private CategoryAxis categoryAxis;
private boolean sponsorView = false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        /*
        XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Equipes");

    EquipeService equipeService = new EquipeService();
    for (int i = 1; i <= 11; i++) {
        int nbEquipes;
        try {
            nbEquipes = equipeService.getNbj(i);
            series.getData().add(new XYChart.Data<>(i, nbEquipes));
        } catch (SQLException ex) {
            Logger.getLogger(StatequipeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        

    }

    // Create a new BarChart object and set its data
    //barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
    barChart.getData().add(series);
*/
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
         series.setName("Prix Moyen Par Categorie");
          EquipeService equipeService = new EquipeService();
          
       for (int i = 1; i <= 11; i++) {
        int nbEquipes;
        try {
            nbEquipes = equipeService.getNbj(i);
            String strNum = Integer.toString(i);
            series.getData().add(new XYChart.Data<>(strNum, nbEquipes));
        } catch (SQLException ex) {
            Logger.getLogger(StatequipeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        

    }

        barChart.getData().add(series);
     
    }    
    

    @FXML
    private void gotoequipes(ActionEvent event) throws IOException {
          sponsorView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/view/BackEquipe.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }

  

    @FXML
    private void gotosonorr(ActionEvent event) throws IOException {
          sponsorView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/view/BackSponsor.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
    
    
}
