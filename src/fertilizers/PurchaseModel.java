/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fertilizers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author ProjectTeam
 */
public class PurchaseModel {
    
    private long id;
    private long supplierId;
    private Date date;
    private double subtotal;
    private double total;
    private double subsidy;
    
    private ArrayList<PurchaseItemsModel> items = new ArrayList();


    public PurchaseModel(long supplierId, Date date, double subtotal, double total, double subsidy) {
        this.supplierId = supplierId;
        this.date = date;
        this.subtotal = subtotal;
        this.total = total;
        this.subsidy = subsidy;
    }

    private PurchaseModel(long id, long supplierId, Date date, double subtotal, double total, double subsidy) {
        this.id = id;
        this.supplierId = supplierId;
        this.date = date;
        this.subtotal = subtotal;
        this.total = total;
        this.subsidy = subsidy;
    }
    
    public void displayPurchaseOrder(){
        System.out.println("Purchase Order Header : ");
        System.out.println("------------------------------------------------------------------");
        System.out.println("ID      : " + this.getId());
        System.out.println("Supplier Id : " + this.supplierId);
        System.out.println("Total       : " + this.total);
        System.out.println("Purchase Order Items : ");
        System.out.println("------------------------------------------------------------------");
        System.out.println("Item No.    Product Id      Price       Quantity        Amount");
        System.out.println("------------------------------------------------------------------");
        this.items.forEach(e -> System.out.println(e.getItemNo() + "    " + e.getProductId() + "    " 
                                + e.getPrice() + "  " + e.getQuantity() + " " + e.getAmount()));
    }
//    public static PurchaseModel getPurchaseById(long id){
//        
//        return new PurchaseModel();
//    }
    
    public void addItem(PurchaseItemsModel item){
        this.items.add(item);
        this.calculateTotal();
    }
    
    public void removeItem(PurchaseItemsModel item){
        this.items.removeIf(e -> e.getId() == item.getId());
    }
    
    public void removeItemByItemNumber(int itemNo){
        this.items.removeIf(e -> e.getId() == itemNo);
    }
    
    public ArrayList<PurchaseItemsModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<PurchaseItemsModel> items) {
        this.items = items;
    }
    
    public double getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(double subsidy) {
        this.subsidy = subsidy;
    }

    public double getTotal() {
        return total;
    }

//    public void setTotal(double total) {
//        this.total = total;
//    }


    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private void calculateTotal() {
        Iterator it = this.items.iterator();
        double total = 0;
        
        while(it.hasNext()){
            total += ((PurchaseItemsModel)it.next()).getAmount();
        }
        
        this.total = total;
    }
}
