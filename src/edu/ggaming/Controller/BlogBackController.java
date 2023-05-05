/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ggaming.Controller;

import edu.ggaming.utils.MyConnection;
import edu.ggaming.entities.Blog;
import edu.ggaming.entities.Commentaire;
import edu.ggaming.services.ServicesBlog;
import edu.ggaming.services.ServicesCommentaire;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.util.Properties;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import jxl.Workbook;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.controlsfx.control.Notifications;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 * FXML Controller class
 *
 * @author oness
 */
public class BlogBackController implements Initializable {

    @FXML
    private TableView<Blog> blogsTable;
    @FXML
    private TableView<Commentaire> CommentairesTable;
    @FXML
    private TableColumn<Blog, Integer> idcol;
    @FXML
    private TableColumn<Blog, Integer> etatcol;
    @FXML
    private TableColumn<Blog, Integer> likescol;
    @FXML
    private TableColumn<Blog, Integer> dislikescol;
    @FXML
    private TableColumn<Blog, String> titrecol;
    @FXML
    private TableColumn<Blog, String> contenucol;
    @FXML
    private TableColumn<Blog, LocalDateTime> datecreationcol;
    @FXML
    private TableColumn<Blog, LocalDateTime> datemodificationcol;
    @FXML
    private TableColumn<Blog, String> imagecol;
    @FXML
    private TextField titretxt;
    @FXML
    private TextField idtxt;
    @FXML
    private TextField etattxt;
    @FXML
    private TextField contenutxt;
    @FXML
    private TextField contenuCommentairetxt;
    @FXML
    private TextField idBlogTxt;
    @FXML
    private TextField idCommenttxt;
    @FXML
    private ImageView imageviewBlog;
    @FXML
    private TextField urlpathimage;

    @FXML
    private TableColumn<Commentaire, Integer> idcommentaire;
    @FXML
    private TableColumn<Commentaire, Integer> NombreSignaler;

    @FXML
    private TableColumn<Commentaire, String> contenucolC;
    @FXML
    private TableColumn<Commentaire, LocalDateTime> datecreationcolC;
    @FXML
    private TableColumn<Commentaire, LocalDateTime> datemodificationcolC;

    private ObservableList<Commentaire> commentaireList = FXCollections.observableArrayList();

