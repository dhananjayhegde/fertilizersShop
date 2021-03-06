/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fertilizers;

import database.DatabaseConnection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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
public class PurchaseForm extends AbstractForm {

    private Vector<Object[]> purchaseItems;
    private String[] headers;
    private PurchaseModel purchaseOrder;
    private ALVDynamicTableModel alvModel;
    private long lastOrderId;

    public PurchaseForm(JFrame prev) {
        super(prev);
        initComponents();
        initialize();
        this.setLocationRelativeTo(null);
    }

    /**
     * Creates new form PurchaseForm
     */
    public PurchaseForm() {
        initComponents();
        initialize();
    }

    private void initializeItemsTable() {
        this.headers = this.getTableColumns();
        this.purchaseItems = new Vector<Object[]>();
        this.alvModel = new ALVDynamicTableModel(this.purchaseItems, this.headers);
        this.jtbitemdata.setModel(alvModel);
    }

    private String[] getTableColumns() {
        return new String[]{"Produ. ID", "Product Name", "Unit Price", "Quantity(in kg)", "Amount (in Rs.)", "Expiry Date"};
    }

    private void clearItemModelData() {
        this.alvModel.removeAllRows();
    }

    private void removeItems(int[] rows) {

        this.alvModel.removeSelectedRows(rows);
    }

    private void addItemToModel() {

        this.errors.clear();
        //Check qty if it is negative - START
        double qty;
        double subsidy;
        String dateText;
        java.util.Date expiryDate;
        
        try {
            qty = Integer.parseInt(this.jtfqty.getText());
            if (qty < 0) {
                this.errors.add("Quantity cannot be negative");
            }
        } catch (NumberFormatException ex) {
            this.errors.add("Please enter a whole number in quantity field");
        }
        
        try {
            subsidy = Double.parseDouble(this.jtfsubsidy.getText());
            if (subsidy < 0) {
                this.errors.add("Subsidy cannot be negative");
            }
        } catch (NumberFormatException ex) {
            this.errors.add("Please enter a whole number in subsidy field");
        }
        //Expiry Date validation
        dateText = this.jtexdate.getText();
        try {
            expiryDate = DateUtil.strinToDate(dateText);
            if(DateUtil.isInPast(expiryDate)){
                this.errors.add("You cannot buy already expired products");
            }
        } catch (ParseException ex) {
            this.errors.add("Please enter date in dd/mm/yyyy format");
        }
        
        if(this.errors.isEmpty()){
            Object[] row = this.getNewITemForModel();
            if (!this.errors.isEmpty()) {
                this.jlmsg.setText(this.msgListToString(this.errors));
            } else if (row == null) {
                this.jlmsg.setText("Enter All fields to add the item to order");
            } else {
                this.jlmsg.setText("");
                this.alvModel.appendRow(row);
            }
        } else {
            this.jlmsg.setText(this.msgListToString(this.errors));
        }
    }

