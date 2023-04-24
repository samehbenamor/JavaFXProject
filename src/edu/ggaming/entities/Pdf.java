/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.entities;


import edu.ggaming.services.ServiceProduit;
import com.itextpdf.text.Document;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author AZAYEZ BINSA
 */
public class Pdf {

    public void GeneratePdf(String nom,String adresse,String reference,String filename, Panier panier, int id) throws FileNotFoundException, DocumentException, BadElementException, IOException, InterruptedException, SQLException {
       

try {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));
    document.open();
    
    // Ajouter les styles CSS
    StyleSheet styles = new StyleSheet();
    styles.loadTagStyle("table", "border", "1");
    styles.loadTagStyle("th", "padding", "5px");
    styles.loadTagStyle("td", "padding", "5px");
    styles.loadTagStyle("td", "text-align", "center");
    styles.loadTagStyle("th", "background-color", "#007bff");
    styles.loadTagStyle("th", "color", "#fff");
    
    // Ajouter le titre de la facture
    Font fontTitre = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    Paragraph titre = new Paragraph("Facture N°"+reference, fontTitre);
    titre.setAlignment(Element.ALIGN_CENTER);
    titre.setSpacingAfter(10);
    document.add(titre);

    // Ajouter les informations du client
    Font fontClient = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    Paragraph client = new Paragraph("Client: " + nom + "\n" + adresse+"\n"+"Pole Technologique El Ghazela", fontClient);
    client.setSpacingAfter(20);
    document.add(client);
    
    // Créer le tableau des produits
    PdfPTable table = new PdfPTable(3);
    
    // Ajouter des en-têtes de colonne avec des styles CSS
    PdfPCell cellProduit = new PdfPCell(new Paragraph("Produit", fontTitre));
    cellProduit.setHorizontalAlignment(Element.ALIGN_CENTER);
    cellProduit.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cellProduit.setBackgroundColor(BaseColor.BLUE);
    cellProduit.setBorderColor(BaseColor.WHITE);
    table.addCell(cellProduit);
    PdfPCell cellQuantite = new PdfPCell(new Paragraph("Quantité", fontTitre));
    cellQuantite.setHorizontalAlignment(Element.ALIGN_CENTER);
    cellQuantite.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cellQuantite.setBackgroundColor(BaseColor.BLUE);
    cellQuantite.setBorderColor(BaseColor.WHITE);
    table.addCell(cellQuantite);
    PdfPCell cellPrix = new PdfPCell(new Paragraph("Prix", fontTitre));
    cellPrix.setHorizontalAlignment(Element.ALIGN_CENTER);
    cellPrix.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cellPrix.setBackgroundColor(BaseColor.BLUE);
    cellPrix.setBorderColor(BaseColor.WHITE);
    table.addCell(cellPrix);

    // Ajouter les données de chaque produit dans le tableau avec des styles CSS
    for (Produit produit : panier.getProduits()) {
        PdfPCell cellProduitData = new PdfPCell(new Paragraph(produit.getNom()));
        cellProduitData.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellProduitData.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellProduitData.setBorderColor(BaseColor.WHITE);
        table.addCell(cellProduitData);

        PdfPCell cellQuantiteData = new PdfPCell(new Paragraph(String.valueOf(produit.getQuantite())));
        cellQuantiteData.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellQuantiteData.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellQuantiteData.setBorderColor(BaseColor.WHITE);
        table.addCell(cellQuantiteData);

        PdfPCell cellPrixData = new PdfPCell(new Paragraph(String.valueOf(produit.getPrix())));
        cellPrixData.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellPrixData.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellPrixData.setBorderColor(BaseColor.WHITE);
        table.addCell(cellPrixData);
    }

    // Ajouter le tableau dans la facture
    document.add(table);
    document.add(new Paragraph("TOTAL :" + panier.getTotal()));
    document.close();
    Process pro = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filename + ".pdf");
    System.out.println("Facture générée avec succès !");
} catch (Exception ex) {
  
            System.out.println("Erreur lors de la génération de la facture: " + ex.getMessage());
        }

}
    
}