/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ggaming.Controller;

import ggaming.cnx.MyConnection;
import ggaming.entity.Blog;
import ggaming.entity.Commentaire;
import ggaming.services.ServicesBlog;
import ggaming.services.ServicesCommentaire;
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
import javafx.util.Duration;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.controlsfx.control.Notifications;

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

/************************************************************/
    String query = null;
    private ServicesBlog servicesBlog;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Blog blog = null ;
    ObservableList<Blog>  blogList = FXCollections.observableArrayList();
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
        //loadDataCommentaire();
        isCommentView = false;
    }

    @FXML
    public void GoComments(ActionEvent event) throws IOException {
        isCommentView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/commentaireB.fxml"));
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

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/blogBack.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        loadDataBlog();
        refreshTable();
    }

    public void clearBlog(){
        titretxt.setText("");
        contenutxt.setText("");
        idtxt.setText("");
        etattxt.setText("");
        imageviewBlog.setImage(null);
        urlpathimage.setText("");
    }

    public void clearComment(){
        contenuCommentairetxt.setText("");
        idBlogTxt.setText("");
        idCommenttxt.setText("");
    }

    public void AnnulerBlog(ActionEvent event){
        clearBlog();
    }

    public void AnnulerCommentaire(ActionEvent event){
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










/*********************************************************************************/







    @FXML
    public void AjouterBlog(ActionEvent event) throws IOException{
        String titre = titretxt.getText().trim();
        String contenu = contenutxt.getText().trim();
        String idstring = idtxt.getText().trim();
        String etatstring = etattxt.getText().trim();
        String imageBlog=urlpathimage.getText().trim();

        if(titre.isEmpty()){
            titretxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le titre");
            alert.showAndWait();
            return;
        }

        if(contenu.isEmpty()){
            contenutxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le contenu");
            alert.showAndWait();
            return;
        }

        if(imageBlog.isEmpty()){
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
        Blog b = new Blog(titre,contenu,currentDate,currentDate,imageBlog, etat);
        ServicesBlog sb = new ServicesBlog();
        sb.initConnection();
        sb.ajouter(b);
        refreshTable();
        clearBlog();
    }

    @FXML
    public void AjouterCommentaire(ActionEvent event){
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

        if(contenu.isEmpty()){
            contenutxt.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le contenu");
            alert.showAndWait();
            return;
        }

        LocalDateTime currentDate = LocalDateTime.now();
        Commentaire c = new Commentaire(contenu,currentDate,currentDate,0);

        ServicesCommentaire sc = new ServicesCommentaire();
        sc.initConnection();
        sc.ajouterCommentaire(c,idblog);
        refreshTableC();
    }




/****************************************************************************/




    private void refreshTable(){
        try{
            blogList.clear();
            query = "SELECT * FROM `blog`";
            Connection connection = MyConnection.getInstance().getCnx();
            if (connection != null) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                LocalDateTime dateCreation = resultSet.getDate("date_creation").toLocalDate().atStartOfDay();
                LocalDateTime dateModification = resultSet.getDate("date_modification").toLocalDate().atStartOfDay();
                    blogList.add(new Blog(
                        resultSet.getInt("id"),
                        resultSet.getInt("etat"),
                        resultSet.getString("titre"),
                        resultSet.getString("contenu"),
                        dateCreation,
                        dateModification,
                        resultSet.getString("image_blog")
                    ));
                    blogsTable.setItems(blogList);
                }
            } else {
                System.out.println("Database connection is null");
            }
        }catch(SQLException ex){
            Logger.getLogger(BlogBackController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    private void refreshTableC(){
        try{
            commentaireList.clear();
            blogList.clear();
            query = "SELECT * FROM `commentaire`";
            Connection connection = MyConnection.getInstance().getCnx();
            if (connection != null) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
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
        }catch(SQLException ex){
            Logger.getLogger(BlogBackController.class.getName()).log(Level.SEVERE,null,ex);
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
                            try{
                                sendEmailToUser(commentaire);
                                ServicesCommentaire sc = new ServicesCommentaire();
                                sc.initConnection();
                                sc.deleteCommentaire(commentaire.getId());
                                refreshTableC();

                                reportButton.setVisible(false);
                                setTextFill(Color.RED);
                            } catch (MessagingException ex) {
                                ex.printStackTrace();
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

                private void sendEmailToUser(Commentaire commentaire) throws MessagingException {
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

                    try{
                        // Create a Message object
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(userName));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("onesskh@gmail.com"));
                        message.setSubject("Avertissement concernant les commentaires inappropriés");
                        message.setText("Je vous écris aujourd'hui au sujet des commentaires que vous avez publiés sur nos blogs. Comme vous le savez, nous apprécions notre communauté et nous nous efforçons de maintenir un environnement positif et respectueux pour tous. Malheureusement, les commentaires que vous avez laissés ont été inappropriés et irrespectueux, ce qui n'est pas conforme aux normes de notre communauté.\n" +
                        "\n" +
                        "Je voulais vous contacter personnellement pour vous faire savoir que si ce comportement persiste, nous n'aurons d'autre choix que de désactiver votre compte. Nous prenons l'intégrité de notre communauté très au sérieux et nous ne tolérerons aucun comportement contraire à nos valeurs.\n" +
                        "\n" +
                        "Je comprends que vous n'ayez peut-être pas été conscient de l'impact de vos commentaires, mais nous vous serions reconnaissants si vous pouviez prendre un moment pour réfléchir à l'impact que vos paroles ont sur les autres. Nous voulons que notre communauté soit un endroit où chacun se sent en sécurité et valorisé.\n" +
                        "\n" +
                        "Si vous avez des questions ou des préoccupations concernant cet avertissement, n'hésitez pas à me contacter. J'espère que nous pourrons travailler ensemble pour créer un environnement positif et respectueux pour tous les membres de notre communauté.\n" +
                        "\n" +
                        "Cordialement,");

                        // Send the message
                        Transport.send(message);
                        System.out.println("email sent");
                    }catch(MessagingException e){
                        e.printStackTrace();
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





/********************************************************************************/





    @FXML
    private void GetSelected(MouseEvent event){
        index = blogsTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
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
    private void GetSelectedCommentaire(MouseEvent event){
        index = CommentairesTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        contenuCommentairetxt.setText(contenucolC.getCellData(index).toString());
        idCommenttxt.setText(idcommentaire.getCellData(index).toString());
    }


    @FXML
    private void SupprimerBlog(ActionEvent event){
        String idstring = idtxt.getText().trim();
        int id = Integer.parseInt(idstring);

        ServicesBlog sb = new ServicesBlog();
        sb.initConnection();
        sb.deleteBlog(id);
        refreshTable();
        clearBlog();
    }

    @FXML
    private void SupprimerCommentaire(ActionEvent event){
        String idstring = idCommenttxt.getText().trim();
        int id = Integer.parseInt(idstring);

        ServicesCommentaire sc = new ServicesCommentaire();
        sc.initConnection();
        sc.deleteCommentaire(id);
        refreshTableC();
        clearComment();
    }




/********************************************************************************/



    @FXML
    private void EditerCommentaire(ActionEvent event){
        String idstring = idCommenttxt.getText();
        String contenu = contenutxt.getText();

        int id = Integer.parseInt(idstring);
        LocalDateTime currentDate = LocalDateTime.now();
        Commentaire c = new Commentaire(id,contenu,currentDate,0);

        ServicesCommentaire sc = new ServicesCommentaire();
        sc.initConnection();
        sc.updateCommentaire(c);
        refreshTableC();
        clearComment();
    }

    @FXML
    private void EditerBlog(ActionEvent event){
        try{
            String idstring = idtxt.getText().trim();
            String etatstring = etattxt.getText().trim();
            String titre = titretxt.getText().trim();
            String contenu = contenutxt.getText().trim();
            String imageBlog=urlpathimage.getText().trim();

            if(titre.isEmpty()){
                titretxt.setStyle("-fx-border-color: red");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir le titre");
                alert.showAndWait();
                return;
            }

            if(contenu.isEmpty()){
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

            if (etat == 2){
                Notifications notificationBuilder = Notifications.create()
                    .title("Blog est publier !")
                    .text("Blog publier avec sucee !!")
                    .graphic(null)
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event){
                        System.out.println("Clicked");
                    }
                });
                notificationBuilder.showConfirm();
            }else if (etat == 1 ){
                Notifications notificationBuilder = Notifications.create()
                    .title("Blog est refuser !")
                    .text("Blog n'est pas accepter !!")
                    .graphic(null)
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event){
                        System.out.println("Clicked");
                    }
                });
                notificationBuilder.showError();
            }

            LocalDateTime currentDate = LocalDateTime.now();
            Blog b = new Blog(id,titre,contenu,imageBlog,currentDate,etat);

            ServicesBlog sb = new ServicesBlog();
            sb.initConnection();
            sb.updateBlog(b);
            refreshTable();
            clearBlog();
        }catch(Exception e){
        }
    }
}
