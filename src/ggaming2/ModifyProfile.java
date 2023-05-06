/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming2;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import ggaming.entity.Joueur;
import java.io.File;
import ggaming.services.JoueurDAO;
import static ggaming2.RegisterController.saveImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.scene.image.Image;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class ModifyProfile {

    @FXML
    private TextField profilenom;

    @FXML
    private TextField profileprenom;

    @FXML
    private TextField profileemail;

    @FXML
    private ImageView profileimg;

    @FXML
    private TextField profileign;

    @FXML
    private DatePicker profiledate;

    @FXML
    private Button Modify;

    private JoueurDAO joueurDAO;
    private Joueur joueur;
    private File profileImageFile;
    private static final String IMAGE_FOLDER = "C:\\xampp\\htdocs\\PI\\Ggaming\\public\\userImages\\";

    public ModifyProfile() {
        joueurDAO = new JoueurDAO();
    }

    @FXML
    public void initialize() {
        String sessionId = Global.sessionId;
        joueur = SessionManager.getSession(sessionId);
        profilenom.setText(joueur.getNom());
        profileprenom.setText(joueur.getPrenom());
        profileemail.setText(joueur.getEmail());
        profileign.setText(joueur.getIgn());
        String dateStr = joueur.getDatenai().toString();
        Date datenais = Date.valueOf(dateStr);
        LocalDate localDate = datenais.toLocalDate();
        profiledate.setValue(localDate);

        String imageFilename = joueur.getProfile();

// Construct a File object pointing to the location of the image file
        File imageFile = new File("C:\\xampp\\htdocs\\PI\\Ggaming\\public\\userImages\\" + imageFilename);

// Create an Image object from the File
        Image image = new Image(imageFile.toURI().toString());
        profileimg.setImage(image);

    }

    @FXML
    private void handleModification(ActionEvent event) {
        System.out.println("Test 1");
        String emailregister = profileemail.getText();
        String firstNameregister = profilenom.getText();
        String lastNameregister = profileprenom.getText();
        String inGameNameregister = profileign.getText();
        String sessionId = Global.sessionId;
        System.out.println(sessionId);
        joueur = SessionManager.getSession(sessionId);
        LocalDate datee = profiledate.getValue();
        DateTimeFormatter formatter69 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = datee.format(formatter69);
        Date date3 = Date.valueOf(formattedDate);
        String datenaistext2 = datee.toString();
        // get the modified values from the input fields
        if (emailregister.isEmpty() || firstNameregister.isEmpty() || lastNameregister.isEmpty() || inGameNameregister.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            System.out.println("Test 2");
            alert.setHeaderText("Verifier que tout is valide");
            alert.setContentText("Verifier que tout is valide");
            alert.showAndWait();
            return;
        }
        if ((validateInputs(firstNameregister, lastNameregister, emailregister, inGameNameregister, datenaistext2)) && (profileimg != null)) {
            String profilepic = null;
            System.out.println("Test 3");
            if (profileImageFile != null) {
            try {
                System.out.println("Test 4");
                //System.out.println("\u001B[31mError: Test 6.\u001B[0m");
                profilepic = saveImage(profileImageFile);
            } catch (IOException e) {
                System.out.println("Error saving image file");
            }
            joueur.setProfile(profilepic);
            }
            System.out.println("Test 5");
            joueur.setNom(profilenom.getText());
            joueur.setPrenom(profileprenom.getText());
            joueur.setIgn(profileign.getText());
            joueur.setEmail(profileemail.getText());
            joueur.setDatenai(date3);
            
            try {
                System.out.println("Test 6");
                // update the player information in the database
                joueurDAO.updateJoueur(joueur);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Test 7");
                Parent page1 = FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public boolean validateInputs(String nom, String prenom, String email, String ign, String datenaistext) {
        if (datenaistext.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Date de naissance invalide!");
            alert.setContentText("Date de naissance invalide!");
            alert.showAndWait();

            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date2 = LocalDate.parse(datenaistext, formatter);
            LocalDate now = LocalDate.now();
            int age = Period.between(date2, now).getYears();
            if (age < 16) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Date de naissance invalide!");
                alert.setContentText("L'âge doit être supérieur ou égal à 16 ans.");
                alert.showAndWait();
                return false;

            }
        } catch (DateTimeParseException e) {
            //showAlert("Date de naissance invalide! Veuillez saisir une date de naissance valide (format: jj/mm/aaaa).");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Date de naissance invalide!");
            alert.setContentText("Date de naissance invalide! Veuillez saisir une date de naissance valide (format: jj/mm/aaaa).");
            alert.showAndWait();
            return false;
        }
        // Validate nom
        if (!nom.matches("^[a-zA-Z]{1,15}$")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Invalid nom");
            alert.setContentText("Le nom doit être entièrement alphabétique et ne peut excéder 15 caractères.");
            alert.showAndWait();
            return false;
        }

        // Validate prenom
        if (!prenom.matches("^[a-zA-Z]{1,15}$")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Invalid prenom");
            alert.setContentText("Le prenom doit être entièrement alphabétique et ne peut excéder 15 caractères.");
            alert.showAndWait();
            return false;
        }

        // Validate email
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Invalid email");
            alert.setContentText("L'adresse électronique doit être valide.");
            alert.showAndWait();
            return false;
        }

        // Validate ign
        if (ign.length() > 15) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Invalid IGN");
            alert.setContentText("L'IGN est soit pris, soit dépasse 15 caractères.");
            alert.showAndWait();
            return false;
        }

        // Validate password
        // If all inputs are valid, return true
        return true;
    }

    public static String saveImage(File imageFile) throws IOException {
        //System.out.println("Test 3");
        String originalFilename = imageFile.getName();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;
        Path destination = Paths.get(IMAGE_FOLDER + newFilename);
        Files.copy(imageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        return newFilename;
    }

    @FXML
    public void BrowseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir l'image du profil");
        // pprofileRegister
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        profileImageFile = file;
        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                //System.out.println("Test 1");
                //ImageView imageView = new ImageView(image);
                //profileImageFile = file;
                profileimg.setImage(image);
                //System.out.println("Test 2");
            } catch (Exception e) {
                System.out.println("Error loading image");
            }
        }
    }
}
