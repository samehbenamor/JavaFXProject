/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.view;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import ggaming.cnx.MyConnection;
import ggaming.entity.Equipe;
import ggaming.entity.Sponsor;
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
import java.time.LocalDateTime;
import java.util.List;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import static org.apache.http.client.methods.RequestBuilder.options;

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
    private TableColumn<Equipe, Date> colldatecr;
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
 private boolean issponsorView = false;
    int index=-1;
    private TextField tfideq;
    @FXML
    private TableColumn<Equipe, LocalDateTime> colldatecreq;
    @FXML
    private Button Sponsor_btn;
    @FXML
    private Button btnqr;
    @FXML
    private Button btnsms;
    @FXML
    private TextField Recherche;
    @FXML
    private TextField urlpathimage;
    private TextField equipesearch;
    private Button searchbtn;
    @FXML
    private TextField Recherche1;
    @FXML
    private Button stat_btn;
    @FXML
    private ComboBox<String>trie;

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
             urlpathimage.setText(newSelection.getLogo_equipe());
            
            tfideq.setText(Integer.toString(newSelection.getId()));
            
             searchbtn.setOnAction(this::searchequipe);
        }
    });
        
         ObservableList<String> options = FXCollections.observableArrayList("Par nom", "Par nb joueurs","Par date ");
       trie.setItems(options);
      trie.valueProperty().addListener((obs, oldVal, newVal) -> {
    // effectuer la requête de tri correspondante ici
    String selectedValue = trie.getValue();
 
    
     switch(selectedValue) {
        case "Par nom":
       ObservableList<Equipe> pourcentageliste = (ObservableList<Equipe>) serviceEquipe.ordredbynom();
     tableEquipe.setItems(pourcentageliste);
            break;
        case "Par nb joueurs":
            ObservableList<Equipe> dateliste = (ObservableList<Equipe>) serviceEquipe.ordredbynbj();
      tableEquipe.setItems(dateliste);
          
            break;
            case "Par date":
            ObservableList<Equipe> finliste = (ObservableList<Equipe>) serviceEquipe.ordredbydate();
     tableEquipe.setItems(finliste);
          
            break;
        // ajouter d'autres cas pour d'autres options de tri si nécessaire
        default:
            // tableEquipe.setItems(equipeList.getPersons());
            break;
    }
    
});
    }    

    @FXML
    private void save(MouseEvent event) {
        
         String nom_equipe= tfnameeq.getText().trim();
    String description_equipe=tfDescripeq.getText().trim();
   String nb_joueurss =tfnbrj.getText().trim();
    String site_web=tfwebsite.getText().trim();
    String logo_equipe=urlpathimage.getText().trim();
    
       if (nom_equipe.isEmpty()) {
            tfnameeq.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le nom d equipe");
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
       
        
         
        
         
          if(site_web.isEmpty()){
            tfwebsite.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le site web de l equipe ");
            alert.showAndWait();
            return;
        }
          
           if(logo_equipe.isEmpty()){
            urlpathimage.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le logo de lequipe ");
            alert.showAndWait();
            return;
        }
          
             if (nom_equipe.length() > 15) {
            //showAlert("Nom invalide! Il doit être composé de lettres seulement et ne doit pas dépasser 15 caractères.");
             tfnameeq.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("le nom d equipe ne doit pas dépasser 15 caractères");
            alert.showAndWait();
            return;
        }
             
               if (!nom_equipe.matches("[a-zA-Z]+")) {
            //showAlert("Nom invalide! Il doit être composé de lettres seulement et ne doit pas dépasser 15 caractères.");
             tfnameeq.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("le nom d equipe  doit pas dépasser être composé de lettres seulement");
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
            int nb_joueurs;
       try {
    nb_joueurs = Integer.parseInt(nb_joueurss);
    if (nb_joueurs > 6) {
        tfnbrj.setStyle("-fx-border-color: red");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Le nombre de joueurs ne peut pas dépasser 6");
        alert.showAndWait();
        return;
    }
} catch (NumberFormatException e) {
    tfnbrj.setStyle("-fx-border-color: red");
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("Veuillez remplir le nombre de joueurs");
    alert.showAndWait();
    return;
}
        
           
     LocalDateTime currentDatee = LocalDateTime.now();
    Equipe p = new Equipe(nom_equipe, description_equipe, logo_equipe, site_web, nb_joueurs,currentDatee);
    
    EquipeService es = new EquipeService();
    es.ajouterEquipe(p);
    refreshTableE();
    annulerequipe();
    }

    @FXML
    private void addlogo(ActionEvent event) throws IOException {
       
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
                logoimage.setImage(image);
                String imageUrl = file.toURI().toString();
                urlpathimage.setText(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (logoimage.getImage() != null) {
            String imageUrl = logoimage.getImage().impl_getUrl();
            urlpathimage.setText(imageUrl);
        }
        
        
        
    }
    
    private void loadDataEquipe() {
        try {
            refreshTableE();
           // collid.setCellValueFactory(new PropertyValueFactory<>("id"));
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
         urlpathimage.setText("");
    }
    
    
    @FXML
   private void modifierequipe() {
         
              Alert alert;
               EquipeService service=new EquipeService();
      String idstringg = tfideq.getText();
            String nom_equipe = tfnameeq.getText();
            String description_equipe = tfDescripeq.getText();
            String site_web = tfwebsite.getText();
          int nb_joueurs=Integer.parseInt(tfnbrj.getText());

            int id = Integer.parseInt(idstringg);
            
            
               alert = new Alert(Alert.AlertType.CONFIRMATION);
                  alert.setTitle("Cofirmation Message");
                  alert.setHeaderText(null);
                alert.setContentText("Etes vous sûr de modifier l equipe ID " + id + "?");
                Optional<ButtonType> option = alert.showAndWait();
               Equipe p = new Equipe(id,nom_equipe,description_equipe,site_web,nb_joueurs);
                 if (option.get().equals(ButtonType.OK)) {
                     
                       service.modifierEquipe(p,id);
                       annulerequipe();
                        loadDataEquipe();//mise à jour de la table
                             
                 }
                 else
                 {
                     annulerequipe();//vidre les champs des textFields
                 }
        
/*
            Equipe p = new Equipe(id,nom_equipe);
        
            EquipeService es = new EquipeService();
            es.initConnection();
            es.modifierEquipe(p,id);
            
            refreshTableE();
           
        }catch(Exception e){
        }
        */
    }

    @FXML
    private void gotosponsors(ActionEvent event) 
        throws IOException {
        issponsorView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/view/BackSponsor.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

       
    }

    @FXML
    private void qr(MouseEvent event) {
         Stage qrStage = new Stage();
            Equipe p;
        
        p=tableEquipe.getSelectionModel().getSelectedItem();
        EquipeService pd=new EquipeService();
        pd.Qr(qrStage,p);
        
    }
    
    public static final String ACCOUNT_SID = "ACb6fcb2f62a595a70a00cc01d6f2825fa";
  public static final String AUTH_TOKEN = "0efed6146a89854a1da7fad48ae3142c";

    @FXML
    private void sms(MouseEvent event) {
        
        EquipeService pd = new EquipeService();
      //  populateTable((ObservableList<Promotion>) pd.SUPPRIME());
        tableEquipe.refresh();
        
        Equipe p;
        p=tableEquipe.getSelectionModel().getSelectedItem();
        String promo="l equipe sous le nom de  "+p.getNom_equipe()+" a été ajouté le "+p.getDate_creation()+" ";
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                    Message message = Message.creator(new PhoneNumber("+21623344165"),
        new PhoneNumber("+16076382981"),promo).create();
        
    }

    @FXML
    private void Recherche(KeyEvent event) {EquipeService pd = new EquipeService();
        String chaine =Recherche.getText();
         populateTable( pd.GetBynomequipe(chaine));}
         private void populateTable(ObservableList<Equipe> branlist) {
        tableEquipe.setItems(branlist);}
    
          /* EquipeService pd = new EquipeService();
        String chaine =search.getText();
         populateTable( pd.GetByPourcentage(chaine));
    }
    
     private void populateTable(ObservableList<Equipe> branlist) {
        tableEquipe.setItems(branlist);}*/
private void updateTableView(String searchTerm) {
       EquipeService equipeService = new EquipeService();
List<Equipe> searchResults = equipeService.searchEquipe(searchTerm); // Call your search function
        tableEquipe.getItems().clear(); // Clear existing items in the table view

        tableEquipe.setItems(FXCollections.observableArrayList(searchResults)); // Add the search results to the table view
    }
    private void searchequipe(ActionEvent event) {
        
          Button searchButton = searchbtn;

        String searchTerm = equipesearch.getText();
        updateTableView(searchTerm);

        // Add an event listener that changes the background color of the search button when the mouse hovers over it
        searchButton.setOnMouseEntered(e -> {

            searchButton.setStyle("-fx-background-color: yellow;");

        });

        // Add an event listener that resets the background color of the search button when the mouse exits
        searchButton.setOnMouseExited(e -> {

            searchButton.setStyle("-fx-background-color: transparent;");

        });
    }

    @FXML
    private void Recherchenb (KeyEvent event) {EquipeService pdd = new EquipeService();
        
         String chaine =Recherche1.getText();
         populateTable( pdd.GetBynbequipe(chaine));}
    
         private void populateTablee(ObservableList<Equipe> branlist) {
        tableEquipe.setItems(branlist);}

    @FXML
    private void gotostat(ActionEvent event)  throws IOException {
        issponsorView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/view/statequipe.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
        
        
        
    }
        
    
  
    }
     
    
     
    
        
