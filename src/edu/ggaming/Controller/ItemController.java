package edu.ggaming.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import edu.ggaming.main.Main;
import edu.ggaming.main.MyListener;
import edu.ggaming.entities.Fruit;
import edu.ggaming.entities.Produit;
import javafx.scene.control.TextArea;

public class ItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;
    
     
    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(produit);
    }

    private Fruit fruit;
    private Produit produit;
    private MyListener myListener;

    public void setData(Produit produit, MyListener myListener) {
        this.produit = produit;
        this.myListener = myListener;
        nameLabel.setText(produit.getNom());
      
        priceLable.setText(Main.CURRENCY + produit.getPrix());
           Image     image = new Image(getClass().getResourceAsStream("../img/souris3.jpg"));

        img.setImage(image);
    }
}
