/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.services;

import javafx.fxml.FXML;

import ggaming.cnx.MaConnection;
import javafx.scene.control.TableCell;
import java.sql.Connection;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ggaming.entity.Joueur;
import java.io.File;
import java.sql.*;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import java.time.Period;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;

import java.time.LocalDate;
import java.util.Calendar;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author DELL
 */
public class JoueurController {

    private final Connection cnx;

    public JoueurController() {
        this.cnx = MaConnection.getInstance().getCnx();
    }
    @FXML
    private TableView<Joueur> joueursTable;

    /////The fields below this comment will be used for adding the user in the backend
    @FXML
    private Button ajouttext;
    @FXML
    private TextField nomtext;
    @FXML
    private TextField prenomtext;
    @FXML
    private TextField igntext;
    @FXML
    private TextField mdptext;
    @FXML
    private TextField emailtext;
    @FXML
    private TextField winstext;
    @FXML
    private TextField losestext;
    @FXML
    private DatePicker datenaistext;
    @FXML
    private ComboBox<String> rolescombo;
    /////
    /*@FXML
    private TableColumn<Joueur, Integer> idCol;*/
    @FXML
    private TableColumn<Joueur, String> email;
    @FXML
    private TableColumn<Joueur, String> pprofile;
    @FXML
    private TableColumn<Joueur, String> nom;
    @FXML
    private TableColumn<Joueur, String> prenom;
    @FXML
    private TableColumn<Joueur, String> datenai;

    @FXML
    private TableColumn<Joueur, String> is_banned;

    @FXML
    private TableColumn<Joueur, String> is_verified;

    @FXML
    private TableColumn<Joueur, String> ign;
    @FXML
    private TableColumn<Joueur, String> wins;
    @FXML
    private TableColumn<Joueur, String> loses;

    /*@FXML
    private TableColumn<Joueur, String> nomCol;*/
    private final JoueurDAO joueurDAO = new JoueurDAO();

