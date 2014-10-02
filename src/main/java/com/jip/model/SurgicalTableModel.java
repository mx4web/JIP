package com.jip.model;

import com.jip.data.SurgicalData;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yu Xiao
 */
public class SurgicalTableModel extends AbstractTableModel {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private ArrayList<SurgicalData> data = new ArrayList<>();
    private ArrayList<String> columnNames = new ArrayList<>();
    
    /**
     * Constructor
     */
    public SurgicalTableModel() {
        super();
        logger.info("SurgicalTableModel instantiated");
    }

   /**
     * Load the column names data structure from the database metadata
     *
     * @param rsmd
     * @return
     */
    public int loadColumnNames(ResultSetMetaData rsmd) {

        int colCount = 0;
        try {
            colCount = rsmd.getColumnCount();
            for (int i = 1; i <= colCount; ++i) {
                columnNames.add(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            logger.error("Error loading column names", e);
        }
        this.fireTableDataChanged();
        return colCount;
    }

    /**
     * Given a ResultSet load the model's data structure
     *
     * @param resultSet
     * @return
     */
    public int loadData(ResultSet resultSet) {
        int rowCount = 0;
        try {
            while (resultSet.next()) {
                rowCount++;

                SurgicalData sd = new SurgicalData();
                sd.setId(resultSet.getLong("ID"));
                sd.setPatientId(resultSet.getLong("PatientID"));
                sd.setDateOfSurgery(resultSet.getTimestamp("DateOfSurgery"));
                sd.setProcedure(resultSet.getString("SurgicalProcedure"));
                sd.setRoomFee(resultSet.getBigDecimal("RoomFee"));
                sd.setSurgeonFee(resultSet.getBigDecimal("SurgeonFee"));
                sd.setSupplies(resultSet.getBigDecimal("Supplies"));                

                data.add(sd);
            }
        } catch (SQLException e) {
            logger.error("Error loading data", e);
        }

        this.fireTableDataChanged();
        return rowCount;
    }

     /**
     * Determine is a row needs to be updated in the database
     *
     * @param row
     * @return
     */
    public boolean getUpdateStatus(int row) {
        return data.get(row).isUpdate();
    }
    
     /**
     * Set the update value in the bean to false
     *
     * @param row
     */
    public void clearUpdate(int row) {
        data.get(row).setUpdate(false);
    }
    
    /**
     * Return the bean from a specific row
     *
     * @param row
     * @return
     */
    public SurgicalData getSurgicalData(int row) {
        return data.get(row);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return data.get(row).getId();
            case 1:
                return data.get(row).getPatientId();
            case 2:
                return data.get(row).getDateOfSurgery();
            case 3:
                return data.get(row).getProcedure();
            case 4:
                return data.get(row).getRoomFee();
            case 5:
               return data.get(row).getSurgeonFee(); 
            case 6:
                return data.get(row).getSupplies();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<? extends Object> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        boolean retVal = true;
        // hard coded to block changes to the first column
        if (col == 0) {
            retVal = false;
        }
        return retVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
     * int, int)
     */
    @Override
    public void setValueAt(Object value, int row, int col) {

        switch (col) {
            case 0:
                data.get(row).setId((Long) value);
                break;
            case 1:
                data.get(row).setPatientId((Long) value);
                break;
            case 2:
                data.get(row).setDateOfSurgery((Timestamp) value);
                break;
            case 3:
                data.get(row).setProcedure((String) value);
                break;
            case 4:
                data.get(row).setRoomFee((BigDecimal) value);
                break;
            case 5:
                data.get(row).setSurgeonFee((BigDecimal) value);
                break;
            case 6:
                data.get(row).setSupplies((BigDecimal) value);
                break;
        }

        // Let the JTable know a change has occurred
        //this.fireTableDataChanged();
        this.fireTableCellUpdated(row, col);
         // Set the row for update
        data.get(row).setUpdate(true);
    }

    /**
     * Remove a selected row from the data structure and then tell the table
     * that the data has changed
     *
     * @param selectedRow
     */
    public void deleteRow(int selectedRow) {
        data.remove(selectedRow);
        this.fireTableDataChanged();
    }
}