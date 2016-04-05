/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fertilizers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This is not really an abstract class.  Merely a super class.  This can be
 * used to create any other forms
 *
 * @author ProjectTeam
 */
public class AbstractForm extends javax.swing.JFrame {

    private JFrame previous = null;
    protected ArrayList errors = new ArrayList();
    protected ArrayList success = new ArrayList();
    
    protected Statement stmt;
    protected ResultSet rs;
    protected String query;
    
    public AbstractForm(JFrame previous) {
        try {
            //My added code - starts here
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            //look and feel could not be set.  What can we do?
        }
        this.previous = previous;        
        initComponents();
        this.setResizable(false);
    }
    /**
     * Creates new form AbstractForm
     */
    public AbstractForm() {
        initComponents();
    }
    
    private java.awt.Dimension getFullScreenMode(){
        Dimension dim =  Toolkit.getDefaultToolkit().getScreenSize();        
        dim.setSize(dim.getWidth(), dim.getHeight() - 50);
        return dim;
    }
    
    public void goToPrevious(){
        
        if(this.previous != null && this.previous instanceof JFrame){
            
            this.setVisible(false);
            this.previous.setVisible(true);
        }
    }
    
    public void logout(){
        System.out.println("Successfully Logged out");
    }
    
    public String msgListToString(java.util.List<String> msgList){
        String message = "";
        Iterator it;
        if(!msgList.isEmpty()){
            it = msgList.iterator();        
            while(it.hasNext()){
                if(!message.isEmpty()){
                    message += "; " + (String) it.next();
                } else {
                    message += (String) it.next();
                }                 
            }
        }
        return message;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setPreferredSize(this.getFullScreenMode());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(AbstractForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AbstractForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AbstractForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AbstractForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AbstractForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
