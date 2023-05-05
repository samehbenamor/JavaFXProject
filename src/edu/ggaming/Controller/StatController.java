/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ggaming.Controller;


import edu.ggaming.entities.Jeux;

import edu.ggaming.services.ServiceJeux;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;



/**
 * FXML Controller class
 *
 * @author dell
 */
public class StatController implements Initializable {
   @FXML
private Pane Panejeux;

    private List<Jeux> jeuxes;
    private boolean catView = false;

@Override
public void initialize(URL url, ResourceBundle rb) {
  try {
    PieChart pieChart = new PieChart();
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    ServiceJeux sj = new ServiceJeux();
    sj.initConnection();
         
        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
    List<Jeux> jeuxes = sj.afficherJeux();
    for (Jeux j : jeuxes) {
        PieChart.Data data = new PieChart.Data(j.getLibelle(), j.getViews());
        data.setName(data.getName() + ": " + data.getPieValue()); 
        pieChart.getData().add(data);
        series.getData().add(new XYChart.Data<>(j.getLibelle(), j.getNoteMyonne()));
        
      
    }
      
        CategoryAxis lineXAxis = new CategoryAxis();
        NumberAxis lineYAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<>(lineXAxis, lineYAxis);
        lineChart.setTitle("Nombre de jeux ajoutés par jour");
        
    
     
    pieChart.setTitle("Nombre de vues par jeux");
    pieChart.setAnimated(false);
    pieChart.setLegendVisible(false);
    pieChart.setLabelLineLength(10); 
    pieChart.setLabelsVisible(true); 
    
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
    barChart.setTitle("Evaluation des jeux");
    barChart.setLegendVisible(false);
    barChart.getData().add(series);
    barChart.setAnimated(false);
    barChart.setHorizontalGridLinesVisible(false);
    barChart.setVerticalGridLinesVisible(false);
    barChart.setVerticalZeroLineVisible(false);
    barChart.setLegendVisible(false);

    xAxis.setLabel(" ");
    xAxis.setTickLabelFont(Font.font("Arial", FontWeight.BOLD, 12));
    xAxis.setTickLabelRotation(45);
    xAxis.setTickLength(0);
  
    yAxis.setLabel("Notemyonne");
    yAxis.setTickLabelFont(Font.font("Arial", FontWeight.BOLD, 12));
    yAxis.setTickLength(0);

    HBox chartContainer = new HBox(pieChart, barChart);
    chartContainer.setSpacing(20);
    Panejeux.getChildren().add(chartContainer);

  } catch (Exception e) {
    e.printStackTrace();
  }
}





    @FXML
    void affjeux(ActionEvent event) throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("jeuxb.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void affcat(ActionEvent event) throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("Backcat.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

       
    }
    
     
    @FXML
    
    public void afficherJeuxFront(ActionEvent event)
    {
         Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("FrontJeux2.fxml"));
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
}

    

