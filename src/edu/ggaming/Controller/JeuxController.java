/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ggaming.Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import edu.ggaming.utils.MyConnection;
import edu.ggaming.entities.CategorieJeux;
import edu.ggaming.entities.Jeux;
import edu.ggaming.services.ServiceCatJeux;
import edu.ggaming.services.ServiceJeux;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


/**
 * FXML Controller class
 *
 * @author dell
 */
public class JeuxController implements Initializable {
    @FXML
    private TextField rech;
    @FXML
    private Button btnrech;
    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;
  @FXML
    private Button minimize;
@FXML
    private Button exl;
    @FXML
    private AnchorPane jeuxback;

    @FXML
    private Button jeuxclearBtn;

    @FXML
    private AnchorPane jeuxform;

    @FXML
    private TextField tfLibelle;

    @FXML
    private TableColumn<Jeux, Float> tcnoteM;

    @FXML
    private Button jeuxbtn;

    @FXML
    private TableColumn<Jeux, LocalDateTime> tcdate;

    @FXML
    private Button jeuxaddBtn;

    @FXML
    private TableColumn<Jeux, Integer> tcviews;
    @FXML
    private TableColumn<Jeux, Integer> tcid;
    @FXML
    private TableColumn<Jeux, String> tclibelle;

    @FXML
    private Button jeuxupdateBtn;

    @FXML
    private ImageView jeuxlogo;

    @FXML
    private ComboBox<?> listeType;

    @FXML
    private ComboBox<CategorieJeux> listeCat;

    @FXML
    private Button jeuxdeleteBtn;
  @FXML
    private Button imp;
    @FXML
    private TableColumn<Jeux, String> tcref;

    @FXML
    private Button logoimportBtn;

    @FXML
    private TableView<Jeux> jeuxtable;
  

    @FXML
    private ImageView jeuximage;
    
    @FXML
    private ImageView jeuxlg;

    @FXML
    private TextField tfid;

    @FXML
    private Button catjeuxbtn;
    @FXML
    private Button newjeux;
    @FXML
    private Label errormsg;
   
    @FXML
    private Button imageimportBtn;

    @FXML
    private Button close;
    @FXML
    private Button statbtn;
    
    private int selectedJeuxId;
    int index=-1;
    private boolean catView = false;
    private ObservableList<Jeux> Data;
    private Connection con;
    String query = null;
    //Connection connection = null ;
    private ServiceJeux servicesJeux;
      private List<Jeux> lt;
    private ServiceCatJeux servicesCatJeux;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Jeux jeux = null ;
    Jeux j = new Jeux();
    ObservableList<Jeux>  jeuxList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        servicesJeux = new ServiceJeux();
        servicesJeux.initConnection();
        loadDataJeux();
         jeuxtable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // Get the id of the selected Jeux item
            selectedJeuxId = newSelection.getId();
            // Populate the input fields with the selected Jeux data
            tfLibelle.setText(newSelection.getLibelle());
            tfid.setText(Integer.toString(newSelection.getId()));
        }
    });
     // listeCat.setItems(FXCollections.observableArrayList(new ServiceCatJeux().getAllNames()));


    }
