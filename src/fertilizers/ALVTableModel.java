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
    String[] header;
    Vector<Object[]> data = new Vector(); //each element is an array of Object === a row is an array of columns
    java.sql.ResultSet resultSet;
    
    public ALVTableModel(java.sql.ResultSet resultSet) throws SQLException{
        this.setData(resultSet);
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
                this.columns[i] = resultSet.getMetaData().getColumnName(i + 1);
            }
            
            //Temporary fix
            this.header = this.columns;
            
            Object[] row;
            while(this.resultSet.next()){
                row = new Object[this.columnCount];
                for(int colIndex = 0; colIndex < this.columnCount; colIndex++){
                    row[colIndex] = this.resultSet.getString(colIndex + 1);
                }
                this.data.addElement(row);
            }            
            //fire data changed event so that the tableView gets updated
            fireTableChanged(null);
        }
    }
    
    /**
     * Adds only if the length of the row array == number of columns
     * We restrict that all rows have same number of columns for simplicity
     * 
     * @param row 
     */
    public void appendRow(Object[] row){
        if(row.length == this.columnCount){
            this.data.addElement(row);
            fireTableChanged(null);
        }
    }
    
    public void removeSelectedRow(Object[] row) {
        this.data.remove(row);
        fireTableChanged(null);
    }
    
    /**
     * If you call this method inside a loop to remove all the selected rows,
     * you are bound to hit ArrayIndexOutOfBoundsException.
     * 
     * This is because, when the first selected row is removed, this.data gets rearranged.
     * This means, total count is reduced by 1 and indices are rearranged
     * accordingly.
     * 
     * Instead, to delete multiple rows, use, removeSelectedRows()
     * 
     * @param rowIndex
     * @throws IndexOutOfBoundsException 
     */
    public void removeSelectedRow(int rowIndex) throws IndexOutOfBoundsException{
        this.data.remove(rowIndex);
        fireTableChanged(null);
    }
    
    /**
     * Optimized for deleting multiple rows
     * 
     * @param selectedRows
     * @throws IndexOutOfBoundsException 
     */
    public void removeSelectedRows(int[] selectedRows) throws IndexOutOfBoundsException{
        
        //Collcted the rows to be removed, and then deleted them
        Object[] toRemove = new Object[selectedRows.length];
        for(int i = 0; i < selectedRows.length; i++){
            toRemove[i] = (Object[])this.data.get(selectedRows[i]);
        }
        
        for (Object row : toRemove) {
            this.data.remove((Object[]) row);
        }
        
        fireTableChanged(null);
    }
    
    public void removeRow(Object[] row){
        this.data.remove(row);
        fireTableChanged(null);
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
    
    @Override
    public String getColumnName(int index) throws ArrayIndexOutOfBoundsException {
        return this.header[index].toUpperCase();
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
    
    @Override
    public void setValueAt(Object value, int rowIndex, int colIndex) throws IndexOutOfBoundsException{
        (this.data.get(rowIndex))[colIndex] = value;
        fireTableChanged(null);
    }
}