    //Call this from action event of Add Item button
    private Object[] getNewITemForModel() {

        Object[] row = new Object[this.headers.length];

        if (!this.jtfsubsidy.getText().isEmpty() && !this.jtfqty.getText().isEmpty()) {
            row[0] = ((ProductModel) this.jcbproduct.getSelectedItem()).getId();
            row[1] = ((ProductModel) this.jcbproduct.getSelectedItem()).getName();
            row[2] = this.jtfprice.getText();
            row[3] = this.jtfqty.getText();
            row[4] = this.jtfamount.getText();
            row[5] = this.jtexdate.getText();
            return row;
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

    private ComboBoxModel getSupplierModel() {
        SupplierModel[] suppliers = null;
        ArrayList<SupplierModel> slist = new ArrayList();

        this.stmt = DatabaseConnection.getConnection().getStatement();

        this.query = "SELECT * FROM supplier";

        try {
            this.rs = this.stmt.executeQuery(this.query);
            while (this.rs.next()) {
                slist.add(new SupplierModel(this.rs.getLong("id"),
                        this.rs.getString("name"),
                        this.rs.getString("address"),
                        this.rs.getString("mobile"),
                        this.rs.getString("tin")
                ));

            }

            suppliers = new SupplierModel[slist.size()];
            Iterator it = slist.iterator();
            int i = 0;
            while (it.hasNext()) {
                suppliers[i++] = (SupplierModel) it.next();
            }

        } catch (SQLException ex) {
            this.jlmsg.setText("There was a problem getting supplier data from database");
        }

        return new DefaultComboBoxModel(suppliers);
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
                        this.rs.getDouble("price"),
                        this.rs.getDate("expiry_date")
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
        jlsupplier = new javax.swing.JLabel();
        jlsubsidy = new javax.swing.JLabel();
        jcbsupplier = new javax.swing.JComboBox<>();
        jtfsubsidy = new javax.swing.JTextField();
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
        jlexdate = new javax.swing.JLabel();
        jtexdate = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jlitems = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbitemdata = new javax.swing.JTable();
        jbtremoveitem = new javax.swing.JButton();
        jbtsaveorder = new javax.swing.JButton();
        jbtinvoice = new javax.swing.JButton();
        jlsuccessmsg = new javax.swing.JLabel();
        jbtback = new javax.swing.JButton();
        jblogout = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jlmsg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbanner.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbanner.setText("Create Purchase Entry");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlheader.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlheader.setText("Header");

        jlsupplier.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlsupplier.setText("Supplier : ");

        jlsubsidy.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlsubsidy.setText("Subsidy Amount : ");

        jcbsupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jtfsubsidy.setToolTipText("");

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

        jlexdate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlexdate.setText("Expiry Date : ");

        jtexdate.setToolTipText("Enter date in format dd/mm/yyyy");

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
                                .addComponent(jlsupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jcbsupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jlsubsidy, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jtfsubsidy, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jbtneworder, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jbtadditem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jbtclearitemdata, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlexdate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtexdate, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jlheader, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlsupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbsupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlsubsidy, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfsubsidy, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlitem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlexdate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtexdate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jbtsaveorder.setText("Place Order");
        jbtsaveorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtsaveorderActionPerformed(evt);
            }
        });

        jbtinvoice.setText("Print Invoice");
        jbtinvoice.setToolTipText("Print invoice last order");
        jbtinvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtinvoiceActionPerformed(evt);
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
                        .addComponent(jbtinvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
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
                    .addComponent(jbtsaveorder, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtinvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jbtback.setText("Back");
        jbtback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtbackActionPerformed(evt);
            }
        });