private void refreshTable() {
    try {
        jeuxList.clear();
        query = "SELECT * FROM jeux";
       java.sql.Connection connection = MyConnection.getInstance().getCnx();

                    

        if (connection != null) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                LocalDateTime dateCreation = resultSet.getTimestamp("date_creation").toLocalDateTime();
             jeuxList.add(new Jeux(
     resultSet.getInt("id"),
        resultSet.getString("ref"),
        resultSet.getString("libelle"),
        dateCreation,
       resultSet.getInt("views"),
                     
        resultSet.getFloat("note_myonne")
    ));
            }
            System.out.println(jeuxList);
            jeuxtable.setItems(jeuxList);
        } else {
            System.out.println("Database connection is null");
        }
    } catch (SQLException ex) {
        Logger.getLogger(JeuxController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

@FXML
public void rechercherJeux(ActionEvent event) {
  
  if (event.getSource() == btnrech) {
        lt = new ArrayList<>();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setHeaderText("");
        
        if(dateFrom.getValue()==null && dateTo.getValue()==null && rech.getText().isEmpty()){ //rien saisie
            alert.setContentText("Rien Saisie !");
            alert.showAndWait();
        }
        else if(dateFrom.getValue()==null || dateTo.getValue()==null && !rech.getText().isEmpty())//nom saisi date pas saisie
        {
            List<Jeux> result = servicesJeux.rechercherJeuxParNom(rech.getText());
        ObservableList<Jeux> observableList = FXCollections.observableArrayList(result);
        jeuxtable.setItems(observableList);
        }
        else if(dateFrom.getValue()!=null && dateTo.getValue()!=null && rech.getText().isEmpty()) //nom pas saisi date saisie
        {
            if(dateFrom.getValue().isAfter(dateTo.getValue())){
                alert.setContentText("La date doit etre superieur à la date d'aujourd'hui ");
                alert.showAndWait(); 
            }
            else{
                
               List<Jeux> result = servicesJeux.rechercherJeuxParDate(Date.valueOf(dateFrom.getValue().toString()), Date.valueOf(dateTo.getValue().toString()));
        ObservableList<Jeux> observableList = FXCollections.observableArrayList(result);
        jeuxtable.setItems(observableList);
            if(result.isEmpty()){
                alert.setContentText("Rien Trouvé !");
                alert.showAndWait();
                  }
            }
        }
        else if(dateFrom.getValue()!=null && dateTo.getValue()!=null && !rech.getText().isEmpty())//nom saisi et date saisie
        {
            List<Jeux> result = servicesJeux.rechercherJeuxParNomEtDate(rech.getText(), Date.valueOf(dateFrom.getValue().toString()), Date.valueOf(dateTo.getValue().toString()));
        ObservableList<Jeux> observableList = FXCollections.observableArrayList(result);
        jeuxtable.setItems(observableList);
            if(result.isEmpty()){
                alert.setContentText("Rien Trouvé !");
                alert.showAndWait();
            }
        }
 
    }
}

 @FXML
    private void emptyfields(ActionEvent event){
       tfid.clear();
        tfLibelle.clear();
    }
private void loadDataJeux() {
    try {
        refreshTable();
        tcref.setCellValueFactory(new PropertyValueFactory<>("ref"));
        tclibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        tcdate.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        tcviews.setCellValueFactory(new PropertyValueFactory<>("views"));
        tcnoteM.setCellValueFactory(new PropertyValueFactory<>("noteMyonne"));
        tcid.setCellValueFactory(new PropertyValueFactory<>("id"));
    } catch (Exception e) {
        e.printStackTrace();
    }
}
     @FXML
    void ajouterJeux(ActionEvent event) {
         String libelle = tfLibelle.getText().trim();
         String stringid = tfid.getText().trim();
         String logo_jeux=jeuxlg.getImage().toString();
         String image_jeux=jeuximage.getImage().toString();
         int id = parseInt(stringid);
        if(libelle.isEmpty()){
            
            tfLibelle.setStyle("-fx-border-color: red");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir le libelle");
            alert.showAndWait();
            return;
        }

        LocalDateTime currentDate = LocalDateTime.now();
        Jeux b = new Jeux(id, libelle, image_jeux, logo_jeux, currentDate, 0);
        ServiceJeux sb = new ServiceJeux();
        sb.initConnection();
        sb.ajouter(b);
        refreshTable();
       tfid.clear();
        tfLibelle.clear();
        
        
    }
    @FXML
    private void addlogo(ActionEvent event) throws IOException {
        
        FileChooser Chooser = new FileChooser();
        
        FileChooser.ExtensionFilter exxFilterJPG= new FileChooser.ExtensionFilter("JPG files (*.jpg)","*.JPG");
        FileChooser.ExtensionFilter exxFilterPNG= new FileChooser.ExtensionFilter("PNG files (*.png)","*.PNG");
        
        Chooser.getExtensionFilters().addAll(exxFilterJPG,exxFilterPNG);
        File file = Chooser.showOpenDialog(null);
        BufferedImage bufferedimg = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedimg, null);
        jeuxlg.setImage(image);
      
        
    }
    @FXML
    private void addimage(ActionEvent event) throws IOException {
        
        FileChooser Chooser = new FileChooser();
        
        FileChooser.ExtensionFilter exxFilterJPG= new FileChooser.ExtensionFilter("JPG files (*.jpg)","*.JPG");
        FileChooser.ExtensionFilter exxFilterPNG= new FileChooser.ExtensionFilter("PNG files (*.png)","*.PNG");
        
        Chooser.getExtensionFilters().addAll(exxFilterJPG,exxFilterPNG);
        File file = Chooser.showOpenDialog(null);
        BufferedImage bufferedimg = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedimg, null);
        jeuximage.setImage(image);
      
        
    }
    @FXML
    private void suppjeux(ActionEvent event){
        try{

            String idstring = tfid.getText();

            int id = Integer.parseInt(idstring);

            Jeux b = new Jeux(id);
        
            ServiceJeux sb = new ServiceJeux();
            sb.initConnection();
            sb.delete(b);
            refreshTable();
            tfid.clear();
        tfLibelle.clear();
        }catch(Exception e){
        }
    }
    @FXML
    private void Editerjeux(ActionEvent event){
        
        try{

            String idstring = tfid.getText();
            String libstring = tfLibelle.getText();
          

            int id = Integer.parseInt(idstring);

            Jeux b = new Jeux(id,libstring);
        
            ServiceJeux sb = new ServiceJeux();
            sb.initConnection();
            sb.updatelibelle(b);
            refreshTable();
            tfid.clear();
        tfLibelle.clear();
        }catch(Exception e){
        }
    }
    
    @FXML
    void affjeux(ActionEvent event) throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("jeuxb.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void checknew(ActionEvent event) throws IOException {
      /*   catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("/ggaming/interfaces/GameA.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();*/

    }
    
    @FXML
    void affcat(ActionEvent event) throws IOException {
        catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("Backcat.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

       
    }
   
    @FXML
    void export(ActionEvent event) throws IOException {
        
       exportExcel();
    }
    @FXML
    void importExcel(ActionEvent event) throws IOException {
        
       exportExcel();
    }
    @FXML
    void affstat(ActionEvent event) throws IOException {
        
         catView = true;

        Parent root = FXMLLoader.load(getClass().getResource("stat.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
          /* try {
       
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ServiceJeux sj = new ServiceJeux();
        sj.initConnection();
        List<Jeux> jeuxes = sj.afficherJeux();
        for (Jeux j : jeuxes) {
            dataset.addValue(j.getNoteMyonne(), "Notemyonne", j.getLibelle());
        }

        JFreeChart chart = ChartFactory.createBarChart("Evaluation des jeux", "Games", "Notemyonne", dataset, PlotOrientation.VERTICAL, false, false, false);
        chart.setBackgroundPaint(Color.WHITE);

        org.jfree.chart.axis.CategoryAxis domainAxis = chart.getCategoryPlot().getDomainAxis();
        domainAxis.setTickLabelFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setTickMarksVisible(false);
        domainAxis.setAxisLineVisible(false);

 
        ValueAxis rangeAxis = chart.getCategoryPlot().getRangeAxis();
        rangeAxis.setTickLabelFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        rangeAxis.setTickMarksVisible(false);
        rangeAxis.setAxisLineVisible(false);


        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getPlot().setOutlinePaint(Color.WHITE);
        
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setBackground(Color.WHITE);

    JFrame frame = new JFrame("Statistiques jeux");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.add(chartPanel);
    frame.pack();
    frame.setVisible(true);
    } catch (Exception e) {
        e.printStackTrace();
    }*/
    }

 @FXML
    public void exportExcel() {
       
      /*  
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
                WritableCell cell1 = new jxl.write.Label(2, 0, "libelle");
                sheet.addCell(cell1);
                WritableCell cell2 = new jxl.write.Label(3, 0, "image_jeux");
                sheet.addCell(cell2);
                WritableCell cell3 = new jxl.write.Label(4, 0, "views");
                sheet.addCell(cell3);
                   WritableCell cell4 = new jxl.write.Label(5, 0, "note_myonne");
                sheet.addCell(cell4);
              
                
            

               
                List<Jeux> jeuxes = servicesJeux.afficherJeux();

                int row = 1;
                for (Jeux j : jeuxes) {
                  
                    sheet.addCell(new jxl.write.Label(0, row, Integer.toString(j.getId())));
                    sheet.addCell(new jxl.write.Label(1, row, j.getLibelle()));
                    sheet.addCell(new jxl.write.Label(2, row, j.getImageJeux()));

                    sheet.addCell(new jxl.write.Label(4, row, Integer.toString(j.getViews())));
                    sheet.addCell(new jxl.write.Label(5, row, Float.toString(j.getNoteMyonne())));
                    
                    
                  
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
        }*/
    }
    
    
   /* public void importEx(File file) {
    try {
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            
            Jeux j = new Jeux();
            j.setId((int) cellIterator.next().getNumericCellValue());
            j.setRef(cellIterator.next().getStringCellValue());
            j.setLibelle(cellIterator.next().getStringCellValue());
            j.setLogoJeux(cellIterator.next().getStringCellValue());
            j.setImageJeux(cellIterator.next().getStringCellValue());
            j.setDateCreation(cellIterator.next().getLocalDateTimeCellValue());
            j.setNote((float) cellIterator.next().getNumericCellValue());
            j.setNoteCount((int) cellIterator.next().getNumericCellValue());
            j.setNoteMyonne((float) cellIterator.next().getNumericCellValue());
            j.setTotalNote((float) cellIterator.next().getNumericCellValue());
            j.setViews((int) cellIterator.next().getNumericCellValue());
            
            ajouter(j);
        }
        
        workbook.close();
        inputStream.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Import from Excel");
        alert.setHeaderText(null);
        alert.setContentText("Data imported from " + file.getAbsolutePath());
        alert.showAndWait();

    } catch (Exception e) {
        e.printStackTrace();
        // show error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Import from Excel");
        alert.setHeaderText(null);
        alert.setContentText("Error: " + e.getMessage());
        alert.showAndWait();
    }
}*/
    
    
    @FXML
    
    public void afficherJeuxFront(ActionEvent event)
    {
         Parent root;
         try {
             root = FXMLLoader.load(getClass().getResource("FrontJeux2.fxml"));
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
    void afficherProduits(ActionEvent event) {
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
    

}
