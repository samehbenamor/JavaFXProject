/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.view;

import ggaming.cnx.MyConnection;
import ggaming.entity.Equipe;
import ggaming.entity.Sponsor;
import ggaming.services.EquipeService;
import ggaming.services.SponsorService;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
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
    private Button Jeux_btn;
    @FXML
    private Button Blogs_btn;
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
    private ImageView logoimage;
    @FXML
    private TextArea tfDescripeq;
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
    
    
    ////////////////////////////////////////////////////
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
 
 private boolean sponsorView = false;
    int index=-1;
    
    /////////////////////////////////////////////////////////
    
   
    
    /////////////////////////////////////////////////////////
    @FXML
    private TextField tfideq;
    @FXML
    private TableColumn<Equipe, LocalDateTime> colldatecreq;
    @FXML
    private Button Equipess_btn;
    @FXML
    private Button Sponsor_btn;
    @FXML
    private Button addimage;
    @FXML
    private Button btnaddeq;
    @FXML
    private Button btnmodifeq;
    @FXML
    private Button btndeleteeq;
    @FXML
    private Button addEmployee_clearBtn;

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
        
        //////////////////////////////////////////////////////////
        /*
        serviceSponsor = new SponsorService();
         //serviceSponsor.initConnection();
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
       */ 
    }    
