/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.view;

import ggaming.entity.Equipe;
import ggaming.entity.Sponsor;
import ggaming.services.EquipeService;
import ggaming.services.SponsorService;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author dhia
 */
public class BackSponsorController implements Initializable {

    @FXML
    private AnchorPane boutique_back;
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
    private AnchorPane home_form;
    @FXML
    private Label home_totalEmployees;
    @FXML
    private Label home_totalPresents;
    @FXML
    private Label home_totalInactiveEm;
    @FXML
    private BarChart<?, ?> home_chart;
    @FXML
    private AnchorPane addEmployee_form;
    @FXML
    private TableColumn<Sponsor, Integer> collidsp;
    @FXML
    private TableColumn<Sponsor, String> collnamesp;
    @FXML
    private TableColumn<Sponsor, String> colldescsp;
    @FXML
    private TableColumn<Sponsor, String> collsitewebsp;
    @FXML
    private TableColumn<Sponsor, String> colllogoeqsp;
    @FXML
    private TableColumn<Sponsor, LocalDateTime> colldatecr;
    @FXML
    private TextField addEmployee_search;
    @FXML
    private TextField tfwebsitesp;
    @FXML
    private TextField tfid;
    @FXML
    private Button addimagesp;
    @FXML
    private ImageView logoimagesp;
    @FXML
    private Button btnaddsp;
    @FXML
    private Button btnmodifsp;
    @FXML
    private Button btndeletesp;
    @FXML
    private Button addsponsor_clearBtn;
    @FXML
    private TextArea tfDescripsp;
    @FXML
    private TextField tfnamesp;
    @FXML
    private TextField tfidsp;
    @FXML
    private AnchorPane salary_form;
    @FXML
    private TextField salary_employeeID;
    @FXML
    private Label salary_firstName;
    @FXML
    private Label salary_lastName;
    @FXML
    private Label salary_position;
    @FXML
    private TextField salary_salary;
    @FXML
    private Button salary_updateBtn;
    @FXML
    private Button salary_clearBtn;
    @FXML
    private TableView<?> salary_tableView;
    @FXML
    private TableColumn<?, ?> salary_col_employeeID;
    @FXML
    private TableColumn<?, ?> salary_col_firstName;
    @FXML
    private TableColumn<?, ?> salary_col_lastName;
    @FXML
    private TableColumn<?, ?> salary_col_position;
    @FXML
    private TableColumn<?, ?> salary_col_salary;
    @FXML
    private TableView<Sponsor> tableSponsor;

     private ObservableList<Sponsor> sponsorData;
    private Connection con;
    String query = null;
    //Connection connection = null ;
    private SponsorService serviceSponsor;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Sponsor sponsor = null ;
    ObservableList<Sponsor> sponsorList = FXCollections.observableArrayList();
private int selectedSponsorId;
    int index=-1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         serviceSponsor = new SponsorService();
         serviceSponsor.initConnection();
        //loadDataSponsor();
        tableSponsor.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // Get the id of the selected Jeux item
            selectedSponsorId = newSelection.getId();
            // Populate the input fields with the selected Jeux data
            tfnamesp.setText(newSelection.getNom_sponsor());
            tfDescripsp.setText(newSelection.getDescription_sponsor());
            
            tfwebsitesp.setText(newSelection.getSite_webs());
            
            
           tfidsp.setText(Integer.toString(newSelection.getId()));
        }
    });
    }    

    @FXML
    private void addlogosp(ActionEvent event) {
    }
    /*
private void loadDataSponsor() {
        try {
            refreshTable();
            collid.setCellValueFactory(new PropertyValueFactory<>("id"));
            collnameeq.setCellValueFactory(new PropertyValueFactory<>("nom_equipe"));
            colldesceq.setCellValueFactory(new PropertyValueFactory<>("description_equipe"));
            collnbjeq.setCellValueFactory(new PropertyValueFactory<>("nb_joueurs"));
            collsiteweb.setCellValueFactory(new PropertyValueFactory<>("site_web"));
            colllogoeq.setCellValueFactory(new PropertyValueFactory<>("logo_equipe"));
            colldatecr.setCellValueFactory(new PropertyValueFactory<>("date_creation;"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    @FXML
    private void savesp(MouseEvent event) { 
        /*
         String nom_sponsor= tfnameeq.getText();
    String description_sponsor=tfDescripeq.getText();
    String site_web=tfwebsite.getText();
    String logo_equipe=logoimage.getImage().toString();
    java.sql.Date date_creation = java.sql.Date.valueOf(datec.getValue());
    Equipe p = new Equipe(nom_equipe, description_equipe, logo_equipe, site_web, nb_joueurs,date_creation);
    
    EquipeService es = new EquipeService();
    es.insert(p);
        */
    }

    @FXML
    private void modifiersponsor(ActionEvent event) {
    }

    @FXML
    private void suppsponsor(ActionEvent event) {
    }

    @FXML
    private void annulersponsor(ActionEvent event) {
    }
    
}
