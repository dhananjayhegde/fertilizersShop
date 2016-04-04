/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fertilizers;

import java.util.Vector;

/**
 *
 * @author ProjectTeam
 */
public class ALVDynamicTableModel extends javax.swing.table.AbstractTableModel{
//    private int columnCount;
    private int rowCount;
    private String[] header;
    private Vector<Object[]> data = new Vector(); //each element is an array of Object === a row is an array of columns

    
    public ALVDynamicTableModel(Vector<Object[]> dataSet, String[] header) {
        this.setData(dataSet, header);
    }

    /**
     * This method refreshes the whole table data. This means, it wipes out the
     * previously set data and sets new data
     *
     * @param header
     * @param dataSet
     */
    public void setData(Vector<Object[]> dataSet, String[] header) {

        if (dataSet != null && header != null && dataSet.size() > 0 && dataSet.get(0).length == header.length) {
            this.data = dataSet;

            this.header = header; //column headers
//            this.columnCount = header.length; //set Column count
            fireTableChanged(null);
        } else if(header != null && header.length > 0){
            this.header = header;
        }else {
            //initiate empty model
            this.header = new String[0];
            this.data.addElement(new Object[0]);
        }
    }

    public void setColumns(String[] headers){
        this.header = headers;
    }
    
    /**
     * Adds only if the length of the row array == number of columns We restrict
     * that all rows have same number of columns for simplicity
     *
     * @param row
     */
    public void appendRow(Object[] row) {
        if (row.length == this.header.length) {
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
     * This is because, when the first selected row is removed, this.data gets
     * rearranged. This means, total count is reduced by 1 and indices are
     * rearranged accordingly.
     *
     * Instead, to delete multiple rows, use, removeSelectedRows()
     *
     * @param rowIndex
     * @throws IndexOutOfBoundsException
     */
    public void removeSelectedRow(int rowIndex) throws IndexOutOfBoundsException {
        this.data.remove(rowIndex);
        fireTableChanged(null);
    }

    /**
     * Optimized for deleting multiple rows
     *
     * @param selectedRows
     * @throws IndexOutOfBoundsException
     */
    public void removeSelectedRows(int[] selectedRows) throws IndexOutOfBoundsException {

        //Collcted the rows to be removed, and then deleted them
        Object[] toRemove = new Object[selectedRows.length];
        for (int i = 0; i < selectedRows.length; i++) {
            toRemove[i] = (Object[]) this.data.get(selectedRows[i]);
        }

        for (Object row : toRemove) {
            this.data.remove((Object[]) row);
        }

        fireTableChanged(null);
    }

    public void removeRow(Object[] row) {
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
    public Object[] getRowByIndex(int rowIndex) throws IndexOutOfBoundsException {
        return this.data.get(rowIndex);
    }

    /**
     * If index > this.columnCount then throws ArrayIndexOutOfBoundsException
     *
     * @param index
     * @return
     * @throws ArrayIndexOutOfBoundsException
     */
    public String getColumnAt(int index) throws ArrayIndexOutOfBoundsException {
        return this.header[index];
    }

    @Override
    public String getColumnName(int index) throws ArrayIndexOutOfBoundsException {
        return this.header[index];
    }

    public String[] getColumns() {
        return this.header;
    }

    @Override
    public int getRowCount() {
        return this.data.size();
    }

    @Override
    public int getColumnCount() {
        return this.header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) throws IndexOutOfBoundsException {
        return (this.data.get(rowIndex))[columnIndex];
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int colIndex) throws IndexOutOfBoundsException {
        (this.data.get(rowIndex))[colIndex] = value;
        fireTableChanged(null);
    }
}
