/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ggaming.Controller;

import edu.ggaming.entities.CategorieProduit;
import edu.ggaming.entities.Joueur;
import edu.ggaming.entities.Produit;
import edu.ggaming.entities.getData;
import edu.ggaming.services.ServiceCategorieProduit;
import edu.ggaming.services.ServiceProduit;
import ggaming2.Global;
import ggaming2.SessionManager;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;


/**
 *
 * @author MarcoMan
 * Subscribe our Channel --> https://www.youtube.com/channel/UCPgcmw0LXToDn49akUEJBkQ
 * Thanks for the support guys! <3
 */
public class BoutiqueBackController  implements Initializable{

     @FXML
    private AnchorPane boutique_back;
     
     @FXML
    private AnchorPane boutique_front;
     
       @FXML
    private AnchorPane statistiques;
     
      @FXML
    private AnchorPane page_produit;
      
       @FXML
    private AnchorPane page_categorie_produit;
    @FXML
    private TextField tfNom;
    @FXML
    private Button sessionlogout;
    @FXML
    private TextField tfNom_categorie;
     @FXML
    private TextField tfid_categorie;
     
       @FXML
    private TextField produit_search;

    @FXML
    private TableView<Produit> addProduit_tableView;
    @FXML
    private TableView<CategorieProduit> categorieProduit_tableView;
    @FXML
    private TextArea tfDescription;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfQuantite;
    @FXML
    private TextField tfid;
     @FXML
    private TextField tfImage;
    
    @FXML
    private ComboBox<String> categorie_produit;
    @FXML
    private ComboBox<String> categorie_produit_filtrer;
    @FXML
    private TableColumn<?, ?> tcid;
    @FXML
    private TableColumn<?, ?> tcnom;
    @FXML
    private TableColumn<?, ?> tcdescription;
     @FXML
    private TableColumn<?, ?> tcid_categorie;
    @FXML
    private TableColumn<?, ?> tcnom_categorie;
    @FXML
    private TableColumn<?, ?> tcref_categorie;
    @FXML
    private TableColumn<?, ?> tcprix;
    @FXML
    private TableColumn<?, ?> tcquantite;
    @FXML
    private TableColumn<?,?> tcimage;
     @FXML
    private TableColumn<?,?> tccreation;
     @FXML
    private ImageView produit_image;
     
      @FXML
    private BarChart<String, Number> barChart;
      
      @FXML
      private Label sessionname;

    @FXML
    private  CategoryAxis categoryAxis;

    @FXML
    private NumberAxis numberAxis;
    
    
     byte [] post_image = null;
   
    @FXML
    private Button addProduit_addBtn;
 private Image image;
 
 
 
 
      @FXML
     public void addProduitAdd() {
        
         Alert alert;
        ServiceProduit sp=new ServiceProduit();
        ServiceCategorieProduit scp=new ServiceCategorieProduit();
        String nom=tfNom.getText();
        String description=tfDescription.getText();
        String prix=tfPrix.getText();
        String categorie_nom=(String) categorie_produit.getSelectionModel().getSelectedItem();
        CategorieProduit categorie_produit=new CategorieProduit();
         try {
            categorie_produit=scp.rechercherCategorieByName(categorie_nom);
         } catch (SQLException ex) {
             Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
         }
        String image=getData.path;
       
        if(controleSaisie(image))
        {
            Produit p2=null;
             try {
                 p2=sp.rechercherProduitByName(nom);
             } catch (SQLException ex) {
                 Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
             }
            if(p2==null)
            {
                 int quantite=Integer.parseInt(tfQuantite.getText());
                  Date now = new Date();
        
                    // Création d'un objet SimpleDateFormat avec le format souhaité
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    // Conversion de la date en une chaîne de caractères
                    String dateString = dateFormat.format(now);
                    System.out.println(dateString);
                 Produit p=new Produit(nom,description,image,prix,quantite,categorie_produit,dateString);

                    sp.ajouterProduit2(p);
                    show_produit(); //pour mettre à jour l'affichage de la table view
                    annulerProduit(); //pour vide les champs du formulaire
                     getData.path=null;
            }
            else
            {
                alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Désole, Produit déja existant...");
             alert.showAndWait();
            }
       
        }
        
        
     }

