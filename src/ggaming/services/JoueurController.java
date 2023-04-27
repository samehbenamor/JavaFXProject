/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.services;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

import ggaming.cnx.MaConnection;
import javafx.scene.control.TableCell;
import java.sql.Connection;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ggaming.entity.Joueur;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import java.time.Period;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import org.mindrot.jbcrypt.BCrypt;
import java.time.LocalDate;
import java.util.Calendar;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import java.util.List;
import java.util.UUID;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import org.json.JSONObject;
//For bluetooth and shit
import javafx.scene.image.Image;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.OutputStream;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
//For qr code and shit 
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

//
/**
 *
 * @author DELL
 */
public class JoueurController {

    private final Connection cnx;

    public JoueurController() {
        this.cnx = MaConnection.getInstance().getCnx();
    }
    @FXML
    private TableView<Joueur> joueursTable;

    /////The fields below this comment will be used for adding the user in the backend
    @FXML
    private Button ajouttext;
    @FXML
    private Button browseimg;
    @FXML
    private TextField nomtext;
    @FXML
    private ImageView viewimg;
    @FXML
    private TextField prenomtext;
    @FXML
    private TextField igntext;
    @FXML
    private PasswordField mdptext;
    @FXML
    private TextField emailtext;
    @FXML
    private TextField winstext;
    @FXML
    private TextField losestext;
    @FXML
    private DatePicker datenaistext;
    @FXML
    private ComboBox<String> rolescombo;

    /*@FXML
    private TableColumn<Joueur, Integer> idCol;*/
    @FXML
    private TableColumn<Joueur, String> email;
    @FXML
    private TableColumn<Joueur, String> pprofile;

    @FXML
    private TableColumn<Joueur, String> nom;
    @FXML
    private TableColumn<Joueur, String> prenom;
    @FXML
    private TableColumn<Joueur, String> datenai;

    @FXML
    private TableColumn<Joueur, String> is_banned;

    @FXML
    private TableColumn<Joueur, String> is_verified;

    @FXML
    private TableColumn<Joueur, String> ign;
    @FXML
    private TableColumn<Joueur, String> wins;
    @FXML
    private TableColumn<Joueur, String> loses;

    private String selectedEmail;
    /*@FXML
    private TableColumn<Joueur, String> nomCol;*/
    private final JoueurDAO joueurDAO = new JoueurDAO();

    //For the search
    @FXML
    private TextField usersearch;
    @FXML
    private Button searchbutt;
    @FXML
    private Button cleareverything;

    /////
    //Functinos concerning the search
    private void updateTableView(String searchTerm) {
        List<Joueur> searchResults = joueurDAO.searchJoueur(searchTerm); // Call your search function
        joueursTable.getItems().clear(); // Clear existing items in the table view

        joueursTable.setItems(FXCollections.observableArrayList(searchResults)); // Add the search results to the table view
    }

    @FXML
    private void setupSearchButton(ActionEvent event) {
        Button searchButton = searchbutt;

        String searchTerm = usersearch.getText();
        updateTableView(searchTerm);

        // Add an event listener that changes the background color of the search button when the mouse hovers over it
        searchButton.setOnMouseEntered(e -> {

            searchButton.setStyle("-fx-background-color: yellow;");

        });

        // Add an event listener that resets the background color of the search button when the mouse exits
        searchButton.setOnMouseExited(e -> {

            searchButton.setStyle("-fx-background-color: transparent;");

        });
    }

