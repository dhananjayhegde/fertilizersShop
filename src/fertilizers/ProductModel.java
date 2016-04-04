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
public class ProductModel {
    
    private long id;
    private String name;
    private String description;
    private String composition;
    private long stockqty;
    private double price;

    public ProductModel(long id, String name, String description, String composition, long stockqty, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.composition = composition;
        this.stockqty = stockqty;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public long getStockqty() {
        return stockqty;
    }

    public void setStockqty(long stockqty) {
        this.stockqty = stockqty;
    }


    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    @Override
    public String toString(){
        return this.name;
    }

}
