/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.view;

import ggaming.cnx.MyConnection;
import ggaming.entity.Equipe;
import ggaming.services.EquipeService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableView<Equipe> tableEquipe;
    @FXML
    private TableColumn<Equipe, Integer> collid;
    @FXML
    private TableColumn<Equipe, String> collnameeq;
    @FXML
    private TableColumn<Equipe, String> colldesceq;
    @FXML
    private TableColumn<Equipe, Integer> collnbjeq;
    @FXML
    private TableColumn<Equipe, String> collsiteweb;
    @FXML
    private TableColumn<Equipe, String> colllogoeq;
    @FXML
    private TableColumn<Equipe, Date> colldatecr;
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
    
    
    
    private ObservableList<Equipe> equipeData;
    private Connection con;
    String query = null;
    //Connection connection = null ;
    private EquipeService serviceEquipe;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Equipe elog = null ;
    ObservableList<Equipe>  equipeList = FXCollections.observableArrayList();
private int selectedEquipeId;
    int index=-1;
    @FXML
    private TextField tfideq;

    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         serviceEquipe = new EquipeService();
        serviceEquipe.initConnection();
        loadDataEquipe();
        tableEquipe.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // Get the id of the selected Jeux item
            selectedEquipeId = newSelection.getId();
            // Populate the input fields with the selected Jeux data
            tfnameeq.setText(newSelection.getNom_equipe());
            tfDescripeq.setText(newSelection.getDescription_equipe());
            tfnbrj.setText(Integer.toString(newSelection.getNb_joueurs()));
            tfwebsite.setText(newSelection.getSite_web());
            
            
            tfideq.setText(Integer.toString(newSelection.getId()));
        }
    });
    }    

    @FXML
    private void save(MouseEvent event) {
        
         String nom_equipe= tfnameeq.getText();
    String description_equipe=tfDescripeq.getText();
    int nb_joueurs=Integer.parseInt(tfnbrj.getText());
    String site_web=tfwebsite.getText();
    String logo_equipe=logoimage.getImage().toString();
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
    
    private void loadDataEquipe() {
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
    
    private void refreshTable(){
        try{
            equipeList.clear();
            query = "SELECT * FROM Equipe";
            Connection connection = MyConnection.getInstance().getCnx();
            if (connection != null) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                Date dateCreation = resultSet.getDate("date_creation");
               
                    equipeList.add(new Equipe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom_equipe"),
                        resultSet.getString("description_equipe"),
                        resultSet.getString("logo_equipe"),
                        resultSet.getString("site_web"),
                           
                        
                             resultSet.getInt("nb_joueurs"),
                        dateCreation
                        
                       
                    ));
                    tableEquipe.setItems(equipeList);
                }
            } else {
                System.out.println("Database connection is null");
            }
        }catch(SQLException ex){
            Logger.getLogger(BackEquipeController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    /*
    private void suppequipe(MouseEvent event) {
        
         try{

            String idstring = tfideq.getText();

            int id = Integer.parseInt(idstring);

            Equipe eq = new Equipe(id);
        
            EquipeService sb = new EquipeService();
            sb.initConnection();
            sb.deletee(eq);
            refreshTable();
            tfideq.clear();
        tfnameeq.clear();
        tfDescripeq.clear();
        tfnbrj.clear();
         tfwebsite.clear();
         
        }catch(Exception e){
        }

        
        
    }
*/
    @FXML
    private void suppequipe() {
        /*
         try{

            String idstring = tfideq.getText();

            int id = Integer.parseInt(idstring);

            Equipe eq = new Equipe(id);
        
            EquipeService sb = new EquipeService();
            sb.initConnection();
            sb.deletee(eq);
            refreshTable();
            tfideq.clear();
        tfnameeq.clear();
        tfDescripeq.clear();
        tfnbrj.clear();
         tfwebsite.clear();
         
        }catch(Exception e){
        }
*/
        
         Alert alert;
        EquipeService service=new EquipeService();
        int id=Integer.parseInt(tfideq.getText());
        
         alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Etes vous sûr de supprimer le produit ID " + id + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                 if (option.get().equals(ButtonType.OK)) 
                 {
                      service.supprimerequipe(id); //procéder à la suppression
                      annulerequipe();
                      loadDataEquipe(); //mise à jour de la table view
                 }
                 else
                 {
                     annulerequipe(); //vider le contenu des textFields
                 }
    }

    @FXML
    private void annulerequipe() {
         tfnameeq.setText("");
        tfDescripeq.setText("");
        tfnbrj.setText("");
        tfwebsite.setText("");
        tfideq.setText("");
        logoimage.setImage(null);
        
    }
    
    public boolean controleSaisie()
      {
          Alert alert;
          EquipeService sp=new EquipeService();
          if(tfnameeq.getText().isEmpty())
        {
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Veuillez saisir le nom du produit");
             alert.showAndWait();
             return false;
        }
       
       
          
          return true;
      }

    @FXML
    private void modifierequipe() {
        Alert alert;
        String nom_equipe=tfnameeq.getText();
        String description_equipe=tfDescripeq.getText();
        int nb_joueurs=Integer.parseInt(tfnbrj.getText());
        String site_web=tfwebsite.getText();
        
        String image_equipe=logoimage.getImage().toString();
        String ancien_image=logoimage.getImage().toString();
        if(controleSaisie())
        {
       
        Equipe e;
        if(image_equipe==null) //il n'a pas choisi une nouvelle image
        { e=new Equipe(nom_equipe,description_equipe,site_web,ancien_image,nb_joueurs);}
        else
        {
            e=new Equipe(nom_equipe,description_equipe,site_web,image_equipe,nb_joueurs);
        }
        
        
        EquipeService service=new EquipeService();
        int id=Integer.parseInt(tfideq.getText());
        
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Etes vous sûr de modifier le produit ID " + id + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                 if (option.get().equals(ButtonType.OK)) {
                       service.modifierEquipe(id,e);
                        loadDataEquipe();//mise à jour de la table
                     
                 }
                 else
                 {
                    annulerequipe();//vidre les champs des textFields
                 }
        }
        
    }
    
    
}
