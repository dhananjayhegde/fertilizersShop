/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fertilizers;

import database.DatabaseConnection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;

/**
 *
 * @author ProjectTeam
 */
public class SalesForm extends AbstractForm {

    private Vector<Object[]> salesItems;
    private String[] headers;
    private SalesModel salesOrder;
    private ALVDynamicTableModel alvModel;

    public SalesForm(JFrame prev) {
        super(prev);
        initComponents();
        initialize();
        this.setLocationRelativeTo(null);
    }

    /**
     * Creates new form PurchaseForm
     */
    public SalesForm() {
        initComponents();
        initialize();
    }

    private void initializeItemsTable() {
        this.headers = this.getTableColumns();
        this.salesItems = new Vector<Object[]>();
        this.alvModel = new ALVDynamicTableModel(this.salesItems, this.headers);
        this.jtbitemdata.setModel(alvModel);
    }

    private String[] getTableColumns() {
        return new String[]{"Produ. ID", "Product Name", "Unit Price", "Quantity(in kg)", "Amount (in Rs.)"};
    }

    private void clearItemModelData() {
        this.alvModel.removeAllRows();
    }

    private void removeItems(int[] rows) {

        this.alvModel.removeSelectedRows(rows);
    }

    private void addItemToModel() {

        Object[] row = this.getNewITemForModel();
        if (row == null && !this.errors.isEmpty()) {
            this.jlmsg.setText(this.msgListToString(this.errors));
        } else if ( row == null){
            this.jlmsg.setText("There is some problem adding item ");
        }        
        else {
            this.jlmsg.setText("");
            this.alvModel.appendRow(row);
        }
    }

    //Call this from action event of Add Item button
    private Object[] getNewITemForModel() {

        Object[] row = new Object[this.headers.length];
        
        this.errors.clear();
        
        if (!this.jtfqty.getText().isEmpty()) {
            
            
            try {
                this.query = "SELECT stockqty "
                        + "FROM products "
                        + "WHERE id=" + ((ProductModel) this.jcbproduct.getSelectedItem()).getId();
                
                this.rs.close();
                
                this.rs = this.stmt.executeQuery(this.query);
                
                if (this.rs.next()) {

                    long stockqty = this.rs.getLong("stockqty");
                    stockqty -= Integer.parseInt(this.jtfqty.getText());
                    
                    if(stockqty >=0 ){
                        row[0] = ((ProductModel) this.jcbproduct.getSelectedItem()).getId();
                        row[1] = ((ProductModel) this.jcbproduct.getSelectedItem()).getName();
                        row[2] = this.jtfprice.getText();
                        row[3] = this.jtfqty.getText();
                        row[4] = this.jtfamount.getText();
                        
                        return row;
                    } else {
                        this.errors.add("You can order only " + (Integer.parseInt(this.jtfqty.getText()) + stockqty) + ""
                                + " quantity of this product");
                    }
                }          
            } catch (Exception ex) {
                this.errors.add(ex.getMessage());
            }
        }
        return null;
    }

    private void updateAmountField() {

        double price = 0.00;
        double amount = 0.00;
        int iQty = 0;
        String qty;

        qty = this.jtfqty.getText();

        try {
            iQty = Integer.parseInt(qty);
            price = Double.parseDouble(this.jtfprice.getText());

            //CALCULATION OF TOTAL AMOUTN PER ITEM
            amount = iQty * price;
            this.jlmsg.setText("");
        } catch (NumberFormatException ex) {
            this.jlmsg.setText("Quantity has to be a whole number and price has to be a decimal");
        }

        this.jtfamount.setText(amount + "");
    }

