/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;

/**
 *
 * @author DELL
 */
public class Chatbot {
    @FXML
private WebView chat;
    @FXML
public void initialize() {
    chat.getEngine().load("https://www.google.com");
}
}