@FXML
    private void save(MouseEvent event) {
        
         String nom_equipe= tfnameeq.getText();
    String description_equipe=tfDescripeq.getText();
    int nb_joueurs=Integer.parseInt(tfnbrj.getText());
    String site_web=tfwebsite.getText();
    String logo_equipe=logoimage.getImage().toString();
      if (tfnameeq.getText().isEmpty() || tfDescripeq.getText().isEmpty() ||tfnbrj.getText().isEmpty()||
            tfwebsite.getText().isEmpty() ||logoimage.getImage()==null ){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("");
      
		alert.setContentText("Remplir tous les champs");
                alert.showAndWait();
                 return;
      }
      if(nom_equipe.isEmpty()){
            tfnameeq.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le nom de l equipe");
            alert.showAndWait();
            return;
        }
       if(description_equipe.isEmpty()){
            tfDescripeq.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir la description de l equipe ");
            alert.showAndWait();
            return;
        }
       
        
         
         if(logo_equipe==null){
            tfwebsite.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le site web de l equipe ");
            alert.showAndWait();
            return;
        }
         
          if(site_web.isEmpty()){
            tfwebsite.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le site web de l equipe ");
            alert.showAndWait();
            return;
        }
           if (!site_web.matches("^(https?|ftp)://.*$")) {
        tfwebsite.setStyle("-fx-border-color: red");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("L'adresse du site web n'est pas valide");
        alert.showAndWait();
        return;
    }
     LocalDateTime currentDatee = LocalDateTime.now();
    Equipe p = new Equipe(nom_equipe, description_equipe, logo_equipe, site_web, nb_joueurs,currentDatee);
    
    EquipeService es = new EquipeService();
   /* 
Equipe existingEquipe = es.findEquipeByNom(nom_equipe);
if (existingEquipe != null) {
    tfnameeq.setStyle("-fx-border-color: red");
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("Une equipe avec le même nom existe déjà");
    alert.showAndWait();
    return;
}
*/
TextField tfnameeq = new TextField();
tfnameeq.textProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue.length() > 15) {
        tfnameeq.setText(oldValue);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Le nom d'équipe ne peut pas dépasser 15 caractères !");
        alert.showAndWait();
    }
});
    es.ajouterEquipe(p);
    refreshTableE();
    annulerequipe();
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
            refreshTableE();
            collid.setCellValueFactory(new PropertyValueFactory<>("id"));
            collnameeq.setCellValueFactory(new PropertyValueFactory<>("nom_equipe"));
            colldesceq.setCellValueFactory(new PropertyValueFactory<>("description_equipe"));
            collnbjeq.setCellValueFactory(new PropertyValueFactory<>("nb_joueurs"));
            collsiteweb.setCellValueFactory(new PropertyValueFactory<>("site_web"));
            colllogoeq.setCellValueFactory(new PropertyValueFactory<>("logo_equipe"));
            colldatecreq.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    private void loadDataSponsor() {
        try {
            refreshTableS();
            collidsp.setCellValueFactory(new PropertyValueFactory<>("id"));
            collnamesp.setCellValueFactory(new PropertyValueFactory<>("nom_sponsor"));
            colldescsp.setCellValueFactory(new PropertyValueFactory<>("description_sponsor"));
            collsitewebsp.setCellValueFactory(new PropertyValueFactory<>("site_webs"));
            colllogoeqsp.setCellValueFactory(new PropertyValueFactory<>("logo_sponsor"));
            colldatecr.setCellValueFactory(new PropertyValueFactory<>("date_creationn"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
    
    
    private void refreshTableE(){
        try{
            equipeList.clear();
            query = "SELECT * FROM Equipe";
            Connection connection = MyConnection.getInstance().getCnx();
            if (connection != null) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                    
                 LocalDateTime dateCreation = resultSet.getTimestamp("date_creation").toLocalDateTime();
               
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
    private void refreshTableS(){
        try{
            sponsorList.clear();
            query = "SELECT * FROM Sponsor";
            Connection connection = MyConnection.getInstance().getCnx();
            if (connection != null) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                 LocalDateTime dateCreationn = resultSet.getTimestamp("date_creationn").toLocalDateTime();
               
                    sponsorList.add(new Sponsor(
                        resultSet.getInt("id"),
                        resultSet.getString("nom_sponsor"),
                        resultSet.getString("description_sponsor"),
                        resultSet.getString("logo_sponsor"),
                        resultSet.getString("site_webs"),
                           dateCreationn                                                                                             
                    ));
                    tableSponsor.setItems(sponsorList);
                }
            } else {
                System.out.println("Database connection is null");
            }
        }catch(SQLException ex){
            Logger.getLogger(BackEquipeController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    */
   
    @FXML
    private void suppequipe() {
               
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
    
    @FXML
    private void modifierequipe() {
         try{
      String idstringg = tfideq.getText();
            String nom_equipe = tfnameeq.getText();
            String description_equipe = tfDescripeq.getText();
            String site_web = tfwebsite.getText();
          int nb_joueurs=Integer.parseInt(tfnbrj.getText());

            int id = Integer.parseInt(idstringg);

            Equipe p = new Equipe(id,nom_equipe);
        
            EquipeService es = new EquipeService();
            es.initConnection();
            es.modifierEquipe(p);
            
            refreshTableE();
           
        }catch(Exception e){
        }
        
    }

    @FXML
    private void gotosp(ActionEvent event) throws IOException {
      sponsorView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/view/BackSponsor.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

       
    }
    
    
    

}
    







    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    @FXML
    private void addlogosp(ActionEvent event) throws IOException {
         FileChooser Chooser = new FileChooser();
        
        FileChooser.ExtensionFilter exxFilterJPG= new FileChooser.ExtensionFilter("JPG files (*.jpg)","*.JPG");
        FileChooser.ExtensionFilter exxFilterPNG= new FileChooser.ExtensionFilter("PNG files (*.png)","*.PNG");
        
        Chooser.getExtensionFilters().addAll(exxFilterJPG,exxFilterPNG);
        File file = Chooser.showOpenDialog(null);
        BufferedImage bufferedimg = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedimg, null);
        logoimagesp.setImage(image);
    }
    
    private void loadDataSponsor() {
        try {
            refreshTableS();
            collidsp.setCellValueFactory(new PropertyValueFactory<>("id"));
            collnamesp.setCellValueFactory(new PropertyValueFactory<>("nom_sponsor"));
            colldescsp.setCellValueFactory(new PropertyValueFactory<>("description_sponsor"));
            collsitewebsp.setCellValueFactory(new PropertyValueFactory<>("site_webs"));
            colllogoeqsp.setCellValueFactory(new PropertyValueFactory<>("logo_sponsor"));
            colldatecrsp.setCellValueFactory(new PropertyValueFactory<>("date_creationn"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
private void refreshTableS(){
        try{
            sponsorList.clear();
            query = "SELECT * FROM Sponsor";
            Connection connection = MyConnection.getInstance().getCnx();
            if (connection != null) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                 LocalDateTime dateCreationn = resultSet.getTimestamp("date_creationn").toLocalDateTime();
               
                    sponsorList.add(new Sponsor(
                        resultSet.getInt("id"),
                        resultSet.getString("nom_sponsor"),
                        resultSet.getString("description_sponsor"),
                        resultSet.getString("logo_sponsor"),
                        resultSet.getString("site_webs"),
                           dateCreationn                                                                                             
                    ));
                    tableSponsor.setItems(sponsorList);
                }
            } else {
                System.out.println("Database connection is null");
            }
        }catch(SQLException ex){
            Logger.getLogger(BackEquipeController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    @FXML
    private void savesp(MouseEvent event) throws SQLException {
        
         String nom_sponsor= tfnamesp.getText();
         String description_sponsor=tfDescripsp.getText();
         String logo_sponsor=logoimagesp.getImage().toString();
         String site_webs=tfwebsitesp.getText();
         EquipeService ese = new EquipeService();
         Equipe equipe = new Equipe();
          String nom_equipe= comboboxsp.getSelectionModel().getSelectedItem();
          try {
          equipe= ese.rechercherEquipeParNom(nom_equipe);
         } catch (SQLException ex) {
             Logger.getLogger(BackEquipeController.class.getName()).log(Level.SEVERE, null, ex);
         }

          
          
        if(nom_sponsor.isEmpty()){
            tfnamesp.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le nom du sponsor");
            alert.showAndWait();
            return;
        }

        LocalDateTime currentDate = LocalDateTime.now();
        Sponsor a = new Sponsor(nom_sponsor, description_sponsor, logo_sponsor, site_webs, currentDate ,equipe);
        SponsorService ss = new SponsorService();
         
        ss.ajouterSponsor(a);
        
        refreshTableS();
    }
    @FXML
    private void modifiersponsor(ActionEvent event) {
        
         try{

            String idstring = tfidsp.getText();
            String nom_sponsor = tfnamesp.getText();
            String description_sponsor = tfDescripsp.getText();
            String site_webs = tfwebsitesp.getText();
          

            int id = Integer.parseInt(idstring);

            Sponsor a = new Sponsor(id,nom_sponsor,description_sponsor,site_webs);
        
            SponsorService ss = new SponsorService();
            ss.initConnection();
            ss.modifiersponsor(a);
            
            refreshTableS();
            /*
            tfid.clear();
        tfLibelle.clear()
            
        }catch(Exception e){
        }
    }
    @FXML
    private void suppsponsor() {
        
          Alert alert;
        SponsorService service=new SponsorService();
        int id=Integer.parseInt(tfidsp.getText());
        
         alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Etes vous sûr de supprimer le produit ID " + id + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                 if (option.get().equals(ButtonType.OK)) 
                 {
                      service.supprimersponsor(id); //procéder à la suppression
                     annulersponsor();
                      loadDataSponsor(); //mise à jour de la table view
                 }
                 else
                 {
                     annulersponsor(); //vider le contenu des textFields
                 }
    }
    @FXML
    private void annulersponsor() {
        
        
          tfnamesp.setText("");
        tfDescripsp.setText("");
        
        tfwebsitesp.setText("");
        tfidsp.setText("");
        logoimagesp.setImage(null);
    }

    @FXML
    private void gotoequipes(ActionEvent event) throws IOException {
         sponsorView = false;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/view/BackEquipe.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
/*
        loadDataCommentaire();
        refreshTableC();
        
        */
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
        
    
    
    

