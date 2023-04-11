/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.view;

import ggaming.entity.Equipe;
import ggaming.services.EquipeService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author dhia
 */
public class BackEquipeController implements Initializable {

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
    private TableView<?> addProduit_tableView;
    @FXML
    private TableColumn<?, ?> collid;
    @FXML
    private TableColumn<?, ?> collnameeq;
    @FXML
    private TableColumn<?, ?> colldesceq;
    @FXML
    private TableColumn<?, ?> collnbjeq;
    @FXML
    private TableColumn<?, ?> collsiteweb;
    @FXML
    private TableColumn<?, ?> colllogoeq;
    @FXML
    private TableColumn<?, ?> colldatecr;
    @FXML
    private TextField addEmployee_search;
    @FXML
    private TextField tfnameeq;
    @FXML
    private TextField tfwebsite;
    @FXML
    private TextField tfnbrj;
    @FXML
    private TextField tfid;
    @FXML
    private Button addimage;
    @FXML
    private ImageView logoimage;
    @FXML
    private Button btnaddeq;
    @FXML
    private Button btnmodifeq;
    @FXML
    private Button btndeleteeq;
    @FXML
    private Button addEmployee_clearBtn;
    @FXML
    private TextArea tfDescripeq;
    @FXML
    private DatePicker datec;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save(MouseEvent event) {
        
         String nom_equipe= tfnameeq.getText();
    String description_equipe=tfDescripeq.getText();
    int nb_joueurs=Integer.parseInt(tfnbrj.getText());
    String site_web=tfwebsite.getText();
    String logo_equipe=logoimage.getImage().toString();;
    java.sql.Date date_creation = java.sql.Date.valueOf(datec.getValue());
    Equipe p = new Equipe(nom_equipe, description_equipe, logo_equipe, site_web, nb_joueurs,date_creation);
    
    EquipeService es = new EquipeService();
    es.insert(p);
    }

    @FXML
    private void addlogo(ActionEvent event) throws IOException {
        
        FileChooser Chooser = new FileChooser();
        
        FileChooser.ExtensionFilter exxFilterJPG= new FileChooser.ExtensionFilter("JPG files (*.jpg)","*.JPG");
        FileChooser.ExtensionFilter exxFilterPNG= new FileChooser.ExtensionFilter("PNG files (*.png)","*.PNG");
        
        Chooser.getExtensionFilters().addAll(exxFilterJPG,exxFilterPNG);
        File file = Chooser.showOpenDialog(null);
        BufferedImage bufferedimg = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedimg, null);
        logoimage.setImage(image);
        
        
        
        
        
        
    }
    
}
