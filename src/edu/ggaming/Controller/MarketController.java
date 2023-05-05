package edu.ggaming.Controller;




import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import edu.ggaming.main.Main;
import edu.ggaming.main.MyListener;
import edu.ggaming.entities.Fruit;
import edu.ggaming.entities.Panier;
import edu.ggaming.entities.Produit;
import edu.ggaming.entities.Session;
import edu.ggaming.services.ServiceProduit;
import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MarketController implements Initializable {
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label labelNomProduit;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private ImageView fruitImg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;
    
   @FXML
    private Label description;
   
     @FXML
    private TextField produit_search;
    
      @FXML
    private ComboBox<Integer> combobox_quantite;

    private List<Fruit> fruits = new ArrayList<>();
    private List<Produit> produits=new ArrayList<>();
    private Image image;
    private MyListener myListener;

   
    private void setChosenProduit(Produit produit)
    {
         List<Integer> listG = new ArrayList<>();
         
         for(Integer i=1;i<=produit.getQuantite();i++)
         {
             listG.add(i);
         }
        ObservableList listData = FXCollections.observableArrayList(listG);
        System.out.println("la liste est "+listData);
        combobox_quantite.setItems(listData);
        
        labelNomProduit.setText(produit.getNom());
        fruitPriceLabel.setText(Main.CURRENCY + produit.getPrix());
        File file = new File("C:\\xamppppp\\htdocs\\GGaming\\GGaming\\public\\uploads\\"+produit.getImage());
        image = new Image(file.toURI().toString());
        fruitImg.setImage(image);
        description.setText(produit.getDescription());
        chosenFruitCard.setStyle("-fx-background-color: #FFFFFF" +  ";\n" +
              "    -fx-background-radius: 30;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         String mot=produit_search.getText();
       
        
        showProduit2(mot);
    }
    
     public void showProduit2(String mot)
    {
        ServiceProduit sp=new ServiceProduit();

        produits=sp.rechercherProduitMultiCriteres(mot);
        
        
        System.out.println(produits);
                
        if (produits.size() > 0) {
            setChosenProduit(produits.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Produit produit) {
                    setChosenProduit(produit);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../views/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                    
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(produits.get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
    public void ajouterPanier(ActionEvent event)
    {
        
        Alert alert;
        BoutiqueBackController bp=new BoutiqueBackController();
         alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Voulez-vous ajouter ce produit a votre panier?");
                Optional<ButtonType> option = alert.showAndWait();
                
                 if (option.get().equals(ButtonType.OK)) {         
        String nom_produit=labelNomProduit.getText();
        
        ServiceProduit sp=new ServiceProduit();
        
        try {
            Produit produit=sp.rechercherProduitByName(nom_produit);
            int quantite_produit=produit.getQuantite();
           // System.out.println("produit afficher après recupération:"+produit);
           if(combobox_quantite.getSelectionModel().getSelectedItem()!=null)
           {
               produit.setQuantite(combobox_quantite.getSelectionModel().getSelectedItem());
           }
           else
           {
               produit.setQuantite(1);
           }
            
            Panier panier=new Panier();
            panier.ajouterArticle(produit);
            
            
            Session session = Session.getInstance();

            // Récupération de l'objet Panier stocké dans la variable de session
            panier = session.getAttribute("panier");

            // Si le panier n'existe pas, on le crée et on le stocke dans la variable de session
            if (panier == null) {
                panier = new Panier();
                session.setAttribute("panier", panier);
            }

            // Ajout du produit au panier
            panier.ajouterArticle(produit);
            session.setAttribute("panier", panier);
            sp.modifierQuantiteProduit(produit.getId(), quantite_produit-produit.getQuantite());//ceci pour mettre à jour la quantite du prdout
            
            bp.afficherBoutique(event);
                 
        } catch (SQLException ex) {
            Logger.getLogger(MarketController.class.getName()).log(Level.SEVERE, null, ex);
        }
                 }
                 
                 
                 
                         
    }
    
    @FXML
    
    public void afficherPanier(MouseEvent event)
    {
         
         Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("../views/panier.fxml"));
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
        String mot=produit_search.getText();
        System.out.println("le mot est "+mot);
        System.out.println("voici le contenu de products"+produits);
        showProduit2(mot);
       
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
    void afficherTournoiFront(ActionEvent event) {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("TournoiHome.fxml"));
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
    void afficherJeuxFront(ActionEvent event) {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("FrontJeux2.fxml"));
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
    void afficherBlogsFront(ActionEvent event) {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("../views/blogFront.fxml"));
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
    void afficherEquipesFront(ActionEvent event) {
        Parent root;
         try {
              root = FXMLLoader.load(getClass().getResource("../views/FrontEquipe.fxml"));
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
