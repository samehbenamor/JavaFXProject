/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ggaming.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.fasterxml.jackson.databind.ObjectMapper;

import ggaming.entity.League;
import ggaming.entity.VideoGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
/**
 * FXML Controller class
 *
 * @author dell
 */
public class GameAController implements Initializable {
private boolean catView = false;
@FXML
    private Button minimize;

    @FXML
    private AnchorPane jeuxback;

    @FXML
    private AnchorPane jeuxform;

    @FXML
    private ListView<VideoGame> videoGameListView;

    @FXML
    private Button jeuxbtn;

    private static final String API_KEY = "9z3Lv3WyZIFClOSMHcxN78sxIbbJp_lpev23INCVr4cFn7-Nir8";

    @FXML
    private Button catjeuxbtn;

    @FXML
    private Button close;

    /**
     * Initializes the controller class.
     */
   @Override
public void initialize(URL url, ResourceBundle rb) {
    // Instantiate an OkHttpClient object
    OkHttpClient client = new OkHttpClient();

    // Create a Request object with your API key included in the header
    Request request = new Request.Builder()
            .url("https://api.pandascore.co/videogames")
            .addHeader("Authorization", "Bearer " + API_KEY)
            .build();

    // Send the request and get the response
    try {
        Response response = client.newCall(request).execute();

        // Convert the response body to a JSON string
        String responseBody = response.body().string();
        System.out.println(responseBody); // print the response body

        // Parse the JSON string into an array of VideoGame objects using the ObjectMapper class
        ObjectMapper mapper = new ObjectMapper();
        VideoGame[] videoGames = mapper.readValue(responseBody, VideoGame[].class);

        // Create an ObservableList of VideoGame objects and add the retrieved video games to it
        ObservableList<VideoGame> videoGameList = FXCollections.observableArrayList(videoGames);

        // Set the items of the ListView to the ObservableList of video games
        videoGameListView.setItems(videoGameList);
 // Set the cell factory of the ListView to customize how each item is displayed
        videoGameListView.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final HBox hbox = new HBox(10, imageView, new Label());

            {
                hbox.setAlignment(Pos.CENTER_LEFT);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
            }
                 @Override
                protected void updateItem(VideoGame videoGame, boolean empty) {
    super.updateItem(videoGame, empty);

    if (empty || videoGame == null || videoGame.getName() == null) {
        setText(null);
        setGraphic(null);
        return;
    }

    Label label = (Label) hbox.getChildren().get(1);
    label.setText(videoGame.getName());

    if (catView) {
        ObservableList<League> leaguesList = FXCollections.observableArrayList(videoGame.getLeagues());
        ListView<League> listView = new ListView<>(leaguesList);

        hbox.getChildren().setAll(listView);
    } else {
        hbox.getChildren().remove(2, hbox.getChildren().size());

        if (!videoGame.getLeagues().isEmpty()) {
    String imageUrl = videoGame.getLeagues().get(0).getImageUrl();
    if (imageUrl != null) {
        Image image = new Image(imageUrl, 50, 50, true, true);
        imageView.setImage(image);

        // Add onError callback to the image
        image.errorProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("Error loading image from URL: " + imageUrl);
            }
        });
    }
}

    }
    setText(null);
    setGraphic(hbox);
}
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
           
  

      @FXML
    void affcat(ActionEvent event) throws IOException {
            catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/Backcat.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void  affjeuxpage(ActionEvent event) 
throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/jeuxback.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

       
    }
    
    
    

}
