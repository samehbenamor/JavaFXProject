/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.GUI;

import javafx.geometry.Insets;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tn.esprit.entity.Jeux;
import tn.esprit.entity.Tournoi;
import tn.esprit.services.TournoiService;
import tn.esprit.tools.MaConnection;

/**
 * FXML Controller class
 *
 * @author hayth
 */
public class TournoiHomeController implements Initializable {
    
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label description;

    @FXML
    private ImageView fruitImg;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    private List<Tournoi> lt = new ArrayList<>();
    private TournoiService ts;
    private Connection cnx = MaConnection.getInstance().getCnx();

    public Jeux findJeuById(int id) { //function in services
        try {
            String sql = "select * from jeux where id = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1,id);
            ResultSet s = ste.executeQuery(); //had to remove ste here why tho
            while (s.next()) { //I need next to go through data
                Jeux t = new Jeux(
                s.getInt("id"),
                s.getString("libelle"));
                System.out.println(t);
                return t;
        }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return new Jeux(); //what should i put this    
    }
    private List <Tournoi> tournoiAdded(){ //function in services
    ArrayList<Tournoi> result = new ArrayList<>();
        try {
            String sql = "select * from tournoi";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {

                Tournoi r = new Tournoi(
                s.getInt("id"),
                s.getInt("participant_total"),
                s.getString("nom_tournoi"),
                findJeuById(s.getInt("jeu_id")),
                s.getInt("prix"),
                s.getString("type_tournoi"),
                s.getString("image_tournoi"),
                s.getDate("date_debut"));
                //System.out.println(r);
                result.add(r);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lt.addAll(tournoiAdded());     
        int column = 0;
        int row = 1;
            for (int i = 0; i < lt.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("TournoiItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                TournoiItemController itemController = fxmlLoader.getController();
                itemController.setData(lt.get(i));
                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                GridPane.setMargin(anchorPane, new Insets(10));

               //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

            } 
            catch (IOException ex) {
                System.out.print(ex);
            }
            }
        
            
    }

   
    
}