     @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
         String sessionId = Global.sessionId;
         Joueur joueur = SessionManager.getSession(sessionId);
         sessionname.setText(joueur.getNom() + " " + joueur.getPrenom());
          show_produit();
          show_categorie_produit();
          
          
           
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
        categorie_produit_filtrer.getItems().clear();
        categorie_produit_filtrer.setItems(listData);
    }
    public void show_produit2(String mot) //a supprimer après
    {
          ServiceProduit service=new ServiceProduit();
          ObservableList<Produit> list = service.rechercherProduitMultiCriteres(mot);
          
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
        categorie_produit_filtrer.setItems(listData);
    }
     public void show_produit4(String mot,int categorie) //a supprimer après
    {
          ServiceProduit service=new ServiceProduit();
          ObservableList<Produit> list = service.rechercherProduitMultiCriteres2(mot,categorie);
          
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
        categorie_produit_filtrer.setItems(listData);
    }
    
    public void show_produit3(int  id_categorie) //a supprimer après
    {
          ServiceProduit service=new ServiceProduit();
          ObservableList<Produit> list = service.rechercherProduitFiltrerCategorie(id_categorie);
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
        categorie_produit_filtrer.setItems(listData);
    }
     public void show_categorie_produit()
    {
          ServiceCategorieProduit service=new ServiceCategorieProduit();
          ObservableList<CategorieProduit> list = service.getall();
          System.out.print(list);
          tcid.setCellValueFactory(new PropertyValueFactory<>("id"));
          tcdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
          tcnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
          tcquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
          tcprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
          tcimage.setCellValueFactory(new PropertyValueFactory<>("image"));
          tccreation.setCellValueFactory(new PropertyValueFactory<>("categorie"));
          categorieProduit_tableView.setItems(list);
    }
     @FXML
     public void produitSelected() {
        
        Produit produit = addProduit_tableView.getSelectionModel().getSelectedItem();
        int num = addProduit_tableView.getSelectionModel().getSelectedIndex();
         System.out.println(produit);
                
        if ((num - 1) < -1) {
            return;
        }

        tfNom.setText(String.valueOf(produit.getNom()));
        tfDescription.setText(produit.getDescription());
        tfQuantite.setText(String.valueOf(produit.getQuantite()));
        tfPrix.setText(produit.getPrix());
        tfid.setText(String.valueOf(produit.getId()));
        categorie_produit.setValue(produit.getCategorie().getNom());
         
        tfImage.setText(String.valueOf(produit.getImage()));
        String image_produit=tfImage.getText();
        
        String lien_image="file:/C:/xamppppp/htdocs/GGaming/GGaming/public/uploads/"+image_produit;
        image = new Image(lien_image, 101, 127, false, true);        produit_image.setImage(image);

        //getData.path = employeeD.getImage();

        // String uri = "file:" + employeeD.getImage();

        //image = new Image(uri, 101, 127, false, true);
        //addEmployee_image.setImage(image);
    }
     @FXML
     public void categorie_produitSelected() {
        
        CategorieProduit categorie = categorieProduit_tableView.getSelectionModel().getSelectedItem();
        int num = categorieProduit_tableView.getSelectionModel().getSelectedIndex();
       
         System.out.println("Bonjourr\n");
               
                
        if ((num - 1) < -1) {
            return;
        }

        tfNom_categorie.setText(String.valueOf(categorie.getNom()));
        tfid_categorie.setText(String.valueOf(categorie.getId()));
    }
     
      @FXML
     public void supprimerProduit() {
         Alert alert;
        ServiceProduit service=new ServiceProduit();
        int id=Integer.parseInt(tfid.getText());
        
         alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Etes vous sûr de supprimer le produit ID " + id + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                 if (option.get().equals(ButtonType.OK)) 
                 {
                      service.supprimerProduit(id); //procéder à la suppression
                      annulerProduit();
                      show_produit(); //mise à jour de la table view
                 }
                 else
                 {
                     annulerProduit(); //vider le contenu des textFields
                 }
       
    }
     
      
      @FXML
     public void supprimerCategorieProduit() {
         Alert alert;
        ServiceCategorieProduit service=new ServiceCategorieProduit();
        int id=Integer.parseInt(tfid_categorie.getText());
        
         alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Etes vous sûr de supprimer la categorie ID " + id + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                 if (option.get().equals(ButtonType.OK)) 
                 {
                      service.supprimerCategorieProduit(id); //procéder à la suppression
                      annulerCategorieProduit();
                      show_categorie_produit(); //mise à jour de la table view
                 }
                 else
                 {
                     annulerCategorieProduit(); //vider le contenu des textFields
                 }
       
    }
     
      @FXML
     public void modifierProduit() {
         Alert alert;
        String nom=tfNom.getText();
        String description=tfDescription.getText();
        String prix=tfPrix.getText();
        
        String image_produit=getData.path;
        String ancien_image=null;
        ancien_image=tfImage.getText();
         System.out.println(ancien_image);
                
        if(controleSaisieModification())
        {
        int quantite=Integer.parseInt(tfQuantite.getText());
        Produit p;
        if(image_produit==null) //il n'a pas choisi une nouvelle image
        { p=new Produit(nom,description,ancien_image,prix,quantite);}
        else
        {
           p=new Produit(nom,description,image_produit,prix,quantite);
        }
        
        ServiceProduit service=new ServiceProduit();
        int id=Integer.parseInt(tfid.getText());
        
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                  alert.setTitle("Cofirmation Message");
                  alert.setHeaderText(null);
                alert.setContentText("Etes vous sûr de modifier le produit ID " + id + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                 if (option.get().equals(ButtonType.OK)) {
                       service.modifierProduit(id,p);
                        show_produit();//mise à jour de la table
                          annulerProduit();   
                 }
                 else
                 {
                     annulerProduit();//vidre les champs des textFields
                 }
        }
    }
     @FXML
     public void modifierCategorieProduit() {
         Alert alert;
        String nom=tfNom_categorie.getText();
       
        CategorieProduit categorie=new CategorieProduit(nom);
        if(nom.isEmpty())
        {
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Vous devez donner le nom de la catégorie");
             alert.showAndWait();
        }
        else
        {
            
        ServiceCategorieProduit service=new ServiceCategorieProduit();
        int id=Integer.parseInt(tfid_categorie.getText());
        
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Etes vous sûr de modifier la categorie ID " + id + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                 if (option.get().equals(ButtonType.OK)) {
                       service.modifierCategorieProduit(id,categorie);
                        show_categorie_produit();//mise à jour de la table
                     
                 }
                 else
                 {
                     annulerCategorieProduit();//vidre les champs des textFields
                 }
        }
    }
     
      @FXML
     public void annulerProduit() {
         
         tfNom.setText("");
        tfDescription.setText("");
        tfQuantite.setText("");
        tfPrix.setText("");
        tfid.setText("");
        tfImage.setText("");
        produit_image.setImage(null);
    }
     
       @FXML
     public void annulerCategorieProduit() {
         
         tfNom_categorie.setText("");
    
    }
     
   
     @FXML
     public void afficher_categorie_produit()
     {
         page_produit.setVisible(false);
         page_categorie_produit.setVisible(true);
         show_categorie_produit();
         
     }
     @FXML
     public void afficher_produit()
     {
         page_categorie_produit.setVisible(false);
         page_produit.setVisible(true);
        show_produit();
     }
    /*  @FXML
      public String importerImageProduit() {

        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(boutique_back.getScene().getWindow());

        if (file != null) {
            getData.path = file.getAbsolutePath();
            
            image = new Image(file.toURI().toString(), 101, 127, false, true);
            produit_image.setImage(image);
            
            return getData.path; //retourner le lien de l'image importer        
        }
        return null; //retourner null si on a pas d'image importé
    }*/
      @FXML
      public String importerImageProduit() throws IOException {

        FileChooser open = new FileChooser();
        open.getExtensionFilters().addAll(
               new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        
        File file = open.showOpenDialog(boutique_back.getScene().getWindow());
         String DBPath = "C:\\\\\\\\xamppppp\\\\\\\\htdocs\\\\\\\\GGaming\\\\\\\\GGaming\\\\\\\\public\\\\\\\\uploads\\\\\\\\"+file.getName();
        if (file != null) {
            
            getData.path = file.getName();
            
            image = new Image(file.toURI().toString(), 101, 127, false, true);
            
            produit_image.setImage(image);
            BufferedImage bufferedImage;
            try {
                bufferedImage = ImageIO.read(file);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage,null);
                  ImageIO.write(bufferedImage, "jpg", new File(DBPath));
            } catch (IOException ex) {
                Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
            }
        FileInputStream fin;
        
            try {
                byte[] buf = new byte [1024];
                fin = new FileInputStream(file);
                 ByteArrayOutputStream bos = new ByteArrayOutputStream();
                  for (int readNum ;(readNum= fin.read(buf)) != -1 ;){
            bos.write(buf,0,readNum);
         post_image = bos.toByteArray();}
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
            }
         
       
            return getData.path; //retourner le lien de l'image importer        
        }
        return null; //retourner null si on a pas d'image importé
    }
      
      public boolean controleSaisie(String image2)
      {
          Alert alert;
          ServiceProduit sp=new ServiceProduit();
          if(!tfNom.getText().isEmpty())
          {
               tfNom.getStyleClass().remove("error");
          }
           if(!tfDescription.getText().isEmpty())
          {
               tfDescription.getStyleClass().remove("error");
          }
            if(!tfPrix.getText().isEmpty()&&sp.isNumeric(tfPrix.getText()))
          {
               tfPrix.getStyleClass().remove("error");
          }
             if(!tfQuantite.getText().isEmpty()&&sp.isNumeric(tfQuantite.getText()))
          {
               tfQuantite.getStyleClass().remove("error");
          }
            if(categorie_produit.getSelectionModel().getSelectedItem()!=null)
            {
                categorie_produit.getStyleClass().remove("error");
            }
             
          if(tfNom.getText().isEmpty())
        {
             tfNom.getStyleClass().add("error"); //rendre le texfield de couleur rouge
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Veuillez saisir le nom du produit");
             alert.showAndWait();
            
             return false;
        }
        else if(tfDescription.getText().isEmpty())
        {
             tfDescription.getStyleClass().add("error");
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Veuillez saisir la description  du produit");
             alert.showAndWait();
            
              return false;
        }
         else if (categorie_produit.getSelectionModel().getSelectedItem()==null)
         {
             categorie_produit.getStyleClass().add("error");
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Veuillez choisir la categorie  du produit");
             alert.showAndWait();
            
              return false;
              
         }
            else if(tfPrix.getText().isEmpty())
        {
             tfPrix.getStyleClass().add("error");
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Veuillez saisir le prix  du produit");
             alert.showAndWait();
             
              return false;
        }
        else if(!sp.isNumeric(tfPrix.getText()))
        {
             tfPrix.getStyleClass().add("error");
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Le prix doit être une valeur numerique positive");
             alert.showAndWait();
             
              return false;
        }
          else if(tfQuantite.getText().isEmpty())
        {
             tfQuantite.getStyleClass().add("error");
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Veuillez saisir la quantite  du produit");
             alert.showAndWait();
             
              return false;
        }
          else if(!sp.isNumeric(tfQuantite.getText()))
        {
             tfQuantite.getStyleClass().add("error");
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("La quantite doit être une valeur numerique positive");
             alert.showAndWait();
            
              return false;
        }
        
        else if(image==null||image2==null)
        {
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Vous devez insérer une image du produit");
             alert.showAndWait();
             return false;
        }
          
          return true;
      }
         
        public boolean controleSaisieModification()
      {
          Alert alert;
          ServiceProduit sp=new ServiceProduit();
          if(!tfNom.getText().isEmpty())
          {
               tfNom.getStyleClass().remove("error");
          }
           if(!tfDescription.getText().isEmpty())
          {
               tfDescription.getStyleClass().remove("error");
          }
            if(!tfPrix.getText().isEmpty()&&sp.isNumeric(tfPrix.getText()))
          {
               tfPrix.getStyleClass().remove("error");
          }
             if(!tfQuantite.getText().isEmpty()&&sp.isNumeric(tfQuantite.getText()))
          {
               tfQuantite.getStyleClass().remove("error");
          }
            if(categorie_produit.getSelectionModel().getSelectedItem()!=null)
            {
                categorie_produit.getStyleClass().remove("error");
            }
             
          if(tfNom.getText().isEmpty())
        {
             tfNom.getStyleClass().add("error"); //rendre le texfield de couleur rouge
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Veuillez saisir le nom du produit");
             alert.showAndWait();
            
             return false;
        }
        else if(tfDescription.getText().isEmpty())
        {
             tfDescription.getStyleClass().add("error");
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Veuillez saisir la description  du produit");
             alert.showAndWait();
            
              return false;
        }
         else if (categorie_produit.getSelectionModel().getSelectedItem()==null)
         {
             categorie_produit.getStyleClass().add("error");
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Veuillez choisir la categorie  du produit");
             alert.showAndWait();
            
              return false;
              
         }
            else if(tfPrix.getText().isEmpty())
        {
             tfPrix.getStyleClass().add("error");
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Veuillez saisir le prix  du produit");
             alert.showAndWait();
             
              return false;
        }
        else if(!sp.isNumeric(tfPrix.getText()))
        {
             tfPrix.getStyleClass().add("error");
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Le prix doit être une valeur numerique positive");
             alert.showAndWait();
             
              return false;
        }
          else if(tfQuantite.getText().isEmpty())
        {
             tfQuantite.getStyleClass().add("error");
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Veuillez saisir la quantite  du produit");
             alert.showAndWait();
             
              return false;
        }
          else if(!sp.isNumeric(tfQuantite.getText()))
        {
             tfQuantite.getStyleClass().add("error");
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("La quantite doit être une valeur numerique positive");
             alert.showAndWait();
            
              return false;
        }
          
           return true;
      }
         
      /////partie categorie
       @FXML
      void addCategorieProduit()
      {
           Alert alert;

        ServiceCategorieProduit scp=new ServiceCategorieProduit();
        String nom=tfNom_categorie.getText();
        CategorieProduit categorie= new CategorieProduit(nom);
        if(nom.isEmpty())
        {
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Vous devez donner le nom de la catégorie");
             alert.showAndWait();
        }
        else
        {
            CategorieProduit categ=new CategorieProduit();
               try {
                   categ=scp.rechercherCategorieByName(nom);
               } catch (SQLException ex) {
                   Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
               }
            if(categ==null)
            {
                scp.ajouterCategorieProduit(categorie);
                show_categorie_produit();
                annulerCategorieProduit();
            }
            else
            {
                alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Désole, Categorie déja existante...");
             alert.showAndWait();
            }
            
       
        }
      //  show(); //pour mettre à jour l'affichage de la table view
       // annulerProduit(); //pour vide les champs du formulaire
        
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
    
    
    @FXML
    
    public void rechercherProduit()
    {
     
       // System.out.println("le mot taper est"+produit_search.getText());
        String categorie=(String) categorie_produit_filtrer.getSelectionModel().getSelectedItem();
        System.out.println("la categorie est "+categorie);
        if(categorie==null)
        {
            
        String mot=produit_search.getText();
        show_produit2(mot);
        }
        else
        {
             ServiceCategorieProduit scp=new ServiceCategorieProduit();
            CategorieProduit categorie_produit;
            try {
                categorie_produit = scp.rechercherCategorieByName(categorie);
                 String mot=produit_search.getText();
            show_produit4(mot,categorie_produit.getId());
            } catch (SQLException ex) {
                Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
            }
             
        }
    }
    
    
    @FXML
    
    public void filtrer()
    {
        String categorie=(String) categorie_produit_filtrer.getSelectionModel().getSelectedItem();
        ServiceCategorieProduit scp=new ServiceCategorieProduit();
        
         try {
             CategorieProduit categorie_produit=scp.rechercherCategorieByName(categorie);
             show_produit3(categorie_produit.getId());
         } catch (SQLException ex) {
             Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }
    @FXML
    public void exporterProduit()
    {
        Alert alert;
        StringBuilder content = new StringBuilder();
        content.append("Nom du produit,Prix,description,Quantite\n");
        ServiceProduit sp=new ServiceProduit();
        List<Produit> listeDesProduits=new ArrayList<>();
        listeDesProduits=sp.afficherProduit();
        
        for (Produit produit : listeDesProduits) {
            content.append(produit.getNom()).append(",").append(produit.getPrix()).append(",").append(produit.getDescription()).append(",").append(produit.getQuantite()).append("\n");
        }
        
        try (FileWriter writer = new FileWriter("liste-des-produits.csv")) {
            writer.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
         alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Liste des Produits Exportée avec succès");
                    alert.showAndWait();
       
        
    }
    ////INTEGRATION 
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
    void afficherEquipesBack(ActionEvent event) {
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
    void afficherJoueurs(ActionEvent event) {
        Parent root;
         try {
              root = FXMLLoader.load(getClass().getResource("../../../ggaming2/boutiqueBack.fxml"));
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
      public void logoutback() throws IOException {
        // remove the session ID
        Global.sessionId = null;

        // redirect the user to the register page
        Parent root = FXMLLoader.load(getClass().getResource("../../../ggaming2/HomePage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) sessionlogout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
          
}



///a voir$

//la modification ne fonctionne pas correctement -----> oKAY
//ajouter la date de création au niveau de produit et catégorie