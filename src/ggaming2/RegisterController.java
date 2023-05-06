/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming2;

/**
 *
 * @author DELL
 */
//import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import javafx.fxml.FXML;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.*;
import javax.mail.Transport;
import javax.mail.Authenticator;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.mindrot.jbcrypt.BCrypt;
import ggaming.entity.Joueur;
import ggaming.services.JoueurDAO;
import java.sql.*;
import javafx.scene.control.Button;
import java.time.Period;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.nio.file.Paths;
import java.util.Properties;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import sun.plugin2.message.transport.Transport;

//For the facebook register button
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
// For twilio and shit
import javafx.scene.control.ChoiceBox;
import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

//
public class RegisterController {

    Parent loginRedirect;
    @FXML
    private AnchorPane register;

    @FXML
    private ImageView pprofileRegister;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private DatePicker datenaistext;

    @FXML
    private TextField inGameName;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button browseBtn;

    @FXML
    private Button registerButton;

    

    


    private File profileImageFile;

    private static final String IMAGE_FOLDER = "C:\\xampp\\htdocs\\PI\\Ggaming\\public\\userImages\\";

    private final String appId = "1384965872358291";
    private final String appSecret = "f5ffe8cdc19629d65deaed095c02a6dc";
    private final String redirectUrl = "https://vocalremover.org/";
    private final String ACCESS_TOKEN_REGEX = "access_token=(.+)&";
    private final String AUTH_URL = "https://www.facebook.com/v12.0/dialog/oauth?client_id=" + appId + "&redirect_uri=" + redirectUrl + "&response_type=token&scope=email";
    ////////////////
    // TWILIO
    private final String ACCOUNT_SID = "ACb3f09a2d40b2e60759af29b509ae1441";
    private final String AUTH_TOKEN = "c2e9ba52ea5a7ee1c0ab309af2812d4a";
    private final static String TWILIO_PHONE_NUMBER = "+21625019058";

