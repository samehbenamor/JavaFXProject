/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.Controller;

import edu.ggaming.entities.CategorieProduit;
import edu.ggaming.entities.Produit;
import edu.ggaming.services.ServiceCategorieProduit;
import edu.ggaming.services.ServiceProduit;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * FXML Controller class
 *
 * @author balla
 */
public class StatistiquesBoutiqueController implements Initializable {

    
     @FXML
    private AnchorPane boutique_back;
     
     @FXML
    private AnchorPane boutique_front;
       @FXML
    private AnchorPane page_produit;
      
       @FXML
    private AnchorPane page_categorie_produit;
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private  CategoryAxis categoryAxis;

    @FXML
    private NumberAxis numberAxis;
 @FXML
    private ComboBox<String> categorie_produit;
   @FXML
    private TableColumn<?, ?> tcid_categorie;
    @FXML
    private TableColumn<?, ?> tcnom_categorie;
    @FXML
    private TableColumn<?, ?> tcref_categorie;
    @FXML
    
    private TableView<Produit> addProduit_tableView;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
         XYChart.Series<String, Number> series = new XYChart.Series<>();
         series.setName("Products");
         ServiceCategorieProduit cp=new ServiceCategorieProduit();
         ServiceProduit sp=new ServiceProduit();
         ArrayList<CategorieProduit> listCategorie=cp.afficherCategorie();
         
        for (CategorieProduit category : listCategorie) {
            
            int numProducts;
             try {
                 numProducts = sp.getNumProductsByCategory(category.getId());
                 series.getData().add(new XYChart.Data<>(category.getNom(), numProducts));
             } catch (SQLException ex) {
                 Logger.getLogger(StatistiquesBoutiqueController.class.getName()).log(Level.SEVERE, null, ex);
             }
           
        }

        barChart.getData().add(series);
    }    
    
      @FXML
    public void afficherBoutique(ActionEvent event)
     {
              
              Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("../views/boutique.fxml"));
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
     public void afficher_produit(ActionEvent event)
     {
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
     
       public void show_produit()
    {
          ServiceProduit service=new ServiceProduit();
          ObservableList<Produit> list = service.getall();
          System.out.print(list);
          tcid_categorie.setCellValueFactory(new PropertyValueFactory<>("id"));
          tcref_categorie.setCellValueFactory(new PropertyValueFactory<>("description"));
          tcnom_categorie.setCellValueFactory(new PropertyValueFactory<>("nom"));
          
          addProduit_tableView.setItems(list);
          List<String> listG = new ArrayList<>();
          ServiceCategorieProduit scp= new ServiceCategorieProduit();
         ObservableList<CategorieProduit> listCategorie = scp.getall();
        for (CategorieProduit cp : listCategorie) {
            listG.add(cp.getNom());
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        categorie_produit.setItems(listData);
    }
       @FXML
    public void StatistiqueProduit(ActionEvent event)
    {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("../views/statistiquesBoutique.fxml"));
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
