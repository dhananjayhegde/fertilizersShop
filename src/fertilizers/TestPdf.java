/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fertilizers;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ProjectTeam
 */
public class TestPdf {
    
    public static void main(String[] args){
        PurchaseInvoicePdf si = new PurchaseInvoicePdf(20160007L);
        try {
            String filecreatedat = si.createPdf();
            System.out.println(filecreatedat);
        } catch (Exception ex) {
            Logger.getLogger(TestPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