    @FXML
    private void handleAddUserButtonAction(ActionEvent event) {
        String nom = nomtext.getText();
        String prenom = prenomtext.getText();
        String ign = igntext.getText();
        String password = mdptext.getText();
        String email = emailtext.getText();
        String winsString = winstext.getText();
        int wins;
        String lossesString = losestext.getText();
        int losses;
        //String datanai = datenaistext.getText();
        LocalDate datee = datenaistext.getValue();
        DateTimeFormatter formatter69 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = datee.format(formatter69);
        Date date3 = Date.valueOf(formattedDate);
        String datenaistext2 = datee.toString();

        String selectedValue = rolescombo.getValue();
        String[] roles = selectedValue.split(",");

        boolean isVerified = false; // set to true if the user has been verified

        String profile = "test";
        boolean isBanned = false; // set to true if the user has been banned

        //Date createdAt = new Date(joueur.getCreatedAt().getTime());
        // validate input fields here, e.g. check if required fields are filled
        //////////////////////VALIDATION
        // validate nom
        if (nom.isEmpty() || nom.length() > 15 || !nom.matches("[a-zA-Z]+")) {
            //showAlert("Nom invalide! Il doit être composé de lettres seulement et ne doit pas dépasser 15 caractères.");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Nom invalide!");
            alert.setContentText("Nom invalide! Il doit être composé de lettres seulement et ne doit pas dépasser 15 caractères.");
            alert.showAndWait();
            return;
        }

// validate prenom
        if (prenom.isEmpty() || prenom.length() > 15 || !prenom.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Prenom invalide!");
            alert.setContentText("Prenom invalide! Il doit être composé de lettres seulement et ne doit pas dépasser 15 caractères.");
            alert.showAndWait();
            return;

        }

// validate ign
        if (ign.isEmpty() || ign.length() > 15) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("IGN invalide!");
            alert.setContentText("IGN invalide! Il ne doit pas dépasser 15 caractères.");
            alert.showAndWait();
            return;
        }

// validate password
        if (password.isEmpty() || password.length() > 10 || !password.matches("^(?=.*[0-9])(?=.*[A-Z]).{1,}$")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Mot de passe invalide!");
            alert.setContentText("Mot de passe invalide! Il doit contenir au moins 1 chiffre, 1 lettre majuscule et ne doit pas dépasser 10 caractères.");
            alert.showAndWait();
            return;
        }

// validate email
        if (email.isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Email invalide!");
            alert.setContentText("Email invalide! Veuillez saisir une adresse email valide.");
            alert.showAndWait();
            return;
        }

// validate wins
        try {
            wins = Integer.parseInt(winsString);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Victoires invalide! Veuillez saisir un entier.");
            alert.showAndWait();
            return;
        }

// validate losses
        try {
            losses = Integer.parseInt(winsString);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Defaites invalide! Veuillez saisir un entier.");
            alert.showAndWait();
            return;
        }

// validate datanai
        if (datenaistext2.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Date de naissance invalide!");
            alert.setContentText("Date de naissance invalide!");
            alert.showAndWait();

            return;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date2 = LocalDate.parse(datenaistext2, formatter);
            LocalDate now = LocalDate.now();
            int age = Period.between(date2, now).getYears();
            if (age < 16) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Date de naissance invalide!");
                alert.setContentText("L'âge doit être supérieur ou égal à 16 ans.");
                alert.showAndWait();
                return;

            }
        } catch (DateTimeParseException e) {
            //showAlert("Date de naissance invalide! Veuillez saisir une date de naissance valide (format: jj/mm/aaaa).");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Date de naissance invalide!");
            alert.setContentText("Date de naissance invalide! Veuillez saisir une date de naissance valide (format: jj/mm/aaaa).");
            alert.showAndWait();
            return;
        }
        Joueur joueur = new Joueur(email, roles, password, isVerified, nom, prenom, date3, profile, isBanned, ign, wins, losses);

        try {
            // Insert the new Joueur into the database using the DAO
            joueurDAO.insert(joueur);
            System.out.println("Joueur added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding joueur: " + e.getMessage());

            //I hate java with the deepest roots of my heart, I hope however invented javaFX roots in hell for his sins.
        }

        // clear input fields
        nomtext.clear();
        prenomtext.clear();
        datenaistext.setValue(null);
        mdptext.clear();
        igntext.clear();
        emailtext.clear();
        winstext.clear();
        losestext.clear();
        updateData();

    }

    public void updateData() {
        // Get the current data from the database or any other source
        List<Joueur> data = joueurDAO.getAllJoueurs();

        // Clear the current data in the table view
        joueursTable.getItems().clear();

        // Add the new data to the table view
        joueursTable.getItems().addAll(data);

        // Refresh the table view to show the new data
        joueursTable.refresh();
    }

    public static int calculateAge(Date birthDate) {
        Calendar now = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        dob.setTime(birthDate);
        int age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }
    private static final String IMAGE_FOLDER = "C:\\xampp\\htdocs\\PI\\Ggaming\\public\\userImages\\";

    @FXML
    public void initialize() {
        rolescombo.getItems().addAll("ROLE_USER", "ROLE_ADMIN");
        rolescombo.setValue("ROLE_USER"); // Set default value
        // Initialize table columns
        //idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        email.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        ign.setCellValueFactory(
                new PropertyValueFactory<>("ign"));
        nom.setCellValueFactory(
                new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(
                new PropertyValueFactory<>("prenom"));

        datenai.setCellValueFactory(new PropertyValueFactory<>("datenai"));

        is_banned.setCellValueFactory(
                new PropertyValueFactory<>("is_banned"));
        is_banned.setCellValueFactory(cellData
                -> {
            boolean isBanned = cellData.getValue().isBanned();
            String bannedStatus = isBanned ? "Banné" : "Actif";
            return new SimpleStringProperty(bannedStatus);
        }
        );
        pprofile.setCellValueFactory(new PropertyValueFactory<>("pprofile"));

        pprofile.setCellFactory(column -> {
            return new TableCell<Joueur, String>() {
                private final ImageView imageView = new ImageView();

                {
                    setGraphic(imageView);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }

                @Override
                protected void updateItem(String profilePicName, boolean empty) {
                    super.updateItem(profilePicName, empty);
                    if (profilePicName == null || empty) {
                        System.out.println("Test 1");
                        setGraphic(null);
                    } else {
                        try {
                            System.out.println("Test 2");
                            // Load the profile picture from the file system
                            File imageFile = new File(IMAGE_FOLDER + profilePicName);
                            Image image = new Image(imageFile.toURI().toString());
                            imageView.setImage(image);
                            setGraphic(imageView);
                        } catch (Exception e) {
                            // Handle the exception, e.g. show a default image
                            setGraphic(null);
                        }
                    }
                }
            };
        });
        is_verified.setCellValueFactory(
                new PropertyValueFactory<>("is_verified"));
        is_verified.setCellValueFactory(cellData
                -> {
            boolean isVerified = cellData.getValue().isVerified();
            String bannedStatus = isVerified ? "✅" : "❌";
            return new SimpleStringProperty(bannedStatus);
        }
        );

        wins.setCellValueFactory(
                new PropertyValueFactory<>("wins"));
        loses.setCellValueFactory(
                new PropertyValueFactory<>("loses"));
        //this is code to add a ban button that changes color based on the state of the user, disgusting amount of filthy code to do sucha  simple task, why are they teaching us this when technology has advanced beyond this.
        // I hate java so much so much so much
        //////////////////
        TableColumn<Joueur, Void> buttonColumn = new TableColumn<>("Action");
        buttonColumn.setCellFactory(column -> {
            return new TableCell<Joueur, Void>() {
                private final Button banButton = new Button();

                {
                    banButton.setOnAction(event -> {
                        Joueur joueur = getTableView().getItems().get(getIndex());
                        boolean isBanned = joueur.isBanned();
                        if (isBanned) {
                            // Unban the joueur
                            joueur.setBanned(false);
                            banButton.setText("Bannir");
                            banButton.setStyle("-fx-background-color: red;");
                        } else {
                            // Ban the joueur
                            joueur.setBanned(true);
                            banButton.setText("Débannir");
                            banButton.setStyle("-fx-background-color: green;");
                        }
                        String updateQuery = "UPDATE joueur SET is_banned = ? WHERE id = ?";
                        try {
                            PreparedStatement preparedStatement = cnx.prepareStatement(updateQuery);
                            preparedStatement.setBoolean(1, joueur.isBanned());
                            preparedStatement.setInt(2, joueur.getId());
                            preparedStatement.executeUpdate();
                        } catch (SQLException e) {
                            System.out.println("Update failed: " + e.getMessage());
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Joueur joueur = getTableView().getItems().get(getIndex());
                        boolean isBanned = joueur.isBanned();
                        if (isBanned) {
                            banButton.setText("Débannir");
                            banButton.setStyle("-fx-background-color: green;");
                        } else {
                            banButton.setText("Bannir");
                            banButton.setStyle("-fx-background-color: red;");
                        }
                        setGraphic(banButton);
                        updateData();

                    }
                }
            };
        });
        ajouttext.setOnAction(this::handleAddUserButtonAction);
        joueursTable.getColumns().add(buttonColumn);

        /////////////////
        //nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        // Load data from database and add to table
        List<Joueur> joueurs = joueurDAO.getAllJoueurs();

        //System.out.println(joueurs);
        joueursTable.getItems()
                .addAll(joueurs);

    }
}
