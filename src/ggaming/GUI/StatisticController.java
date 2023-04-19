/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.GUI;

import ggaming.entity.SingletonClass;
import ggaming.entity.StatTournoi;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ggaming.entity.Tournoi;
import ggaming.services.MatchService;
import ggaming.services.TournoiService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hayth
 */
public class StatisticController implements Initializable {
    @FXML
    private PieChart pieChart;
    
    private TournoiService ts=new TournoiService();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int total=ts.getTotalType();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (StatTournoi data : ts.getStatsType()) {
            pieChartData.add(new PieChart.Data(data.getType(),(data.getStat()*100)/ts.getTotalType()));
            // Set a value of 1 for each data point
       // pieChartData.get
        }
        pieChartData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " " , data.pieValueProperty(),"%")));
        
// Create the pie chart
        pieChart.setTitle("Tournois Les Plus Populaires");
        Label title = (Label) pieChart.lookup(".chart-title");
        title.setStyle("-fx-text-fill: white;");
        pieChart.getData().addAll(pieChartData);
     
    }    
    
}
