/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.GUI;

import javafx.geometry.Insets;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ggaming.entity.Jeux;
import ggaming.entity.Tournoi;
import ggaming.entity.Mailer;
import ggaming.entity.TypeTournoi;
import ggaming.services.TournoiService;
import ggaming.tools.MaConnection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hayth
 */
public class TournoiHomeController implements Initializable {
    
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label description;

    @FXML
    private ImageView fruitImg;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private TextField tfNom;
    
    @FXML
    private GridPane grid;
    @FXML
    private GridPane gridR;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private ScrollPane scroll;
    private List<Tournoi> lt;
    private final TournoiService ts=new TournoiService();
    @FXML
    private ComboBox<String> tri;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> options = FXCollections.observableArrayList("Jeu","Alphabetique","Date");
        tri.setItems(options);

        lt = new ArrayList<>();
        lt.addAll(ts.afficherTournoi());     
        int column = 0;
        int row = 1;
            for (int i = 0; i < lt.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("TournoiItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                TournoiItemController itemController = fxmlLoader.getController();
                itemController.setData(lt.get(i));
                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                GridPane.setMargin(anchorPane, new Insets(10));
               //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

            } 
            catch (IOException ex) {
                System.out.print(ex);
            }
            } 
    }
    
    @FXML
    public void rechercherTournoi(){
        System.out.println("dd");
        lt = new ArrayList<>();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setHeaderText("");
        //nothing found message
        if(dateFrom.getValue()==null && dateTo.getValue()==null && tfNom.getText().isEmpty()){ //rien saisie
            alert.setContentText("Rien Saisie !");
            alert.showAndWait();
        }
        else if(dateFrom.getValue()==null || dateTo.getValue()==null && !tfNom.getText().isEmpty())//nom saisi date pas saisie
        {
                  lt.addAll(ts.rechercherTournoiParNom(tfNom.getText()));  
                  if(lt.isEmpty()){
                      alert.setContentText("Rien Trouvé !");
                      alert.showAndWait();
                  }
        }
        else if(dateFrom.getValue()!=null && dateTo.getValue()!=null && tfNom.getText().isEmpty()) //nom pas saisi date saisie
        {
            if(dateFrom.getValue().isAfter(dateTo.getValue())){
                alert.setContentText("La date de commencement doit etre superieur à la date de fin ");
                alert.showAndWait(); 
            }
            else{
            lt.addAll(ts.rechercherTournoiParDate(Date.valueOf(dateFrom.getValue().toString()), Date.valueOf(dateTo.getValue().toString())));
            if(lt.isEmpty()){
                alert.setContentText("Rien Trouvé !");
                alert.showAndWait();
                  }
            }
        }
        else if(dateFrom.getValue()!=null && dateTo.getValue()!=null && !tfNom.getText().isEmpty())//nom saisi et date saisie
        {
            lt.addAll(ts.rechercherTournoiParNomEtDate(tfNom.getText(), Date.valueOf(dateFrom.getValue().toString()), Date.valueOf(dateTo.getValue().toString())));
            if(lt.isEmpty()){
                alert.setContentText("Rien Trouvé !");
                alert.showAndWait();
            }
        }
        afficher(lt);
        dateFrom.setValue(null);
        dateTo.setValue(null);
        tfNom.setText("");
    }
    public void afficher(List<Tournoi> lt)
    {
      grid.getChildren().clear();    
      int column = 0;
        int row = 1;
            for (int i = 0; i < lt.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("TournoiItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                TournoiItemController itemController = fxmlLoader.getController();
                itemController.setData(lt.get(i));
                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                GridPane.setMargin(anchorPane, new Insets(10));
               //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

            } 
            catch (IOException ex) {
                System.out.print(ex);
            }
            } 
    }
    @FXML
    void goBackOffice(MouseEvent event) {
    try {
            Parent root = FXMLLoader.load(getClass().getResource("tournoiBack.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex);        
        }
    }
    
    @FXML
    void trier(ActionEvent event) {
        String selectedOption = tri.getValue();
        switch (selectedOption) {
            case "Date":
                afficher(ts.trierDate());
                break;
            case "Jeu":
                afficher(ts.trierJeu());
                break;
            case "Alphabetique":
                afficher(ts.trierAlpha());
                break;
            default:
                break;
        }
    }
    
    @FXML
    public void SendMailSSL()
    {
    String tournoi="";
        List<Tournoi> tournois = ts.getTournoiAujourdhui();
             for (Tournoi t: tournois)
                { 
tournoi=tournoi+"Le Tournoi : " + t.getNomTournoi()+" de type "+ t.getTypeTournoi()+" pour le jeu "+t.getJeu().getLibelle()+" avec une cagnotte de "+t.getPrix()+"<br>";
    }
             //from,password,to,subject,message  
Mailer.send("haythem.benkhaled@esprit.tn","yomwnyelvnrddkvy","haythembkhal@gmail.com","Profitez des meilleuirs tournois aujourd'hui","<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
"<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
"<head>\n" +
"<!--[if gte mso 9]>\n" +
"<xml>\n" +
"  <o:OfficeDocumentSettings>\n" +
"    <o:AllowPNG/>\n" +
"    <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
"  </o:OfficeDocumentSettings>\n" +
"</xml>\n" +
"<![endif]-->\n" +
"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"  <meta name=\"x-apple-disable-message-reformatting\">\n" +
"  <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n" +
"  <title></title>\n" +
"  \n" +
"    <style type=\"text/css\">\n" +
"      @media only screen and (min-width: 620px) {\n" +
"  .u-row {\n" +
"    width: 600px !important;\n" +
"  }\n" +
"  .u-row .u-col {\n" +
"    vertical-align: top;\n" +
"  }\n" +
"\n" +
"  .u-row .u-col-100 {\n" +
"    width: 600px !important;\n" +
"  }\n" +
"\n" +
"}\n" +
"\n" +
"@media (max-width: 620px) {\n" +
"  .u-row-container {\n" +
"    max-width: 100% !important;\n" +
"    padding-left: 0px !important;\n" +
"    padding-right: 0px !important;\n" +
"  }\n" +
"  .u-row .u-col {\n" +
"    min-width: 320px !important;\n" +
"    max-width: 100% !important;\n" +
"    display: block !important;\n" +
"  }\n" +
"  .u-row {\n" +
"    width: 100% !important;\n" +
"  }\n" +
"  .u-col {\n" +
"    width: 100% !important;\n" +
"  }\n" +
"  .u-col > div {\n" +
"    margin: 0 auto;\n" +
"  }\n" +
"}\n" +
"a[x-apple-data-detectors='true'] {\n" +
"  color: inherit !important;\n" +
"  text-decoration: none !important;\n" +
"}\n" +
"\n" +
"@media (max-width: 480px) { #u_content_image_2 .v-src-width { width: auto !important; } #u_content_image_2 .v-src-max-width { max-width: 36% !important; } #u_content_text_15 .v-container-padding-padding { padding: 4px 15px !important; } #u_content_text_15 .v-text-align { text-align: center !important; } #u_content_text_1 .v-container-padding-padding { padding: 4px 15px !important; } #u_content_text_1 .v-text-align { text-align: center !important; } }\n" +
"    </style>\n" +
"  \n" +
"  \n" +
"\n" +
"</head>\n" +
"\n" +
"<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #e7e7e7;color: #000000\">\n" +
"  <!--[if IE]><div class=\"ie-container\"><![endif]-->\n" +
"  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
"  <table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #e7e7e7;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
"  <tbody>\n" +
"  <tr style=\"vertical-align: top\">\n" +
"    <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
"    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #e7e7e7;\"><![endif]-->\n" +
"    \n" +
"\n" +
"<div class=\"u-row-container\" style=\"padding: 0px;background-color: #291962\">\n" +
"  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
"    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
"      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #291962;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
"      \n" +
"<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"  <div style=\"height: 100%;width: 100% !important;\">\n" +
"  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n" +
"  \n" +
"<table id=\"u_content_image_2\" style=\"font-family:tahoma,arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
"  <tbody>\n" +
"    <tr>\n" +
"      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:tahoma,arial,helvetica,sans-serif;\" align=\"left\">\n" +
"        \n" +
"<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
"  <tr>\n" +
"    <td class=\"v-text-align\" style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\n" +
"      \n" +
"      \n" +
"    </td>\n" +
"  </tr>\n" +
"</table>\n" +
"\n" +
"      </td>\n" +
"    </tr>\n" +
"  </tbody>\n" +
"</table>\n" +
"\n" +
"  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"  </div>\n" +
"</div>\n" +
"<!--[if (mso)|(IE)]></td><![endif]-->\n" +
"      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
"    </div>\n" +
"  </div>\n" +
"</div>\n" +
"\n" +
"\n" +
"\n" +
"<div class=\"u-row-container\" style=\"padding: 50px 0px 40px;background-color: transparent\">\n" +
"  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
"    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
"      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 50px 0px 40px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
"      \n" +
"<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"  <div style=\"height: 100%;width: 100% !important;\">\n" +
"  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n" +
"  \n" +
"<table style=\"font-family:tahoma,arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
"  <tbody>\n" +
"    <tr>\n" +
"      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:tahoma,arial,helvetica,sans-serif;\" align=\"left\">\n" +
"        \n" +
"  <h1 class=\"v-text-align\" style=\"margin: 0px; color: #291962; line-height: 140%; text-align: center; word-wrap: break-word; font-family: arial black,AvenirNext-Heavy,avant garde,arial; font-size: 34px; \">Le Programme d'Aujourd'hui</h1>\n" +
"\n" +
"      </td>\n" +
"    </tr>\n" +
"  </tbody>\n" +
"</table>\n" +
"\n" +
"<table style=\"font-family:tahoma,arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
"  <tbody>\n" +
"    <tr>\n" +
"      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:tahoma,arial,helvetica,sans-serif;\" align=\"left\">\n" +
"        \n" +
"    <p style=\"font-size: 14px; line-height: 200%;\">"+tournoi+
                        "</p>\n" +
"\n" +
"      </td>\n" +
"    </tr>\n" +
"  </tbody>\n" +
"</table>\n" +
"\n" +
"<table style=\"font-family:tahoma,arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
"  <tbody>\n" +
"    <tr>\n" +
"      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:tahoma,arial,helvetica,sans-serif;\" align=\"left\">\n" +
"        \n" +
"  <!--[if mso]><style>.v-button {background: transparent !important;}</style><![endif]-->\n" +
"<div class=\"v-text-align\" align=\"center\">\n" +
"  <!--[if mso]><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"\" style=\"height:52px; v-text-anchor:middle; width:133px;\" arcsize=\"7.5%\"  stroke=\"f\" fillcolor=\"#291962\"><w:anchorlock/><center style=\"color:#FFFFFF;font-family:tahoma,arial,helvetica,sans-serif;\"><![endif]-->  \n" +
"  <!--[if mso]></center></v:roundrect><![endif]-->\n" +
"</div>\n" +
"\n" +
"      </td>\n" +
"    </tr>\n" +
"  </tbody>\n" +
"</table>\n" +
"\n" +
"  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"  </div>\n" +
"</div>\n" +
"<!--[if (mso)|(IE)]></td><![endif]-->\n" +
"      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
"    </div>\n" +
"  </div>\n" +
"</div>\n" +
"\n" +
"\n" +
"\n" +
"<div class=\"u-row-container\" style=\"padding: 50px 0px 11px;background-color: #291962\">\n" +
"  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
"    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
"      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 50px 0px 11px;background-color: #291962;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
"      \n" +
"<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"  <div style=\"height: 100%;width: 100% !important;\">\n" +
"  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n" +
"  \n" +
"<table style=\"font-family:tahoma,arial,helvetica,sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
"  <tbody>\n" +
"    <tr>\n" +
"      <td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:tahoma,arial,helvetica,sans-serif;\" align=\"left\">\n" +
"        \n" +
"  <h1 class=\"v-text-align\" style=\"margin: 0px; color: #ffffff; line-height: 140%; text-align: center; word-wrap: break-word; font-family: arial black,AvenirNext-Heavy,avant garde,arial; font-size: 34px; \">Contacez Nous Si Besoin !</h1>\n" +
"\n" +
"      </td>\n" +
"    </tr>\n" +
"  </tbody>\n" +
"</table>\n" +
"\n" +
"<div class=\"u-row-container\" style=\"padding: 0px 0px 11px;background-color: #201756\">\n" +
"  <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
"    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
"      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px 0px 11px;background-color: #201756;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
"      \n" +
"<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"  <div style=\"height: 100%;width: 100% !important;\">\n" +
"  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n" +
"  \n" +
"\n" +
"  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"  </div>\n" +
"</div>\n" +
"<!--[if (mso)|(IE)]></td><![endif]-->\n" +
"      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
"    </div>\n" +
"  </div>\n" +
"</div>\n" +
"\n" +
"\n" +
"    <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
"    </td>\n" +
"  </tr>\n" +
"  </tbody>\n" +
"  </table>\n" +
"  <!--[if mso]></div><![endif]-->\n" +
"  <!--[if IE]></div><![endif]-->\n" +
"</body>\n" +
"\n" +
"</html>");  
     //change from, password and to  
    }
}