    /**
     * *********************************************************
     */
    String query = null;
    private ServicesBlog servicesBlog;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Blog blog = null;
    ObservableList<Blog> blogList = FXCollections.observableArrayList();
    private boolean isCommentView = false;
    int index = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        servicesBlog = new ServicesBlog();
        servicesBlog.initConnection();
        loadDataBlog();
        loadDataCommentaire();
        isCommentView = false;
    }

    @FXML
    public void GoComments(ActionEvent event) throws IOException {
        isCommentView = true;

        Parent root = FXMLLoader.load(getClass().getResource("../views/commentaireB.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        loadDataCommentaire();
        refreshTableC();
    }

    @FXML
    public void RetourBlog(ActionEvent event) throws IOException {
        isCommentView = false;

        Parent root = FXMLLoader.load(getClass().getResource("../views/blogBack.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        loadDataBlog();
        refreshTable();
    }

    public void clearBlog() {
        titretxt.setText("");
        contenutxt.setText("");
        idtxt.setText("");
        etattxt.setText("");
        imageviewBlog.setImage(null);
        urlpathimage.setText("");
    }

    public void clearComment() {
        contenuCommentairetxt.setText("");
        idBlogTxt.setText("");
        idCommenttxt.setText("");
    }

    public void AnnulerBlog(ActionEvent event) {
        clearBlog();
    }

    public void AnnulerCommentaire(ActionEvent event) {
        clearComment();
    }

    public void addimageblog(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();

        FileChooser.ExtensionFilter exxFilterJPG = new FileChooser.ExtensionFilter("JPG files (.jpg)", "*.jpg");
        FileChooser.ExtensionFilter exxFilterPNG = new FileChooser.ExtensionFilter("PNG files (.png)", "*.png");
        chooser.getExtensionFilters().addAll(exxFilterJPG, exxFilterPNG);
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            try {
                BufferedImage bufferedimg = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedimg, null);
                imageviewBlog.setImage(image);
                String imageUrl = file.toURI().toString();
                urlpathimage.setText(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (imageviewBlog.getImage() != null) {
            String imageUrl = imageviewBlog.getImage().impl_getUrl();
            urlpathimage.setText(imageUrl);
        }
    }

    /**
     * ******************************************************************************
     */
    @FXML
    public void AjouterBlog(ActionEvent event) throws IOException {
        String titre = titretxt.getText().trim();
        String contenu = contenutxt.getText().trim();
        String idstring = idtxt.getText().trim();
        String etatstring = etattxt.getText().trim();
        String imageBlog = urlpathimage.getText().trim();

        if (titre.isEmpty()) {
            titretxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le titre");
            alert.showAndWait();
            return;
        }

        if (contenu.isEmpty()) {
            contenutxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le contenu");
            alert.showAndWait();
            return;
        }

        if (imageBlog.isEmpty()) {
            contenutxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez ajouter image");
            alert.showAndWait();
            return;
        }

        int etat;
        try {
            etat = Integer.parseInt(etatstring);
        } catch (NumberFormatException e) {
            etattxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir l'etat");
            alert.showAndWait();
            return;
        }

        LocalDateTime currentDate = LocalDateTime.now();
        Blog b = new Blog(titre, contenu, currentDate, currentDate, imageBlog, etat, 0, 0);
        ServicesBlog sb = new ServicesBlog();
        sb.initConnection();
        sb.ajouter(b);
        System.out.println("ajouter avec succes");
               
        refreshTable();
        clearBlog();
    }

    @FXML
    public void AjouterCommentaire(ActionEvent event) {
        String contenu = contenuCommentairetxt.getText().trim();
        String idblogstring = idBlogTxt.getText().trim();

        int idblog;
        try {
            idblog = Integer.parseInt(idblogstring);
        } catch (NumberFormatException e) {
            etattxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir l'id du blog");
            alert.showAndWait();
            return;
        }

        if (contenu.isEmpty()) {
            contenutxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le contenu");
            alert.showAndWait();
            return;
        }

        LocalDateTime currentDate = LocalDateTime.now();
        Commentaire c = new Commentaire(contenu, currentDate, currentDate, 0);

        ServicesCommentaire sc = new ServicesCommentaire();
        sc.initConnection();
        sc.ajouterCommentaire(c, idblog);
        refreshTableC();
    }

    /**
     * *************************************************************************
     */
    private void refreshTable() {
        try {
            blogList.clear();
            query = "SELECT * FROM blog";
            Connection connection = MyConnection.getInstance().getCnx();
            if (connection != null) {
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                System.out.println("test 1");

                while (resultSet.next()) {
                    LocalDateTime dateCreation = resultSet.getDate("date_creation").toLocalDate().atStartOfDay();
                    LocalDateTime dateModification = resultSet.getDate("date_modification").toLocalDate().atStartOfDay();
                    blogList.add(new Blog(
                            resultSet.getInt("id"),
                            resultSet.getInt("etat"),
                            resultSet.getString("titre"),
                            resultSet.getString("contenu"),
                            dateCreation,
                            dateModification,
                            resultSet.getString("image_blog"),
                            resultSet.getInt("likeblog"),
                            resultSet.getInt("dislikeblog")
                    ));
                    blogsTable.setItems(blogList);
                }
            } else {
                System.out.println("Database connection is null");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void refreshTableC() {
        try {
            commentaireList.clear();
            //blogList.clear();
            query = "SELECT * FROM commentaire";
            Connection connection = MyConnection.getInstance().getCnx();
            if (connection != null) {
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    LocalDateTime dateCreation = resultSet.getDate("date_creation").toLocalDate().atStartOfDay();
                    LocalDateTime dateModification = resultSet.getDate("date_modification").toLocalDate().atStartOfDay();
                    commentaireList.add(new Commentaire(
                            resultSet.getInt("id"),
                            resultSet.getString("contenu"),
                            dateCreation,
                            dateModification,
                            resultSet.getInt("reportCount")
                    ));
                    if (CommentairesTable != null) {
                        CommentairesTable.setItems(commentaireList);
                    }
                }

            } else {
                System.out.println("Database connection is null");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDataBlog() {
        try {
            refreshTable();
            idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            etatcol.setCellValueFactory(new PropertyValueFactory<>("etat"));
            titrecol.setCellValueFactory(new PropertyValueFactory<>("titre"));
            contenucol.setCellValueFactory(new PropertyValueFactory<>("contenu"));
            datecreationcol.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
            datemodificationcol.setCellValueFactory(new PropertyValueFactory<>("date_modification"));
            imagecol.setCellValueFactory(new PropertyValueFactory<>("imageblog"));
            likescol.setCellValueFactory(new PropertyValueFactory<>("like"));
            dislikescol.setCellValueFactory(new PropertyValueFactory<>("dislike"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataCommentaire() {
        try {
            refreshTableC();
            System.out.println(idcommentaire);
            idcommentaire.setCellValueFactory(new PropertyValueFactory<>("id"));
            contenucolC.setCellValueFactory(new PropertyValueFactory<>("contenu"));
            datecreationcolC.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
            datemodificationcolC.setCellValueFactory(new PropertyValueFactory<>("date_modification"));
            NombreSignaler.setCellValueFactory(new PropertyValueFactory<>("reportCount"));
            NombreSignaler.setCellFactory(column -> new TableCell<Commentaire, Integer>() {
                private final Button reportButton = new Button("Supprimer");

                {
                    reportButton.setOnAction(event -> {
                        int reportCount = getItem();
                        reportCount++;
                        setItem(reportCount);
                        if (reportCount >= 5) {
                            Commentaire commentaire = getTableView().getItems().get(getIndex());
                            try {
                                sendEmailToUser(commentaire);
                                ServicesCommentaire sc = new ServicesCommentaire();
                                sc.initConnection();
                                sc.deleteCommentaire(commentaire.getId());
                                refreshTableC();

                                reportButton.setVisible(false);
                                setTextFill(Color.RED);
                            } catch (MessagingException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                Logger.getLogger(BlogBackController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }

                @Override
                protected void updateItem(Integer reportCount, boolean empty) {
                    super.updateItem(reportCount, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(reportButton);
                        setText(reportCount.toString());
                        // Reset the font color to black
                        setTextFill(Color.BLACK);
                    }
                }

                private void sendEmailToUser(Commentaire commentaire) throws MessagingException, IOException {
                    String host = "smtp.gmail.com";
                    String port = "587";
                    String userName = "ons.khiari@esprit.tn";
                    String password = "201JFT4252";

                    Properties properties = new Properties();
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.host", host);
                    properties.put("mail.smtp.port", port);

                    // Create a Session object
                    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(userName, password);
                        }
                    });

                    try {
                        // Create a Message object
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(userName));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("onesskh@gmail.com"));
                        message.setSubject("Avertissement concernant les commentaires inappropriés");
                        // Set the message content as HTML
String messageText = "<html><head><style>"
    + "body { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; color: #333; line-height: 1.5em; }"
    + "h1, h2, h3, h4, h5, h6 { color: #333; font-weight: bold; }"
    + "p { margin-bottom: 10px; font-size: 20px; }"
    + "a { color: #0078e7; text-decoration: none; }"
    + "a:hover { color: #005fba; }"
    + "</style></head>"
    + "<body>"
    + "<p>Bonjour,</p>"
    + "<p>Je vous écris aujourd'hui au sujet des commentaires que vous avez publiés sur nos blogs.</p>"
    + "<p>Comme vous le savez, nous apprécions notre communauté et nous nous efforçons de maintenir un environnement positif et respectueux pour tous.</p>"
    + "<p><h1>Malheureusement, les commentaires que vous avez laissés ont été inappropriés et irrespectueux,</h1></p>"
    + "<p>ce qui n'est pas conforme aux normes de notre communauté.</p>"
    + "<p>Je voulais vous contacter personnellement pour vous faire savoir que si ce comportement persiste,</p>"
    + "<p>nous n'aurons d'autre choix que de désactiver votre compte.</p>"
    + "<p>Nous prenons l'intégrité de notre communauté très au sérieux et nous ne tolérerons aucun comportement contraire à nos valeurs.</p>"
    + "<p>Je comprends que vous n'ayez peut-être pas été conscient de l'impact de vos commentaires,</p>"
    + "<p>mais nous vous serions reconnaissants si vous pouviez prendre un moment pour réfléchir à l'impact que vos paroles ont sur les autres.</p>"
    + "<p>Nous voulons que notre communauté soit un endroit où chacun se sent en sécurité et valorisé.</p>"
    + "<p>Si vous avez des questions ou des préoccupations concernant cet avertissement,n'hésitez pas à me contacter.</p>"
    + "<p><h2>J'espère que nous pourrons travailler ensemble pour créer un environnement positif et respectueux pour tous les membres de notre communauté.</h2></p>"
    + "<p>Cordialement,</p>" +
    "<img src=\"cid:image1\">"
    + "</body></html>";
                        //message.setContent(messageText, "text/html");
                        MimeMultipart multipart = new MimeMultipart("related");
                        MimeBodyPart htmlPart = new MimeBodyPart();
                        htmlPart.setContent(messageText, "text/html");
                        multipart.addBodyPart(htmlPart);
                        File imageFile = new File("C:\\Users\\oness\\OneDrive\\Desktop\\Some 9raya shit\\pidev\\ggamingblack.png");
                        MimeBodyPart imagePart = new MimeBodyPart();
                        imagePart.attachFile(imageFile);
                        imagePart.setContentID("<image1>");
                        multipart.addBodyPart(imagePart);
                        message.setContent(multipart);

                        // Send the message
                        Transport.send(message);
                        System.out.println("email sent");
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * *****************************************************************************
     */
    @FXML
    private void GetSelected(MouseEvent event) {
        index = blogsTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        titretxt.setText(titrecol.getCellData(index).toString());
        contenutxt.setText(contenucol.getCellData(index).toString());
        idtxt.setText(idcol.getCellData(index).toString());
        etattxt.setText(etatcol.getCellData(index).toString());
        urlpathimage.setText(imagecol.getCellData(index).toString());

        String imageUrl = imagecol.getCellData(index).toString();
        if (!imageUrl.isEmpty()) {
            try {
                Image image = new Image(imageUrl);
                imageviewBlog.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void GetSelectedCommentaire(MouseEvent event) {
        index = CommentairesTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        contenuCommentairetxt.setText(contenucolC.getCellData(index).toString());
        idCommenttxt.setText(idcommentaire.getCellData(index).toString());
    }

    @FXML
    private void SupprimerBlog(ActionEvent event) {
        String idstring = idtxt.getText().trim();
        int id = Integer.parseInt(idstring);

        ServicesBlog sb = new ServicesBlog();
        sb.initConnection();
        sb.deleteBlog(id);
        refreshTable();
        clearBlog();
    }

    @FXML
    private void SupprimerCommentaire(ActionEvent event) {
        String idstring = idCommenttxt.getText().trim();
        int id = Integer.parseInt(idstring);

        ServicesCommentaire sc = new ServicesCommentaire();
        sc.initConnection();
        sc.deleteCommentaire(id);
        refreshTableC();
        clearComment();
    }

    /**
     * *****************************************************************************
     */
    @FXML
    private void EditerCommentaire(ActionEvent event) {
        String idstring = idCommenttxt.getText();
        String contenu = contenutxt.getText();

        int id = Integer.parseInt(idstring);
        LocalDateTime currentDate = LocalDateTime.now();
        Commentaire c = new Commentaire(id, contenu, currentDate, 0);

        ServicesCommentaire sc = new ServicesCommentaire();
        sc.initConnection();
        sc.updateCommentaire(c);
        refreshTableC();
        clearComment();
    }

    @FXML
    private void EditerBlog(ActionEvent event) {
        try {
            String idstring = idtxt.getText().trim();
            String etatstring = etattxt.getText().trim();
            String titre = titretxt.getText().trim();
            String contenu = contenutxt.getText().trim();
            String imageBlog = urlpathimage.getText().trim();

            if (titre.isEmpty()) {
                titretxt.setStyle("-fx-border-color: red");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir le titre");
                alert.showAndWait();
                return;
            }

            if (contenu.isEmpty()) {
                contenutxt.setStyle("-fx-border-color: red");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir le contenu");
                alert.showAndWait();
                return;
            }

            int etat;
            try {
                etat = Integer.parseInt(etatstring);
            } catch (NumberFormatException e) {
                etattxt.setStyle("-fx-border-color: red");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir l'etat");
                alert.showAndWait();
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idstring);
            } catch (NumberFormatException e) {
                etattxt.setStyle("-fx-border-color: red");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir l'id");
                alert.showAndWait();
                return;
            }

            if (etat == 2) {
                Notifications notificationBuilder = Notifications.create()
                        .title("Blog est publier !")
                        .text("Blog publier avec sucee !!")
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BOTTOM_RIGHT)
                        .onAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                System.out.println("Clicked");
                            }
                        });
                notificationBuilder.showConfirm();
            } else if (etat == 1) {
                Notifications notificationBuilder = Notifications.create()
                        .title("Blog est refuser !")
                        .text("Blog n'est pas accepter !!")
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BOTTOM_RIGHT)
                        .onAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                System.out.println("Clicked");
                            }
                        });
                notificationBuilder.showError();
            }

            LocalDateTime currentDate = LocalDateTime.now();
            Blog b = new Blog(id, titre, contenu, imageBlog, currentDate, etat);

            ServicesBlog sb = new ServicesBlog();
            sb.initConnection();
            sb.updateBlog(b);
            refreshTable();
            clearBlog();
        } catch (Exception e) {
        }
    }




/***********************************************************************/

    

    @FXML
    public void exportaction() {
       
        
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (.xls)", ".xls");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
               
                WritableWorkbook workbook = Workbook.createWorkbook(file);

                WritableSheet sheet = workbook.createSheet("jeux", 0);

                WritableCell cell = new jxl.write.Label(0, 0, "id");
                sheet.addCell(cell);
                WritableCell cell1 = new jxl.write.Label(1, 0, "titre");
                sheet.addCell(cell1);
                WritableCell cell2 = new jxl.write.Label(2, 0, "contenu");
                sheet.addCell(cell2);
                WritableCell cell3 = new jxl.write.Label(3, 0, "date_creation");
                sheet.addCell(cell3);
                WritableCell cell4 = new jxl.write.Label(4, 0, "date_modification");
                sheet.addCell(cell4);
                WritableCell cell5 = new jxl.write.Label(5, 0, "image_blog");
                sheet.addCell(cell5);
                WritableCell cell6 = new jxl.write.Label(6, 0, "etat");
                sheet.addCell(cell6);
                WritableCell cell7 = new jxl.write.Label(7, 0, "likeblog");
                sheet.addCell(cell7);
                WritableCell cell8 = new jxl.write.Label(8, 0, "dislikeblog");
                sheet.addCell(cell8);
              
                
            

               
                ServicesBlog serviceblog = new ServicesBlog();
                serviceblog.initConnection();
                List<Blog> blogs = serviceblog.getAllBlogs();

                int row = 1;
                for (Blog b : blogs) {
                  
                    sheet.addCell(new jxl.write.Label(0, row, Integer.toString(b.getId())));
                    sheet.addCell(new jxl.write.Label(1, row, b.getTitre()));
                    sheet.addCell(new jxl.write.Label(2, row, b.getContenu()));

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String creationDate = b.getDate_creation().format(formatter);
                    String modificationDate = b.getDate_modification().format(formatter);
                    sheet.addCell(new jxl.write.Label(3, row, creationDate));
                    sheet.addCell(new jxl.write.Label(4, row, modificationDate));

                    sheet.addCell(new jxl.write.Label(5, row, b.getImageblog()));
                    sheet.addCell(new jxl.write.Label(6, row, Integer.toString(b.getEtat())));
                    sheet.addCell(new jxl.write.Label(7, row, Integer.toString(b.getLike())));
                    sheet.addCell(new jxl.write.Label(8, row, Integer.toString(b.getDislike())));
                    
                  
                    row++;
                }

                workbook.write();
                workbook.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export to Excel");
                alert.setHeaderText(null);
                alert.setContentText("Data exported to " + file.getAbsolutePath());
                alert.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
                // show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export to Excel");
                alert.setHeaderText(null);
                alert.setContentText("Error: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    
    public void afficherTournois(ActionEvent event)
    {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("tournoiBack.fxml"));
              Scene scene = new Scene(root);
                
                Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
         } catch (IOException ex) {
             Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
     
    @FXML
    void afficherJeux(ActionEvent event) {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("jeuxb.fxml"));
              Scene scene = new Scene(root);
                
                Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
         } catch (IOException ex) {
             Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
     @FXML
    void afficherBlogs(ActionEvent event) {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("../views/blogBack.fxml"));
              Scene scene = new Scene(root);
                
                Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
         } catch (IOException ex) {
             Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
     @FXML
    void afficherEquipes(ActionEvent event) {
        Parent root;
         try {
              root = FXMLLoader.load(getClass().getResource("../views/BackEquipe.fxml"));
              Scene scene = new Scene(root);
                
                Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
         } catch (IOException ex) {
             Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
     @FXML
    void afficherBoutique(ActionEvent event) {
        Parent root;
         try {
              root = FXMLLoader.load(getClass().getResource("../views/boutiqueBack.fxml"));
              Scene scene = new Scene(root);
                
                Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
         } catch (IOException ex) {
             Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
     @FXML
    void afficherBlogsFront(ActionEvent event) {
        Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("../views/blogFront.fxml"));
              Scene scene = new Scene(root);
                
                Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
         } catch (IOException ex) {
             Logger.getLogger(BoutiqueBackController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
     
}
