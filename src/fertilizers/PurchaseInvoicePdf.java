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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
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

public class PurchaseInvoicePdf {

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
    
    private static final String DEST = "/pdf/purchaseinvoice/";
    private File file;
    
    private long orderId;
    private PurchaseModel po;
    private ArrayList<PurchaseItemsModel> poitem = new ArrayList();
    private SupplierModel sm;

    public PurchaseInvoicePdf(long orderId) {
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
        this.loadSupplierData();
        this.loadOrderData();
    }

    private void loadOrderData() throws SQLException {
        Statement stmt;
        ResultSet rs;
        String query;

        stmt = DatabaseConnection.getConnection().getStatement();
        query = "SELECT * FROM purchase where id=" + this.orderId;
        rs = stmt.executeQuery(query);

        if (rs.next()) {
            this.po = new PurchaseModel(rs.getLong("id"), rs.getLong("supplierid"), rs.getDate("date"), rs.getDouble("subtotal"), rs.getDouble("total"),
                                        rs.getDouble("subsidy"));
        } else {
            new Exception("No Order found with Id " + this.orderId);
        }

        //load order item data
        query = "SELECT * FROM purchasedetails WHERE id=" + this.orderId;

        rs = stmt.executeQuery(query);

        while (rs.next()) {
            this.poitem.add(new PurchaseItemsModel(rs.getLong("id"),
                    rs.getInt("itemno"),
                    rs.getLong("productid"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getDouble("amount")));
        }
        
        if(this.poitem.isEmpty()) {
            new Exception("Order has no items");
        }

    }

    private void loadSupplierData() throws SQLException {

        Statement stmt;
        ResultSet rs;
        String query;

        stmt = DatabaseConnection.getConnection().getStatement();
        query = "SELECT s.id, s.name, s.address, s.mobile, s.tin "
                + "FROM purchase p INNER JOIN supplier s ON p.supplierid = s.id "
                + "WHERE p.id =" + this.orderId;
        rs = stmt.executeQuery(query);

        if (rs.next()) {
            this.sm = new SupplierModel(rs.getLong("id"), rs.getString("name"), rs.getString("address"), 
                    rs.getString("mobile"), rs.getString("tin"));
        } else {
            new Exception("No supplier data found for order Id " + this.orderId);
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
        Paragraph header = new Paragraph("Fartilo Enterprises Purchase Invoice", headerFont);
        header.setAlignment(Paragraph.ALIGN_CENTER);
        preface.add(header);
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Order ID : " + this.orderId, titleFont));
        try {
            preface.add(new Paragraph("Order Date : " + DateUtil.dateToString(this.po.getDate())));
        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoicePdf.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEmptyLine(preface, 1);
        addEmptyLine(preface, 1);
        //add customer data here
        preface.add(new Paragraph("Supplier Details : ", subtitleFont));
        preface.add(new Paragraph("Supplier Id      : " + this.sm.getId(), customerFont));
        preface.add(new Paragraph("Name             : " + this.sm.getName(), customerFont));
        preface.add(new Paragraph("Address          : " + this.sm.getAddress(), customerFont));
        preface.add(new Paragraph("Mobile           : " + this.sm.getMobile(), customerFont));
        preface.add(new Paragraph("Tin              : " + this.sm.getTin(), customerFont));

        //add order header data
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Order Details: ", subtitleFont));
        preface.add(new Paragraph("Subtotal     : Rs. " + this.po.getSubtotal(), customerFont));
        preface.add(new Paragraph("Subsidy      : Rs. " + this.po.getSubsidy(), customerFont));
        preface.add(new Paragraph("Total        : Rs. " + this.po.getTotal(), customerFont));

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
        Iterator ii = this.poitem.iterator();
        PurchaseItemsModel pi;
        while(ii.hasNext()){
            pi = (PurchaseItemsModel)ii.next();
            table.addCell(pi.getItemNo() + "");
            table.addCell(pi.getProductId() + "");
            table.addCell(pi.getPrice() + "");
            table.addCell(pi.getQuantity() + "");
            table.addCell(pi.getAmount() + "");
        }

        para.add(table);

    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
