package edu.ggaming.Controller;

import edu.ggaming.entities.Classement;
import edu.ggaming.entities.Equipe;
import edu.ggaming.entities.Jeux;
import edu.ggaming.entities.Match;
import edu.ggaming.entities.SingletonClass;
import edu.ggaming.entities.Tournoi;
import edu.ggaming.services.MatchService;
import edu.ggaming.services.TournoiService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class TournoiSelectController implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private GridPane gridT;

    @FXML
    private ScrollPane scroll;
    
    @FXML
    private Label lblDate;
    @FXML
    private Label lblTournoiNom;
    @FXML
    private Label lblJeu;

    @FXML
    private Label lblPrix;

    @FXML
    private Label lblType;
    
    @FXML
    private TableView<Classement> tableClassement;

    @FXML
    private TableColumn<Classement, String> posCl;

    @FXML
    private TableColumn<Classement, String> equipeCl;

    private final MatchService ms=new MatchService();
    private final TournoiService ts=new TournoiService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
            Tournoi t=SingletonClass.INSTANCE.getTournoi();
            afficherClassement(t.getId());
            setData(t);
            setMatches(ms.getMatchs(t.getId()));
            setTournoiSim(ts.TournoiSimilaire(t));
    }
    public void afficherClassement(int id)
    {
            List<Classement> classementList = ms.getClassement(id);
            ObservableList<Classement> data = FXCollections.observableArrayList(classementList);
            System.out.print(data);
            equipeCl.setCellValueFactory(cellData -> {
                Equipe e = cellData.getValue().getNom_equipe();
                String nom = "";
                if (e != null) {
                    nom = String.valueOf(e.getNom_equipe());
                }
                return new SimpleStringProperty(nom);
            });
            //equipeCl.setCellValueFactory(new PropertyValueFactory<>("nom_equipe"));
            posCl.setCellValueFactory(new PropertyValueFactory<>("score"));
            tableClassement.setItems(data);
    }
    public void setMatches(List <Match> m)
    {
        int column = 0;
        int row = 1;
            for (int i = 0; i < m.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("MatchItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                MatchItemController itemController = fxmlLoader.getController();
                itemController.setData(m.get(i));
                if (column == 1) {
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
public void setTournoiSim(List <Tournoi> t)
    {   
        int column = 0;
        int row = 1;
            for (int i = 0; i < t.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("TournoiItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                TournoiItemController itemController = fxmlLoader.getController();
                itemController.setData(t.get(i));
                /*if (column == 3) {
                    column = 0;
                    row++;
                }*/

                gridT.add(anchorPane, column++, row); //(child,column,row)
                GridPane.setMargin(anchorPane, new Insets(10));

               //set grid width
                gridT.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridT.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridT.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridT.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridT.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridT.setMaxHeight(Region.USE_PREF_SIZE);

            } 
            catch (IOException ex) {
                System.out.print(ex);
            }
            } 
    
    }  

    public void setData(Tournoi tournoi)
    {
        lblDate.setText(tournoi.getDateDebut().toString());
        lblType.setText(tournoi.getTypeTournoi());
        lblPrix.setText(Integer.toString(tournoi.getPrix())+"dt");
        lblJeu.setText(tournoi.getJeu().getLibelle());
        lblTournoiNom.setText(tournoi.getNomTournoi());
    
    }
 
    
     @FXML
    void retourTournoi(MouseEvent event) {
   try {
            Parent root = FXMLLoader.load(getClass().getResource("TournoiHome.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex);        
        }

    }
    @FXML
    void afficherStat() { //on mouse clicked event
        Parent root;     
        Stage newStage = new Stage();
            // Set the properties of the new stage
            newStage.setTitle("Statistics");
                 
        try {
            root = FXMLLoader.load(getClass().getResource("Statistic.fxml"));
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException ex) {
            System.out.println(ex);
            }
    }
}
