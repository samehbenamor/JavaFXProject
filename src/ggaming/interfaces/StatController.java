/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ggaming.interfaces;


import ggaming.entity.Jeux;

import ggaming.services.ServiceJeux;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


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
    // Create a dataset to hold the data for the chart
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    ServiceJeux sj = new ServiceJeux();
    sj.initConnection();
    List<Jeux> jeuxes = sj.afficherJeux();
    // Populate the dataset with data from the list of games
    for (Jeux j : jeuxes) {
        series.getData().add(new XYChart.Data<>(j.getLibelle(), j.getNoteMyonne()));
    }

    // Create a chart using the dataset
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
    barChart.setTitle("Evaluation des jeux");
    barChart.setLegendVisible(false);
    barChart.getData().add(series);

    // Customize the chart appearance
    barChart.setStyle("-fx-background-color: transparent;");

    // Customize the x-axis
    xAxis.setLabel("Games");
    xAxis.setTickLabelFont(Font.font("Arial", FontWeight.BOLD, 12));
    xAxis.setTickLabelRotation(45);
    xAxis.setTickLength(0);

    // Customize the y-axis
    yAxis.setLabel("Notemyonne");
    yAxis.setTickLabelFont(Font.font("Arial", FontWeight.BOLD, 12));
    yAxis.setTickLength(0);

    // Customize the chart plot area
    barChart.setAnimated(false);
    barChart.setHorizontalGridLinesVisible(false);
    barChart.setVerticalGridLinesVisible(false);
    barChart.setVerticalZeroLineVisible(false);
    barChart.setLegendVisible(false);

    // Add the chart to the pane
    Panejeux.getChildren().add(barChart);
    } catch (Exception e) {
  e.printStackTrace();
}
  

}




    @FXML
    void affjeux(ActionEvent event) throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/backjeux.fxml"));
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

    

