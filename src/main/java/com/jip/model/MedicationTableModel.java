package com.jip.model;

import com.jip.data.MedicationData;
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
public class MedicationTableModel extends AbstractTableModel {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private ArrayList<MedicationData> data = new ArrayList<>();
    private ArrayList<String> columnNames = new ArrayList<>();
    
    /**
     * Constructor
     */
    public MedicationTableModel() {
        super();
        logger.info("MedicationTableModel instantiated");
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

                MedicationData md = new MedicationData();
                md.setId(resultSet.getLong("ID"));
                md.setPatientId(resultSet.getLong("PatientID"));
                md.setDateOfMed(resultSet.getTimestamp("DateOfMed"));
                md.setMed(resultSet.getString("Med"));
                md.setUnitCost(resultSet.getBigDecimal("UnitCost"));
                md.setUnits(resultSet.getDouble("Units"));
              
                data.add(md);
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
    public MedicationData getMedicationData(int row) {
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
                return data.get(row).getDateOfMed();
            case 3:
                return data.get(row).getMed();
            case 4:
                return data.get(row).getUnitCost();
            case 5:
               return data.get(row).getUnits(); 
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
                data.get(row).setDateOfMed((Timestamp) value);
                break;
            case 3:
                data.get(row).setMed((String) value);
                break;
            case 4:
                data.get(row).setUnitCost((BigDecimal) value);
                break;
            case 5:
                data.get(row).setUnits((double) value);
                break;
        }

        // Let the JTable know a change has occurred
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