        jblogout.setText("Logout");
        jblogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jblogoutActionPerformed(evt);
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
                        .addComponent(jlsuccessmsg, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtback, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jblogout, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addComponent(jbtback, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jblogout, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
        this.refreshExpDate();
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

    private void jblogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jblogoutActionPerformed
        // TODO add your handling code here:
        this.dispose();
        (new LoginForm()).setVisible(true);
    }//GEN-LAST:event_jblogoutActionPerformed

    private void jbtinvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtinvoiceActionPerformed
        // TODO add your handling code here:
        this.prepareInvoice();
    }//GEN-LAST:event_jbtinvoiceActionPerformed

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
            java.util.logging.Logger.getLogger(PurchaseForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PurchaseForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PurchaseForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PurchaseForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PurchaseForm().setVisible(true);
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
    private javax.swing.JButton jblogout;
    private javax.swing.JButton jbtadditem;
    private javax.swing.JButton jbtback;
    private javax.swing.JButton jbtclearitemdata;
    private javax.swing.JButton jbtinvoice;
    private javax.swing.JButton jbtneworder;
    private javax.swing.JButton jbtremoveitem;
    private javax.swing.JButton jbtsaveorder;
    private javax.swing.JComboBox<String> jcbproduct;
    private javax.swing.JComboBox<String> jcbsupplier;
    private javax.swing.JLabel jlamount;
    private javax.swing.JLabel jlbanner;
    private javax.swing.JLabel jlexdate;
    private javax.swing.JLabel jlheader;
    private javax.swing.JLabel jlitem;
    private javax.swing.JLabel jlitems;
    private javax.swing.JLabel jlmsg;
    private javax.swing.JLabel jlprice;
    private javax.swing.JLabel jlproduct;
    private javax.swing.JLabel jlquantity;
    private javax.swing.JLabel jlsubsidy;
    private javax.swing.JLabel jlsuccessmsg;
    private javax.swing.JLabel jlsupplier;
    private javax.swing.JTable jtbitemdata;
    private javax.swing.JTextField jtexdate;
    private javax.swing.JTextField jtfamount;
    private javax.swing.JTextField jtfprice;
    private javax.swing.JTextField jtfqty;
    private javax.swing.JTextField jtfsubsidy;
    // End of variables declaration//GEN-END:variables

    private void initialize() {
        this.jcbproduct.setModel(this.getProductModel());
        this.jcbsupplier.setModel(this.getSupplierModel());
        this.jtfqty.setText("0");
        this.updatePrice();
        this.refreshExpDate();
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
        this.jcbsupplier.setSelectedIndex(0);
    }

    private void saveOrder() {
        //local variable declaration
        PurchaseItemsModel item;
        long orderId = -1;
        String pstmt = "";
        //initialize the messages
        this.errors.clear();
        this.success.clear();

        //check if there are any items added at all
        if (this.prepareOrderData()) {
            //first save the order header data into "purchase" tbale
            this.jlmsg.setText("Order prepared successfully");
            this.stmt = DatabaseConnection.getConnection().getStatement();

            this.query = "INSERT INTO purchase "
                    + "(supplierid, date, subtotal, total, subsidy) "
                    + "VALUES "
                    + "('" + this.purchaseOrder.getSupplierId() + "', '" + new java.sql.Date(this.purchaseOrder.getDate().getTime()) + "', "
                    + "'" + this.purchaseOrder.getSubtotal() + "', '" + this.purchaseOrder.getTotal() + "', "
                    + "'" + this.purchaseOrder.getSubsidy() + "')";

            try {
                this.rs.close(); //close the previous result set
                Integer numero = this.stmt.executeUpdate(this.query, Statement.RETURN_GENERATED_KEYS);
                this.rs = this.stmt.getGeneratedKeys();

                if (this.rs.next()) {
                    orderId = this.rs.getLong(1);
                    this.purchaseOrder.setId(orderId); //set the generated Order ID
                    Iterator itemIterator = this.purchaseOrder.getItems().iterator();

                    while (itemIterator.hasNext()) {
                        this.query = "";
                        this.rs.close();

                        item = (PurchaseItemsModel) itemIterator.next();

                        this.query = "INSERT INTO purchasedetails "
                                + "(id, itemno, productid, price, quantity, amount, expiry_date) "
                                + "VALUES "
                                + "('" + orderId + "', '" + item.getItemNo() + "', "
                                + "'" + item.getProductId() + "', '" + item.getPrice() + "', "
                                + "'" + item.getQuantity() + "', '" + item.getAmount() + "', "
                                + "'" + new java.sql.Date(item.getExpiryDate().getTime()) + "')";

                        this.stmt.executeUpdate(this.query);
                        //insert using prepared statement

                        //UPDATE PRODUCT STOCK - get the current stock and then add the purchase quantity and update
                        this.query = "SELECT stockqty "
                                + "FROM products "
                                + "WHERE id=" + item.getProductId();
                        this.rs.close();

                        this.rs = this.stmt.executeQuery(this.query);
                        if (this.rs.next()) {

                            long stockqty = this.rs.getLong("stockqty");
                            stockqty += item.getQuantity();

//                            this.query = "UPDATE products "
//                                    + "SET stockqty=" + stockqty
//                                    + " WHERE id=" + item.getProductId();
//                            this.stmt.executeUpdate(this.query);
                            
                            //using prepared statement to update the data into product table
                            pstmt = "UPDATE products SET stockqty=?, expiry_date=? where id=?";                            
                            this.pstmt = this.getPreparedStatement(pstmt);
                            this.pstmt.setLong(1, stockqty);
                            this.pstmt.setDate(2, new java.sql.Date(item.getExpiryDate().getTime()));
                            this.pstmt.setLong(3, item.getProductId());
                            
                            this.pstmt.executeUpdate();
                            
                        }
                    }

                    this.jlsuccessmsg.setText("Order saved successfully. Order ID : " + orderId);
                    //update lastOrderId
                    this.lastOrderId = orderId;
                    //clear Model and tableModel
                    this.clearItemModelData();
                    this.clearItemData();
                }

            } catch (SQLException ex) {
                Logger.getLogger(PurchaseForm.class.getName()).log(Level.SEVERE, null, ex);
                if (orderId > 0) {
                    //this means, order header saved but not item details.
                    //So, to maintain data consistency, we delete the order header/detials
                    this.query = "DELETE FROM purchase "
                            + "WHERE id=" + orderId;

                    try {
                        this.stmt.executeUpdate(this.query);
                        //if successfull, delete all partially saved item details
                        this.query = "DELETE FROM purchasedetails "
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

        Long supplierId;
        java.util.Date util_today;
        double subsidy = 0;
        java.util.Date expiryDate;

        PurchaseItemsModel purchaseItem;
        //initialize the messages
        this.errors.clear();
        this.success.clear();
        this.purchaseOrder = null; //initialize

        if (this.alvModel.getRowCount() <= 0) {
            this.jlmsg.setText("Add at least one item to order");
            return false;
        }

        if (this.jtfsubsidy.getText().isEmpty()) {
            this.errors.add("Enter a subsidy amount in Rs.");
        }

        //if we are here, then there may not be any errors   
        supplierId = ((SupplierModel) this.jcbsupplier.getSelectedItem()).getId();
        util_today = new java.util.Date();

        this.purchaseOrder = new PurchaseModel(supplierId, util_today);

        //get Subsisdy amount from the screen field
        try {
            subsidy = Double.parseDouble(this.jtfsubsidy.getText());
        } catch (NumberFormatException ex) {
            this.errors.add("Enter a decimal number for subsidy amount");
        }

        if (this.errors.isEmpty()) {
            this.purchaseOrder.setSubsidy(subsidy);
            //prepare item data

            for (int rowIndex = 0; rowIndex < this.alvModel.getRowCount(); rowIndex++) {

                purchaseItem = new PurchaseItemsModel();
                int colIndex = 0;
                //purchaseItem.setItemNo(itemNo++); addITem method takes care of this
                purchaseItem.setProductId((long) this.alvModel.getValueAt(rowIndex, colIndex++));
                colIndex++; //skip the product name column from the table model
                purchaseItem.setPrice(Double.parseDouble((String) this.alvModel.getValueAt(rowIndex, colIndex++)));
                purchaseItem.setQuantity(Integer.parseInt((String) this.alvModel.getValueAt(rowIndex, colIndex++)));
                purchaseItem.setAmount(Double.parseDouble((String) this.alvModel.getValueAt(rowIndex, colIndex++)));
                
                try {
                    //set the expiry date
                    expiryDate = DateUtil.strinToDate((String) this.alvModel.getValueAt(rowIndex, colIndex++));
                    if(DateUtil.isInPast(expiryDate)){
                        this.errors.add("Product on item  " + (rowIndex + 1) + " has already expired");
                    } else {
                        purchaseItem.setExpiryDate(expiryDate);
                    }
                } catch (ParseException ex) {
                    this.errors.add("Enter date in format dd/mm/yyyy for Item number " + (rowIndex+1));
                }
                //add the item to purchaseOrder.  Total and subtotal are calculated automatically
                this.purchaseOrder.addItem(purchaseItem);
            }

            if (this.purchaseOrder.getNumberOfItems() <= 0) {
                this.errors.add("No Items are added to order");
            }
            if (this.purchaseOrder.getTotal() <= 0) {
                this.errors.add("Looks Like subsidy amount is more than order total amount");
            }
        }

        //if any errors are raised, we return false and errors are displayed on
        //screen by saveOrder() method.  Don't clear 'errors' from here on.
        if (this.errors.isEmpty()) {
            return true;
        } else {
            //this.jlmsg.setText(this.msgListToString(this.errors));
            return false;
        }

    }
    
    private void refreshExpDate() {
        java.util.Date exp_date = ((ProductModel) this.jcbproduct.getSelectedItem()).getExpiryDate();
        if (exp_date == null) {
            this.jtexdate.setText("Not Entered");
        } else {
            try {
                this.jtexdate.setText(DateUtil.dateToString(exp_date));
            } catch (Exception ex) {
                this.jtexdate.setText("Not Entered");
            }
        }
    }

    private void prepareInvoice() {
        if (this.lastOrderId > 0) {
            PurchaseInvoicePdf si = new PurchaseInvoicePdf(this.lastOrderId);
            try {
                String filecreatedat = si.createPdf();
                this.jlmsg.setText("Invoice saved at : " + filecreatedat);
            } catch (Exception ex) {
                this.jlmsg.setText(ex.getMessage());
            }
        } else {
            this.jlmsg.setText("Create an order first");
        }
    }
}
