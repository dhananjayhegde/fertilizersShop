/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fertilizers;

/**
 *
 * @author ProjectTeam
 */
import java.io.FileOutputStream;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DatabaseConnection;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalesInvoicePdf {

    private static String FILE = "e:/pdf/si";
    private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font subtitleFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Font customerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);
    private static Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLDITALIC);
//    private static Font itemFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
//            Font.BOLD);
//    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
//            Font.BOLD);
    
    private static final String DEST = "/pdf/salesinvoice/";
    private File file;
    
    private long orderId;
    private SalesModel so;
    private ArrayList<SalesItemsModel> soitem = new ArrayList();
    private FarmerModel fm;

    public SalesInvoicePdf(long orderId) {
        this.orderId = orderId;
        this.file = new File(DEST + this.orderId + ".PDF");
        file.getParentFile().mkdirs();
    }

    public String createPdf() throws Exception {

            this.loadData();
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(this.file));
            document.open();
            //loading the data
//            this.loadData();
            //creating the pdf file
            this.addMetaData(document);
            this.addTitlePage(document);
            document.close();
            return this.file.getAbsolutePath();

    }

    private void loadData() throws SQLException {
        this.loadFamerData();
        this.loadOrderData();
    }

    private void loadOrderData() throws SQLException {
        Statement stmt;
        ResultSet rs;
        String query;

        stmt = DatabaseConnection.getConnection().getStatement();
        query = "SELECT * FROM sales where id=" + this.orderId;
        rs = stmt.executeQuery(query);

        if (rs.next()) {
            this.so = new SalesModel(rs.getLong("id"), rs.getLong("farmerid"), rs.getDate("date"), rs.getDouble("subtotal"), rs.getDouble("total"));
        } else {
            new Exception("No Order found with Id " + this.orderId);
        }

        //load order item data
        query = "SELECT * FROM salesdetails WHERE id=" + this.orderId;

        rs = stmt.executeQuery(query);

        while (rs.next()) {
            this.soitem.add(new SalesItemsModel(rs.getLong("id"),
                    rs.getInt("itemno"),
                    rs.getLong("productid"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getDouble("amount")));
        }
        
        if(this.soitem.isEmpty()) {
            new Exception("Order has not items");
        }

    }

    private void loadFamerData() throws SQLException {

        Statement stmt;
        ResultSet rs;
        String query;

        stmt = DatabaseConnection.getConnection().getStatement();
        query = "SELECT f.id, f.name, f.address, f.mobile "
                + "FROM sales s INNER JOIN farmer as f ON s.farmerid = f.id "
                + "WHERE s.id=" + this.orderId;
        rs = stmt.executeQuery(query);

        if (rs.next()) {
            this.fm = new FarmerModel(rs.getLong("id"), rs.getString("name"), rs.getString("address"), rs.getString("mobile"));
        } else {
            new Exception("No farmer data found for order Id " + this.orderId);
        }
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private void addMetaData(Document document) {
//        document.addTitle("My first PDF");
//        document.addSubject("Using iText");
//        document.addKeywords(this.orderId + "");
//        document.addAuthor("Lars Vogel");
//        document.addCreator("Lars Vogel");
    }

    private void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        Paragraph header = new Paragraph("Fartilo Enterprises Sales Invoice", headerFont);
        header.setAlignment(Paragraph.ALIGN_CENTER);
        preface.add(header);
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Order ID : " + this.orderId, titleFont));
        try {
            preface.add(new Paragraph("Order Date : " + DateUtil.dateToString(this.so.getDate())));
        } catch (Exception ex) {
            Logger.getLogger(SalesInvoicePdf.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEmptyLine(preface, 1);
        addEmptyLine(preface, 1);
        //add customer data here
        preface.add(new Paragraph("Customer/Farmer Details: ", subtitleFont));
        preface.add(new Paragraph("Farmer Id    : " + this.fm.getId(), customerFont));
        preface.add(new Paragraph("Name         : " + this.fm.getName(), customerFont));
        preface.add(new Paragraph("Address      : " + this.fm.getAddress(), customerFont));
        preface.add(new Paragraph("Mobile       : " + this.fm.getMobile(), customerFont));

        //add order header data
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Order Details: ", subtitleFont));
        preface.add(new Paragraph("Subtotal     : Rs. " + this.so.getSubtotal(), customerFont));
        preface.add(new Paragraph("Total        : Rs. " + this.so.getTotal(), customerFont));

        //add item details
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Order Item Details: ", subtitleFont));
        addEmptyLine(preface, 1);
        
        this.createTable(preface);  
        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private void createTable(Paragraph para)
            throws BadElementException, DocumentException {
        PdfPTable table = new PdfPTable(5);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);
        //add table headers
        PdfPCell c1 = new PdfPCell(new Phrase("Item#"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Product Id"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Price(Rs.)"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Quantity (kg)"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Amount (Rs.)"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        //add table items
        Iterator ii = this.soitem.iterator();
        SalesItemsModel si;
        while(ii.hasNext()){
            si = (SalesItemsModel)ii.next();
            table.addCell(si.getItemNo() + "");
            table.addCell(si.getProductId() + "");
            table.addCell(si.getPrice() + "");
            table.addCell(si.getQuantity() + "");
            table.addCell(si.getAmount() + "");
        }

        para.add(table);

    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
