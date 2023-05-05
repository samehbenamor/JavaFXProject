/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ggaming.Controller;

import edu.ggaming.utils.MyConnection;
import edu.ggaming.entities.Equipe;
import edu.ggaming.entities.Sponsor;
import edu.ggaming.services.EquipeService;
import edu.ggaming.services.SponsorService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

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
    private TableColumn<Sponsor, Integer> collidsp;
    @FXML
    private TableColumn<Sponsor, String> collnamesp;
    @FXML
    private TableColumn<Sponsor, String> colldescsp;
    @FXML
    private TableColumn<Sponsor, String> collsitewebsp;
    @FXML
    private TableColumn<Sponsor, String> colllogoeqsp;
    private TableColumn<Sponsor, LocalDateTime> colldatecr;
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
     private boolean sponsorView = false;
private int selectedSponsorId;
    int index=-1;
    SponsorService sss;
    @FXML
    private ComboBox<String> comboboxsp;
    @FXML
    private TableColumn<Sponsor, LocalDateTime> colldatecrsp;
    @FXML
    private TableColumn<Sponsor, String> colleqsp;
    @FXML
    private TextField urlpathimagee;
    @FXML
    private Button qrcode;
    @FXML
    private TextField Recherche1;
    /**
     * Initializes the controller class.
     */






    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sss= new SponsorService();
        List<Sponsor> sponsorList = sss.displayAlll();
        ObservableList<Sponsor> data = FXCollections.observableArrayList(sponsorList);
         serviceSponsor = new SponsorService();
         serviceSponsor.initConnection();
        loadDataSponsor();
         colleqsp.setCellValueFactory(cellData -> {
                Equipe equipe = cellData.getValue().getEquipe();
                String nom = "";
                if (equipe != null) {
                    nom = String.valueOf(equipe.getNom_equipe());
                }
                return new SimpleStringProperty(nom);
            });
          ObservableList<String> optEqpExt = FXCollections.observableArrayList(sss.getEquipe());
             comboboxsp.setItems(optEqpExt);
              tableSponsor.setItems(data);
        tableSponsor.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // Get the id of the selected Jeux item
            selectedSponsorId = newSelection.getId();
            // Populate the input fields with the selected Jeux data
            tfnamesp.setText(newSelection.getNom_sponsor());
            tfDescripsp.setText(newSelection.getDescription_sponsor());
            
            tfwebsitesp.setText(newSelection.getSite_webs());
            urlpathimagee.setText(newSelection.getLogo_sponsor());
            
           tfidsp.setText(Integer.toString(newSelection.getId()));
        }
        
        
    });
    }    

    @FXML
    private void addlogosp(ActionEvent event) throws IOException {
      
       
          FileChooser chooser = new FileChooser();

        FileChooser.ExtensionFilter exxFilterJPG = new FileChooser.ExtensionFilter("JPG files (.jpg)", "*.jpg");
        FileChooser.ExtensionFilter exxFilterPNG = new FileChooser.ExtensionFilter("PNG files (.png)", "*.png");
        chooser.getExtensionFilters().addAll(exxFilterJPG, exxFilterPNG);
        chooser.setInitialDirectory(new File(System.getProperty("user.home")+ "/Pictures"));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            try {
                BufferedImage bufferedimg = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedimg, null);
                 logoimagesp.setImage(image);
                String imageUrl = file.toURI().toString();
                urlpathimagee.setText(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ( logoimagesp.getImage() != null) {
            String imageUrl =  logoimagesp.getImage().impl_getUrl();
            urlpathimagee.setText(imageUrl);
        }
    }
    
private void loadDataSponsor() {
        try {
            refreshTableS();
          //  collidsp.setCellValueFactory(new PropertyValueFactory<>("id"));
            collnamesp.setCellValueFactory(new PropertyValueFactory<>("nom_sponsor"));
            colldescsp.setCellValueFactory(new PropertyValueFactory<>("description_sponsor"));
            collsitewebsp.setCellValueFactory(new PropertyValueFactory<>("site_webs"));
            colllogoeqsp.setCellValueFactory(new PropertyValueFactory<>("logo_sponsor"));
            colldatecrsp.setCellValueFactory(new PropertyValueFactory<>("date_creationn"));
           colleqsp.setCellValueFactory(new PropertyValueFactory<>("id_equipe_id"));
           
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
        String nom_sponsor= tfnamesp.getText().trim();
         String description_sponsor=tfDescripsp.getText().trim();
         String logo_sponsor=urlpathimagee.getText().trim();
         String site_webs=tfwebsitesp.getText().trim();
          String equipeExt = comboboxsp.getValue();
        
            if (nom_sponsor.isEmpty()) {
            tfnamesp.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le nom du sponsor");
            alert.showAndWait();
            return;
        }
             
             
             if (description_sponsor.isEmpty()) {
            tfDescripsp.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir la description du sponsor");
            alert.showAndWait();
            return;
        }
             
             if (site_webs.isEmpty()) {
            tfwebsitesp.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le site web");
            alert.showAndWait();
            return;
        }
             
              if (equipeExt.isEmpty()) {
            comboboxsp.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le nom d equipe sponsorisée");
            alert.showAndWait();
            return;
        }
             if(logo_sponsor.isEmpty()){
            urlpathimagee.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le logo du sponsor ");
            alert.showAndWait();
            return;
        }
           if (!site_webs.matches("^(https?|ftp)://.*$")) {
        tfwebsitesp.setStyle("-fx-border-color: red");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("L'adresse du site web n'est pas valide");
        alert.showAndWait();
        return;
    }
         
  Equipe equipe = sss.findEquipeByNom(comboboxsp.getValue());
        LocalDateTime currentDate = LocalDateTime.now();
        Sponsor a = new Sponsor ( equipe ,nom_sponsor, description_sponsor, logo_sponsor, site_webs, currentDate );
        SponsorService ss = new SponsorService();
         
        sss.ajouterSponsor(a);
        
        refreshTableS();

    }

    @FXML
    private void modifiersponsor(ActionEvent event) {
         
Alert alert;
SponsorService servicee=new SponsorService();

            String idstring = tfidsp.getText();
            String nom_sponsor = tfnamesp.getText();
            String description_sponsor = tfDescripsp.getText();
            String site_webs = tfwebsitesp.getText();
            String logo_sponsor=urlpathimagee.getText().trim();

            int id = Integer.parseInt(idstring);

               alert = new Alert(Alert.AlertType.CONFIRMATION);
                  alert.setTitle("Cofirmation Message");
                  alert.setHeaderText(null);
                alert.setContentText("Etes vous sûr de modifier le sponsor ID " + id + "?");
                Optional<ButtonType> option = alert.showAndWait();
            Sponsor a = new Sponsor(id,nom_sponsor,description_sponsor,site_webs);
        
             if (option.get().equals(ButtonType.OK)) {
                     
                       servicee.modifiersponsor(a,id);
                       annulersponsor();
                       loadDataSponsor();//mise à jour de la table
                             
                 }
                 else
                 {
                     annulersponsor();//vidre les champs des textFields
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
                alert.setContentText("Etes vous sûr de supprimer le sponsor ID " + id + "?");
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
        comboboxsp.setValue("");
        logoimagesp.setImage(null);
        urlpathimagee.setText("");
    }

  

    @FXML
    private void gotostats(ActionEvent event) throws IOException  {
         sponsorView = true;

        Parent root = FXMLLoader.load(getClass().getResource("../views/statequipe.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void qr(MouseEvent event) {
        
         Stage qrStage = new Stage();
            Sponsor a;
        
       a=tableSponsor.getSelectionModel().getSelectedItem();
        SponsorService pd=new SponsorService();
        pd.Qr(qrStage,a);
    }

    @FXML
    private void Recherchesp(KeyEvent event)  {SponsorService ss = new SponsorService();
        String chaine =Recherche1.getText();
         populateTable( ss.GetBynomsponsor(chaine));}
         private void populateTable(ObservableList<Sponsor> branlist) {
        tableSponsor.setItems(branlist);}
         
         
         
       @FXML
    
    public void afficherTournois(ActionEvent event)
    {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("tournoiBack.fxml"));
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
    void afficherJeux(ActionEvent event) {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("jeuxb.fxml"));
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
    void afficherBlogsBack(ActionEvent event) {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("../views/blogBack.fxml"));
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
    void afficherEquipes(ActionEvent event) {
        Parent root;
         try {
              root = FXMLLoader.load(getClass().getResource("../views/BackEquipe.fxml"));
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
    void afficherBoutique(ActionEvent event) {
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
    
        
        
    }
    