    private ComboBoxModel getFarmerModel() {
        FarmerModel[] farmers = null;
        ArrayList<FarmerModel> flist = new ArrayList();

        this.stmt = DatabaseConnection.getConnection().getStatement();

        this.query = "SELECT * FROM farmer";

        try {
            this.rs = this.stmt.executeQuery(this.query);
            while (this.rs.next()) {
                flist.add(new FarmerModel(this.rs.getLong("id"),
                        this.rs.getString("name"),
                        this.rs.getString("address"),
                        this.rs.getString("mobile")
                ));

            }

            farmers = new FarmerModel[flist.size()];
            Iterator it = flist.iterator();
            int i = 0;
            while (it.hasNext()) {
                farmers[i++] = (FarmerModel) it.next();
            }

        } catch (SQLException ex) {
            this.jlmsg.setText("There was a problem getting farmer data from database");
        }

        return new DefaultComboBoxModel(farmers);
    }

    private ComboBoxModel getProductModel() {

        ProductModel[] products = null;
        ArrayList<ProductModel> plist = new ArrayList();

        this.stmt = DatabaseConnection.getConnection().getStatement();

        this.query = "SELECT * FROM products";

        try {
            this.rs = this.stmt.executeQuery(this.query);
            while (this.rs.next()) {
                plist.add(new ProductModel(this.rs.getLong("id"),
                        this.rs.getString("name"),
                        this.rs.getString("description"),
                        this.rs.getString("composition"),
                        this.rs.getLong("stockqty"),
                        this.rs.getDouble("price")
                ));

            }

            products = new ProductModel[plist.size()];
            Iterator it = plist.iterator();
            int i = 0;
            while (it.hasNext()) {
                products[i++] = (ProductModel) it.next();
            }

        } catch (SQLException ex) {
            this.jlmsg.setText("There was a problem getting product data from database");
        }

        return new DefaultComboBoxModel(products);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jlbanner = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jlheader = new javax.swing.JLabel();
        jlfarmer = new javax.swing.JLabel();
        jcbfarmer = new javax.swing.JComboBox<>();
        jlproduct = new javax.swing.JLabel();
        jlprice = new javax.swing.JLabel();
        jlquantity = new javax.swing.JLabel();
        jlamount = new javax.swing.JLabel();
        jcbproduct = new javax.swing.JComboBox<>();
        jtfprice = new javax.swing.JTextField();
        jtfqty = new javax.swing.JTextField();
        jtfamount = new javax.swing.JTextField();
        jbtadditem = new javax.swing.JButton();
        jbtclearitemdata = new javax.swing.JButton();
        jbtneworder = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jlitem = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jlitems = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbitemdata = new javax.swing.JTable();
        jbtremoveitem = new javax.swing.JButton();
        jbtsaveorder = new javax.swing.JButton();
        jlsuccessmsg = new javax.swing.JLabel();
        jbtback = new javax.swing.JButton();
        jbtlogout = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jlmsg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbanner.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbanner.setText("Create Sales Entry");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlheader.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlheader.setText("Header");

        jlfarmer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlfarmer.setText("Farmer : ");

        jcbfarmer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jlproduct.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlproduct.setText("Product : ");

        jlprice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlprice.setText("Price : ");

        jlquantity.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlquantity.setText("Quantity : ");

        jlamount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlamount.setText("Amount : ");

        jcbproduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbproduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbproductActionPerformed(evt);
            }
        });

        jtfprice.setEditable(false);
        jtfprice.setToolTipText("User input not allowed on this field");

        jtfqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfqtyKeyReleased(evt);
            }
        });

        jtfamount.setEditable(false);
        jtfamount.setToolTipText("User input not allowed on this field");

        jbtadditem.setText("Add Item");
        jbtadditem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtadditemActionPerformed(evt);
            }
        });

        jbtclearitemdata.setText("Clear Item Data");
        jbtclearitemdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtclearitemdataActionPerformed(evt);
            }
        });

        jbtneworder.setText("New Order ");
        jbtneworder.setToolTipText("Wipes out existing order data and initiates a new order");
        jbtneworder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtneworderActionPerformed(evt);
            }
        });

        jlitem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlitem.setText("Item");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jlfarmer, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jcbfarmer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jbtneworder, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtadditem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtclearitemdata, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jlquantity, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jtfqty, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jlprice, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jtfprice, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jlamount, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jtfamount, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jlproduct, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jcbproduct, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlitem, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlheader, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jlheader, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlfarmer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbfarmer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlitem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlproduct, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbproduct, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlprice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfprice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlquantity, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfqty, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlamount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfamount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtadditem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtclearitemdata, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtneworder, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlitems.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlitems.setText("Items");

        jtbitemdata.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtbitemdata.setToolTipText("");
        jScrollPane1.setViewportView(jtbitemdata);

        jbtremoveitem.setText("Remove Items");
        jbtremoveitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtremoveitemActionPerformed(evt);
            }
        });

        jbtsaveorder.setText("Save Order");
        jbtsaveorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtsaveorderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jlitems, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jbtremoveitem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtsaveorder, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jlitems, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtremoveitem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtsaveorder, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jbtback.setText("Back");
        jbtback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtbackActionPerformed(evt);
            }
        });

        jbtlogout.setText("Logout");
        jbtlogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtlogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlbanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlsuccessmsg, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtback, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtlogout, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jlbanner, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlsuccessmsg, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtback)
                        .addComponent(jbtlogout)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlmsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlmsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcbproductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbproductActionPerformed
        // TODO add your handling code here:
        this.updatePrice();
    }//GEN-LAST:event_jcbproductActionPerformed

    private void jtfqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfqtyKeyReleased
        // TODO add your handling code here:
        this.updateAmountField();
    }//GEN-LAST:event_jtfqtyKeyReleased

    private void jbtclearitemdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtclearitemdataActionPerformed
        // TODO add your handling code here:
        this.clearItemData();
    }//GEN-LAST:event_jbtclearitemdataActionPerformed

    private void jbtadditemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtadditemActionPerformed
        // TODO add your handling code here:
        this.addItemToModel();
    }//GEN-LAST:event_jbtadditemActionPerformed

    private void jbtremoveitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtremoveitemActionPerformed
        // TODO add your handling code here:
        this.removeItems(this.jtbitemdata.getSelectedRows());
    }//GEN-LAST:event_jbtremoveitemActionPerformed

    private void jbtneworderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtneworderActionPerformed
        // TODO add your handling code here:
        this.clearItemModelData();
        this.clearItemData();
        this.jlsuccessmsg.setText("");
    }//GEN-LAST:event_jbtneworderActionPerformed

    private void jbtsaveorderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtsaveorderActionPerformed
        // TODO add your handling code here:
        this.saveOrder();
    }//GEN-LAST:event_jbtsaveorderActionPerformed

    private void jbtbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtbackActionPerformed
        // TODO add your handling code here:
        this.goToPrevious();
    }//GEN-LAST:event_jbtbackActionPerformed

    private void jbtlogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtlogoutActionPerformed
        // TODO add your handling code here:
        this.dispose();
        (new LoginForm()).setVisible(true);
    }//GEN-LAST:event_jbtlogoutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalesForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbtadditem;
    private javax.swing.JButton jbtback;
    private javax.swing.JButton jbtclearitemdata;
    private javax.swing.JButton jbtlogout;
    private javax.swing.JButton jbtneworder;
    private javax.swing.JButton jbtremoveitem;
    private javax.swing.JButton jbtsaveorder;
    private javax.swing.JComboBox<String> jcbfarmer;
    private javax.swing.JComboBox<String> jcbproduct;
    private javax.swing.JLabel jlamount;
    private javax.swing.JLabel jlbanner;
    private javax.swing.JLabel jlfarmer;
    private javax.swing.JLabel jlheader;
    private javax.swing.JLabel jlitem;
    private javax.swing.JLabel jlitems;
    private javax.swing.JLabel jlmsg;
    private javax.swing.JLabel jlprice;
    private javax.swing.JLabel jlproduct;
    private javax.swing.JLabel jlquantity;
    private javax.swing.JLabel jlsuccessmsg;
    private javax.swing.JTable jtbitemdata;
    private javax.swing.JTextField jtfamount;
    private javax.swing.JTextField jtfprice;
    private javax.swing.JTextField jtfqty;
    // End of variables declaration//GEN-END:variables

    private void initialize() {
        this.jcbproduct.setModel(this.getProductModel());
        this.jcbfarmer.setModel(this.getFarmerModel());
        this.jtfqty.setText("0");
        this.updatePrice();
        this.updateAmountField();
        this.initializeItemsTable();
    }

    private void updatePrice() {

        ProductModel selProd;

        selProd = (ProductModel) this.jcbproduct.getSelectedItem();
        this.jtfprice.setText(selProd.getPrice() + "");
        this.updateAmountField();

    }

    private void clearItemData() {
        this.jtfamount.setText("");
        this.jtfqty.setText("");
        this.jtfprice.setText("");
        this.jtfqty.setText("0");
//        this.jtfsubsidy.setText("");
        this.jcbproduct.setSelectedIndex(0);
        this.jcbfarmer.setSelectedIndex(0);
    }

    private void saveOrder() {
        //local variable declaration
        SalesItemsModel item;
        long orderId = -1;
        //initialize the messages
        this.errors.clear();
        this.success.clear();

        //check if there are any items added at all
        if (this.prepareOrderData()) {
            //first save the order header data into "purchase" tbale
            this.jlmsg.setText("Order prepared successfully");
            this.stmt = DatabaseConnection.getConnection().getStatement();
            
            this.query = "INSERT INTO sales "
                    + "(farmerid, date, subtotal, total) "
                    + "VALUES "
                    + "('" + this.salesOrder.getFarmerId() + "', '" + new java.sql.Date(this.salesOrder.getDate().getTime()) + "', "
                    + "'" + this.salesOrder.getSubtotal() + "', '" + this.salesOrder.getTotal() + "')";

            try {
                this.rs.close(); //close the previous result set
                Integer numero = this.stmt.executeUpdate(this.query, Statement.RETURN_GENERATED_KEYS);
                this.rs = this.stmt.getGeneratedKeys();

                if (this.rs.next()) {
                    orderId = this.rs.getLong(1);
                    this.salesOrder.setId(orderId); //set the generated Order ID
                    Iterator itemIterator = this.salesOrder.getItems().iterator();

                    while (itemIterator.hasNext()) {
                        this.query = "";
                        this.rs.close();

                        item = (SalesItemsModel) itemIterator.next();

                        this.query = "INSERT INTO salesdetails "
                                + "(id, itemno, productid, price, quantity, amount) "
                                + "VALUES "
                                + "('" + orderId + "', '" + item.getItemNo() + "', "
                                + "'" + item.getProductId() + "', '" + item.getPrice() + "', "
                                + "'" + item.getQuantity() + "', '" + item.getAmount() + "')";

                        this.stmt.executeUpdate(this.query);
                        //There are two more updates required
                        //UPDATE PRODUCT STOCK - get the current stock and then deduct the sales quantity and update
                        this.query = "SELECT stockqty "
                                + "FROM products "
                                + "WHERE id=" + item.getProductId();
                        this.rs.close();
                        
                        this.rs = this.stmt.executeQuery(this.query);
                        if(this.rs.next()){
                        
                            long stockqty = this.rs.getLong("stockqty");
                            stockqty -= item.getQuantity();
                            //here, stockqty cannot go negative!
                            this.query = "UPDATE products "
                                    + "SET stockqty=" + stockqty
                                    + " WHERE id=" + item.getProductId();
                            this.rs.close();
                            
                            this.stmt.executeUpdate(this.query);
                        }
                    }
                    
                    //UPDATE FARMER ACCOUNT BALANCE  - add order total to account balance
                    double accountBalance = 0;
                    long accountId;
                    this.query = "SELECT * "
                            + "FROM account "
                            + "WHERE farmerid=" + this.salesOrder.getFarmerId();
                    
                    this.rs.close();
                    this.rs = this.stmt.executeQuery(this.query);
                    if(this.rs.next()){
                        accountId = this.rs.getLong("id");
                        accountBalance = this.rs.getDouble("balance");
                        
                        //create transaction
                        this.query = "INSERT INTO transaction "
                                + "(accountid, date, type, amount) "
                                + "VALUES "
                                + "('" + accountId + "', '" + new java.sql.Date(this.salesOrder.getDate().getTime()) + "', "
                                + "'" + 1 + "', '" + this.salesOrder.getTotal() + "')";
                        this.rs.close();
                        this.stmt.executeUpdate(this.query);
                        
                        //Update account balance
                        accountBalance -= this.salesOrder.getTotal();
                        
                        this.query = "UPDATE account "
                                + "SET balance=" + accountBalance
                                + " WHERE id=" + accountId;
                        this.rs.close();
                        this.stmt.executeUpdate(this.query);
                        this.jlsuccessmsg.setText("Order saved successfully. Order ID : " + orderId);
                        this.clearItemModelData();
                        this.clearItemData();
                    }                            
                }

            } catch (SQLException ex) {
                Logger.getLogger(SalesForm.class.getName()).log(Level.SEVERE, null, ex);
                if (orderId > 0) {
                    //this means, order header saved but not item details.
                    //So, to maintain data consistency, we delete the order header/detials
                    this.query = "DELETE FROM sales "
                            + "WHERE id=" + orderId;

                    try {
                        this.stmt.executeUpdate(this.query);
                        //if successfull, delete all partially saved item details
                        this.query = "DELETE FROM salesdetails "
                                + "WHERE id=" + orderId;
                        this.stmt.executeUpdate(this.query);
                        
                    } catch (SQLException ex1) {
                        this.jlmsg.setText("There was a problem saving the order");
                    }
                }
            }

        } else {
            this.jlmsg.setText(this.msgListToString(this.errors));
        }  
    }

    private boolean prepareOrderData() {

        Long farmerId;
        java.util.Date util_today;
        double subsidy = 0;

        SalesItemsModel salesItem;
        //initialize the messages
        this.errors.clear();
        this.success.clear();
        this.salesOrder = null; //initialize

        if (this.alvModel.getRowCount() <= 0) {
            this.jlmsg.setText("Add at least one item to order");
            return false;
        }

//        if (this.jtfsubsidy.getText().isEmpty()) {
//            this.errors.add("Enter a subsidy amount in Rs.");
//        }

        //if we are here, then there may not be any errors   
        farmerId = ((FarmerModel) this.jcbfarmer.getSelectedItem()).getId();
        util_today = new java.util.Date();

        this.salesOrder = new SalesModel(farmerId, util_today);

        //no subsidy amount for sales order data
//        try {
//            subsidy = Double.parseDouble(this.jtfsubsidy.getText());
//        } catch (NumberFormatException ex) {
//            this.errors.add("Enter a decimal number for subsidy amount");
//        }

        if (this.errors.isEmpty()) {
            //this.salesOrder.setSubsidy(subsidy);
            //prepare item data

            for (int rowIndex = 0; rowIndex < this.alvModel.getRowCount(); rowIndex++) {

                salesItem = new SalesItemsModel();
                int colIndex = 0;
                //purchaseItem.setItemNo(itemNo++); addITem method takes care of this
                salesItem.setProductId((long) this.alvModel.getValueAt(rowIndex, colIndex++));
                colIndex++; //skip the product name column from the table model
                salesItem.setPrice(Double.parseDouble((String) this.alvModel.getValueAt(rowIndex, colIndex++)));
                salesItem.setQuantity(Integer.parseInt((String) this.alvModel.getValueAt(rowIndex, colIndex++)));
                salesItem.setAmount(Double.parseDouble((String) this.alvModel.getValueAt(rowIndex, colIndex++)));

                //add the item to purchaseOrder.  Total and subtotal are calculated automatically
                this.salesOrder.addItem(salesItem);
            }

            if (this.salesOrder.getNumberOfItems() <= 0) {
                this.errors.add("No Items are added to order");
            }
//            if (this.salesOrder.getTotal() <= 0) {
//                this.errors.add("Looks Like subsidy amount is more than order total amount");
//            }
        }

        if (this.errors.isEmpty()) {
            return true;
        } else {
            //this.jlmsg.setText(this.msgListToString(this.errors));
            return false;
        }

    }
}
