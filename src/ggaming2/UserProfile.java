/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import ggaming.entity.Joueur;
import java.io.File;
import java.io.IOException;
import javafx.scene.image.Image;
import java.text.SimpleDateFormat;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class UserProfile {

    @FXML
    private Label profilenom;

    @FXML
    private Label profileprenom;

    @FXML
    private Label profileemail;

    @FXML
    private ImageView profileimg;

    @FXML
    private Label profilewins;

    @FXML
    private Label profileloses;

    @FXML
    private Label profileign;

    @FXML
    private Label profiledate;

    @FXML
    private Button logout;

    @FXML
    private Button gotoback;

    /*private String sessionId;

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }*/
    //Joueur joueur = SessionManager.getSession(sessionId);
    @FXML
    public void initialize() {
        String sessionId = Global.sessionId;

        System.out.println("Session ID: " + sessionId);

        Joueur joueur = SessionManager.getSession(sessionId);
        if (joueur.getRoleJava_joueur_id() == 2) {
            // Show the dashboard button
            gotoback.setVisible(true);
        } else {
            // Hide the dashboard button
            gotoback.setVisible(false);
        }
        String imageFilename = joueur.getProfile();

// Construct a File object pointing to the location of the image file
        File imageFile = new File("C:\\xampp\\htdocs\\PI\\Ggaming\\public\\userImages\\" + imageFilename);

// Create an Image object from the File
        Image image = new Image(imageFile.toURI().toString());

// Set the Image object as the image of the ImageView
        profileimg.setImage(image);
        profilenom.setText(joueur.getNom());
        profileprenom.setText(joueur.getPrenom());
        profileemail.setText(joueur.getEmail());
        profileimg.setImage(image);
        profilewins.setText(String.valueOf(joueur.getWins()));
        profileloses.setText(String.valueOf(joueur.getLoses()));
        profileign.setText(joueur.getIgn());
        java.util.Date date = new java.util.Date(joueur.getDatenai().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = dateFormat.format(date);
        profiledate.setText(stringDate);
    }

    @FXML
    public void GotoModifier(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ModifyProfile.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleback(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("boutiqueBack.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleLogout() throws IOException {
        // remove the session ID
        Global.sessionId = null;

        // redirect the user to the register page
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
