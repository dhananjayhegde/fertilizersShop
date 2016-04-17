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
public class SalesModel {
    private long id = -1L; //BEFORE SAVING TO DATABASE
    private long farmerId;
    private Date date;
    private double subtotal;
    private double total;

    private static int numberOfItems = 0;

    private ArrayList<SalesItemsModel> items = new ArrayList();

    public SalesModel(long farmerId, Date date) {
        this.farmerId = farmerId;
        this.date = date;
        this.subtotal = 0.00;
        this.total = 0.00;
    }

    public SalesModel(long farmerId, Date date, double subtotal, double total) {
        this.farmerId = farmerId;
        this.date = date;
        this.subtotal = subtotal;
        this.total = total;
    }

    public SalesModel(long id, long farmerId, Date date, double subtotal, double total) {
        this.id = id;
        this.farmerId = farmerId;
        this.date = date;
        this.subtotal = subtotal;
        this.total = total;
    }

    public void displaySalesOrder() {
        System.out.println("Sales Order Header : ");
        System.out.println("------------------------------------------------------------------");
        System.out.println("ID      : " + this.getId());
        System.out.println("Supplier Id : " + this.farmerId);
        System.out.println("Total       : " + this.total);
        System.out.println("Sales Order Items : ");
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

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void addItem(SalesItemsModel item) {
        item.setItemNo(++SalesModel.numberOfItems);
        this.items.add(item);
        this.calculateTotal();
    }

    public void removeItem(SalesItemsModel item) {
        if (this.items.removeIf(e -> e.getId() == item.getId() && e.getItemNo() == item.getItemNo())) {
            --SalesModel.numberOfItems;
            this.calculateTotal();
        }
    }

    public void removeItemByItemNumber(int itemNo) {
        this.items.removeIf(e -> e.getId() == itemNo);
    }

    public ArrayList<SalesItemsModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<SalesItemsModel> items) {
        this.items = items;
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

    public long getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(long farmerId) {
        this.farmerId = farmerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private void calculateTotal() {
        Iterator it = this.items.iterator();
        double subtotal = 0;
        while (it.hasNext()) {
            subtotal += ((SalesItemsModel) it.next()).getAmount();
        }
        if (subtotal > 0) {
            this.subtotal = subtotal;
            this.total = this.subtotal;
        }

    }
}
