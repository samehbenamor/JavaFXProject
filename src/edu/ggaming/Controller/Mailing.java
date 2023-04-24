
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.Controller;

/**
 *
 * @author hayth
 */
import java.util.Properties;
import javafx.scene.control.Alert;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;       
public class Mailing{  
    public static void send(String from,String password,String to,String sub,String msg){  
          //Get properties object    
          Properties props = new Properties();    
          props.put("mail.smtp.host", "smtp.gmail.com");    
          props.put("mail.smtp.socketFactory.port", "465");    
          props.put("mail.smtp.socketFactory.class",    
                    "javax.net.ssl.SSLSocketFactory");    
          props.put("mail.smtp.auth", "true");    
          props.put("mail.smtp.port", "465"); 
          props.put("mail.smtp.ssl.enable", "true");
          props.put("mail.smtp.ssl.protocols", "TLSv1.2");

          //get Session   
          Session session = Session.getDefaultInstance(props,    
           new javax.mail.Authenticator() {    
           @Override
           protected PasswordAuthentication getPasswordAuthentication() {    
           return new PasswordAuthentication(from,password);  
           }    
          });    
          //compose message    
          try {    
           MimeMessage message = new MimeMessage(session);    
           message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
           message.setSubject(sub);    
           message.setContent(msg,"text/html");    
           //send message  
           Transport.send(message);    
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Confirmation");
           alert.setHeaderText("");
           alert.setContentText("Verifiez Votre Boite Mail");
           alert.showAndWait();
          } catch (MessagingException e) {
              System.out.println(e);
          }    
             
    }  
} 

/*
public class SendMailSSL{    
 public static void main(String[] args) {    
     //from,password,to,subject,message  
     Mailer.send("from@gmail.com","xxxxx","to@gmail.com","hello javatpoint","How r u?");  
     //change from, password and to  
 }    
}   */ 




