package ggaming2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.fxml.FXML;
import org.mindrot.jbcrypt.BCrypt;
import ggaming.cnx.MaConnection;
import ggaming.entity.Joueur;
import ggaming.services.JoueurDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import ggaming2.SessionManager;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.servlet.http.HttpServletRequest;
import ggaming2.UserProfile;
import ggaming2.Global;
import javax.servlet.http.HttpSession;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class LoginController {
    // Inject your FXML controls here
    public String sessionId;
    
    private final Connection cnx;

    public LoginController() {
        this.cnx = MaConnection.getInstance().getCnx();
    }
    /*@FXML
    private ImageView loginLogo;*/

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label wrongLogIn;

    // Wanted to programmatically load an image.
    /*@Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("D:\\Photoshop\\Web project\\Charte graphique\\w.png");
        loginLogo.setImage(image);
    }*/

    // Event handler for the login button
    @FXML
    private void handleLogin(ActionEvent event) {
        // Get the user's email and password from the text fields
        String emailLogin = email.getText();
        String pass = password.getText();

        // Check the email and password against your database
        if (checkCredentials(emailLogin, pass)) {
            JoueurDAO joueurDAO = new JoueurDAO();
            
            Joueur joueur = joueurDAO.getJoueurByEmail(emailLogin);
            if (joueur.isBanned()) {
                try {
                //UserProfile controller = new UserProfile();
                //controller.setSessionId(sessionId);
                
                Parent page1 = FXMLLoader.load(getClass().getResource("Banned.fxml"));
                Scene scene = new Scene(page1);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfile.fxml"));;
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                //loader.setController(controller);
                //controller.setSessionId(sessionId);
                
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            } else {
            sessionId = SessionManager.createSession(joueur);
            
            Global.sessionId = sessionId;
            System.out.println("Session ID in the login: " + Global.sessionId);
            
            // If the credentials are valid, display a success message
            
            try {
                //UserProfile controller = new UserProfile();
                //controller.setSessionId(sessionId);
                
                Parent page1 = FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
                Scene scene = new Scene(page1);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfile.fxml"));;
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                //loader.setController(controller);
                //controller.setSessionId(sessionId);
                
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            }
        } else {
            // If the credentials are invalid, display an error message
            wrongLogIn.setText("Email ou mot de passe invalide");
        }
    }

    // Check the user's credentials against your database
    private boolean checkCredentials(String emailLogin, String pass) {
        try {
            PreparedStatement statement = cnx.prepareStatement("SELECT * FROM joueur WHERE email = ?");
            statement.setString(1, emailLogin);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String hashedPass = resultSet.getString("password");
                
                return BCrypt.checkpw(pass, hashedPass);
            } else {
                return false; // email not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Other methods and variables for your controller class
}
