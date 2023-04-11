package ggaming2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.fxml.FXML;

import ggaming.cnx.MaConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author DELL
 */
public class LoginController {
    // Inject your FXML controls here

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
            // If the credentials are valid, display a success message
            wrongLogIn.setText("Login successful");
        } else {
            // If the credentials are invalid, display an error message
            wrongLogIn.setText("Invalid email or password");
        }
    }

    // Check the user's credentials against your database
    private boolean checkCredentials(String emailLogin, String pass) {
        try {
            PreparedStatement statement = cnx.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            statement.setString(1, emailLogin);
            statement.setString(2, pass);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // true if a row is returned, false otherwise
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Other methods and variables for your controller class
}
