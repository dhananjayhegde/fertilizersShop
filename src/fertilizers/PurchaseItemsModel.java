/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fertilizers;

import java.util.Date;

/**
 *
 * @author ProjectTeam
 */
public class PurchaseItemsModel {
    
    private long id;
    private int itemNo;
    private long productId;
    private double price;
    private int quantity;
    private double amount;
    private java.util.Date expiryDate;
    
    public PurchaseItemsModel(){
    
    }

    public PurchaseItemsModel(long id, long productId, double price, int quantity, double amount) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
    }
    
    public PurchaseItemsModel(long id, int itemNo, long productId, double price, int quantity, double amount) {
        this.id = id;
        this.itemNo = itemNo;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
