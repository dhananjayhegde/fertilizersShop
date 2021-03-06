/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fertilizers;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author ProjectTeam
 */
public class ShopMainForm extends AbstractForm {

    public ShopMainForm(JFrame prev) {
        super(prev);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * Creates new form ShopMainForm
     */
    public ShopMainForm() {
        initComponents();
    }

    @Override
    protected java.awt.Dimension getFullScreenMode() {
        Dimension dim = new Dimension(680, 620);
        //dim.setSize(dim.getWidth() - 200, dim.getHeight() - 200);
        return dim;
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
        jlmasterdata = new javax.swing.JLabel();
        jbtcreatefarmer = new javax.swing.JButton();
        jbtdisplayfarmer = new javax.swing.JButton();
        jbtcreatesupp = new javax.swing.JButton();
        jbtdisplaysupp = new javax.swing.JButton();
        jbtcreateproducts = new javax.swing.JButton();
        jbtdisplayproducts = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jlsales = new javax.swing.JLabel();
        jbtsell = new javax.swing.JButton();
        jbtsalesreport = new javax.swing.JButton();
        jbtfpayments = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jlpurchase = new javax.swing.JLabel();
        jbtpurchase = new javax.swing.JButton();
        jbtpurchasereport = new javax.swing.JButton();
        jbtlogout = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jlmsg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(getFullScreenMode());
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbanner.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbanner.setText("Main Page");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlmasterdata.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlmasterdata.setText("Master Data");

        jbtcreatefarmer.setText("Create Farmer");
        jbtcreatefarmer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtcreatefarmerActionPerformed(evt);
            }
        });

        jbtdisplayfarmer.setText("Display Farmer Accounts");
        jbtdisplayfarmer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtdisplayfarmerActionPerformed(evt);
            }
        });

        jbtcreatesupp.setText("Create Supplier");
        jbtcreatesupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtcreatesuppActionPerformed(evt);
            }
        });

        jbtdisplaysupp.setText("Supplier Report");
        jbtdisplaysupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtdisplaysuppActionPerformed(evt);
            }
        });

        jbtcreateproducts.setText("Create Products");
        jbtcreateproducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtcreateproductsActionPerformed(evt);
            }
        });

        jbtdisplayproducts.setText("Products Stock");
        jbtdisplayproducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtdisplayproductsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlmasterdata, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtcreatefarmer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(36, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jbtdisplayfarmer, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtcreatesupp, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtdisplaysupp, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtcreateproducts, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtdisplayproducts, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlmasterdata, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtcreatefarmer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtdisplayfarmer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtcreatesupp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtdisplaysupp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtcreateproducts, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtdisplayproducts, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlsales.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlsales.setText("Sales Area");

        jbtsell.setText("Sell");
        jbtsell.setToolTipText("");
        jbtsell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtsellActionPerformed(evt);
            }
        });

        jbtsalesreport.setText("Sales Report");
        jbtsalesreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtsalesreportActionPerformed(evt);
            }
        });

        jbtfpayments.setText("Payments Entry");
        jbtfpayments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtfpaymentsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlsales, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtsell, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtsalesreport, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtfpayments, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlsales, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jbtsell, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtsalesreport, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtfpayments, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlpurchase.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlpurchase.setText("Purchase Area");

        jbtpurchase.setText("Purchase");
        jbtpurchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtpurchaseActionPerformed(evt);
            }
        });

        jbtpurchasereport.setText("Purchase Report");
        jbtpurchasereport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtpurchasereportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlpurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtpurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtpurchasereport, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlpurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtpurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtpurchasereport, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
                    .addComponent(jlbanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbtlogout, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbanner, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtlogout, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlmsg, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlmsg, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        System.out.println("W : " + this.getSize().width + ", H : " + this.getSize().height);
    }//GEN-LAST:event_formComponentResized

    private void jbtlogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtlogoutActionPerformed
        // TODO add your handling code here:
        this.goToPrevious();
    }//GEN-LAST:event_jbtlogoutActionPerformed

    private void jbtcreatefarmerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtcreatefarmerActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        (new FarmerForm(this)).setVisible(true);
    }//GEN-LAST:event_jbtcreatefarmerActionPerformed

    private void jbtcreatesuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtcreatesuppActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        (new SupplierForm(this)).setVisible(true);
    }//GEN-LAST:event_jbtcreatesuppActionPerformed

    private void jbtdisplayfarmerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtdisplayfarmerActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        (new FarmerAccountsForm(this)).setVisible(true);
    }//GEN-LAST:event_jbtdisplayfarmerActionPerformed

    private void jbtcreateproductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtcreateproductsActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        (new ProductsForm(this)).setVisible(true);
    }//GEN-LAST:event_jbtcreateproductsActionPerformed

    private void jbtpurchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtpurchaseActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        (new PurchaseForm(this)).setVisible(true);
    }//GEN-LAST:event_jbtpurchaseActionPerformed

    private void jbtsellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtsellActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        (new SalesForm(this)).setVisible(true);
    }//GEN-LAST:event_jbtsellActionPerformed

    private void jbtdisplaysuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtdisplaysuppActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        (new SupplierReport(this)).setVisible(true);
    }//GEN-LAST:event_jbtdisplaysuppActionPerformed

    private void jbtdisplayproductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtdisplayproductsActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        (new ProductReport(this)).setVisible(true);
    }//GEN-LAST:event_jbtdisplayproductsActionPerformed

    private void jbtsalesreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtsalesreportActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        (new SalesReport(this)).setVisible(true);
    }//GEN-LAST:event_jbtsalesreportActionPerformed

    private void jbtpurchasereportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtpurchasereportActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        (new PurchaseReport(this)).setVisible(true);
    }//GEN-LAST:event_jbtpurchasereportActionPerformed

    private void jbtfpaymentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtfpaymentsActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        (new PaymentForm(this)).setVisible(true);
    }//GEN-LAST:event_jbtfpaymentsActionPerformed

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
            java.util.logging.Logger.getLogger(ShopMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShopMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShopMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShopMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShopMainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JButton jbtcreatefarmer;
    private javax.swing.JButton jbtcreateproducts;
    private javax.swing.JButton jbtcreatesupp;
    private javax.swing.JButton jbtdisplayfarmer;
    private javax.swing.JButton jbtdisplayproducts;
    private javax.swing.JButton jbtdisplaysupp;
    private javax.swing.JButton jbtfpayments;
    private javax.swing.JButton jbtlogout;
    private javax.swing.JButton jbtpurchase;
    private javax.swing.JButton jbtpurchasereport;
    private javax.swing.JButton jbtsalesreport;
    private javax.swing.JButton jbtsell;
    private javax.swing.JLabel jlbanner;
    private javax.swing.JLabel jlmasterdata;
    private javax.swing.JLabel jlmsg;
    private javax.swing.JLabel jlpurchase;
    private javax.swing.JLabel jlsales;
    // End of variables declaration//GEN-END:variables
}