    //
    @FXML
    private void handleAddUserButtonAction(ActionEvent event) {
        String nom = nomtext.getText();
        String prenom = prenomtext.getText();
        String ign = igntext.getText();
        String password = mdptext.getText();
        String email = emailtext.getText();
        String winsString = winstext.getText();
        int wins;
        String lossesString = losestext.getText();
        int losses;
        //String datanai = datenaistext.getText();
        LocalDate datee = datenaistext.getValue();
        DateTimeFormatter formatter69 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = datee.format(formatter69);
        Date date3 = Date.valueOf(formattedDate);
        String datenaistext2 = datee.toString();

        String selectedValue = rolescombo.getValue();
        String[] roles = selectedValue.split(",");

        boolean isVerified = false; // set to true if the user has been verified

        //String profile = "test";
        String profilepic = null;
        try {
            //System.out.println("\u001B[31mError: Test 6.\u001B[0m");
            profilepic = saveImage(profileImageFile);
        } catch (IOException e) {
            System.out.println("Error saving image file");
        }
        boolean isBanned = false; // set to true if the user has been banned

        //Date createdAt = new Date(joueur.getCreatedAt().getTime());
        // validate input fields here, e.g. check if required fields are filled
        //////////////////////VALIDATION
        // validate nom
        if (nom.isEmpty() || nom.length() > 15 || !nom.matches("[a-zA-Z]+")) {
            //showAlert("Nom invalide! Il doit être composé de lettres seulement et ne doit pas dépasser 15 caractères.");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Nom invalide!");
            alert.setContentText("Nom invalide! Il doit être composé de lettres seulement et ne doit pas dépasser 15 caractères.");
            alert.showAndWait();
            return;
        }

// validate prenom
        if (prenom.isEmpty() || prenom.length() > 15 || !prenom.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Prenom invalide!");
            alert.setContentText("Prenom invalide! Il doit être composé de lettres seulement et ne doit pas dépasser 15 caractères.");
            alert.showAndWait();
            return;

        }

// validate ign
        if (ign.isEmpty() || ign.length() > 15) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("IGN invalide!");
            alert.setContentText("IGN invalide! Il ne doit pas dépasser 15 caractères.");
            alert.showAndWait();
            return;
        }

// validate password
        if (password.isEmpty() || password.length() > 10 || !password.matches("^(?=.*[0-9])(?=.*[A-Z]).{1,}$")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Mot de passe invalide!");
            alert.setContentText("Mot de passe invalide! Il doit contenir au moins 1 chiffre, 1 lettre majuscule et ne doit pas dépasser 10 caractères.");
            alert.showAndWait();
            return;
        }

// validate email
        if (email.isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Email invalide!");
            alert.setContentText("Email invalide! Veuillez saisir une adresse email valide.");
            alert.showAndWait();
            return;
        }

// validate wins
        try {
            wins = Integer.parseInt(winsString);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Victoires invalide! Veuillez saisir un entier.");
            alert.showAndWait();
            return;
        }

// validate losses
        try {
            losses = Integer.parseInt(winsString);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Defaites invalide! Veuillez saisir un entier.");
            alert.showAndWait();
            return;
        }

// validate datanai
        if (datenaistext2.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Date de naissance invalide!");
            alert.setContentText("Date de naissance invalide!");
            alert.showAndWait();

            return;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date2 = LocalDate.parse(datenaistext2, formatter);
            LocalDate now = LocalDate.now();
            int age = Period.between(date2, now).getYears();
            if (age < 16) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Date de naissance invalide!");
                alert.setContentText("L'âge doit être supérieur ou égal à 16 ans.");
                alert.showAndWait();
                return;

            }
        } catch (DateTimeParseException e) {
            //showAlert("Date de naissance invalide! Veuillez saisir une date de naissance valide (format: jj/mm/aaaa).");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Date de naissance invalide!");
            alert.setContentText("Date de naissance invalide! Veuillez saisir une date de naissance valide (format: jj/mm/aaaa).");
            alert.showAndWait();
            return;
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        Joueur joueur = new Joueur(email, roles, hashedPassword, isVerified, nom, prenom, date3, profilepic, isBanned, ign, wins, losses);

        try {
            // Insert the new Joueur into the database using the DAO
            joueurDAO.insert(joueur);
            System.out.println("Joueur added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding joueur: " + e.getMessage());

            //I hate java with the deepest roots of my heart, I hope however invented javaFX roots in hell for his sins.
        }

        // clear input fields
        nomtext.clear();
        prenomtext.clear();
        datenaistext.setValue(null);
        mdptext.clear();
        igntext.clear();
        emailtext.clear();
        winstext.clear();
        losestext.clear();
        updateData();

    }

    private File profileImageFile;

    private static final String IMAGE_FOLDER = "C:\\xampp\\htdocs\\PI\\Ggaming\\public\\userImages\\";

    public static String saveImage(File imageFile) throws IOException {
        //System.out.println("Test 3");
        String originalFilename = imageFile.getName();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;
        Path destination = Paths.get(IMAGE_FOLDER + newFilename);
        Files.copy(imageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        return newFilename;
    }
    private static final String IMGUR_CLIENT_ID = "3d02f319c246baf";
    private static final String IMGUR_API_URL = "https://api.imgur.com/3/image";

    @FXML
    public void clear(ActionEvent event) {
        nomtext.clear();
        prenomtext.clear();
        datenaistext.setValue(null);
        mdptext.clear();
        igntext.clear();
        emailtext.clear();
        winstext.clear();
        losestext.clear();
        usersearch.clear();
        updateData();
    }

    @FXML
    void handleRowClick() {
        Joueur selectedJoueur = joueursTable.getSelectionModel().getSelectedItem();

        if (selectedJoueur != null) {
            nomtext.setText(selectedJoueur.getNom());
            prenomtext.setText(selectedJoueur.getPrenom());
            igntext.setText(selectedJoueur.getIgn());
            emailtext.setText(selectedJoueur.getEmail());
            winstext.setText(String.valueOf(selectedJoueur.getWins()));
            losestext.setText(String.valueOf(selectedJoueur.getLoses()));
            mdptext.clear();
            selectedEmail = selectedJoueur.getEmail();
            System.out.println("Selected email: " + selectedEmail);

            // and so on for all text fields you want to populate
        }
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
                viewimg.setImage(image);
                //System.out.println("Test 2");
            } catch (Exception e) {
                System.out.println("Error loading image");
            }
        }
    }

    public void updateData() {
        // Get the current data from the database or any other source
        List<Joueur> data = joueurDAO.getAllJoueurs();

        // Clear the current data in the table view
        joueursTable.getItems().clear();

        // Add the new data to the table view
        joueursTable.getItems().addAll(data);

        // Refresh the table view to show the new data
        joueursTable.refresh();
    }

    public static int calculateAge(Date birthDate) {
        Calendar now = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        dob.setTime(birthDate);
        int age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    public static String uploadImage(Image imageFile) throws IOException {
        String responseIMGUR = "";
        System.out.println("Imgur 1");
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageFile, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            System.out.println("Imgur 2");
            ImageIO.write(bufferedImage, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] imageBytes = baos.toByteArray();
        //String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        try {
            System.out.println("Imgur 3");
            URL url = new URL(IMGUR_API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
            con.setDoOutput(true);
            con.setDoInput(true);

            String boundary = "Boundary-" + UUID.randomUUID().toString();
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (OutputStream os = con.getOutputStream()) {
                System.out.println("Imgur 4");
                String contentDisposition = "Content-Disposition: form-data; name=\"image\"; filename=\"image.png\"\r\n";
                os.write(("--" + boundary + "\r\n" + contentDisposition + "Content-Type: image/png\r\n\r\n").getBytes("UTF-8"));

                // Write the image data
                os.write(imageBytes);

                // Write the final boundary
                os.write(("\r\n--" + boundary + "--\r\n").getBytes("UTF-8"));
                os.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Imgur 5");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                responseIMGUR = content.toString();
            } else {
                System.out.println("POST request failed. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonResponse = new JSONObject(responseIMGUR);
        String imageLink = jsonResponse.getJSONObject("data").getString("link");
        System.out.println("Image link: " + imageLink);
        return imageLink;
    }

    @FXML
    private void exportimg(ActionEvent event) {
        //
        if (selectedEmail != null && !selectedEmail.isEmpty()) {
            // Retrieve the user from the database using the selectedEmail
            // You can use your existing code or any database access method of your choice

            // Create the image with user info and profile picture
            Joueur joueur = joueurDAO.getJoueurByEmail(selectedEmail);
            if (joueur != null) {
                // Create the image with user data and profile picture
                //TODO
                Image userImage = createExportedImage(joueur);
                //showExportedImage(userImage);

                // Failed code.
                // Send the image through Bluetooth
                // sendImageThroughBluetooth(userImage);
                // Using imgur API
                // Client ID:3d02f319c246baf
                // Client Secret:e625f1081d77fc21fd8d9fa1daa59ac93d700962
                try {
                    String link = uploadImage(userImage);
                    System.out.println("Imgur uploaded image: " + link);
                    BufferedImage QrCode = generateQRCode(link);
                    showExportedQr(QrCode);

                } catch (IOException e) {
                    // handle the exception here
                    System.out.println("An I/O error occurred: " + e.getMessage());
                    e.printStackTrace();
                }

            }

            // Use exportedImage for whatever purpose you need (e.g. transfer through Bluetooth)
        }
    }

    public static BufferedImage generateQRCode(String url) {
        int width = 300;
        int height = 300;
        ByteArrayOutputStream outputStream = QRCode.from(url)
                .withSize(width, height)
                .to(ImageType.PNG)
                .stream();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(outputStream.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    //Failed code. Leaving it to be deleted Soon.
    /*
    public void sendImageThroughBluetooth(Image image) {
        try {
            // First, we create a new instance of the local device and discover the devices in the area
            LocalDevice localDevice = LocalDevice.getLocalDevice();
            DiscoveryAgent discoveryAgent = localDevice.getDiscoveryAgent();
            RemoteDevice[] remoteDevices = discoveryAgent.retrieveDevices(DiscoveryAgent.PREKNOWN);

            // We prompt the user to select the device to send the image to
            // In this example, we will assume the first device is selected
            RemoteDevice remoteDevice = remoteDevices[0];
            System.out.println("Remote device name: " + remoteDevice.getFriendlyName(true));
            System.out.println("Remote device address: " + remoteDevice.getBluetoothAddress());

            // We create a new OBEX client session with the remote device
            ClientSession clientSession = (ClientSession) Connector.open(
                    "btgoep://" + remoteDevice.getBluetoothAddress() + ":6" + ";authenticate=true;encrypt=true;master=true");

            // We create a new OBEX PUT request
            HeaderSet headerSet = clientSession.createHeaderSet();
            headerSet.setHeader(HeaderSet.NAME, "image.jpg");
            headerSet.setHeader(HeaderSet.TYPE, "image/jpeg");
            Operation putOperation = clientSession.put(headerSet);

            // We write the image data to the output stream of the PUT request
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", baos);
            byte[] imageData = baos.toByteArray();
            OutputStream os = putOperation.openOutputStream();
            os.write(imageData);
            os.close();
            // Check the response code from the server
            int responseCode = putOperation.getResponseCode();
            if (responseCode != ResponseCodes.OBEX_HTTP_OK) {
                System.out.println("Failed to send image through Bluetooth: " + responseCode);
            }
            // We close the client session and put operation
            putOperation.close();
            clientSession.disconnect(null);
            clientSession.close();
        } catch (BluetoothStateException e) {
            System.out.println("Bluetooth is not available or not enabled.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Failed to send image through Bluetooth.");
            e.printStackTrace();
        }
    }
     */
    private void showExportedImage(Image image) {
        Stage stage = new Stage();
        stage.setTitle("Exported Image");
        ImageView imageView = new ImageView(image);
        ScrollPane scrollPane = new ScrollPane(imageView);
        Scene scene = new Scene(scrollPane);
        stage.setScene(scene);
        stage.show();
    }

    private void showExportedQr(BufferedImage image) {
        Stage stage = new Stage();
        stage.setTitle("Exported Qr code!");
        Image fxImage = SwingFXUtils.toFXImage(image, null);
        ImageView imageView = new ImageView(fxImage);
        ScrollPane scrollPane = new ScrollPane(imageView);
        Scene scene = new Scene(scrollPane);
        stage.setScene(scene);
        stage.show();
    }

    private Image createExportedImage(Joueur user) {
        // Create an empty image with the desired dimensions and background color
        int imageWidth = 800;
        int imageHeight = 800;
        Color backgroundColor = Color.WHITE;
        WritableImage image = new WritableImage(imageWidth, imageHeight);
        PixelWriter pixelWriter = image.getPixelWriter();
        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                pixelWriter.setColor(x, y, backgroundColor);
            }
        }
        String imgpath = IMAGE_FOLDER + user.getProfile();
        System.out.println("Image path: " + imgpath);
        File imageFile = new File(imgpath);
        Image profilePicture = new Image(imageFile.toURI().toString());
        // Load user profile picture from file or database
        //Image profilePicture = new Image("file:" + user.getProfile());

        // Add user info and profile picture to the image
        Canvas canvas = new Canvas(imageWidth, imageHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //// IMAGES MODIFICATIONS
        //// RADIOACTIVE ZONE!!!!!!!!!
        /*
    
                                | \
                                | |
                                | |
           |\                   | |
          /, ~\                / /
         X     `-.....-------./ /
          ~-. ~  ~              |
             \             /    |
              \  /_     ___\   /
              | /\ ~~~~~   \ |
              | | \        || |
              | |\ \       || )
             (_/ (_/      ((_/
    
    
         */
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gc.fillText(user.getNom() + " " + user.getPrenom(), 500, 80);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        gc.fillText(user.getEmail(), 500, 120);
        gc.fillText(user.getIgn(), 500, 150);
        gc.fillText("Banned: " + user.isBanned(), 500, 180);
        gc.fillText("Verified: " + user.isVerified(), 500, 210);
        double imageWidthRatio = profilePicture.getWidth() / profilePicture.getHeight();
        double imageHeightRatio = profilePicture.getHeight() / profilePicture.getWidth();
        double scaledImageWidth = imageWidth / 2;
        double scaledImageHeight = scaledImageWidth * imageHeightRatio;
        gc.drawImage(profilePicture, 20, 20, scaledImageWidth, scaledImageHeight);

        /////////////////////////
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.WHITE);
        // Save the image as a JPEG file
        Image imageResult = canvas.snapshot(params, null);

        return imageResult;
    }

    @FXML
    public void initialize() {
        rolescombo.getItems().addAll("ROLE_USER", "ROLE_ADMIN");
        rolescombo.setValue("ROLE_USER"); // Set default value
        // Initialize table columns
        //idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        email.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        ign.setCellValueFactory(
                new PropertyValueFactory<>("ign"));
        nom.setCellValueFactory(
                new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(
                new PropertyValueFactory<>("prenom"));

        datenai.setCellValueFactory(new PropertyValueFactory<>("datenai"));

        is_banned.setCellValueFactory(
                new PropertyValueFactory<>("is_banned"));
        is_banned.setCellValueFactory(cellData
                -> {
            boolean isBanned = cellData.getValue().isBanned();
            String bannedStatus = isBanned ? "Banné" : "Actif";
            return new SimpleStringProperty(bannedStatus);
        }
        );
        //pprofile.setCellValueFactory(new PropertyValueFactory<>("pprofile"));
        joueursTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) { // checks if the click count is 1
                handleRowClick(); // calls the handleRowClick function
            }
        });

        is_verified.setCellValueFactory(
                new PropertyValueFactory<>("is_verified"));
        is_verified.setCellValueFactory(cellData
                -> {
            boolean isVerified = cellData.getValue().isVerified();
            String bannedStatus = isVerified ? "✅" : "❌";
            return new SimpleStringProperty(bannedStatus);
        }
        );

        wins.setCellValueFactory(
                new PropertyValueFactory<>("wins"));
        loses.setCellValueFactory(
                new PropertyValueFactory<>("loses"));
        //this is code to add a ban button that changes color based on the state of the user, disgusting amount of filthy code to do sucha  simple task, why are they teaching us this when technology has advanced beyond this.
        // I hate java so much so much so much
        //////////////////
        TableColumn<Joueur, Void> buttonColumn = new TableColumn<>("Action");
        buttonColumn.setCellFactory(column -> {
            return new TableCell<Joueur, Void>() {
                private final Button banButton = new Button();

                {
                    banButton.setOnAction(event -> {
                        Joueur joueur = getTableView().getItems().get(getIndex());
                        boolean isBanned = joueur.isBanned();
                        if (isBanned) {
                            // Unban the joueur
                            joueur.setBanned(false);
                            banButton.setText("Bannir");
                            banButton.setStyle("-fx-background-color: red;");
                        } else {
                            // Ban the joueur
                            joueur.setBanned(true);
                            banButton.setText("Débannir");
                            banButton.setStyle("-fx-background-color: green;");
                        }
                        String updateQuery = "UPDATE joueur SET is_banned = ? WHERE id = ?";
                        try {
                            PreparedStatement preparedStatement = cnx.prepareStatement(updateQuery);
                            preparedStatement.setBoolean(1, joueur.isBanned());
                            preparedStatement.setInt(2, joueur.getId());
                            preparedStatement.executeUpdate();
                        } catch (SQLException e) {
                            System.out.println("Update failed: " + e.getMessage());
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Joueur joueur = getTableView().getItems().get(getIndex());
                        boolean isBanned = joueur.isBanned();
                        if (isBanned) {
                            banButton.setText("Débannir");
                            banButton.setStyle("-fx-background-color: green;");
                        } else {
                            banButton.setText("Bannir");
                            banButton.setStyle("-fx-background-color: red;");
                        }
                        setGraphic(banButton);
                        //updateData();

                    }
                }
            };
        });
        //cleareverything
        cleareverything.setOnAction(this::clear);
        ajouttext.setOnAction(this::handleAddUserButtonAction);
        joueursTable.getColumns().add(buttonColumn);

        /////////////////
        //nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        // Load data from database and add to table
        List<Joueur> joueurs = joueurDAO.getAllJoueurs();

        //System.out.println(joueurs);
        joueursTable.getItems()
                .addAll(joueurs);
        searchbutt.setOnAction(this::setupSearchButton);
    }
}
