package edu.ggaming.GUI;

import edu.ggaming.entity.Match;
import edu.ggaming.entity.SingletonClass;
import edu.ggaming.entity.Tournoi;
import edu.ggaming.services.MatchService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    private Tournoi myVariable;
    private final MatchService ms=new MatchService();
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            Tournoi t=SingletonClass.INSTANCE.getTournoi();
            setData(t);
            setMatches(ms.getMatchs(t.getId()));
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

}
