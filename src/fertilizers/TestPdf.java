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
public class TestPdf {
    
    public static void main(String[] args){
        SalesInvoicePdf si = new SalesInvoicePdf(900002L);
        si.createPdf();
    }
}
