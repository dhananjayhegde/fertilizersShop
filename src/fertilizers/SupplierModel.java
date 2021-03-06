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
public class SupplierModel {
    
    private long id;
    private String name;
    private String address;
    private String mobile;
    private String tin;

    public SupplierModel(long id, String name, String address, String mobile, String tin) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.tin = tin;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    
    public String toString(){
       return this.name;
    }
}
