/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.Controller;

import edu.ggaming.entities.SingletonClass;
import edu.ggaming.entities.StatTournoi;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import edu.ggaming.entities.Tournoi;
import edu.ggaming.services.MatchService;
import edu.ggaming.services.TournoiService;
import java.io.IOException;
import java.sql.SQLException;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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
    private BarChart barChart;
    
    private TournoiService ts=new TournoiService();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         XYChart.Series<String, Number> series = new XYChart.Series<>();
         series.setName("Prix Moyen Par Categorie");
         
        for (StatTournoi data : ts.getStatsType()) {
            
            series.getData().add(new XYChart.Data<>(data.getType(), data.getStat()));
           
        }

        barChart.getData().add(series);
     
    }    
    
}
