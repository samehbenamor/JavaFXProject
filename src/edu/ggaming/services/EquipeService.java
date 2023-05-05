/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import edu.ggaming.utils.MyConnection;
import edu.ggaming.entities.Equipe;
import edu.ggaming.interfaces.equipeinterfaces;
import edu.ggaming.Controller.BackEquipeController;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author dhia
 */
public class EquipeService implements equipeinterfaces<Equipe> {

    private static EquipeService instance;
    private Statement st;
    private ResultSet rs;
    Connection cnx2;

    /*
    {
        cnx2=MyConnection.getInstance().getCnx();
    }
     */
    public EquipeService() {
        MyConnection cs = MyConnection.getInstance();
        try {
            st = cs.getCnx().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        cnx2 = MyConnection.getInstance().getCnx();

    }

    public void initConnection() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public static EquipeService getInstance() {
        if (instance == null) {
            instance = new EquipeService();
        }
        return instance;
    }

    @Override
    public void insert(Equipe o) {
        String req = "INSERT INTO Equipe (id, nom_equipe , description_equipe, nb_joueurs, logo_equipe, site_web ,date_creation ) VALUES ('" + o.getId() + "', '" + o.getNom_equipe() + "', '" + o.getDescription_equipe() + "', '" + o.getNb_joueurs() + "', '" + o.getLogo_equipe() + "', '" + o.getSite_web() + "', '" + o.getDate_creation() + "')";

        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ajouterEquipe(Equipe e) {

        try {
            Alert alert;
            String requete2 = "INSERT INTO Equipe"
                    + "(nom_equipe , description_equipe, nb_joueurs, logo_equipe, site_web ,date_creation )"
                    + "VALUES (?,?,?,?,?,?)";
            Timestamp timestamps = Timestamp.valueOf(e.getDate_creation());
            PreparedStatement pst = cnx2.prepareStatement(requete2);
            pst.setString(1, e.getNom_equipe());
            pst.setString(2, e.getDescription_equipe());
            pst.setInt(3, e.getNb_joueurs());
            pst.setString(4, e.getLogo_equipe());
            pst.setString(5, e.getSite_web());
            pst.setTimestamp(6, timestamps);

            pst.executeUpdate();
            System.out.println("equipe Ajouté avec succès...");
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("equipe ajouté avec succès!");
            alert.showAndWait();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deletee(Equipe e) {
        try {
            String sql = "delete from Equipe where id = ? ";

            PreparedStatement stmt = cnx2.prepareStatement(sql);

            stmt.setInt(1, e.getId());

            stmt.executeUpdate();
            System.out.println("equipe supprimer avec success");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void supprimerequipe(int id) {
        String requete = "DELETE FROM Equipe WHERE id=?";
        try {
            Alert alert;
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            pst.close();
            System.out.println("equipe supprimé avec succès...");
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("equipe supprimé avec succès!");
            alert.showAndWait();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Equipe> displayAll() {
        List<Equipe> all = new ArrayList<>();
        Statement stmt;
        try {
            String sql = "select * from Equipe";

            stmt = cnx2.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                
               // Equipe b = new Equipe(rs.getInt("id"), rs.getString("nom_equipe"), rs.getString("description_equipe"), rs.getString("logo_equipe"), rs.getString("site_web"), rs.getInt("nb_joueurs"), rs.getTimestamp("date_creation").toLocalDateTime());
  //Equipe b = new Equipe(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getInt(6),rs.getString(7), rs.getString(8), rs.getTimestamp(9).toLocalDateTime());
  LocalDateTime dateCreation = rs.getTimestamp("date_creation").toLocalDateTime();
               
                    all.add(new Equipe(
                        rs.getInt("id"),
                        rs.getString("nom_equipe"),
                        rs.getString("description_equipe"),
                        rs.getString("logo_equipe"),
                        rs.getString("site_web"),                                              
                             rs.getInt("nb_joueurs"),
                        dateCreation
                        
                       
                    ));
               
            }
            
           
            return all;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return all;
    }
    /*
    public List<Equipe> displayjeux() {
         try{
            String query = null;
            query = "SELECT * FROM Equipe";
            Connection connection = MyConnection.getInstance().getCnx();
            if (connection != null) {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                    
                 LocalDateTime dateCreation = resultSet.getTimestamp("date_creation").toLocalDateTime();
               
                    equipeList.add(new Equipe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom_equipe"),
                        resultSet.getString("description_equipe"),
                        resultSet.getString("logo_equipe"),
                        resultSet.getString("site_web"),                                              
                             resultSet.getInt("nb_joueurs"),
                        dateCreation
                        
                       
                    ));
                    tableEquipe.setItems(equipeList);
                }
            } else {
                System.out.println("Database connection is null");
            }
        }catch(SQLException ex){
            Logger.getLogger(BackEquipeController.class.getName()).log(Level.SEVERE,null,ex);
        }
        
    
    }
           */ 
    

    @Override
    public Equipe displayById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Equipe os) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void modifierEquipe(Equipe e , int id) {
        try {
            String sql = "UPDATE Equipe SET nom_equipe=?,description_equipe=?,nb_joueurs=?,site_web=? WHERE id=? ";

            PreparedStatement pst = cnx2.prepareStatement(sql);
          
            pst.setString(1, e.getNom_equipe());
             
            
             pst.setString(2,e.getDescription_equipe());                       
             
             pst.setInt(3,e.getNb_joueurs());
            pst.setString(4,e.getSite_web());
            
            pst.setInt(5,id);
            pst.executeUpdate();
            System.out.println("equipe modifiée avec success");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

   

    public Equipe findEquipeByNom(String nom) {
        try {
            String sql = "select * from Equipe where nom_equipe = ?";
            PreparedStatement ste = cnx2.prepareStatement(sql);
            ste.setString(1, nom);
            ResultSet s = ste.executeQuery();

            while (s.next()) {
                Equipe r = new Equipe(
                        s.getInt("id"),
                        s.getString("nom_equipe"));
                return r;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return new Equipe(); //what should i put this
    }
    
    
    
    
    
    public ObservableList<Equipe> GetBynomequipe(String id) {
    String req = "select * from Equipe where nom_equipe like ?";
    ObservableList<Equipe> list = FXCollections.observableArrayList();
    try (PreparedStatement pstmt = cnx2.prepareStatement(req)) {
        pstmt.setString(1, "%" + id + "%");
        
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Equipe p = new Equipe(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getInt(6),  rs.getString(7),  rs.getString(8), rs.getTimestamp(9).toLocalDateTime());
            list.add(p);
        }
    } catch (SQLException ex) {
        Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}
     public List<Equipe> ordredbynom() {
         String req="select * from Equipe order BY nom_equipe";
        ObservableList<Equipe> list=FXCollections.observableArrayList();       
        
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                
                Equipe p=new Equipe();
                p.setId(rs.getInt(1));
                p.setNom_equipe(rs.getString("nom_equipe"));
                p.setDescription_equipe(rs.getString("description_equipe"));
                p.setNb_joueurs(rs.getInt("nb_joueurs"));
                p.setLogo_equipe(rs.getString("logo_equipe"));
                p.setSite_web(rs.getString("site_web"));
                p.setDate_creation(rs.getTimestamp("date_creation").toLocalDateTime());
                
               
                
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list; //To change body of generated methods, choose Tools | Templates.
    }
        public List<Equipe> ordredbynbj() {
         String req="select * from Equipe order BY nb_joueurs";
        ObservableList<Equipe> list=FXCollections.observableArrayList();       
        
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                
                Equipe p=new Equipe();
                p.setId(rs.getInt(1));
                p.setNom_equipe(rs.getString("nom_equipe"));
                p.setDescription_equipe(rs.getString("description_equipe"));
                p.setNb_joueurs(rs.getInt("nb_joueurs"));
                p.setLogo_equipe(rs.getString("logo_equipe"));
                p.setSite_web(rs.getString("site_web"));
                p.setDate_creation(rs.getTimestamp("date_creation").toLocalDateTime());
                
               
                
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list; //To change body of generated methods, choose Tools | Templates.
    }
      
         public List<Equipe> ordredbydate() {
         String req="select * from Equipe order BY date_creation";
        ObservableList<Equipe> list=FXCollections.observableArrayList();       
        
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                
                Equipe p=new Equipe();
                p.setId(rs.getInt(1));
                p.setNom_equipe(rs.getString("nom_equipe"));
                p.setDescription_equipe(rs.getString("description_equipe"));
                p.setNb_joueurs(rs.getInt("nb_joueurs"));
                p.setLogo_equipe(rs.getString("logo_equipe"));
                p.setSite_web(rs.getString("site_web"));
                p.setDate_creation(rs.getTimestamp("date_creation").toLocalDateTime());
                
               
                
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list; //To change body of generated methods, choose Tools | Templates.
    }
       
    
    public ObservableList<Equipe> GetBynbequipe(String id) {
    String req = "select * from Equipe where nb_joueurs like ?";
    ObservableList<Equipe> list = FXCollections.observableArrayList();
    try (PreparedStatement pstmt = cnx2.prepareStatement(req)) {
        pstmt.setString(1, "%" + id + "%");
        
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Equipe p = new Equipe(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getInt(6),  rs.getString(7),  rs.getString(8), rs.getTimestamp(9).toLocalDateTime());
            list.add(p);
        }
    } catch (SQLException ex) {
        Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}
     
    
    
     public void Qr( Stage primaryStage,Equipe p) {
         //Stage primaryStage = null;
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    
        String myWeb = p.toString() ;
        
        int width = 300;
        int height = 300;
        String fileType = "png";
        BufferedImage bufferedImage = null;
        try {
            ByteMatrix byteMatrix =qrCodeWriter.encode(myWeb, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();
            
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)!=0) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            
            System.out.println("Success...");
            
        } catch (WriterException ex) {
            //Logger.getLogger(JavaFX_QRCo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ImageView qrView = new ImageView();
        qrView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        
        StackPane root = new StackPane();
        root.getChildren().add(qrView);
        
        Scene scene = new Scene(root, 350, 350);
        
        primaryStage.setTitle("QRCODE");
        primaryStage.setScene(scene);
       primaryStage.show();
    }
    
     
      public List<Equipe> searchEquipe(String searchTerm) {
        try {
            PreparedStatement statement = cnx2.prepareStatement("SELECT * FROM Equipe WHERE nom_equipe LIKE ? OR description_equipe LIKE ? OR nb_joueurs LIKE ? OR site_web LIKE ?");

            String likeSearchTerm = "%" + searchTerm + "%";
            statement.setString(1, likeSearchTerm);
            statement.setString(2, likeSearchTerm);
            statement.setString(3, likeSearchTerm);
            statement.setString(4, likeSearchTerm);
           

            ResultSet rs = statement.executeQuery();

            List<Equipe> equipes = new ArrayList<>();
            while (rs.next()) {
                Equipe equipe = new Equipe();
                equipe.setId(rs.getInt("id"));
                equipe.setNom_equipe(rs.getString("nom_equipe"));
                equipe.setDescription_equipe(rs.getString("description_equipe"));
                equipe.setNb_joueurs(rs.getInt("nb_joueurs"));
                equipe.setLogo_equipe(rs.getString("logo_equipe"));
                equipe.setSite_web(rs.getString("site_web"));
                equipe.setDate_creation(rs.getTimestamp("date_creation").toLocalDateTime());
                
                equipes.add(equipe);
            }
            return equipes;
        } catch (SQLException e) {
            System.err.println("Erreur de recherche: " + e.getMessage());
            return null;
        }
    }
      
      public Map<String, Integer> getNbJoueursParEquipe() {
    Map<String, Integer> nbJoueursParEquipe = new HashMap<>();
    String req = "SELECT nom_equipe, COUNT(*) AS nb_joueurs FROM Equipe GROUP BY nom_equipe";
    try (PreparedStatement pstmt = cnx2.prepareStatement(req)) {
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String nomEquipe = rs.getString("nom_equipe");
            int nbJoueurs = rs.getInt("nb_joueurs");
            nbJoueursParEquipe.put(nomEquipe, nbJoueurs);
        }
    } catch (SQLException ex) {
        Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return nbJoueursParEquipe;
}

      
      
       public int getNbj(int nb_joueurs) throws SQLException {
        String req = "SELECT COUNT(*) AS total_nbj FROM Equipe WHERE nb_joueurs = ?";
        int nbe=0;
        try (PreparedStatement ps = cnx2.prepareStatement(req)) {
            ps.setInt(1, nb_joueurs);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nbe= rs.getInt("total_nbj");
                }
            }
        }
        catch (SQLException ex) {
        throw new SQLException("Erreur lors de la récupération  " + ex.getMessage(), ex);
    }
        return nbe;
    }

       
       
       
       
       
       
       
       
       
       
}
