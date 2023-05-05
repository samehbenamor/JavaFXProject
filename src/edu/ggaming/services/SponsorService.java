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
import edu.ggaming.entities.Sponsor;
import edu.ggaming.interfaces.sponsorinterfaces;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
public class SponsorService implements sponsorinterfaces <Sponsor>{
    
     Connection cnx2;
    
    public SponsorService()
    {
        cnx2=MyConnection.getInstance().getCnx();
    }
     public void initConnection() {
        cnx2 = MyConnection.getInstance().getCnx();
    }
    
     public void ajouterSponsor(Sponsor s)
    {
         
         try {
             Alert alert;
             String requete2="INSERT INTO Sponsor"
                + "( id_equipe_id ,nom_sponsor, description_sponsor, logo_sponsor, site_webs, date_creationn  )"
                + "VALUES (?,?,?,?,?,?)";
         Timestamp timestampp = Timestamp.valueOf(s.getDate_creationn());
             PreparedStatement pst=cnx2.prepareStatement(requete2);
            
            pst.setInt(1,s.getEquipe().getId());
             pst.setString(2, s.getNom_sponsor());
             pst.setString(3, s.getDescription_sponsor());
             pst.setString(4, s.getLogo_sponsor());
             pst.setString(5, s.getSite_webs());
             pst.setTimestamp(6, timestampp);
             
          
             pst.executeUpdate();
             System.out.println("sponsor Ajouté avec succès...");
              alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("sponsor ajouté avec succès!");
                    alert.showAndWait();
         } catch (SQLException ex) {
             System.err.println(ex.getMessage());
         }
         
         
         

    
         
    }

    @Override
    public void inserts(Sponsor o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletes(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*
    @Override
    public List<Sponsor> displayAll() {
        List<Sponsor> all = new ArrayList<>();
        Statement stmt;
        try {
            String sql = "select * from Sponsor";
            
          stmt = cnx2.createStatement();
            
               ResultSet rs =  stmt.executeQuery(sql);
                
                while(rs.next()){
                    
                   Sponsor a = new Sponsor
        (rs.getInt("id"),findByIdE(rs.getInt("id_equipe_id")),rs.getString("nom_sponsor") , rs.getString("description_sponsor"), rs.getString("logo_sponsor"), rs.getString("site_webs"),rs.getTimestamp("date_creationn").toLocalDateTime());               
                   System.out.println(a);
                all.add(a);  

                     LocalDateTime dateCreationn = rs.getTimestamp("date_creationn").toLocalDateTime();
               
                    all.add(new Sponsor(
                        rs.getInt("id"),
                           
                        rs.getString("nom_sponsor"),
                        rs.getString("description_sponsor"),
                        rs.getString("logo_sponsor"),
                        rs.getString("site_webs"),
                           dateCreationn             
                            
                    ));
                }
               return all;
                    
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }
        return all;
    }
*/
    @Override
    public Sponsor displayById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updates(Sponsor os) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public void supprimersponsor(int id)
    {
        String requete="DELETE FROM Sponsor WHERE id=?";
        try {
            Alert alert;
            PreparedStatement pst=cnx2.prepareStatement(requete);
            pst.setInt(1,id);
            pst.executeUpdate();
            pst.close();
            System.out.println("sponsor supprimé avec succès...");
             alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("sponsor supprimé avec succès!");
                    alert.showAndWait();
        } catch (SQLException ex) {
           System.err.println(ex.getMessage());
        }
    }
    
     public void modifiersponsor(Sponsor s, int id) {
  try {
            String sql = "UPDATE Sponsor SET nom_sponsor=?,description_sponsor=?,logo_sponsor=?,site_webs=? WHERE id=?";
            
            PreparedStatement stmt = cnx2.prepareStatement(sql);
           //
             stmt.setString(1, s.getNom_sponsor());
             stmt.setString(2, s.getDescription_sponsor());
             stmt.setString(3, s.getLogo_sponsor());
            stmt.setString(4, s.getSite_webs());
             stmt.setInt(5,id);
         
            stmt.executeUpdate();
            System.out.println("sponsor modifié avec success");
            
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }
            }
    
    
   
     public Equipe findByIdE(int id) {
        try {
            String sql = "select * from Equipe where id = ?";
            PreparedStatement ste = cnx2.prepareStatement(sql);
            ste.setInt(1,id);
            ResultSet s = ste.executeQuery();

                while (s.next()) {
                Equipe r = new Equipe(
                s.getInt("id"),
                s.getString("nom_equipe"));
                System.out.println(r);
                return r;
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return new Equipe(); //what should i put this
    }
    
     public Equipe findEquipeByNom(String nom) {
        try {
            String sql = "select * from Equipe where nom_equipe = ?";
            PreparedStatement ste = cnx2.prepareStatement(sql);
            ste.setString(1,nom);
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
    
    
     public List<String> getEquipe() {
ArrayList<String> result = new ArrayList<>();
        try {
            String sql = "select nom_equipe from Equipe";
            Statement ste = cnx2.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                result.add(s.getString("nom_equipe"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
    
     public void Qr( Stage primaryStage,Sponsor a) {
         //Stage primaryStage = null;
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    
        String myWeb = a.toString() ;
        
        int width = 300;
        int height = 300;
        String fileType = "png";
        BufferedImage bufferedImage = null;
        try {
            ByteMatrix byteMatrix = qrCodeWriter.encode(myWeb, BarcodeFormat.QR_CODE, width, height);
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
      public ObservableList<Sponsor> GetBynomsponsor(String id) {
    String req = "select * from Sponsor where nom_sponsor like ?";
    ObservableList<Sponsor> list = FXCollections.observableArrayList();
    try (PreparedStatement pstmt = cnx2.prepareStatement(req)) {
        pstmt.setString(1, "%" + id + "%");
        
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Sponsor p = new Sponsor(rs.getInt(1), findByIdE(rs.getInt(2)), rs.getString(3), rs.getString(4),  rs.getString(5),  rs.getString(6), rs.getTimestamp(7).toLocalDateTime());
          //  (rs.getInt("id"),findByIdE(rs.getInt("id_equipe_id")),rs.getString("nom_sponsor") , rs.getString("description_sponsor"), rs.getString("logo_sponsor"), rs.getString("site_webs"),rs.getTimestamp("date_creationn").toLocalDateTime());
            list.add(p);
        }
    } catch (SQLException ex) {
        Logger.getLogger(SponsorService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}

    @Override
    public List<Sponsor> displayAlll() {
       List<Sponsor> all = new ArrayList<>();
        Statement stmt;
        try {
            String sql = "select * from Sponsor";
            
          stmt = cnx2.createStatement();
            
               ResultSet rs =  stmt.executeQuery(sql);
                
                while(rs.next()){
                    /*
                   Sponsor a = new Sponsor
        (rs.getInt("id"),findByIdE(rs.getInt("id_equipe_id")),rs.getString("nom_sponsor") , rs.getString("description_sponsor"), rs.getString("logo_sponsor"), rs.getString("site_webs"),rs.getTimestamp("date_creationn").toLocalDateTime());               
                   System.out.println(a);
                all.add(a);  
*/
                     LocalDateTime dateCreationn = rs.getTimestamp("date_creationn").toLocalDateTime();
               
                    all.add(new Sponsor(
                        rs.getInt("id"),
                           
                        rs.getString("nom_sponsor"),
                        rs.getString("description_sponsor"),
                        rs.getString("logo_sponsor"),
                        rs.getString("site_webs"),
                           dateCreationn             
                            
                    ));
                }
               return all;
                    
            
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }
        return all;
    }
     
     
}
