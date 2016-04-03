/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fertilizers;

import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author ProjectTeam
 */
public class ALVTableModel extends javax.swing.table.AbstractTableModel {

    int columnCount;
    int rowCount;
    String[] columns;
    Vector<Object[]> data; //each element is an array of Object === a row is an array of columns
    java.sql.ResultSet resultSet;
    
    public ALVTableModel(java.sql.ResultSet resultSet){
        
    }
    
    /**
     * This method refreshes the whole table data.  This means, it wipes out the
     * previously set data and sets new data
     * @param resultSet 
     */
    
    public void setData(java.sql.ResultSet resultSet) throws SQLException{
        
        if(resultSet != null) {
            this.resultSet = resultSet;
            
            this.columnCount = resultSet.getMetaData().getColumnCount(); //set Column count
            this.columns = new String[this.columnCount]; //column headers

            //initiate columns
            for (int i = 0; i < this.columnCount; i++) {
                this.columns[i] = resultSet.getMetaData().getColumnName(i);
            }
            
            Object[] row;
            while(this.resultSet.next()){
                row = new Object[this.columnCount];
                for(int colIndex = 0; colIndex < this.columnCount; colIndex++){
                    row[colIndex] = this.resultSet.getString(colIndex);
                }
                this.data.addElement(row);
            }            
            //fire data changed event so that the tableView gets updated
            fireTableChanged(null);
        }
    }
    
    public void appendRow(Object[] row){
        if(row.length == this.columnCount){
            this.data.addElement(row);
            fireTableChanged(null);
        }
    }
    
    /**
     * Returns the whole row
     * 
     * @param rowIndex
     * @return
     * @throws IndexOutOfBoundsException 
     */
    public Object[] getRowByIndex(int rowIndex) throws IndexOutOfBoundsException{
        return this.data.get(rowIndex);
    }
    /**
     * If index > this.columnCount then throws ArrayIndexOutOfBoundsException
     * 
     * @param index
     * @return
     * @throws ArrayIndexOutOfBoundsException 
     */
    public String getColumnAt(int index) throws ArrayIndexOutOfBoundsException{
        return this.columns[index];
    }
    
    public String[] getColumns(){
        return this.columns;
    }
    
    @Override
    public int getRowCount() {
        return this.data.size();
    }

    @Override
    public int getColumnCount() {
       return this.columnCount; 
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) throws IndexOutOfBoundsException{
        return (this.data.get(rowIndex))[columnIndex];
    }
}
