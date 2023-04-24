

package edu.ggaming.Controller;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Sms  {

    // Votre clé de compte et votre jeton d'authentification Twilio
    public static final String ACCOUNT_SID = "AC3900524ff6d569c8928c2b4778c6f12a";
    public static final String AUTH_TOKEN = "72fab090620bc3c4bc79c5c80834dae3";

  

    public void sendSMS(String to, String message) {
        // Initialiser la bibliothèque Twilio avec votre clé de compte et votre jeton d'authentification
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Envoyer le message en utilisant l'API Twilio
        Message smsMessage = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber("+17078737302"), // Votre numéro Twilio
                message)
            .create();

        // Afficher le SID du message envoyé
        System.out.println("Message envoyé avec succès, SID : " + smsMessage.getSid());
    }
}