    ////////////////
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
    public void browseImage(ActionEvent event) {
        //System.out.println("Test 0");
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
                pprofileRegister.setImage(image);
                //System.out.println("Test 2");
            } catch (Exception e) {
                System.out.println("Error loading image");
            }
        }
    }

    public boolean validateInputs(String nom, String prenom, String email, String ign, String password, String datenaistext) {
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
        if (password.length() < 5 || password.length() > 10 || !password.matches(".*[A-Z].*") || !password.matches(".*\\d.*")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Invalid password");
            alert.setContentText("Le mot de passe doit comporter entre 5 et 10 caractères et contenir au moins une lettre majuscule et un chiffre.");
            alert.showAndWait();
            return false;
        }

        // If all inputs are valid, return true
        return true;
    }

    @FXML
    public void registerUser(ActionEvent event) {
        String emailregister = email.getText();
        String passwordregister = password.getText();
        String firstNameregister = firstName.getText();
        String lastNameregister = lastName.getText();
        String inGameNameregister = inGameName.getText();
        //LocalDate dob = dobPicker.getValue();
        LocalDate datee = datenaistext.getValue();
        DateTimeFormatter formatter69 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = datee.format(formatter69);
        Date date3 = Date.valueOf(formattedDate);
        String datenaistext2 = datee.toString();
        //System.out.println("\u001B[31mError: Test4.\u001B[0m");
        // Check if all fields are filled
        if (emailregister.isEmpty() || passwordregister.isEmpty() || firstNameregister.isEmpty() || lastNameregister.isEmpty() || inGameNameregister.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Verifier que tout is valide");
            alert.setContentText("Verifier que tout is valide");
            alert.showAndWait();
            return;
        }
        if ((validateInputs(firstNameregister, lastNameregister, emailregister, inGameNameregister, passwordregister, datenaistext2)) && (profileImageFile != null)) {
            JoueurDAO joueurDAO = new JoueurDAO();
            // System.out.println("\u001B[31mError: Test 5.\u001B[0m");
            String profilepic = null;
            try {
                //System.out.println("\u001B[31mError: Test 6.\u001B[0m");
                profilepic = saveImage(profileImageFile);
            } catch (IOException e) {
                System.out.println("Error saving image file");
            }
            String salt = BCrypt.gensalt(12);
            String hashedPassword = BCrypt.hashpw(passwordregister, salt);
            Joueur joueur = new Joueur(emailregister, hashedPassword, firstNameregister, lastNameregister, inGameNameregister);
            //joueur.setDob(Date.valueOf(dob));
            //System.out.println("\u001B[31mError: Test 7.\u001B[0m");
            joueur.setVerified(false);
            joueur.setBanned(false);
            joueur.setProfile(profilepic);
            joueur.setDatenai(date3);
            String role = "ROLE_USER";
            String[] roles = new String[]{"\"" + role + "\""};
            joueur.setRoles(roles);
            joueurDAO.insertTest(joueur);
            try {
                sendVerificationEmail(joueur.getEmail());
            } catch (MessagingException e) {
                System.out.println("Error sending verification email: " + e.getMessage());
            }
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("EmailHasBeenSent.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            /*if (verificationMethod.equals("email")) {
                try {
                    sendVerificationEmail(joueur.getEmail());
                } catch (MessagingException e) {
                    System.out.println("Error sending verification email: " + e.getMessage());
                }
                try {
                    Parent page1 = FXMLLoader.load(getClass().getResource("EmailHasBeenSent.fxml"));
                    Scene scene = new Scene(page1);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else if (verificationMethod.equals("sms")) {
                try {
                    Parent page1 = FXMLLoader.load(getClass().getResource("SMSMethod.fxml"));
                    Scene scene = new Scene(page1);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }*/

        }

        // Get the file path of the selected image
        //String imagePath = imageFile != null ? imageFile.getAbsolutePath() : null;
        // Insert the user into the database
        /*JoueurDAO joueurDAO = new JoueurDAO();
        Joueur joueur = new Joueur(emailregister, passwordregister, firstNameregister, lastNameregister, inGameNameregister);
        //joueur.setDob(Date.valueOf(dob));
        joueur.setVerified(false);
        joueur.setBanned(false);
        joueurDAO.insertTest(joueur);

        // Show success message
        Alert registerAlert = new Alert(AlertType.CONFIRMATION);
        registerAlert.setHeaderText("Done!");
        registerAlert.showAndWait();*/
    }

    public static void sendVerificationEmail(String recipientEmail) throws MessagingException {
        System.out.println("Test email");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ggamingnotifier@gmail.com", "foxmfudleljjlyrp");
            }
        };
// create session and authenticate
        Session session = Session.getInstance(props, auth);

        // create message
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("ggamingnotifier@gmail.com"));
        } catch (AddressException e) {
            System.out.println("Invalid email address: " + e.getMessage());
        }
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject("Confirmation email");

        // set content and add link to verify
        try {
            JoueurDAO joueurDAO = new JoueurDAO();
            int joueurId = joueurDAO.getJoueurIdByEmail(recipientEmail);
            String joueurIdStr = String.valueOf(joueurId);
            System.out.println(joueurId);
            String content = "Cher utilisateur,<br><br>Veuillez cliquer sur le lien ci-dessous pour confirmer votre inscription:<br><br>"
                    + "<a href=http://127.0.0.1:8000/verify/java/" + joueurIdStr + ">Confirmer l'inscription</a><br><br>"
                    + "Merci de votre attention,<br>Ggaming.";
            System.out.println(content);
            message.setContent(content, "text/html");

            // send message
            Transport.send(message);
            // TODO fix roles
            System.out.println("Confirmation email sent successfully.");
        } catch (SQLException e) {
            // handle the exception
            e.printStackTrace();
        }
        //System.out.println("Error sending confirmation email: " + e.getMessage());
    }

    /*@FXML
    public void ManipulateFacebookRegister(ActionEvent event) {
        String authUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + appId + "&redirect_uri=" + redirectUrl + "&scope=user_about_me,"
                + "user_actions.books,user_actions.fitness,user_actions.music,user_actions.news,user_actions.video,user_activities,user_birthday,user_education_history,"
                + "user_events,user_photos,user_friends,user_games_activity,user_groups,user_hometown,user_interests,user_likes,user_location,user_photos,user_relationship_details,"
                + "user_relationships,user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,ads_management,ads_read,email,"
                + "manage_notifications,manage_pages,publish_actions,read_friendlists,read_insights,read_mailbox,read_page_mailboxes,read_stream,rsvp_event";
        System.setProperty("webdirver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(authUrl);
        String accessToken;
        while (true) {

            if (!driver.getCurrentUrl().contains("facebook.com")) {
                String url = driver.getCurrentUrl();
                accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");

                driver.quit();

                FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_11_0);
                User user = fbClient.fetchObject("me", User.class);
                System.out.println(user.getName());
                //message.setText(user.getName());

            }
        }
    }*/
    

    private String extractAccessTokenFromUrl(String url) {
        String accessToken = null;
        int startIndex = url.indexOf("access_token=");
        int endIndex = url.indexOf("&");
        if (startIndex != -1 && endIndex != -1) {
            accessToken = url.substring(startIndex + 13, endIndex);
        }
        return accessToken;
    }
}
