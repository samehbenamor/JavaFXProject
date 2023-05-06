/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming2;
import ggaming.cnx.MaConnection;
import java.sql.Connection;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import java.util.Map;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import com.google.common.base.Splitter;
import javafx.scene.web.WebView;
//import jdk.nashorn.internal.codegen.Splitter;
/**
 *
 * @author DELL
 */

public class RegistrationConfirmationController {
    private final Connection cnx;

    public RegistrationConfirmationController() {
        this.cnx = MaConnection.getInstance().getCnx();
    }
    @FXML
    private WebView webView;

    public void initialize() {
        webView.getEngine().locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldLoc, String newLoc) {
                /*if (newLoc.startsWith("myapp://")) {
                    handleMyAppUrl(newLoc);
                }*/
                if (newLoc.startsWith("http://127.0.0.1:8000/")) {
                    handleMyAppUrl(newLoc);
                }
            }
        });
    }

    private void handleMyAppUrl(String url) {
    // Parse the URL to extract the user ID
    String userIdString = null;
    try {
        URI uri = new URI(url);
        String query = uri.getQuery();
        Map<String, String> queryParams = Splitter.on('&')
                .trimResults()
                .withKeyValueSeparator("=")
                .split(query);
        userIdString = queryParams.get("id");
    } catch (URISyntaxException e) {
        // Handle invalid URL
        e.printStackTrace();
        return;
    }

    if (userIdString == null) {
        // Handle missing user ID
        return;
    }

    // Convert the user ID to an integer
    int userId = Integer.parseInt(userIdString);

    // Update the is_verified field in your database for the user with this ID
    try {
        //Connection cnx = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        String query = "UPDATE joueur SET is_verified = 1 WHERE id = ?";
        PreparedStatement stmt = cnx.prepareStatement(query);
        stmt.setInt(1, userId);
        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated == 1) {
            System.out.println("User with ID " + userId + " has been verified.");
        } else {
            System.out.println("No user was found with ID " + userId + ".");
        }
    } catch (SQLException e) {
        // Handle database error
        e.printStackTrace();
    }
}


    /*private void updateUserVerificationStatus(String verificationCode) {
        // Update the user's verification status in the database based on the verification code
        // You will need to implement this method according to how you have set up your database
    }*/
}
